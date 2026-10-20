# Evaluación 2 - Sistema de Bitácoras de Aprendizaje

Aplicación móvil nativa en Android desarrollada para la gestión de bitácoras y registros de aprendizaje, implementando un sistema multi-rol (Docente/Estudiante).

## Integrantes
* Luis Soto Farias

## Arquitectura y Tecnologías
* **Lenguaje:** Kotlin
* **UI:** Jetpack Compose & Material Design 3
* **Arquitectura:** MVVM (Model-View-ViewModel) con patrón Repository
* **Base de Datos:** SQLite usando Room Database
* **Asincronía:** Coroutines & StateFlow

## Funcionalidades Principales
* **Autenticación:** Inicio y cierre de sesión basado en roles.
* **Panel Docente:** Creación, edición, cierre y retroalimentación de bitácoras.
* **Panel Estudiante:** Creación y edición de registros de aprendizaje con adjunto de evidencias (Cámara/Galería nativa).
* **Historial y Filtros:** Búsqueda por título/asignatura y filtrado por estado (Abierta/Cerrada).

## Instrucciones de Ejecución
1. Clonar este repositorio y abrirlo en Android Studio.
2. Sincronizar los archivos de Gradle.
3. Ejecutar en un emulador con API 24 o superior.

**Cuentas de prueba pre-configuradas:**
* **Docente:** `docente1@escuela.cl` | Contraseña: `1234`
* **Estudiante:** `estudiante1@escuela.cl` | Contraseña: `1234`