"use strict";
// ------------------MANEJO DE DATOS CON JSON Y ASYNC/AWAIT------------------------
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.registrarUsuario = registrarUsuario;
exports.accesoUsuario = accesoUsuario;
exports.nuevaReceta = nuevaReceta;
exports.buscarRecetas = buscarRecetas;
exports.buscarRecetasByID = buscarRecetasByID;
exports.actualizarReceta = actualizarReceta;
exports.deleteReceta = deleteReceta;
// import fs from 'fs/promises';
// import { v4 as uuidv4 } from 'uuid';
// export interface User {
//     id: string;
//     name: string;
//     age: number;
// }
// const FILE_PATH = './src/users.json';
// async function writeUsers(users: User[]): Promise<void> {
//     await fs.writeFile(FILE_PATH, JSON.stringify(users, null, 2));
// }
// export async function createUser(name: string, age: number): Promise<User> {
//     const users = await readUsers();
//     const newUser: User = {id: uuidv4(), name , age }; 
//     users.push(newUser);
//     await writeUsers(users);
//     return newUser;
// }
// export async function listUsers(): Promise<User[]> {
//     return await readUsers();
// }
// export async function getUserById(id: string): Promise<User | undefined> {
//     const users = await readUsers();
//     return users.find(user => user.id === id);
// }
// export async function updateUserName(id: string, newName: string): Promise<void> {
//     const users = await readUsers();
//     const user = users.find(u => u.id === id);
//     if (user) {
//         user.name = newName;
//         await writeUsers(users);
//     }
// }
// async function readUsers(): Promise<User[]> {
//     try {
//         const data = await fs.readFile(FILE_PATH, 'utf-8');
//         return JSON.parse(data);
//     } catch{
//         return [];
//     }
// }
//-------------------------------------------------------------------------------
// ------------------- MENEJO DE DATOS CON BASE DE DATOS ------------------------
// npm install mysql2
const db_1 = require("./db");
// Usuarios
function registrarUsuario(nombre, correo, contraseña) {
    return __awaiter(this, void 0, void 0, function* () {
        const [resultados] = yield db_1.pool.query('INSERT INTO usuarios (nombre, correo, contraseña) VALUES (?, ?, ?)', [nombre, correo, contraseña]);
        const id = resultados.insertId;
        return { id, nombre, correo, contraseña };
    });
}
function accesoUsuario(correo, contraseña) {
    return __awaiter(this, void 0, void 0, function* () {
        const [buscar] = yield db_1.pool.query('SELECT * FROM `usuarios` WHERE correo = ? AND contraseña = ?', [correo, contraseña]);
        if (buscar.length === 0) {
            throw new Error("Usuario no encontrado");
        }
        ;
        return buscar[0];
    });
}
// Recetas
function nuevaReceta(nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id_usuario) {
    return __awaiter(this, void 0, void 0, function* () {
        const [resultados] = yield db_1.pool.query('INSERT INTO `recetas`(`nombre`, `ingredientes`, `instrucciones`, `tiempo_preparacion`, `porciones`, `dificultad`, `id_usuario`) VALUES (?, ?, ?, ?, ?, ?, ?)', [nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id_usuario]);
        const id = resultados.insertId;
        return { id, nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id_usuario };
    });
}
function buscarRecetas(id_usuario) {
    return __awaiter(this, void 0, void 0, function* () {
        const [resultados] = yield db_1.pool.query('SELECT * FROM recetas AS r JOIN usuarios AS u ON u.id = r.id_usuario WHERE id_usuario = ?;', [id_usuario]);
        if (resultados.length === 0)
            throw new Error("Recetas no encontradas");
        return resultados;
    });
}
function buscarRecetasByID(id) {
    return __awaiter(this, void 0, void 0, function* () {
        const [recetas] = yield db_1.pool.query('SELECT * FROM recetas WHERE id = ?', [id]);
        return recetas;
    });
}
function actualizarReceta(id, nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad) {
    return __awaiter(this, void 0, void 0, function* () {
        const [resultados] = yield db_1.pool.query('UPDATE `recetas` SET `nombre`=?,`ingredientes`=?,`instrucciones`=?,`tiempo_preparacion`=?,`porciones`=?,`dificultad`=? WHERE id = ?', [nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id]);
        return resultados;
    });
}
function deleteReceta(id) {
    return __awaiter(this, void 0, void 0, function* () {
        const [resultados] = yield db_1.pool.query('DELETE FROM recetas WHERE id = ?', [id]);
        return resultados;
    });
}
