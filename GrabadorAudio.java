import javax.sound.sampled.*;
import java.io.*;

public class GrabadorAudio {

    private AudioFormat formato;
    private TargetDataLine linea;
    private ByteArrayOutputStream buffer;
    private boolean grabando;

    public GrabadorAudio() {
        formato = new AudioFormat(16000, 16, 1, true, false);
    }

    public void comenzarGrabacion() {
        try {
            linea = AudioSystem.getTargetDataLine(formato);
            linea.open(formato);
            linea.start();
            grabando = true;
            buffer = new ByteArrayOutputStream();

            Thread hiloGrabacion = new Thread(new Runnable() {
                @Override
                public void run() {
                    grabarAudio();
                }
            });

            hiloGrabacion.start();

            System.out.println("Grabando... Presiona Enter para detener.");
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void detenerGrabacion() {
        grabando = false;
        linea.stop();
        linea.close();
        guardarAudioEnArchivo();

        // Llama al script de Python después de guardar el audio
        ejecutarScriptPython("main.py");

        System.out.println("Grabación detenida. Audio guardado en 'grabacion.wav'.");
    }

    private void grabarAudio() {
        byte[] tempBuffer = new byte[1024];
        try {
            while (grabando) {
                int bytesLeidos = linea.read(tempBuffer, 0, tempBuffer.length);
                if (bytesLeidos > 0) {
                    buffer.write(tempBuffer, 0, bytesLeidos);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarAudioEnArchivo() {
        try {
            AudioSystem.write(
                    new AudioInputStream(new ByteArrayInputStream(buffer.toByteArray()), formato, buffer.size()),
                    AudioFileFormat.Type.WAVE, new File("grabacion.wav"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ejecutarScriptPython(String script) {
        try {
            // Ejecuta el script de Python usando el comando de shell
            ProcessBuilder pb = new ProcessBuilder("python", script);
            pb.redirectErrorStream(true);

            Process process = pb.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            System.out.println("Salida del script Python:");

            // Lee y muestra la salida del script de Python
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Espera a que el proceso termine
            int exitCode = process.waitFor();
            System.out.println("El script Python ha finalizado con código de salida: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        boolean fin = false;

        while (fin == false) {

            GrabadorAudio grabador = new GrabadorAudio();

            System.out.println("Presiona Enter para comenzar la grabación...");
            try {
                System.in.read();
            } catch (Exception e) {
                e.printStackTrace();
            }

            grabador.comenzarGrabacion();

            System.out.println("Presiona Enter para detener la grabación...");
            try {
                System.in.read();
            } catch (Exception e) {
                e.printStackTrace();
            }

            grabador.detenerGrabacion();

        }

    }
}
