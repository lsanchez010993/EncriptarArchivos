import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Encriptar {
    private static final String ALGORITMO_CLAVE = "AES";
    private static final String ALGORITMO_CLAVE_SECRETA = "PBKDF2WithHmacSHA256";
    private static final int ITERACIONES_PBKDF2 = 10000;
    private static final int LONGITUD_CLAVE_PBKDF2 = 256;
    public static ArrayList<Boolean> encriptadoCorrecto = new ArrayList<>();

    public static boolean encriptarArchivo(String rutaString, String pass) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] claveEnBytes = genererarPassword_en_Bytes(pass);

        try {
            // Leer archivo a cifrar
            FileInputStream fis = new FileInputStream(rutaString);
            byte[] archivoEnBytes = new byte[(int) fis.available()];
            fis.read(archivoEnBytes);
            fis.close();

            // Crear clave secreta a partir de los bytes de la clave
            SecretKeySpec clave = new SecretKeySpec(claveEnBytes, ALGORITMO_CLAVE);

            // Crear objeto de cifrado
            @SuppressWarnings("GetInstance") Cipher cifrador = Cipher.getInstance(ALGORITMO_CLAVE + "/ECB/PKCS5Padding");
            cifrador.init(Cipher.ENCRYPT_MODE, clave);

            // Cifrar archivo y guardar el resultado en un nuevo archivo
            byte[] archivoCifrado = cifrador.doFinal(archivoEnBytes);

            FileOutputStream fos = new FileOutputStream(rutaString + ".aes");
            fos.write(archivoCifrado);
            fos.close();
            File archivo = new File(rutaString);
            archivo.delete();//Una vez cifrado, elimina el archivo original
            return true;

        } catch (
                FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado. " + e.getMessage());
        } catch (
                IOException e) {
            System.out.println("Error: Lectura o escritura de archivo. " + e.getMessage());

        } catch (NoSuchAlgorithmException |
                 NoSuchPaddingException e) {
            System.out.println("Error: Algoritmo o relleno no válido. " + e.getMessage());

        } catch (InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            System.out.println("Error: Problema con la clave de cifrado o tamaño de bloque. " + e.getMessage());

        } catch (
                Exception e) {
            System.out.println("Error: " + e.getMessage());

        }
        return false;
    }

    public static byte[] genererarPassword_en_Bytes(String contrasena) throws
            NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITMO_CLAVE_SECRETA);
        byte[] salt = new byte[256];
        KeySpec spec = new PBEKeySpec(contrasena.toCharArray(), salt, ITERACIONES_PBKDF2, LONGITUD_CLAVE_PBKDF2);
        SecretKey claveSecreta = factory.generateSecret(spec);
        return claveSecreta.getEncoded();
    }

    public static void encriptarDirectorio(String rutaString, String pass)
            throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        Stack<File> stack = new Stack<>();
        stack.push(new File(rutaString));
        while (!stack.isEmpty()) {
            File contenido = stack.pop();
            if (contenido.isDirectory()) {
                for (File contenido1 : Objects.requireNonNull(contenido.listFiles())) {
                    stack.push(contenido1);
                }
            } else {
                if (!contenido.getName().endsWith(".aes") && !contenido.getName().endsWith(".jar")) {
                    encriptadoCorrecto.add(encriptarArchivo(contenido.getAbsolutePath(), pass));
                }
            }

        }
    }
}

