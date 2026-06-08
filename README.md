# NEVSRG

## Requisitos del Sistema

* **Java Development Kit (JDK):** 8 Update 271
* **IDE:** Eclipse 2020-06

---

## Pasos para la Instalación

1. Clonar el repositorio desde GitHub o descomprimir el archivo .zip del proyecto.
2. Abrir Eclipse.
3. Ir a File > Import....
4. Seleccionar Gradle > Existing Gradle Project.
5. Elegir la carpeta raíz del proyecto NEVSRG.
6. Confirmar la importación y esperar a que Gradle descargue las dependencias.

Si Eclipse muestra errores después de importar, hacer clic derecho sobre el proyecto y seleccionar: <br>
<pre>Gradle > Refresh Gradle Project </pre>

También se puede ejecutar el siguiente comando para regenerar los archivos de configuración de Eclipse: <br>
<pre>.\gradlew.bat eclipse</pre>


---
## Ejecución

### Ejecucion desde Release
Si quieres jugar sin necesidad de compilar algo, sigue estos pasos:

1. **Descarga el archivo**: Busca la sección "Releases" en la parte derecha de esta página de GitHub y haz clic en el archivo .zip más reciente (ej. [NEVSRG.zip](https://github.com/Rhyzt/NEVSRG/releases/tag/v1.0.0)).

2. **Descomprime**: Extrae el contenido del archivo .zip en una carpeta de tu preferencia en tu computadora.

3. **Ejecuta el juego**: Haz doble clic sobre el archivo NEVSRG-desktop.jar.

**Nota**: Asegúrate de tener instalado Java Runtime Environment (JRE) o JDK (versión 8 o superior) en tu sistema para ejecutar el juego correctamente.

**¡Disfruta!**: El juego cargará automáticamente los mapas que se encuentran en la carpeta charts.


### Ejecución Desde Eclipse

1. En el explorador de paquetes, abrir el módulo desktop.
2. Buscar la clase DesktopLauncher.java.
3. Hacer clic derecho sobre la clase.
4. Seleccionar: Run As > Java Application.

El juego se abrirá en la pantalla de selección de canciones. <br>
### Ejecución Desde el Terminal

También es posible ejecutar el proyecto usando Gradle en la consola de comandos:
1. Usar <pre>cd DireccionCarpetaRaiz</pre>
2. Luego <pre>.\gradlew.bat desktop:run</pre>

Este comando compila el proyecto y ejecuta la versión de escritorio.

## Configuración del Juego

Desde la pantalla de configuración se pueden modificar: <br>
    •Scroll Speed: velocidad de caída de las notas. <br>
    •Teclas de los cuatro carriles. <br>
    •Judge: dificultad del sistema de precisión. <br>

La configuración se guarda automáticamente en un archivo local llamado:
settings.json

Este archivo conserva las preferencias del jugador incluso después de cerrar el juego.

## Compilar el juego (Build)
Para generar el archivo ejecutable (`.jar`) del juego, sigue estos pasos:

1. **Abre la consola de comandos** y navega hasta la carpeta raíz del proyecto. 
   *(Ejemplo de ruta local en Windows):*
   <pre>cd C:\Users\tu_usuario\Documents\NEVSRG </pre>

2. Ejecuta el comando para construir el proyecto
    <pre>gradlew desktop:dist</pre>
3. Una vez que el proceso termine exitosamente, el archivo .jar compilado y listo para jugar se generará dentro de la siguiente ruta:   
    <pre>desktop/build/libs/ </pre>

## Estructura de Mapas
Cada mapa debe estar dentro de su propia subcarpeta y debe contener al menos: <br>
•Un archivo .nevsrg u .osu. <br>
•El archivo de audio correspondiente, normalmente audio.mp3. <br>

El `.zip` en release contiene 5 niveles de prueba. Con 4 tipo .osu y 1 tipo .nevsrg

### Caso Ejecucion IDE
Los mapas deben ubicarse dentro de la carpeta charts, ubicada en la raíz del proyecto y a la misma altura que carpetas como assets, desktop o core.

Ejemplo:
<pre>
NEVSRG/
└── charts/
    └── mapa/
        ├── ejemplo.nevsrg
        └── audio.mp3
</pre>


### Caso Build
Los mapas deben ubicarse en una carpeta llamada charts a la misma altura que el .jar

Ejemplo:
<pre>
NEVSRG/
├── NEVSRG-1.0.jar
└── charts/
    └── mapa/
        ├── ejemplo.nevsrg
        └── audio.mp3
</pre>