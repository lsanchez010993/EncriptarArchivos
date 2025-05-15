import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EncriptarDesencriptar extends JDialog {
    private JPasswordField passwordField;
    private JTextField numLongitudPass;
    private JButton encriptarButton;
    private JButton desencriptarButton;
    private JButton encriptarDesencriptarRandon;
    private JCheckBox changeExtension;
    private JCheckBox encriptarPass;
    private JCheckBox cambiarRuta;
   public static int longitud_Pass_Encriptado;
    public static String ruta_directorio = "E:/oo";
    public static final int DESPLAZAMIENTO = 9;
    public static String pass;
    private JLabel labelLongitud;
    private JButton atras;


    public EncriptarDesencriptar(Frame parent, String title) {
        super(parent, title, true);


        JPanel panelPrincipal = new JPanel();

        panelPrincipal.setLayout(new GridLayout(6, 2));


        JLabel contrase = new JLabel("Introduce la contraseña:");

        passwordField = new JPasswordField(30);
        encriptarButton = new JButton("Encriptar");
        desencriptarButton = new JButton("Desencriptar");
        changeExtension = new JCheckBox("Cambiar extensión");
        encriptarPass = new JCheckBox("Encriptar password");
        encriptarDesencriptarRandon = new JButton("Encriptado Random");
        cambiarRuta = new JCheckBox("Cambiar ruta por defecto");
        atras = new JButton("Atras");
        labelLongitud = new JLabel("Longitud del nuevo password:");
        numLongitudPass = new JTextField();
        panelPrincipal.add(contrase);
        panelPrincipal.add(passwordField);
        panelPrincipal.add(changeExtension);
        panelPrincipal.add(cambiarRuta);
        panelPrincipal.add(cambiarRuta);
        panelPrincipal.add(encriptarPass);
        panelPrincipal.add(new JLabel());
        panelPrincipal.add(labelLongitud);
        panelPrincipal.add(numLongitudPass);
        numLongitudPass.setText("0");
        panelPrincipal.add(encriptarButton);
        panelPrincipal.add(desencriptarButton);
        panelPrincipal.add(encriptarDesencriptarRandon);


        Color rojoClaro = new Color(248, 114, 114);

        encriptarDesencriptarRandon.setBackground(rojoClaro);
        encriptarPass.setSelected(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                dispose();
                System.exit(1);
            }
        });


        atras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                JFrame frame = new JFrame("Encriptar/Desencriptar");
                new EncriptarDesencriptar(frame, "Encriptar/Desencriptar");
            }
        });
        encriptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean contieneTrue = false;
                boolean contieneFalse = false;


                if (Integer.parseInt(numLongitudPass.getText()) == 0) {
                    longitud_Pass_Encriptado = passwordField.getPassword().length;
                } else {
                    longitud_Pass_Encriptado = Integer.parseInt(numLongitudPass.getText());
                    pass = Metodos.igualar_a_X(Metodos.sumarNumeros(Metodos.char_a_String(passwordField.getPassword())),
                            Metodos.char_a_String(passwordField.getPassword()));
                    System.out.println("Pass igualado: " + pass);
                }
                if (passwordField.getPassword().length > longitud_Pass_Encriptado) {
                    System.out.println("El password es más largo que la longitud introducida. Por lo tanto este ha sido reducido");
                }
                if (cambiarRuta.isSelected()) {
                    ruta_directorio = Metodos.mensajeIntroInfo("Introduce la ruta:", new JFrame());
                }
                if (changeExtension.isSelected()) {
                    try {
                        CambiarExtension.renombrarExtensionArchivos(ruta_directorio);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (encriptarPass.isSelected()) {
                    pass = Metodos.encriptarPass(pass);
                    System.out.println(pass);

                }

                try {
                    Encriptar.encriptarDirectorio(ruta_directorio, pass);
                    if (Encriptar.encriptadoCorrecto.isEmpty()) {
                        Metodos.mostrarMensaje("No se han encontrado archivos validos para encriptar.",
                                new JFrame("Error"));
                    }
                    if (Encriptar.encriptadoCorrecto.contains(false)) {
                        contieneFalse = true;
                    }
                    if (Encriptar.encriptadoCorrecto.contains(true)) {
                        contieneTrue = true;

                    }
                    Encriptar.encriptadoCorrecto.clear();
                    if (contieneFalse && contieneTrue) {
                        Metodos.mostrarMensaje("No todos los archivos se han encriptado con exito",
                                new JFrame("Información"));
                        return;

                    }
                    if (contieneFalse) {
                        Metodos.mostrarMensaje("No se han podido encriptar los archivos",
                                new JFrame("Error"));
                    }
                    if (contieneTrue) {
                        Metodos.mostrarMensaje("Archivo/s encriptado/s con éxito.", new JFrame("Información"));
                        System.exit(0);
                    }

                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidKeySpecException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });


        desencriptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean contieneTrue = false;
                boolean contieneFalse = false;

                if (Integer.parseInt(numLongitudPass.getText()) == 0) {
                    longitud_Pass_Encriptado = passwordField.getPassword().length;
                } else {
                    longitud_Pass_Encriptado = Integer.parseInt(numLongitudPass.getText());
                    pass = Metodos.igualar_a_X(Metodos.sumarNumeros(Metodos.char_a_String(passwordField.getPassword())),
                            Metodos.char_a_String(passwordField.getPassword()));
                    System.out.println("Pas igualado: " + pass);
                }
                if (passwordField.getPassword().length > longitud_Pass_Encriptado) {
                    System.out.println("El password es más largo que la longitud introducida. Por lo tanto este ha sido reducido");
                }

                if (cambiarRuta.isSelected()) {
                    ruta_directorio = Metodos.mensajeIntroInfo("Introduce la ruta:", new JFrame());
                }
                if (encriptarPass.isSelected()) {
                    pass = Metodos.encriptarPass(pass);
                    System.out.println(pass);
                } 
                try {
                    Desencriptar.desencriptarDirectorio(ruta_directorio, pass);
                    if (Desencriptar.desencriptadoCorrecto.contains(false)) {
                        contieneFalse = true;
                    }
                    if (Desencriptar.desencriptadoCorrecto.contains(true)) {
                        contieneTrue = true;

                    }
                    if (contieneFalse && contieneTrue) {
                        Metodos.mostrarMensaje("No todos los archivos se han desencriptado con exito",
                                new JFrame("Información"));
                        System.exit(0);
                    }
                    if (contieneFalse) {
                        Metodos.mostrarMensaje("Contraseña incorrecta. No se han podido desencriptar los archivos",
                                new JFrame("Información"));
                    }
                    if (contieneTrue) {
                        Metodos.mostrarMensaje("Archivo/s desencriptado/s con éxito.", new JFrame("Información"));
                    }
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidKeySpecException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (changeExtension.isSelected()) {
                    try {
                        CambiarExtension.renombrarExtensionArchivos(ruta_directorio);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }


                System.exit(0); // Cerrar el diálogo después de realizar la acción
            }
        });

        encriptarDesencriptarRandon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    EncriptarDesencriptarRandom ventana = new EncriptarDesencriptarRandom("Encriptar/Desencriptar Random");
                    ventana.setVisible(true);
                });
            }
        });


        add(panelPrincipal);

        pack();

        setLocationRelativeTo(parent);

        setVisible(true);

        setModalityType(ModalityType.APPLICATION_MODAL);


    }


}
