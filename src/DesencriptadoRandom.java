import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class DesencriptadoRandom {


    public static void desencriptadoRandom(boolean cifrarPass, int num) throws
            IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        System.out.println("Numero de combinaciones generadas: " + Metodos.listaSet.size());
        if (num == 0) encontrarPassRandom(EncriptarDesencriptar.ruta_directorio, cifrarPass);
        else {
            String pass = Metodos.lista.get(num);
            if (cifrarPass) pass=Metodos.encriptarPass(pass);
            Desencriptar.desencriptarDirectorio(EncriptarDesencriptar.ruta_directorio, pass);
        }
    }


    static void encontrarPassRandom(String ruta, boolean cifrarPass) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        Desencriptar.encontrarPass = true; //Se utiliza en la funcion 'descifrarArchivoAES'. Cuando esta true no se eliminan los archivos que se desencriptan
        boolean correcto2;
        boolean correcto3 = false;
        File archivos = new File(ruta);
        File[] archivosArray = archivos.listFiles();
        assert archivosArray != null;
        if (archivosArray.length < 3) {
            Metodos.mostrarMensaje("Error: El directorio debe contener, al menos, 3 archivos", new JFrame());
            throw new Error("El directorio contiene menos de tres archivos");

        }
        File archivo1 = archivosArray[0];
        File archivo2 = archivosArray[1];
        File archivo3 = archivosArray[2];
        String pass;
        int contador = 0;
        for (String clave : Metodos.listaSet) {
            if (cifrarPass) pass = Metodos.encriptarPass(clave);
            else pass = clave;



            if (Desencriptar.desencriptarDirectorio(String.valueOf(archivo1), pass)) {
                System.out.println("Intento numero: " + contador + " Primer pass probado " + pass);

                correcto2 = Desencriptar.desencriptarDirectorio(String.valueOf(archivo2), pass);
                if (correcto2) {
                    System.out.println("\tSegundo paas probado " + pass);
                    correcto3 = Desencriptar.desencriptarDirectorio(String.valueOf(archivo3), pass);
                }
                if (correcto3) {
                    System.out.println("\t\tTeercer paas probado " + pass);
                    
                }
                if (correcto2 && correcto3) { //Esta comprobacion es necesaria ya que alguna vez da un falso positivo en correcto2
                    System.out.println("\t\t\tPassword encontrado con exito");
                    Desencriptar.encontrarPass = false;//Cuando esta en false se eliminan los archivos que se desencriptan
                    Desencriptar.desencriptarDirectorio(ruta, pass);
                    break;

                }
            }
            contador++;
        }

    }
}