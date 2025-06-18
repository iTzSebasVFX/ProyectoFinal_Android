import express, { Request, Response } from 'express';
import { registrarUsuario, accesoUsuario, nuevaReceta, buscarRecetas, buscarRecetasByID, actualizarReceta, deleteReceta } from './asyncUserService';

const app = express();
const PORT = 3000;

app.use(express.json());

// Usuarios

app.post('/registrar', async (req: Request, res: Response) => {
    try {
        const { nombre, correo, contraseña } = req.body;
        if (!nombre || !correo || !contraseña) {
            return res.status(400).send('Nombre, correo y contraseña son requeridos');
        }
        const registrado = await registrarUsuario(nombre, correo, contraseña);
        res.status(201).json(registrado);
    } catch {
        res.status(404).json({ error: "No se pudo registrar el usuario." });
    }
});

app.get('/login', async (req: Request, res: Response) => {
    try {
        const { correo, contraseña } = req.body;
        if (!correo || !contraseña) {
            return res.status(400).send('Correo y contraseña son requeridos');
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
            return res.status(400).send('Todos los datos son requeridos');
        }
        const agregar = await nuevaReceta(nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id_usuario);
        res.status(201).json(agregar);
    } catch {
        res.status(404).json({ error: "No se pudo agregar la receta." });
    }
});

app.get('/recetas/obtener/:id_usuario', async (req: Request, res: Response) => {
    try {
        const { id_usuario } = req.params;
        if (!id_usuario) {
            return res.status(400).send('Todos los datos son requeridos');
        }
        const obtenerPorCliente = await buscarRecetas(Number(id_usuario));
        res.status(200).json(obtenerPorCliente);
    } catch (error: any) {
        res.status(401).json({ error: error.message });
    }
});

app.put('/recetas/actualizar', async (req: Request, res: Response) => {
    const { id, nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad } = req.body;
    if (!id || !nombre || !ingredientes || !instrucciones || !tiempo_preparacion || !porciones || !dificultad) {
        return res.status(400).send('Datos requeridos');
    }
    const respuesta: any = await actualizarReceta(id, nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad);
    if (respuesta.affectedRows > 0) {
        const updated = await buscarRecetasByID(id);
        res.json(updated);
    } else {
        res.status(404).json({ error: "No se encontró la receta para actualizar o no hubo cambios." });
    }
});

app.delete('/recetas/eliminar/:id', async (req: Request, res: Response) => {
    const { id } = req.params;
    if (!id) {
        return res.status(400).send('Datos requeridos');
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