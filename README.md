# SpeakingAI

## Descripción

Esta es una herramienta que busca ayudar a estudiantes de inglés como yo, permitiendo mantener diálogos que mejoren el nivel hablado y estudiar para exámenes orales.

## Requisitos

### Software
- **Sistema operativo**: Linux.
- Tener instalador los paquetes **espeak**, **espeak-devel**, **portaudio** y **portaudio-devel**.
- **Python**: 3.9 (la aplicación fue desarrollada y probada con Python 3.9).
- **Java**: Java Development Kit (JDK).

### API
- **API Key**: Debes tener acceso a una API Key de ChatGPT. Puedes obtener una desde [la web oficial](https://platform.openai.com/).

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

    - Abre tu archivo de configuración de entorno (por ejemplo, `~/.bashrc` o `~/.zshrc`) y añade las siguientes líneas:

        ```bash
        export OPEN_AI_KEY='tu_api_key_aqui'
        export ENUNCIADO='El enunciado del ejercicio de speaking aquí'
        ```

    - Aplica los cambios (usando Bash como ejemplo):

        ```bash
        source ~/.bashrc
        ```

4. **OPCIONAL: Descargar otro modelo de Vosk**

    Si quieres utilizar un modelo Vosk diferente para un mejor reconocimiento de voz, sigue estos pasos:

    - Descarga el modelo desde la [web oficial de Vosk](https://alphacephei.com/vosk/models).
    - Descomprime el archivo ZIP en la ruta del proyecto.
    - Cambia la ruta del modelo en el archivo `main.py`.

## Ejecución

1. **Ejecutar el archivo Java**

    ```bash
    javac GrabadorAudio.java
    java GrabadorAudio
    ```

2. **Seguir las indicaciones en la terminal**

    El programa te guiará a través de los pasos necesarios para grabar tu voz y obtener respuestas de ChatGPT. ¡MUCHA SUERTE EN TUS ESTUDIOS!

## Licencia

Este proyecto está bajo la Licencia GNU GPL 3.0, ya que creo firmemente en los proyectos libres y de código abierto. Consulta el archivo `LICENSE` para más detalles.