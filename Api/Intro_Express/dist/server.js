"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const asyncUserService_1 = require("./asyncUserService");
const app = (0, express_1.default)();
const PORT = 3000;
app.use(express_1.default.json());
// Usuarios
app.post('/registrar', (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const { nombre, correo, contraseña } = req.body;
        if (!nombre || !correo || !contraseña) {
            return res.status(400).send('Nombre, correo y contraseña son requeridos');
        }
        const registrado = yield (0, asyncUserService_1.registrarUsuario)(nombre, correo, contraseña);
        res.status(201).json(registrado);
    }
    catch (_a) {
        res.status(404).json({ error: "No se pudo registrar el usuario." });
    }
}));
app.get('/login', (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const { correo, contraseña } = req.body;
        if (!correo || !contraseña) {
            return res.status(400).send('Correo y contraseña son requeridos');
        }
        const logueado = yield (0, asyncUserService_1.accesoUsuario)(correo, contraseña);
        res.json(logueado);
    }
    catch (error) {
        res.status(401).json({ error: error.message });
    }
}));
// Recetas
app.post('/recetas/agregar', (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const { nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id_usuario } = req.body;
        if (!nombre || !ingredientes || !instrucciones || !tiempo_preparacion || !porciones || !dificultad || !id_usuario) {
            return res.status(400).send('Todos los datos son requeridos');
        }
        const agregar = yield (0, asyncUserService_1.nuevaReceta)(nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id_usuario);
        res.status(201).json(agregar);
    }
    catch (_a) {
        res.status(404).json({ error: "No se pudo agregar la receta." });
    }
}));
app.get('/recetas/obtener/:id_usuario', (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const { id_usuario } = req.params;
        if (!id_usuario) {
            return res.status(400).send('Todos los datos son requeridos');
        }
        const obtenerPorCliente = yield (0, asyncUserService_1.buscarRecetas)(Number(id_usuario));
        res.status(200).json(obtenerPorCliente);
    }
    catch (error) {
        res.status(401).json({ error: error.message });
    }
}));
app.put('/recetas/actualizar', (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { id, nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad } = req.body;
    if (!id || !nombre || !ingredientes || !instrucciones || !tiempo_preparacion || !porciones || !dificultad) {
        return res.status(400).send('Datos requeridos');
    }
    const respuesta = yield (0, asyncUserService_1.actualizarReceta)(id, nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad);
    if (respuesta.affectedRows > 0) {
        const updated = yield (0, asyncUserService_1.buscarRecetasByID)(id);
        res.json(updated);
    }
    else {
        res.status(404).json({ error: "No se encontró la receta para actualizar o no hubo cambios." });
    }
}));
app.delete('/recetas/eliminar/:id', (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { id } = req.params;
    if (!id) {
        return res.status(400).send('Datos requeridos');
    }
    const respuesta = yield (0, asyncUserService_1.deleteReceta)(Number(id));
    if (respuesta.affectedRows > 0) {
        res.status(201).json({ exito: "Se ha eliminado correctamente la receta" });
    }
    else {
        res.status(404).json({ error: "No se encontró la receta para actualizar o no hubo cambios." });
    }
}));
app.listen(PORT, () => {
    console.log(`Servidor escuchando en  http://localhost:${PORT}`);
});
