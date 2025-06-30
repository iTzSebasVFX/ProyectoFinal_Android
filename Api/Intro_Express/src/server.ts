import express, { Request, Response } from 'express';
import { registrarUsuario, accesoUsuario, nuevaReceta, buscarRecetas, buscarRecetasByID, deleteReceta } from './asyncUserService';
import { pool } from "./db";

const app = express();
const PORT = 3000;

app.use(express.json());

// Usuarios

app.post('/registrar', async (req: Request, res: Response) => {
    try {
        const { nombre, correo, contraseña } = req.body;
        if (!nombre || !correo || !contraseña) {
            return res.status(400).json({ error: "Nombre, correo y contraseña son requeridos" });
        }
        const existe = await pool.query("SELECT * FROM usuarios WHERE correo = ?", [correo]);
        if (existe.length > 0) {
            return res.status(409).json({ error: "Correo ya registrado" });
        }
        const registrado = await registrarUsuario(nombre, correo, contraseña);
        if (registrado.affectedRows > 0) {
            res.status(201).json({ exito: "Usuario registrado exitosamente en la nube" });
        } else {
            res.status(500).json({ error: "No se pudo registrar el usuario" });
        }
    } catch {
        res.status(500).json({ error: "No se pudo conectar a la bd" });
    }
});

app.post('/login', async (req: Request, res: Response) => {
    try {
        const { correo, contraseña } = req.body;
        if (!correo || !contraseña) {
            return res.status(400).json({ error: "Correo y contraseña son requeridos" });
        }
        const logueado = await accesoUsuario(correo, contraseña);
        res.json(logueado);
    } catch (error: any) {
        res.status(401).json({ error: error.message });
    }
});

// Recetas

app.post('/recetas/agregar', async (req: Request, res: Response) => {
    try {
        const { nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id_usuario } = req.body;
        if (!nombre || !ingredientes || !instrucciones || !tiempo_preparacion || !porciones || !dificultad || !id_usuario) {
            return res.status(400).json({ error: "Todos los datos son requeridos" });
        }
        const agregar = await nuevaReceta(nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id_usuario);
        if (agregar.affectedRows > 0) {
            res.status(201).json({ exito: "Se ha agregado correctamente una nueva receta" });
        } else {
            res.status(404).json({ error: "No se pudo insertar la receta" })
        }
    } catch {
        res.status(500).json({ error: "No se pudo conectar a la bd" });
    }
});

app.get('/recetas/obtener/:id_usuario', async (req: Request, res: Response) => {
    try {
        const { id_usuario } = req.params;
        if (!id_usuario) {
            return res.status(400).json({ error: "Todos lo datos son requeridos" });
        }
        const obtenerPorCliente = await buscarRecetas(Number(id_usuario));
        res.status(200).json(obtenerPorCliente);
    } catch (error: any) {
        res.status(500).json({ error: error.message });
    }
});

app.get('/recetas/:id', async (req: Request, res: Response) => {
    try{
        const { id } = req.params;
        if (!id) {
            return res.status(400).json({ error: "Se requiere el id" });
        }
        const obtenerPorId = await buscarRecetasByID(Number(id));
        if (obtenerPorId != null) {
            res.status(201).json(obtenerPorId);
        }
    } catch (error: any) {
        res.status(500).json({ error: error.message })
    }
});

app.put('/recetas/actualizar', async (req: Request, res: Response) => {
    const { id, nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad } = req.body;

    if (!id) return res.status(400).json({ error: "El ID es requerido" });

    // Construir actualización dinámica
    const campos = [];
    const valores = [];

    if (nombre) {
        campos.push("nombre = ?");
        valores.push(nombre);
    }
    if (ingredientes) {
        campos.push("ingredientes = ?");
        valores.push(ingredientes);
    }
    if (instrucciones) {
        campos.push("instrucciones = ?");
        valores.push(instrucciones);
    }
    if (tiempo_preparacion) {
        campos.push("tiempo_preparacion = ?");
        valores.push(tiempo_preparacion);
    }
    if (porciones) {
        campos.push("porciones = ?");
        valores.push(porciones);
    }
    if (dificultad) {
        campos.push("dificultad = ?");
        valores.push(dificultad);
    }

    if (campos.length === 0) {
        return res.status(400).json({ error: "No se enviaron campos para actualizar" });
    }

    valores.push(id); // ID al final

    const sql = `UPDATE recetas SET ${campos.join(', ')} WHERE id = ?`;

    const [respuesta]: any = await pool.query(sql, valores);

    if (respuesta.affectedRows > 0) {
        res.status(201).json({ exito: "La receta fue actualizada correctamente" });
    } else {
        res.status(404).json({ error: "No se encontró la receta para actualizar" });
    }
});

app.delete('/recetas/eliminar/:id', async (req: Request, res: Response) => {
    const { id } = req.params;
    if (!id) {
        return res.status(400).json({ error: "Es requerido el id" });
    }
    const respuesta: any = await deleteReceta(Number(id));
    if (respuesta.affectedRows > 0) {
        res.status(201).json({ exito: "Se ha eliminado correctamente la receta" });
    } else {
        res.status(404).json({ error: "No se encontró la receta para actualizar o no hubo cambios." });
    }
});

app.listen(PORT, () => {
    console.log(`Servidor escuchando en  http://localhost:${PORT}`);
});