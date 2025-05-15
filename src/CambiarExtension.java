import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class CambiarExtension {
    public static void renombrarExtensionArchivos(String ruta) throws IOException {
        File carpeta = new File(ruta);

        String extension;


        for (File contenido : Objects.requireNonNull(carpeta.listFiles())) {
            if (contenido.getName().contains("$")) continue;
            extension = obtenerExtension(contenido);

            if (contenido.isDirectory()) renombrarExtensionArchivos(String.valueOf(contenido));
            else {
               renombrarExtenArch(contenido, extension);

            }
        }

    }

    private static String cambiarExtension(String extension) {

        switch (extension) {

            case ".jpg", ".txt" -> extension = (extension.equalsIgnoreCase(".jpg")) ? ".txt" : ".jpg";
            case ".png", ".doc" -> extension = (extension.equalsIgnoreCase(".png")) ? ".doc" : ".png";
            case ".mp4", ".rar" -> extension = (extension.equalsIgnoreCase(".mp4")) ? ".rar" : ".mp4";
            case ".avi", ".xml" -> extension = (extension.equalsIgnoreCase(".avi")) ? ".xml" : ".avi";
            case ".jpje", ".docx" -> extension = (extension.equalsIgnoreCase(".jpje")) ? ".docx" : ".jpje";
            case ".gif", ".pdf" -> extension = (extension.equalsIgnoreCase(".gif")) ? ".pdf" : ".gif";
            case ".mkv", ".html" -> extension = (extension.equalsIgnoreCase(".mkv")) ? ".html" : ".mkv";
            case ".mov", ".py" -> extension = (extension.equalsIgnoreCase(".mov")) ? ".py" : ".mov";
            case ".mpg", ".zip" -> extension = (extension.equalsIgnoreCase(".mpg")) ? ".zip" : ".mpg";
            case ".wmv", ".tar.gz" -> extension = (extension.equalsIgnoreCase(".wmv")) ? ".tar.gz" : ".wmv";
            case ".flv", ".css" -> extension = (extension.equalsIgnoreCase(".flv")) ? ".css" : ".flv";
            case ".webm", ".cpp" -> extension = (extension.equalsIgnoreCase(".webm")) ? ".cpp" : ".webm";
            case ".f4v", ".svg" -> extension = (extension.equalsIgnoreCase(".f4v")) ? ".svg" : ".f4v";
            default -> {
                return extension;
            }
        }

        return extension;
    }

    public static String obtenerExtension(File archivo) {
        String nombreArchivo = archivo.getName();
        int ultimoPunto = nombreArchivo.lastIndexOf('.');

        if (ultimoPunto > 0 && ultimoPunto < nombreArchivo.length() - 1) {
            return nombreArchivo.substring(ultimoPunto);
        } else {
            return nombreArchivo;
        }
    }

    public static boolean renombrarExtenArch(File archico, String extension) throws IOException {
        String nombreArchivo = archico.getName();
        if (nombreArchivo.endsWith(extension)) {
            String nuevoNombre = nombreArchivo.replace(extension, cambiarExtension(extension));
            if (!nombreArchivo.equals(nuevoNombre)) {
                Path pathAnterior = Paths.get(archico.getAbsolutePath());
                Path pathNuevo = Paths.get(archico.getParentFile().getAbsolutePath(), nuevoNombre);
                Files.move(pathAnterior, pathNuevo);
                return true;
            }
        }
        return false;
    }
}
