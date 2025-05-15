import javax.swing.*;
import java.io.*;
import java.util.*;

public class Metodos implements Serializable {
    public static Set<String> listaSet = new HashSet<>();
    public static List<String> lista = new ArrayList<>();

    static String char_a_String(char[] password) {

        String pass = "";
        for (char car : password) {
            pass += "" + car;
        }
        return pass;
    }

    public static String mensajeIntroInfo(String mensaje, JFrame frame) {
        return JOptionPane.showInputDialog(frame, mensaje);
    }

    public static void mostrarMensaje(String mensaje, JFrame frame) {
        JOptionPane.showMessageDialog(frame, mensaje);
    }



    public static String encriptarPass(String pass) {

        String[] conjuntoCaracteres = new String[]{"!", " ", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",",
                "-", ".", "/", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ":", ";", "<", "=", ">", "?", "@", "A",
                "B", "C", "D", "E", "F", "G", "X", "Y", "Z", "[", "\\", "]", "^", "_", "`", "a", "b", "c", "d", "e",
                "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "u", "v", "w", "x", "y", "z", "{", "|", "}",
                "~", "Á", "É", "Í", "Ó", "Ú", "á", "é", "í", "ó", "ú"};
//        if (cambiarLongitud) pass = Metodos.igualar_a_X(sumarNumeros(pass), pass);

        int i;
        for (i = 0; i < conjuntoCaracteres.length / 2; i += 2) {
            i = conjuntoCaracteres.length - 1 - i;
            intercambiarPosiciones(conjuntoCaracteres, i, i);
        }

        StringBuilder resultado = new StringBuilder();


        i = 1;
        char[] var6 = pass.toCharArray();
        int var7 = var6.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            char caracter = var6[var8];
            String caracterActual = String.valueOf(caracter);
            if (Arrays.asList(conjuntoCaracteres).contains(caracterActual)) {
                int indice = Arrays.asList(conjuntoCaracteres).indexOf(caracterActual);
                int nuevoIndice = (indice + EncriptarDesencriptar.DESPLAZAMIENTO * i) % conjuntoCaracteres.length;
                resultado.append(conjuntoCaracteres[nuevoIndice]);
                ++i;
            } else {
                resultado.append(caracter);
            }
        }

        return resultado.toString();
    }

    static int sumarNumeros(String pass) {
        ArrayList<Character> characterArrayList = new ArrayList<Character>();
        for (int i = 0; i < pass.length(); i++) {
            characterArrayList.add(pass.charAt(i));
        }
        int sumaNum = 0;
        for (char carac : characterArrayList) {
            if (Character.isDigit(carac)) {
                sumaNum += carac;
            } else if (Character.isLetter(carac)) {
                sumaNum += carac * 2;
            } else {
                sumaNum += carac * 99;
            }
        }

        return sumaNum;
    }

    static String igualar_a_X(int sumaNum, String pass) {
        int contador = 2;
        StringBuilder nuevoPass = new StringBuilder(pass);

        while (nuevoPass.length() != EncriptarDesencriptar.longitud_Pass_Encriptado) {
            nuevoPass.append(sumaNum * contador);
            ++contador;
            if (nuevoPass.length() > EncriptarDesencriptar.longitud_Pass_Encriptado) {
                nuevoPass.delete(EncriptarDesencriptar.longitud_Pass_Encriptado, nuevoPass.length());
            }
        }

        return nuevoPass.toString();
    }

    static <T> void intercambiarPosiciones(T[] array, int posicion1, int posicion2) {
        T temp = array[posicion1];
        array[posicion1] = array[posicion2];
        array[posicion2] = temp;
    }

    public static void generarCombinaciones(String palabrasClave, String combinaciones) {
        String[] palabras = palabrasClave.split(" ");
        String[] combinaciones1 = combinaciones.split(" ");

        int numPalabras = palabras.length;
        if (numPalabras < 3 || numPalabras > 7) throw new Error("El password debe contener entre 3 y 7 palabras");

        switch (numPalabras) {
            case 3:
                for (String s : combinaciones1) {
                    for (String string : combinaciones1) {
                        String password = palabras[0] + s + palabras[1] + string + palabras[2];
                        listaSet.add(password);
                    }
                }
                break;
            case 4:
                for (String s : combinaciones1) {
                    for (String string : combinaciones1) {
                        for (String value : combinaciones1) {
                            String password = palabras[0] + s + palabras[1] + string +
                                    palabras[2] + value + palabras[3];
                            listaSet.add(password);
                        }
                    }
                }
                break;
            case 5:
                for (String s : combinaciones1) {
                    for (String string : combinaciones1) {
                        for (String value : combinaciones1) {
                            for (String item : combinaciones1) {
                                String password = palabras[0] + string + palabras[1] + value +
                                        palabras[2] + item + palabras[3] + s + palabras[4];
                                listaSet.add(password);

                            }
                        }
                    }
                }
                break;
            case 6:
                for (String s : combinaciones1) {
                    for (String string : combinaciones1) {
                        for (String value : combinaciones1) {
                            for (String item : combinaciones1) {
                                for (String element : combinaciones1) {
                                    String password = palabras[0] + value + palabras[1] + item +
                                            palabras[2] + element + palabras[3] + string +
                                            palabras[4] + s + palabras[5];
                                    listaSet.add(password);
                                }
                            }
                        }
                    }
                }
                break;
            case 7:
                for (String s : combinaciones1) {
                    for (String string : combinaciones1) {
                        for (String value : combinaciones1) {
                            for (String item : combinaciones1) {
                                for (String element : combinaciones1) {
                                    for (String s1 : combinaciones1) {
                                        String password = palabras[0] + value + palabras[1] + item +
                                                palabras[2] + element + palabras[3] + string +
                                                palabras[4] + s + palabras[5] + s1 + palabras[6];
                                        listaSet.add(password);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            default:
                System.out.println("Error. Se deben introducir entre 3 y 7 palabras");

        }
        lista.addAll(listaSet);
    }

    public static <T> T passAleatorio(int numPass, boolean escribirNum) throws IOException {
        int indiceAleatorio;
        if (listaSet.isEmpty()) {
            return null; // Devolver null si el lista está vacío
        }

        // Convertir el lista a un array
        Object[] array = listaSet.toArray();

        // Obtener un índice aleatorio
        if (numPass != 0) {
            indiceAleatorio = numPass;
        } else indiceAleatorio = new Random().nextInt(1,array.length);
        //tener en cuenta que 0 (el primer valor del array), jamas se debe tomar. Por eso comienza por 1.
        if (escribirNum) {
            escribirNum(indiceAleatorio);
            Metodos.mostrarMensaje("El numero seleccionado para desencriptar el archivo es: "+indiceAleatorio,new JFrame());
        }else Metodos.mostrarMensaje("El numero seleccionado para desencriptar el archivo es: "+indiceAleatorio,new JFrame());
        // Devolver el elemento en el índice aleatorio
        return (T) array[indiceAleatorio];
    }

    public static void escribirNum(int numAleatorio) throws FileNotFoundException {
        // Crear un archivo con el nombre basado en numAleatorio
        File num = new File(EncriptarDesencriptar.ruta_directorio + File.separator + numAleatorio + ".txt");

        try (PrintStream txt = new PrintStream(num)) {
            txt.println(numAleatorio); // Cambiado a println para que imprima en una nueva línea
            System.out.println("Archivo creado con éxito: " + num.getName());
        } catch (FileNotFoundException e) {
            throw e; // Re-lanzar la excepción para manejarla en un nivel superior si es necesario
        }
    }
}