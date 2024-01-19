import os
import json
from vosk import Model, KaldiRecognizer
import wave
from gtts import gTTS
from playsound import playsound
from openai import OpenAI

client = OpenAI(

    api_key = "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"

)

def transcribir_audio(ruta_audio):
    modelo_vosk = Model("vosk-model-small-en-us-0.15")  # Reemplaza con la ruta al modelo Vosk
    reconocedor = KaldiRecognizer(modelo_vosk, 16000)

    with wave.open(ruta_audio, 'rb') as audio_file:
        muestras = audio_file.readframes(audio_file.getnframes())
        resultado = reconocedor.AcceptWaveform(muestras)

    transcripcion_json = json.loads(reconocedor.Result())
    transcripcion_texto = transcripcion_json["text"] if "text" in transcripcion_json else "Error al transcribir"

    return transcripcion_texto

def reproducir_tts(texto):
    tts = gTTS(text=texto, lang='en')
    tts.save("transcripcion.mp3")
    playsound("transcripcion.mp3")

def obtener_respuesta_gpt(texto_usuario):

    completion = client.chat.completions.create(

        model="gpt-3.5-turbo",
        messages = [
            {"role": "system", "content": "You and your partner are going to talk about the possibility of staying away from work for a year if you could. Consider the following: The advantages and disadvantages of taking a sabbatical year, What you would do if you had that time for yourself and How it would affect your career once you were back to work. You must agree or disagree with your partner. Your parter will be the prompt that I'll be giving you, and don't give too much informatione"},
            {"role": "user", "content": texto_usuario}
        ]
        
    )

    respuesta_gpt = completion.choices[0].message.content
    return respuesta_gpt

if __name__ == "__main__":
    archivo_audio = "grabacion.wav"

    if os.path.exists(archivo_audio):
        transcripcion = transcribir_audio(archivo_audio)
        print("Transcripción del audio:")
        print(transcripcion)

        respuesta_gpt = obtener_respuesta_gpt(transcripcion)
        print("Respuesta de ChatGPT:")
        print(respuesta_gpt)

        reproducir_tts(respuesta_gpt)
    else:
        print(f"El archivo {archivo_audio} no existe.")
