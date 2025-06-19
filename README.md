# ProyectoFinal_Android

Este proyecto tiene dos carpeta, una en la cual encontraremos la api y la otra donde se hallará el proyecto de Android Studio.

# API REST de Gestión de Usuarios y Recetas 🍽️

En este proyecto se desarrollo una API Rest con **Node.js**, **Express** y **TypeScript**, conectada a una base de datos **MySQL**. Permite gestionar usuarios y recetas, con funciones como:

- Registro e inicio de sesión de usuarios
- CRUD completo de recetas
- Búsqueda de recetas por usuario

---

## 📦 Requisitos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- [Node.js](https://nodejs.org/) (v16 o superior recomendado)
- [npm](https://www.npmjs.com/)
- [MySQL](https://www.mysql.com/)
- [MySQL] (incluido en WampServer) en mi caso, solo no olvides iniciar el servidor.

---


## ⚙️ Instalación

1. Clona el repositorio:

    ```bash
    git clone https://github.com/iTzSebasVFX/ProyectoFinal_Android.git
    cd tu_repo

2. Instala las dependencias:

    ```bash
    npm install

## Estructura de la Api
├── dist             # Archivos compilados a Java Script
├── node_modules
├── src/
|   ├── asyncUserService.ts # Interfaces TypeScript(User, Recetas) y funciones (Login, Registro, CRUD Recetas)
│   ├── db.ts             # Acceso a la base de datos (MySql)
│   └── server.ts           # Rutas y puntos de entrada principal
├── package.json
├── tsconfig.json
├── README.md

## Activar permiso de Scripts

Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

## Scripts 🚀
Script	| Comando	       | Descripción
dev	    | npm run dev	   | Ejecuta el servidor con recarga automática
build	| npm run build    | Compila el proyecto TypeScript a JavaScript
start	| npm start	       | Ejecuta el proyecto compilado (dist/)

## 🔌 Configuración de Base de Datos
1. Crea la base de datos en MYSQL:

    CREATE DATABASE proyectofinal_recetas;

    USE proyectofinal_recetas;

    CREATE TABLE usuarios (
        id INT PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(100) NOT NULL,
        correo VARCHAR(100) NOT NULL UNIQUE,
        contraseña VARCHAR(100) NOT NULL
    );

    CREATE TABLE recetas (
        id INT PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(100) NOT NULL,
        ingredientes TEXT NOT NULL,
        instrucciones TEXT NOT NULL,
        tiempo_preparacion VARCHAR(50) NOT NULL,
        porciones VARCHAR(50) NOT NULL,
        dificultad VARCHAR(50) NOT NULL,
        id_usuario INT,
        FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
    );

2. Modifica el archivo db.ts

-- Ve al directorio src en la API y busca el archivo db.ts

    host: 'tu_host',
    user: 'tu_user',
    password: 'tu_pass',
    database: 'proyectofinal_recetas'

3. Compila: npm run build
4. inicia: npm start

# Ejecución 🧪
**Durante el desarrollo (con recarga):**
    *npm run dev*

**Compilar:**
    *npm run build*

**Ejecutar Compilado:**
    *npm run start*

# ✅ Endpoints
Método	Ruta	                Descripción
POST	/registrar	            Registra un nuevo usuario
GET	    /login	                Inicia sesión
GET	    /recetas/:id_usuario	Obtiene recetas por usuario
POST	/recetas/agregar	    Crea una receta
PUT	    /recetas/actualizar     Actualiza una receta
DELETE	/recetas/eliminar/:id	Elimina una receta

# ✍️ Autor
Desarrollado por [Juan Sebastian Quiceno Cano]