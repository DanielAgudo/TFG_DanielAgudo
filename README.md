# Escuela Cilista Seseña

## Descripción

El proyecto Escuela Cilista Seseña es una aplicación desarrollada para Android como trabajo de fin de grado para el curso 2025/2026.

El proyecto consiste en el diseño e implementación de una aplicación Android en Kotlin. Su objetivo principal es facilitar el registro y gestión de usuarios para una escuela ciclista, proporcionando una herramienta sencilla y eficiente para la administración de inscripciones, datos personales y actividades relacionadas. Se usa Firebase para almacenamiento de datos en la nube.

---

## Objetivos

### Objetivos específicos

- Diseñar la arquitectura del sistema.
- Implementar las funcionalidades principales.
- Realizar pruebas y validaciones.
- Documentar el proceso de desarrollo.

---

## Tecnologías utilizadas

- Android Studio
- Kotlin
- XML
- Firebase

---

## Instalación y ejecución

### Requisitos previos

Para su ejecución será necesario dispositivo móvil Android. 

### Pasos para ejecutar el proyecto

```bash
git clone https://github.com/DanielAgudo/TFG_DanielAgudo.git

cd TFG_DanielAgudo
````
---

**Autor:**  
Daniel Agudo Iglesias

**Curso:**  
2º Desarrollo de Aplicaciones Multiplataforma (DAM)

**Centro:**  
IES Las Salinas

---

![version | 232](https://img.shields.io/badge/version-v1.0.0-purple)

### Tutorial de uso de la aplicación

## 1. Inicio de sesión

Al iniciar la aplicación, deberás acceder con un usuario válido introduciendo correo electrónico (Gmail) y contraseña.

Puedes utilizar las siguientes credenciales:

- Correo 1: director1@gmail.com 
- Contraseña 1: director1
##
- Correo 2: director2@gmail.com 
- Contraseña 2: director2

  
Una vez introducidos los datos, pulsa el botón Iniciar sesión.
Si las credenciales son correctas, accederás al menú principal de la aplicación.

## 2. Menú principal
En el menú principal encontrarás dos botones, cada uno te llevará a una sección diferente de la aplicación:

- Crear alumno: Lleva al apartado donde podrás rellenar los datos necesarios para registrar un nuevo alumno.

- Ver lista de alumnos: Muestra un listado con todos los alumnos que ya han sido registrados en el sistema.

## 3. Crear alumno
En esta sección deberás rellenar todos los campos del formulario, ya que todos los datos son obligatorios.

Una vez completados los datos tendrás dos opciones:

- Guardar: Guardará la información del alumno y te llevará automáticamente al apartado Lista de alumnos, donde aparecerá el nuevo alumno creado.

- Borrar: Si has rellenado información pero deseas eliminarla antes de guardar, puedes pulsar el botón Borrar para limpiar todos los campos del formulario.

## 4. Lista de alumnos
En esta sección se muestran todos los alumnos registrados en la aplicación.
Además, dispones de un buscador que permite filtrar alumnos por nombre, facilitando encontrar rápidamente al alumno deseado.

Cada alumno se muestra en una tarjeta que contiene los siguientes datos principales:

-- Nombre del alumno

-- DNI del alumno

-- Tutor (DNI del tutor)

-- Modalidad (modalidad de ciclismo preferida)

