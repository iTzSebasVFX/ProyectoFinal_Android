**Readme** este es un readme creado con el fin de explicar como usar este proyecto de android studio, contiene informacion como la instalacion de las dependencias de la api hasta la ejecucion de la app, los endpoints y el gestor e entorno de desarrollo usado para la db.

---

```markdown
# 📱 ProyectoFinal_Android

Este proyecto permite la **gestión de usuarios y recetas** mediante una API REST desarrollada con **Node.js + Express + TypeScript**, y una aplicación móvil hecha con **Android Jetpack Compose + Room** que funciona **offline-first**, permitiendo sincronización con la API cuando haya conexión.

---

## 📂 Estructura del Repositorio

```

ProyectoFinal\_Android/
├── api/                   # API REST con Node.js + TypeScript
└── app/                   # Proyecto Android Studio (Jetpack Compose)

````

---

## 🍽️ API REST - Node.js + TypeScript + MySQL

La API permite:

- ✅ Registro e inicio de sesión de usuarios
- 🍲 CRUD de recetas (crear, leer, actualizar, eliminar)
- 🔄 Sincronización con la app Android

---

### 📋 Requisitos API

- [Node.js](https://nodejs.org/) (v16 o superior)
- [npm](https://www.npmjs.com/)
- [MySQL](https://www.mysql.com/) (o Wamp/XAMPP)

---

### ⚙️ Instalación de la API

```bash
# Clona el repositorio y entra a la carpeta de la API:
git clone https://github.com/iTzSebasVFX/ProyectoFinal_Android.git
cd ProyectoFinal_Android/api

# Instala las dependencias:
npm install
````

---

### 🛠️ Configuración de Base de Datos

1. Crea la base de datos:

```sql
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
```

2. Configura tu archivo `src/db.ts`:

```ts
host: 'localhost',
user: 'tu_usuario',
password: 'tu_contraseña',
database: 'proyectofinal_recetas'
```

---

### 📁 Estructura API

```
api/
├── dist/                   # Archivos JS compilados
├── node_modules/
├── src/
│   ├── asyncUserService.ts # Lógica de negocio y tipos
│   ├── db.ts               # Conexión MySQL
│   └── server.ts           # Rutas y servidor
├── package.json
├── tsconfig.json
└── README.md
```

---

### 🚀 Scripts disponibles

| Comando         | Acción                                  |
| --------------- | --------------------------------------- |
| `npm run dev`   | Inicia con recarga automática (nodemon) |
| `npm run build` | Compila TypeScript a JavaScript (dist/) |
| `npm start`     | Ejecuta la versión compilada            |

---

### 🔌 Endpoints API

| Método | Ruta                    | Descripción                    |
| ------ | ----------------------- | ------------------------------ |
| POST   | `/registrar`            | Registra un nuevo usuario      |
| GET    | `/login`                | Inicia sesión                  |
| GET    | `/recetas/:id_usuario`  | Obtiene recetas de un usuario  |
| POST   | `/recetas/agregar`      | Agrega una nueva receta        |
| PUT    | `/recetas/actualizar`   | Actualiza una receta existente |
| DELETE | `/recetas/eliminar/:id` | Elimina una receta             |

---

## 🤖 App Android - Jetpack Compose + Room + Hilt

La aplicación permite:

* 📶 Funcionar sin conexión (offline-first con Room)
* 🔄 Sincronización automática con la API al recuperar la conexión
* 🧾 Login/Registro de usuario
* 🍽️ Crear, leer, actualizar y eliminar recetas
* 💡 Arquitectura basada en MVVM + Repositorio

---

### 🛠️ Tecnologías utilizadas

* Kotlin + Jetpack Compose
* Room (SQLite local)
* Hilt (inyección de dependencias)
* Retrofit (API REST)
* StateFlow + ViewModel
* Navigation Compose

---

### 📱 ¿Cómo ejecutarla?

1. Abre el proyecto en **Android Studio** (abre la carpeta `app/`).
2. Asegúrate de tener un emulador o dispositivo físico configurado.
3. Cambia la IP de la base URL de Retrofit (`RecetaApiService.kt`) si usas un emulador.

```kotlin
// Por ejemplo, si estás usando Android Studio Emulator:
private const val BASE_URL = "http://10.0.2.2:3000/"
```

4. Ejecuta el proyecto normalmente (Shift + F10)

---

### 📁 Estructura app/

```
app/
├── dataAPI/             # Modelos de red y servicio Retrofit
├── dataRoom/            # Modelos y DAO de Room (BD local)
├── repository/          # Repositorios API y locales
├── ui/                  # Pantallas Compose (login, recetas, etc.)
├── utils/               # Clases auxiliares (sesión, conectividad)
├── viewModel/           # ViewModels con lógica UI y sincronización
```

---

## ✍️ Autor

Desarrollado por **Juan Sebastián Quiceno Cano**

```

---

✅ **Este README te sirve para documentación profesional**, tanto para portafolios como presentaciones. Si quieres añadir capturas de pantalla, animaciones o GIFs más adelante, puedo ayudarte con eso también.
```
