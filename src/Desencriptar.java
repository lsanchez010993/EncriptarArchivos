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
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Desencriptar {
    private static final String ALGORITMO_CLAVE = "AES";
    private static final String ALGORITMO_CLAVE_SECRETA = "PBKDF2WithHmacSHA256";
    private static final int ITERACIONES_PBKDF2 = 10000;
    private static final int LONGITUD_CLAVE_PBKDF2 = 256;

    public static boolean encontrarPass = false;
    public static ArrayList<Boolean> desencriptadoCorrecto = new ArrayList<>();


    public static boolean desencriptarDirectorio(String rutaString, String pass) throws
            NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        boolean exitoo = false;
        Stack<File> stack = new Stack<>();
        stack.push(new File(rutaString));
        while (!stack.isEmpty()) {
            File contenido = stack.pop();
            if (contenido.isDirectory()) {
                for (File contenido1 : Objects.requireNonNull(contenido.listFiles())) {
                    stack.push(contenido1);
                }
            } else {
                if (contenido.getName().endsWith(".aes")) {
                    exitoo = desencriptarArchivo(contenido.getAbsolutePath(), pass);
                    desencriptadoCorrecto.add(exitoo);
                    if (encontrarPass && exitoo) {
                        return true;
                    }
                }

            }
        }


        return exitoo;
    }


    public static byte[] genererarPassword_en_Bytes(String contrasena) throws
            NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITMO_CLAVE_SECRETA);
        byte[] salt = new byte[256];
        KeySpec spec = new PBEKeySpec(contrasena.toCharArray(), salt, ITERACIONES_PBKDF2, LONGITUD_CLAVE_PBKDF2);
        SecretKey claveSecreta = factory.generateSecret(spec);
        return claveSecreta.getEncoded();
    }

    public static boolean desencriptarArchivo(String rutaString, String pass) throws NoSuchAlgorithmException, InvalidKeySpecException {
        boolean descifrado = false;
        byte[] claveEnBytes = genererarPassword_en_Bytes(pass);
        try {
            FileInputStream fis = new FileInputStream(rutaString);
            byte[] archivoCifrado = new byte[(int) fis.available()];
            fis.read(archivoCifrado);
            fis.close();

            SecretKeySpec clave = new SecretKeySpec(claveEnBytes, ALGORITMO_CLAVE);

            Cipher descifrador = Cipher.getInstance(ALGORITMO_CLAVE + "/ECB/PKCS5Padding");
            descifrador.init(Cipher.DECRYPT_MODE, clave);

            byte[] archivoDescifrado = descifrador.doFinal(archivoCifrado);

            FileOutputStream fos = new FileOutputStream(rutaString.replace(".aes", ""));
            fos.write(archivoDescifrado);
            fos.close();

            descifrado = true;

            if (!encontrarPass) {
                File archivo = new File(rutaString);
                archivo.delete();
            }
        } catch (FileNotFoundException e) {
            // Manejar el caso de que el archivo no exista
//            e.printStackTrace();
        } catch (IOException e) {
            // Manejar problemas de lectura/escritura de archivos
            e.printStackTrace();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            // Manejar excepciones relacionadas con la desencriptación incorrecta
//            e.printStackTrace();
            descifrado = false;
        }
        return descifrado;
    }

}
