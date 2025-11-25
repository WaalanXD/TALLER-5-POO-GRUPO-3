package actividad05;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private JButton botonAgregar;
    private JButton botonEliminar;
    private JButton botonActualizar;
    private JButton botonLeer;
    private JButton botonLimpiar;
    private Container contenedor;
    private JLabel nombre;
    private JLabel telefono;
    private JTextField campoNombre;
    private JTextField campoTelefono;

    public VentanaPrincipal() {
        inicio();
        setTitle("Gestión de Amigos");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

    private void inicio() {
        contenedor = getContentPane();
        contenedor.setLayout(null);

        nombre = new JLabel("Nombre:");
        telefono = new JLabel("Teléfono:");
        campoNombre = new JTextField();
        campoTelefono = new JTextField();

        nombre.setBounds(10, 10, 80, 25);
        campoNombre.setBounds(100, 10, 160, 25);
        telefono.setBounds(10, 40, 80, 25);
        campoTelefono.setBounds(100, 40, 160, 25);

        botonAgregar = new JButton("Agregar");
        botonEliminar = new JButton("Eliminar");
        botonActualizar = new JButton("Actualizar");
        botonLeer = new JButton("Leer");
        botonLimpiar = new JButton("Limpiar");
        botonAgregar.setBounds(10, 80, 100, 25);
        botonEliminar.setBounds(120, 80, 100, 25);
        botonActualizar.setBounds(230, 80, 100, 25);
        botonLeer.setBounds(10, 120, 100, 25);
        botonLimpiar.setBounds(120, 120, 100, 25);
        botonAgregar.addActionListener(this);
        botonEliminar.addActionListener(this);
        botonActualizar.addActionListener(this);
        botonLeer.addActionListener(this);
        botonLimpiar.addActionListener(this);
        

        contenedor.add(nombre);
        contenedor.add(campoNombre);
        contenedor.add(telefono);
        contenedor.add(campoTelefono);
        contenedor.add(botonAgregar);
        contenedor.add(botonEliminar);
        contenedor.add(botonActualizar);
        contenedor.add(botonLeer);
        contenedor.add(botonLimpiar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == botonAgregar) {

            try {
            String nombreStr = campoNombre.getText().trim();
            String telTexto = campoTelefono.getText().trim();

            if (nombreStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo Nombre no puede estar vacío.");
                campoNombre.requestFocus();
                return;
            }

            if (telTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo Teléfono no puede estar vacío.");
                campoTelefono.requestFocus();
                return;
            }

            if (!telTexto.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "El campo Teléfono debe contener solo números.");
                campoTelefono.requestFocus();
                return;
            }

            Amigos grupo = new Amigos();
            grupo.agregarAmigo(nombreStr, telTexto);

           

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar amigo: " + ex.getMessage());
            }

        } else if (e.getSource() == botonEliminar) {
            try {
                String nombreStr = campoNombre.getText().trim();
                String telTexto = campoTelefono.getText().trim();

                if (nombreStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo Nombre no puede estar vacío.");
                    campoNombre.requestFocus();
                    return;
                }

                
                Amigos grupo = new Amigos();
                grupo.eliminarAmigo(nombreStr, telTexto);


            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar amigo: " + ex.getMessage());
            }
        } else if (e.getSource() == botonActualizar) {
            try {
                String nombreStr = campoNombre.getText().trim();
                String telTexto = campoTelefono.getText().trim();

                if (nombreStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo Nombre no puede estar vacío.");
                    campoNombre.requestFocus();
                    return;
                }
                if (telTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo Teléfono no puede estar vacío.");
                    campoTelefono.requestFocus();
                    return;
                }
                if (!telTexto.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "El campo Teléfono debe contener solo números.");
                    campoTelefono.requestFocus();
                    return;
                }

                Amigos grupo = new Amigos();
                grupo.actualizarAmigo(nombreStr, telTexto);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar amigo: " + ex.getMessage());
    }


            } else if (e.getSource() == botonLeer) {

            try {
                String nombreStr = campoNombre.getText().trim();

                if (nombreStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo Nombre no puede estar vacío.");
                    campoNombre.requestFocus();
                    return;
                }
                Amigos grupo = new Amigos();
                grupo.leerAmigo(nombreStr);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al leer amigos: " + ex.getMessage());
            }
        } else if (e.getSource() == botonLimpiar) {
            campoNombre.setText("");
            campoTelefono.setText("");
        }
    
}

}

