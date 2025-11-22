package Vista;

import Controlador.ControladorCuenta;
import Modelo.Cuenta;
import Modelo.Usuario;
import Modelo.Vehiculo;

import javax.swing.*;
import java.awt.*;

public class VistaUsuariosVehiculos extends JPanel {

    private final ControladorCuenta cuentaController;

    // Campos de alta de Usuario
    private JTextField txtDniAlta;
    private JTextField txtNombreAlta;
    private JTextField txtApellidoAlta;
    private JTextField txtTelefonoAlta;
    private JTextField txtEmailAlta;

    // Campos de alta de Vehículo
    private JTextField txtPatenteAlta;
    private JTextField txtMarcaAlta;
    private JTextField txtModeloAlta;
    private JTextField txtAseguradoraAlta;

    public VistaUsuariosVehiculos(ControladorCuenta cuentaController) {
        this.cuentaController = cuentaController;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Crear cuenta"));

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));

        // -------- Usuario --------
        txtDniAlta = new JTextField(10);
        txtNombreAlta = new JTextField(10);
        txtApellidoAlta = new JTextField(10);
        txtTelefonoAlta = new JTextField(10);
        txtEmailAlta = new JTextField(10);

        form.add(new JLabel("DNI:"));
        form.add(txtDniAlta);
        form.add(new JLabel("Nombre:"));
        form.add(txtNombreAlta);
        form.add(new JLabel("Apellido:"));
        form.add(txtApellidoAlta);
        form.add(new JLabel("Teléfono:"));
        form.add(txtTelefonoAlta);
        form.add(new JLabel("Email:"));
        form.add(txtEmailAlta);

        // -------- Vehículo --------
        txtPatenteAlta = new JTextField(10);
        txtMarcaAlta = new JTextField(10);
        txtModeloAlta = new JTextField(10);
        txtAseguradoraAlta = new JTextField(10);

        form.add(new JLabel("Patente:"));
        form.add(txtPatenteAlta);
        form.add(new JLabel("Marca:"));
        form.add(txtMarcaAlta);
        form.add(new JLabel("Modelo:"));
        form.add(txtModeloAlta);
        form.add(new JLabel("Seguro:"));
        form.add(txtAseguradoraAlta);

        JButton btnCrear = new JButton("Crear cuenta");
        btnCrear.addActionListener(e -> crearCuenta());

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        abajo.add(btnCrear);

        add(form, BorderLayout.CENTER);
        add(abajo, BorderLayout.SOUTH);
    }

        private void crearCuenta() {
        String dniStr = txtDniAlta.getText().trim();
        String nombre = txtNombreAlta.getText().trim();
        String apellido = txtApellidoAlta.getText().trim();
        String telefono = txtTelefonoAlta.getText().trim();
        String email = txtEmailAlta.getText().trim();

        String patente = txtPatenteAlta.getText().trim();
        String marca = txtMarcaAlta.getText().trim();
        String modelo = txtModeloAlta.getText().trim();
        String aseguradora = txtAseguradoraAlta.getText().trim();

        // Validar que no haya campos vacíos
        if (dniStr.isEmpty() || nombre.isEmpty() || apellido.isEmpty()
                || telefono.isEmpty() || email.isEmpty()
                || patente.isEmpty() || marca.isEmpty()
                || modelo.isEmpty() || aseguradora.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que el DNI sea numérico 
        int dni;
        try {
            dni = Integer.parseInt(dniStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El DNI debe ser numérico.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que el teléfono sea numérico, aunque se siga guardando como String
        try {
            Long.parseLong(telefono);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El teléfono debe ser numérico.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que el mail contenga un arroba
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this,
                    "El email debe contener '@'.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Usuario usuario = new Usuario(dni, nombre, apellido, telefono, email);
            // public Vehiculo(String aseguradora, String modelo, String marca, String patente)
            Vehiculo vehiculo = new Vehiculo(aseguradora, modelo, marca, patente);

            Cuenta cuenta = cuentaController.crearCuenta(usuario, vehiculo);

            JOptionPane.showMessageDialog(this,
                    "Cuenta creada Nº " + cuenta.getNumeroCuenta(),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCamposAlta();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al crear cuenta: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limpiarCamposAlta() {
        txtDniAlta.setText("");
        txtNombreAlta.setText("");
        txtApellidoAlta.setText("");
        txtTelefonoAlta.setText("");
        txtEmailAlta.setText("");
        txtPatenteAlta.setText("");
        txtMarcaAlta.setText("");
        txtModeloAlta.setText("");
        txtAseguradoraAlta.setText("");
    }
}
