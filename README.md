# Gestor Personal de Tareas

Aplicación Android para organizar tareas personales, académicas o profesionales. Permite crear, consultar, editar, completar y eliminar tareas, conserva la información entre ejecuciones, recuerda una preferencia de visualización, consulta tareas de ejemplo en un servicio web y permite compartir una tarea con otras aplicaciones del dispositivo.

Práctica del módulo **Programación Multimedia y Dispositivos Móviles (PMDM)**.

- **Autora:** María Dolores Barba
- **Repositorio:** https://github.com/MariaDoloresBarba/gestor_personal_tareas
- **Tecnologías:** Kotlin, Android Studio, Jetpack Compose, Material 3

## Funcionalidades implementadas

**Tareas personales**
- Listado en una lista desplazable (`LazyColumn`) que muestra título, prioridad y estado de cada tarea.
- Mensaje informativo cuando no hay tareas.
- Creación de tareas con un formulario: título, descripción, prioridad (menú desplegable), botón Guardar y botón Cancelar.
- Validaciones con mensaje junto al campo: el título no puede estar vacío ni tener solo espacios, y debe tener al menos 3 caracteres tras recortar los espacios exteriores. La descripción puede quedar vacía. La prioridad solo admite Baja, Media o Alta porque es un `enum`.
- Protección contra duplicados por pulsaciones repetidas del botón Guardar.
- Edición de una tarea con el mismo formulario, cargado con sus datos actuales.
- Marcar una tarea como completada o pendiente con un `Checkbox`.
- Eliminación con diálogo de confirmación (`AlertDialog`). Si se cancela, la tarea no cambia.
- Todas las operaciones actualizan la base de datos, no solo la pantalla.

**Persistencia**
- **Room**: todas las tareas se guardan en una base de datos local y se recuperan al abrir la app.
- **Preferences DataStore**: el interruptor «Mostrar solo pendientes» se guarda y se recupera en la siguiente ejecución. Valor inicial: mostrar todas las tareas.

**Servicio web**
- Apartado «Tareas de ejemplo (API)» que consulta `GET /todos?userId=1` de JSONPlaceholder con Retrofit y coroutines.
- Indicador de carga, botón de carga deshabilitado mientras hay una petición en curso, mensaje de error y botón **Reintentar**.
- Las tareas del servidor son datos de ejemplo: no se guardan en Room y se muestran en una pantalla aparte, claramente identificada.

**Compartir**
- Opción **Compartir** en cada tarea: abre el selector de aplicaciones con un `Intent` implícito `ACTION_SEND` que lleva título, descripción y prioridad.
- Si no hay ninguna aplicación compatible, se avisa al usuario. Cuando el selector se abre no se muestra ningún mensaje de «compartido», porque abrir el selector no garantiza que se haya enviado nada.

---

## Organización del proyecto

Una única `MainActivity` y pantallas definidas como funciones `@Composable`. La navegación es sencilla y se basa en estados.

```
app/src/main/java/com/example/gestorpersonaldetareas/
├── MainActivity.kt
├── model/
│   ├── Prioridad.kt            enum: BAJA, MEDIA, ALTA
│   └── Tarea.kt                data class del modelo
├── ui/
│   ├── AppTareas.kt            pantalla raíz: estado, navegación y conexión con datos
│   ├── ListaTareas.kt          LazyColumn y mensaje de lista vacía
│   ├── TarjetaTarea.kt         una tarea con sus acciones
│   ├── FormularioTarea.kt      formulario de creación y edición
│   ├── Validaciones.kt         validación del título
│   ├── PantallaTareasEjemplo.kt  consulta a la API y sus estados
│   ├── Compartir.kt            Intent ACTION_SEND
│   └── theme/                  tema Material 3
├── data/                       almacenamiento (Room)
│   ├── TareaEntity.kt          entidad + conversiones con el modelo
│   ├── TareaDao.kt             consultar, insertar, actualizar, eliminar
│   ├── TareaDatabase.kt        base de datos (singleton)
│   └── TareaRepository.kt      puente entre almacenamiento e interfaz
├── preferences/
│   └── PreferenciasUsuario.kt  lectura y escritura con DataStore
└── network/                    comunicaciones (Retrofit)
    ├── TareaRemota.kt          modelo de la respuesta JSON
    ├── JsonPlaceholderApi.kt   interfaz con la petición
    └── ClienteRetrofit.kt      configuración de Retrofit
```

**Decisiones de diseño**
- La interfaz recibe los datos por parámetros y avisa de los eventos mediante funciones (*callbacks*). El estado vive en `AppTareas`.
- El modelo (`Tarea`) está separado de la entidad de Room (`TareaEntity`), para que la interfaz no dependa del almacenamiento.
- Los estados de la consulta a la API (inicial, cargando, éxito, error) se representan con una `sealed interface`.
- Room y DataStore se usan cada uno para lo suyo: Room para las tareas y DataStore solo para la preferencia de visualización.

## Bibliotecas utilizadas

| Biblioteca | Versión | Uso |
|---|---|---|
| Jetpack Compose (BOM) + Material 3 | 2026.02.01 | Interfaz |
| Material Icons Core | (BOM) | Iconos locales |
| Room (runtime, ktx, compiler) | 2.8.5 | Base de datos local |
| KSP | 2.3.10 | Procesador de anotaciones de Room |
| DataStore Preferences | 1.2.1 | Preferencia de visualización |
| Retrofit | 3.0.0 | Peticiones HTTP |
| Converter Gson | 3.0.0 | Conversión JSON a objetos Kotlin |
| Kotlin coroutines y Flow | (incluidas) | Operaciones asíncronas |

Configuración: Android Gradle Plugin 9.4.1, Kotlin 2.2.10, `minSdk` 24, `targetSdk` 37.

Las dependencias se declaran en `gradle/libs.versions.toml` y `app/build.gradle.kts`. El único permiso declarado es `INTERNET`.

## Instrucciones para ejecutar la aplicación

1. Clonar el repositorio: `git clone https://github.com/MariaDoloresBarba/gestor_personal_tareas.git`
2. Abrir la carpeta con **Android Studio**.
3. Esperar a que Gradle sincronice el proyecto (o pulsar **Sync Now**).
4. Elegir un emulador o dispositivo y pulsar **Run**.

**Instalar el APK:** el APK de depuración está en `apk/app-debug.apk`. Se puede arrastrar sobre la ventana de un emulador o instalar con `adb install apk/app-debug.apk`.

## Pruebas realizadas

| Nº | Prueba | Resultado esperado | Emulador 1 | Emulador 2 |
|---|---|---|---|---|
| 1 | Crear una tarea válida | Se guarda y aparece en la lista | ok | |
| 2 | Intentar guardar un título vacío | Se muestra un error y no se crea la tarea | | |
| 3 | Editar una tarea | Los cambios se muestran correctamente | ok | |
| 4 | Marcar una tarea como completada | Su estado cambia y queda guardado | ok | |
| 5 | Cancelar una eliminación | La tarea no desaparece |  ok | |
| 6 | Eliminar una tarea confirmando | La tarea desaparece de la base de datos | ok | |
| 7 | Cerrar y volver a abrir la aplicación | Se recuperan las tareas guardadas | | |
| 8 | Activar el filtro y reiniciar | Se conserva la preferencia seleccionada | | |
| 9 | Consultar la API con Internet | Se muestran las tareas recibidas | | |
| 10 | Consultar la API sin conexión | Se muestra un error y se permite reintentar | | |
| 11 | Compartir una tarea | Se abre el selector de aplicaciones compatibles | | |
| 12 | Cambiar la orientación del dispositivo | La app sigue funcionando y conserva sus datos persistidos | | |

---

## Capturas de pantalla

![Captura 1](Imágenes/Captura%20de%20pantalla%202026-11-15%20a%20las%2018.49.03.png)
![Captura 2](Imágenes/Captura%20de%20pantalla%202026-11-15%20a%20las%20XX.XX.XX.png)
![Captura 3](Imágenes/Captura%20de%20pantalla%202026-11-15%20a%20las%20XX.XX.XX.png)
![Captura 4](Imágenes/Captura%20de%20pantalla%202026-11-15%20a%20las%20XX.XX.XX.png)
![Captura 5](Imágenes/Captura%20de%20pantalla%202026-11-15%20a%20las%20XX.XX.XX.png)

## Uso de la inteligencia artificial

Enlace al chat utilizado durante el desarrollo: (https://gemini.google.com/share/d/1qrT-At1p2KGj2Qqi2uCmY-pnM6TnDxnE?usp=sharing)

La IA me ha ayudado poniedome ejemplos para poder realizar las distintas fases del proyecto. M e ha explicado los conceptos nuevos y me ayudaba a solucionar cualquier error de compilación que me han ido surgiendo.

## Incidencias conocidas

- La lista de «Tareas de ejemplo» no se conserva al girar el dispositivo: hay que volver a pulsar el botón de carga. (El estado de esa pantalla no se guarda al cambiar de orientación.)
- La consulta a la API controla los fallos de conexión y los errores HTTP, pero no un JSON con formato inesperado.
- No se han incluido pruebas automatizadas, porque el enunciado no las exige.
- La base de datos no exporta su esquema ni define migraciones (`version = 1`).
