import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EncriptadoRandom {
    public static void encriptadoRandom(boolean cifrarPass, boolean escribirNum, int numPreseleccionado) throws
            IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String pass;

//       Metodos.generarCombinaciones(palabrasClave, combinaciones);
       System.out.println("Numero de combinaciones generadas: " + Metodos.listaSet.size());

       //Si 'numPreseleccionado' es 0, selecciona un numero aleatorio, en caso contrario devuelve el pass que se encuentre en la posicion de 'numPreselec'
        pass = Metodos.passAleatorio(numPreseleccionado,escribirNum);
        System.out.println("Pass sin cifrar: " + pass);

        if (cifrarPass){

            pass = Metodos.encriptarPass(pass);
            System.out.println("Pass cifrado: " + pass);
        }

        Metodos.listaSet.clear();//Importante vaciar la lista.
        try {
            Encriptar.encriptarDirectorio(EncriptarDesencriptar.ruta_directorio, pass);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }
}
