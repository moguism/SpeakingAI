import os
import json
from vosk import Model, KaldiRecognizer
import wave

def transcribir_audio(ruta_audio):
    modelo_vosk = Model("vosk-model-small-en-us-0.15")  # Reemplaza con la ruta al modelo Vosk
    reconocedor = KaldiRecognizer(modelo_vosk, 16000)

    with wave.open(ruta_audio, 'rb') as audio_file:
        muestras = audio_file.readframes(audio_file.getnframes())
        resultado = reconocedor.AcceptWaveform(muestras)

    transcripcion_json = json.loads(reconocedor.Result())
    transcripcion_texto = transcripcion_json["text"] if "text" in transcripcion_json else "Error al transcribir"

    return transcripcion_texto

if __name__ == "__main__":
    archivo_audio = "grabacion.wav"

    if os.path.exists(archivo_audio):
        transcripcion = transcribir_audio(archivo_audio)
        print("Transcripción del audio:")
        print(transcripcion)
    else:
        print(f"El archivo {archivo_audio} no existe.")
