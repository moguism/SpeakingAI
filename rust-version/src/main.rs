use std::io::stdin;
use std::process::Command;
use std::sync::{Arc, Mutex};

use cpal::traits::{DeviceTrait, HostTrait, StreamTrait};
use hound;

fn main() {
    println!("Presiona Enter para comenzar la grabación o escribe 'q' para salir...");

    let mut input = String::new();
    stdin().read_line(&mut input).unwrap();

    if input.trim() == "q" {
        println!("Saliendo del programa...");
        return;
    }

    // Pilla el micro por defecto (pensaba que no iba a haber librerías para esto xD) y coge los datos
    let host = cpal::default_host();
    let device = host
        .default_input_device()
        .expect("No se encontró un dispositivo de entrada");
    let config = device.default_input_config().unwrap();

    // Configuración del Wav
    let spec = hound::WavSpec {
        channels: config.channels() as u16,
        sample_rate: config.sample_rate().0,
        bits_per_sample: 16,
        sample_format: hound::SampleFormat::Int,
    };

    // Pensaba que nunca diría esto pero la concurrencia me gusta más en Java :( (crea el fichero para que puedan usarlos todos los hilos)
    let writer = Arc::new(Mutex::new(Some(
        hound::WavWriter::create("grabacion.wav", spec).unwrap(),
    )));
    let writer_clone = Arc::clone(&writer);

    // Grabación de datos, poco más que comentar la verdad
    let stream = device
        .build_input_stream(
            &config.into(),
            move |data: &[f32], _: &cpal::InputCallbackInfo| {
                let mut guard = writer_clone.lock().unwrap();
                if let Some(ref mut writer) = *guard {
                    for &sample in data {
                        let sample = (sample * i16::MAX as f32) as i16;
                        writer.write_sample(sample).unwrap();
                    }
                }
            },
            move |err| {
                eprintln!("Error en la entrada de audio: {:?}", err);
            },
            None,
        )
        .unwrap();

    stream.play().unwrap();

    println!("Grabando... Presiona Enter para detener.");
    stdin().read_line(&mut String::new()).unwrap();

    drop(stream); // Detiene la grabación

    // Las {} son como el "using" de .NET (me gusta más así lol)
    {
        let mut guard = writer.lock().unwrap();
        if let Some(writer) = guard.take() {
            writer.finalize().unwrap();
        }
    }

    println!("Grabación detenida. Audio guardado en 'grabacion.wav'.");

    match Command::new("python").arg("main.py").output() {
        Ok(output) => {
            println!("Salida del script Python:");
            println!("{}", String::from_utf8_lossy(&output.stdout));
            println!("Código de salida: {}", output.status.code().unwrap_or(-1));
        }
        Err(e) => {
            eprintln!("Error al ejecutar el script Python: {}", e);
        }
    }
}
