
// ------------------MANEJO DE DATOS CON JSON Y ASYNC/AWAIT------------------------

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

import mysql from 'mysql2/promise';

export interface User {
    id: string;
    nombre: string;
    correo: string;
    contraseña: string;
}

export interface Recipes {
    id: number;
    nombre: string;
    ingredientes: string;
    instrucciones: string;
    tiempo_preparacion: string;
    porciones: string;
    dificultad: string;
    id_usuario: number;
}

const pool = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'proyectofinal_recetas'
});

// Usuarios

export async function registrarUsuario(nombre: string, correo: string, contraseña: string): Promise<User> {
    const [resultados]: any = await pool.query(
        'INSERT INTO usuarios (nombre, correo, contraseña) VALUES (?, ?, ?)',
        [nombre, correo, contraseña]
    );
    const id = resultados.insertId;
    return { id, nombre, correo, contraseña };
}

export async function accesoUsuario(correo: string, contraseña: string): Promise<User> {
    const [buscar]: any = await pool.query(
        'SELECT * FROM `usuarios` WHERE correo = ? AND contraseña = ?', [correo, contraseña]
    );
    if (buscar.length === 0) { throw new Error("Usuario no encontrado") };
    return buscar[0];
}

// Recetas

export async function nuevaReceta(
    nombre: string,
    ingredientes: string,
    instrucciones: string,
    tiempo_preparacion: string,
    porciones: string,
    dificultad: string,
    id_usuario: number,
): Promise<Recipes> {
    const [resultados]: any = await pool.query(
        'INSERT INTO `recetas`(`nombre`, `ingredientes`, `instrucciones`, `tiempo_preparacion`, `porciones`, `dificultad`, `id_usuario`) VALUES (?, ?, ?, ?, ?, ?, ?)',
        [nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id_usuario]
    );
    const id = resultados.insertId;
    return { id, nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id_usuario };
}

export async function buscarRecetas(id_usuario: number) {
    const [resultados]: any = await pool.query(
        'SELECT * FROM recetas AS r JOIN usuarios AS u ON u.id = r.id_usuario WHERE id_usuario = ?;',
        [id_usuario]
    );
    if (resultados.length === 0) throw new Error("Recetas no encontradas");
    return resultados;
}

export async function buscarRecetasByID(id: number) {
    const [recetas]: any = await pool.query(
        'SELECT * FROM recetas WHERE id = ?',
        [id]
    );
    return recetas;
}

export async function actualizarReceta(
    id: number,
    nombre: string,
    ingredientes: string,
    instrucciones: string,
    tiempo_preparacion: string,
    porciones: string,
    dificultad: string
): Promise<any> {
    const [resultados]: any = await pool.query(
        'UPDATE `recetas` SET `nombre`=?,`ingredientes`=?,`instrucciones`=?,`tiempo_preparacion`=?,`porciones`=?,`dificultad`=? WHERE id = ?',
        [nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad, id]
    );
    return resultados;
}

export async function deleteReceta(id: number): Promise<any> {
    const [resultados]: any = await pool.query(
        'DELETE FROM recetas WHERE id = ?', [id]
    );
    return resultados;
}