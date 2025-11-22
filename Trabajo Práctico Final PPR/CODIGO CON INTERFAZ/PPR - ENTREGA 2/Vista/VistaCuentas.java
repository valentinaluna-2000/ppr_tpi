package Vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;


import Controlador.ControladorCuenta;
import Controlador.ControladorIngresoEgreso;
import Modelo.Cuenta;
import Modelo.Usuario;
import Modelo.Vehiculo;
import Modelo.IngresoEgreso;

public class VistaCuentas extends JPanel {

    private final ControladorCuenta cuentaController;
    private final ControladorIngresoEgreso ingresoEgresoController;

    private JList<Cuenta> listaCuentas;
    private DefaultListModel<Cuenta> modeloLista;

    private JTextField txtBuscarNumero;

    // Campos para modificar datos (desde la cuenta seleccionada)
    private JTextField txtDni;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    
    private JTextField txtPatente;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtAseguradora;

    private JTextArea movimientosArea;

    // Botones edición
    private JButton btnModificarDatos;
    private JButton btnGuardarCambios;

    public VistaCuentas(ControladorCuenta cuentaController,ControladorIngresoEgreso ingresoEgresoController) {
        this.cuentaController = cuentaController;
        this.ingresoEgresoController = ingresoEgresoController;
        inicializarComponentes();
        cargarCuentas();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(5, 5));

        // Arriba: búsqueda 
        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscarNumero = new JTextField(8);
        JButton btnBuscar = new JButton("Buscar por número");
        btnBuscar.addActionListener(e -> buscarCuentaPorNumero());
        JButton btnRefrescar = new JButton("Refrescar lista");
        btnRefrescar.addActionListener(e -> cargarCuentas());

        arriba.add(new JLabel("Nº de cuenta:"));
        arriba.add(txtBuscarNumero);
        arriba.add(btnBuscar);
        arriba.add(btnRefrescar);

        add(arriba, BorderLayout.NORTH);

        // Izquierda: lista de cuentas 
        modeloLista = new DefaultListModel<>();
        listaCuentas = new JList<>(modeloLista);
        listaCuentas.setCellRenderer(new CuentaRenderer());
        listaCuentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCuentas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDatosCuentaSeleccionada();
            }
        });

        JScrollPane scrollLista = new JScrollPane(listaCuentas);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Cuentas"));
        scrollLista.setPreferredSize(new Dimension(220, 400));

        add(scrollLista, BorderLayout.WEST);

        // Derecha: datos y movimientos 
        JPanel panelDerecha = new JPanel(new BorderLayout(5, 5));

        // Panel de datos
        JPanel panelDatos = new JPanel(new GridLayout(0, 2, 5, 5));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de usuario y vehículo"));

        // Usuario
        txtDni = new JTextField(10);
        txtDni.setEditable(false);  // NO se modifica DNI
        txtNombre = new JTextField(10);
        txtApellido = new JTextField(10);
        txtTelefono = new JTextField(10);
        txtEmail = new JTextField(10);

        // No son editables hasta que se presiona "Modificar"
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtTelefono.setEditable(false);
        txtEmail.setEditable(false);

        panelDatos.add(new JLabel("DNI:"));
        panelDatos.add(txtDni);
        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(txtNombre);
        panelDatos.add(new JLabel("Apellido:"));
        panelDatos.add(txtApellido);
        panelDatos.add(new JLabel("Teléfono:"));
        panelDatos.add(txtTelefono);
        panelDatos.add(new JLabel("Email:"));
        panelDatos.add(txtEmail);

        // Vehículo
        txtPatente = new JTextField(10);
        txtPatente.setEditable(false);
        txtMarca = new JTextField(10);
        txtMarca.setEditable(false);
        txtModelo = new JTextField(10);
        txtModelo.setEditable(false);
        txtAseguradora = new JTextField(10);
        txtAseguradora.setEditable(false); 

        panelDatos.add(new JLabel("Patente:"));
        panelDatos.add(txtPatente);
        panelDatos.add(new JLabel("Marca:"));
        panelDatos.add(txtMarca);
        panelDatos.add(new JLabel("Modelo:"));
        panelDatos.add(txtModelo);
        panelDatos.add(new JLabel("Aseguradora:"));
        panelDatos.add(txtAseguradora);

        // Botones de edición
        btnModificarDatos = new JButton("Modificar datos de cuenta");
        btnGuardarCambios = new JButton("Guardar cambios");
        btnGuardarCambios.setEnabled(false); 

        btnModificarDatos.addActionListener(e -> habilitarEdicion());
        btnGuardarCambios.addActionListener(e -> guardarCambios());

        JPanel abajoDatos = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        abajoDatos.add(btnModificarDatos);
        abajoDatos.add(btnGuardarCambios);

        JPanel panelDatosCompleto = new JPanel(new BorderLayout());
        panelDatosCompleto.add(panelDatos, BorderLayout.CENTER);
        panelDatosCompleto.add(abajoDatos, BorderLayout.SOUTH);

        // Panel movimientos
        movimientosArea = new JTextArea(10, 30);
        movimientosArea.setEditable(false);
        JScrollPane scrollMov = new JScrollPane(movimientosArea);
        scrollMov.setBorder(BorderFactory.createTitledBorder("Movimientos de la cuenta"));

        panelDerecha.add(panelDatosCompleto, BorderLayout.NORTH);
        panelDerecha.add(scrollMov, BorderLayout.CENTER);

        add(panelDerecha, BorderLayout.CENTER);
    }

    private void cargarCuentas() {
        modeloLista.clear();
        List<Cuenta> cuentas = cuentaController.obtenerCuentas();
        for (Cuenta c : cuentas) {
            modeloLista.addElement(c);
        }
    }

    private void buscarCuentaPorNumero() {
    try {
        int numero = Integer.parseInt(txtBuscarNumero.getText().trim());

        // Actualizamos la lista con las cuentas actuales
        cargarCuentas();

        Cuenta cuenta = cuentaController.buscarPorNumero(numero);
        if (cuenta == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró una cuenta con ese número.",
                    "Sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        listaCuentas.setSelectedValue(cuenta, true);

        } 
        
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El número de cuenta debe ser numérico.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
       }
    }

    
    // Mostrar datos de la cuenta seleccionada en los campos
    private void mostrarDatosCuentaSeleccionada() {
        Cuenta cuenta = listaCuentas.getSelectedValue();
        if (cuenta == null) {
            limpiarCampos();
            movimientosArea.setText("");
            return;
        }

        Usuario u = cuenta.getUsuario();
        Vehiculo v = cuenta.getVehiculo();

        if (u != null) {
            txtDni.setText(String.valueOf(u.getDni()));
            txtNombre.setText(u.getNombre());
            txtApellido.setText(u.getApellido());
            txtTelefono.setText(u.getTelefono());
            txtEmail.setText(u.getEmail());
        } else {
            txtDni.setText("");
            txtNombre.setText("");
            txtApellido.setText("");
            txtTelefono.setText("");
            txtEmail.setText("");
        }

        if (v != null) {
            txtPatente.setText(v.getPatente());
            txtMarca.setText(v.getMarca());
            txtModelo.setText(v.getModelo());
            txtAseguradora.setText(v.getAseguradora());
        } else {
            txtPatente.setText("");
            txtMarca.setText("");
            txtModelo.setText("");
            txtAseguradora.setText("");
        }

        // Si se selecciona una cuenta, vuelve a modo solo lectura, no deja modificar
        setEdicionHabilitada(false);
        btnGuardarCambios.setEnabled(false);
        // Cargar movimientos
        List<IngresoEgreso> movimientos = ingresoEgresoController.obtenerMovimientosPorCuenta(cuenta);
        StringBuilder sb = new StringBuilder();
        for (IngresoEgreso mov : movimientos) {
            sb.append("Transacción Nº ").append(mov.getNumeroTransaccion())
            .append(" - Tipo: ").append(mov.getTipoMovimiento())
            .append(" - Lugar: ").append(mov.getLugar().getId())
            .append("\n");
        }
        movimientosArea.setText(sb.toString());
    } 




    // Limpiar campos de texto
    private void limpiarCampos() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtPatente.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtAseguradora.setText("");

        setEdicionHabilitada(false);
        btnGuardarCambios.setEnabled(false);
    }

    // Habilitar o deshabilitar edición de campos (excepto DNI, patente, marca y modelo)
    private void setEdicionHabilitada(boolean enabled) {
        txtNombre.setEditable(enabled);
        txtApellido.setEditable(enabled);
        txtTelefono.setEditable(enabled);
        txtEmail.setEditable(enabled);
        txtAseguradora.setEditable(enabled);
    }

    // Habilitar edición al presionar el botón
    private void habilitarEdicion() {
        Cuenta cuenta = listaCuentas.getSelectedValue();
        if (cuenta == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una cuenta de la lista.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        setEdicionHabilitada(true);
        btnGuardarCambios.setEnabled(true);
    }
    
    // Guardar cambios de los datos modificados
    private void guardarCambios() {
        Cuenta cuenta = listaCuentas.getSelectedValue();
        if (cuenta == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una cuenta de la lista.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String nuevaAseguradora = txtAseguradora.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()
                || email.isEmpty() || nuevaAseguradora.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos deben estar completos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar datos mediante el controlador
        cuentaController.actualizarDatosUsuario(cuenta, nombre, apellido, telefono, email);
        cuentaController.actualizarAseguradoraVehiculo(cuenta, nuevaAseguradora);

        JOptionPane.showMessageDialog(this,
                "Datos actualizados correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        setEdicionHabilitada(false);
        btnGuardarCambios.setEnabled(false);
        listaCuentas.repaint();
    }


    // Usamos renderer para mostrar número de cuenta en el JComboBox
    private static class CuentaRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list,
        Object value,
        int index,
        boolean isSelected,
        boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Cuenta c) {
            setText("Cuenta Nº " + c.getNumeroCuenta());
        }
        return this;
        }
    }
    
    // Método para refrescar la vista, es decir
    public void refrescar() {
    cargarCuentas();
    limpiarCampos();
    movimientosArea.setText("");
    }
}
