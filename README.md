# SpeakingAI

## Descripción

Esta es una herramienta que busca ayudar a estudiantes de inglés como yo, permitiendo mantener diálogos que mejoren el nivel hablado y estudiar para exámenes orales.

## Requisitos

### Software
- **Sistema operativo**: Windows o Linux.
- Tener instalador los paquetes **espeak**, **espeak-devel**, **portaudio** y **portaudio-devel**.
- **Python**: 3.12 (la aplicación fue desarrollada y probada con Python 3.9).
- **Java**: Java Development Kit (JDK).
- **OPCIONAL**: Rust si quieres usar la versión con este lenguaje

### API
- **API Key**: Debes tener una API Key de ChatGPT. Puedes pillar una desde [la web oficial](https://platform.openai.com/).

## Instalación y Configuración

1. **Clonar el repositorio**

    ```bash
    git clone https://github.com/moguism/SpeakingAI.git
    cd SpeakingAI
    ```

2. **Instalar dependencias de Python**

    ```bash
    pip install --upgrade -r requirements.txt
    ```

3. **Configurar variables de entorno**

    - Si eres un linuxero como yo :), abre el archivo de configuración de entorno y añade las siguientes líneas:

        ```bash
        export OPEN_AI_KEY='tu_api_key_aqui'
        export ENUNCIADO='El enunciado del ejercicio de speaking aquí'
        ```

    - Si usas Windows, simplemente edita las variables de entorno
    - Aplica los cambios (usando Bash como ejemplo):

        ```bash
        source ~/.bashrc
        ```

4. **OPCIONAL: Descargar otro modelo de Vosk**

    Si bien creo que el modelo que he incluido funciona bastante bien, puedes usar otro.

    - Descarga un modelo desde la [web oficial](https://alphacephei.com/vosk/models).
    - Descomprime el archivo ZIP en la ruta del proyecto.
    - Cambia la ruta del modelo en el archivo `main.py` (he puesto un comentario en el sitio que, aunque lo parezca, no es un comentario de ChatGPT xD).

## Ejecución

1. **Ejecutar el archivo Java**

    ```bash
    javac GrabadorAudio.java
    java GrabadorAudio
    ```

    Si usas Rust, simplemente accede a la carpeta "rust-version/src" y haz "cargo run"

2. **Seguir las indicaciones en la terminal**

    Si crees que las indicaciones no son lo suficientemente claras, haz una Issue. por favor. ¡MUCHA SUERTE EN TUS ESTUDIOS!

## Licencia

Este proyecto está bajo la Licencia GNU GPL 3.0, ya que creo firmemente en los proyectos libres y de código abierto. Consulta el archivo `LICENSE` para más detalles.