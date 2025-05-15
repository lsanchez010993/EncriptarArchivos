import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EncriptarDesencriptarRandom extends JFrame {
    private boolean cifrarPass = false;
    private JPasswordField palabrasClave;
    private JPasswordField combinaciones;
    private JCheckBox encriptarPassRandom;
    private JCheckBox encriptarPass;
    private JButton encriptarButtonRandom;
    private JButton desencriptarButtonRandom;
    private JCheckBox changeExtension;
    private JCheckBox cambiarRuta;
    private JButton atras;
    private JTextField num;
    private JCheckBox generarTXT;


    public EncriptarDesencriptarRandom(String title) {
        super(title);
        initComponents();
        addComponents();
        addListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Centrar ventana en la pantalla
        setVisible(true);
    }

    private void initComponents() {
        palabrasClave = new JPasswordField(70);
        combinaciones = new JPasswordField(70);
        encriptarPassRandom = new JCheckBox("Encriptar Password");
        encriptarButtonRandom = new JButton("Encriptar");
        desencriptarButtonRandom = new JButton("Desencriptar");
        atras = new JButton("Atras");
        encriptarPass = new JCheckBox("Encriptar pass");
        changeExtension = new JCheckBox("Cambiar extension");
        cambiarRuta = new JCheckBox("Cambiar ruta por defecto");
        generarTXT = new JCheckBox("Guardar numero en archivo .txt");
    }

    private void addComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(createLabelPanel("Escribe las palabras clave separadas por un espacio: "));
        panel.add(palabrasClave);
        panel.add(createLabelPanel("Escribe las diferentes combinaciones separadas por un espacio: "));
        panel.add(combinaciones);

// Panel para los botones encriptar y desencriptar
        JPanel botonesPanel = new JPanel(new GridLayout(0, 3));
        botonesPanel.add(encriptarPass);
        botonesPanel.add(changeExtension);
        botonesPanel.add(cambiarRuta);
        botonesPanel.add(generarTXT);
        botonesPanel.add(new JPanel());
        botonesPanel.add(new JPanel());

        botonesPanel.add(encriptarButtonRandom);
        botonesPanel.add(new JPanel());
        botonesPanel.add(desencriptarButtonRandom);

//        encriptarPass.setSelected(false);
        panel.add(botonesPanel); // Agregar el panel de botones al panel principal

// Crear un nuevo JPanel para los botones con BorderLayout
        JPanel botonesBorderPanel = new JPanel(new BorderLayout());

// Integrar los botones con BorderLayout en el nuevo JPanel

        JLabel b = new JLabel("Especifica un numero: ");
        b.setPreferredSize(new Dimension(155, 25));

        botonesBorderPanel.add(b, BorderLayout.LINE_START);
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setPreferredSize(new Dimension(450, 25));
                // Cuando el mouse entra, cambiamos el texto del JLabel
                b.setText("Se utilizara dicho numero para tomar un valor de la lista de combinaciones.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setPreferredSize(new Dimension(155, 25));
                // Cuando el mouse sale, restauramos el texto original del JLabel
                b.setText("Especifica un numero:");
            }
        });

        num = new JTextField("0");

        botonesBorderPanel.add(num, BorderLayout.CENTER);
        botonesBorderPanel.add(atras, BorderLayout.AFTER_LAST_LINE);


// Agregar el nuevo JPanel de botones con BorderLayout al panel principal
        panel.add(botonesBorderPanel);

        getContentPane().add(panel);

    }

    private JPanel createLabelPanel(String text) {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS)); // Establecer BoxLayout horizontal

        JLabel label = new JLabel("<html><div style='text-align: left;'>" + text + "</div></html>");
        label.setAlignmentY(Component.TOP_ALIGNMENT); // Alineación del JLabel en el eje Y
        labelPanel.add(label);
        return labelPanel;

    }

    private void addListeners() {
        atras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JFrame frame = new JFrame("Encriptar/Desencriptar");
                new EncriptarDesencriptar(frame, "Encriptar/Desencriptar");
            }
        });

        encriptarButtonRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean contieneTrue = false;
                boolean contieneFalse = false;

                boolean guardarTxt = false;
                try {
                    Metodos.generarCombinaciones(Metodos.char_a_String(palabrasClave.getPassword()),
                            Metodos.char_a_String(combinaciones.getPassword()));
                    if (Metodos.listaSet.size() < Integer.parseInt(num.getText()) && Integer.parseInt(num.getText()) != 0) {
                        throw new Error("El numero de combinaciones generadas es inferior al numero seleccionado");
                    }

                    if (generarTXT.isSelected()) {
                        guardarTxt = true;
                    }
                    if (cambiarRuta.isSelected()) {
                        EncriptarDesencriptar.ruta_directorio = Metodos.mensajeIntroInfo("Introduce la ruta:", new JFrame());
                    }
                    if (changeExtension.isSelected()) {
                        try {
                            CambiarExtension.renombrarExtensionArchivos(EncriptarDesencriptar.ruta_directorio);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (encriptarPass.isSelected()) {
                        cifrarPass = true;
                    }

                    try {
                        EncriptadoRandom.encriptadoRandom(cifrarPass, guardarTxt, Integer.parseInt(num.getText()));
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
                        Encriptar.encriptadoCorrecto.clear();// limpia el array de boleadons
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
                    } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
                        throw new RuntimeException(ex);
                    }

                } catch (Error er) {
                    System.out.println(er.getMessage());
                    Metodos.mostrarMensaje("Error: El numero de combinaciones generadas es inferior al numero preSeleccionado", new JFrame());


                }
            }

        });

        desencriptarButtonRandom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean contieneTrue = false;
                boolean contieneFalse = false;
                try {
                    Metodos.generarCombinaciones(Metodos.char_a_String(palabrasClave.getPassword()),
                            Metodos.char_a_String(combinaciones.getPassword()));
                    if (Metodos.listaSet.size() < Integer.parseInt(num.getText()) && Integer.parseInt(num.getText()) != 0) {
                        throw new Error("El numero de combinaciones generadas es inferior al numero preSeleccionado");
                    }
                    if (cambiarRuta.isSelected()) {
                        EncriptarDesencriptar.ruta_directorio = Metodos.mensajeIntroInfo("Introduce la ruta:", new JFrame());
                    }

                    if (encriptarPass.isSelected()) {
                        cifrarPass = true;
                    }
                    try {
                        DesencriptadoRandom.desencriptadoRandom(cifrarPass, Integer.parseInt(num.getText()));
                        if (Desencriptar.desencriptadoCorrecto.contains(false)) {
                            contieneFalse = true;
                        }
                        if (Desencriptar.desencriptadoCorrecto.contains(true)) {
                            contieneTrue = true;

                        }
                        Desencriptar.desencriptadoCorrecto.clear();// limpia el array de boleadons
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
                    } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (changeExtension.isSelected()) {
                        try {
                            CambiarExtension.renombrarExtensionArchivos(EncriptarDesencriptar.ruta_directorio);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } catch (Error er) {
                    System.out.println(er);
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Error: El numero de combinaciones generadas es inferior al numero preSeleccionado");
                }
            }
        });
    }


}
