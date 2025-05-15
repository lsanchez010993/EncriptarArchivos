import javax.swing.*;
import java.io.Serializable;

public class Main implements Serializable {
    public static void main(String[] args) {


        JFrame frame = new JFrame("Aplicación Principal");
        frame.setSize(370, 125);
        frame.setResizable(false);

        new EncriptarDesencriptar(frame, "Encriptar/Desencriptar");

    }
}