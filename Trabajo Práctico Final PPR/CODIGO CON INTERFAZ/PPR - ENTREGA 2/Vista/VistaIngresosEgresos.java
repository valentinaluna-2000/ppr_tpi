package Vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import Controlador.ControladorCuenta;
import Controlador.ControladorEstacionamiento;
import Controlador.ControladorIngresoEgreso;
import Modelo.Cuenta;
import Modelo.Lugar;

public class VistaIngresosEgresos extends JPanel {

    private final ControladorEstacionamiento estController;
    private final ControladorIngresoEgreso ingresoEgresoController;
    private final ControladorCuenta cuentaController;

    private JComboBox<Cuenta> comboCuentaIngreso;
    private JComboBox<String> comboTipoLugar;
    private JComboBox<Lugar> comboLugarDisponible;
    private JLabel lblPrimerLugarSugerido;
    private JLabel lblMensajeIngreso;

    private JComboBox<Cuenta> comboCuentaEgreso;
    private JLabel lblMensajeEgreso;

    public VistaIngresosEgresos(ControladorEstacionamiento estController,
                                ControladorIngresoEgreso ingresoEgresoController,
                                ControladorCuenta cuentaController) {
        this.estController = estController;
        this.ingresoEgresoController = ingresoEgresoController;
        this.cuentaController = cuentaController;
        inicializarComponentes();
        cargarCuentas();
        actualizarLugaresDisponibles();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(5, 5));

        // Panel de ingreso 
        JPanel panelIngreso = new JPanel(new GridLayout(0, 2, 5, 5));
        panelIngreso.setBorder(BorderFactory.createTitledBorder("Registrar ingreso"));

        comboCuentaIngreso = new JComboBox<>();
        comboCuentaIngreso.setRenderer(new CuentaComboRenderer());
        comboTipoLugar = new JComboBox<>(new String[]{"GENERAL", "ACCESIBLE"});
        comboLugarDisponible = new JComboBox<>();
        lblPrimerLugarSugerido = new JLabel("-");
        lblMensajeIngreso = new JLabel(" ");

        comboTipoLugar.addActionListener(e -> actualizarLugaresDisponibles());

        panelIngreso.add(new JLabel("Cuenta:"));
        panelIngreso.add(comboCuentaIngreso);
        panelIngreso.add(new JLabel("Tipo de lugar:"));
        panelIngreso.add(comboTipoLugar);
        panelIngreso.add(new JLabel("Lugar disponible:"));
        panelIngreso.add(comboLugarDisponible);
        panelIngreso.add(new JLabel("Primer lugar sugerido:"));
        panelIngreso.add(lblPrimerLugarSugerido);

        JButton btnRegistrarIngreso = new JButton("Registrar ingreso");
        btnRegistrarIngreso.addActionListener(e -> registrarIngreso());
        panelIngreso.add(btnRegistrarIngreso);
        panelIngreso.add(lblMensajeIngreso);

        // Panel de egreso
        JPanel panelEgreso = new JPanel(new GridLayout(0, 2, 5, 5));
        panelEgreso.setBorder(BorderFactory.createTitledBorder("Registrar egreso"));



        comboCuentaEgreso = new JComboBox<>();
        comboCuentaEgreso.setRenderer(new CuentaComboRenderer());

        lblMensajeEgreso = new JLabel(" ");

        JPanel filaCuentaEgreso = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaCuentaEgreso.add(new JLabel("Cuenta:"));
        filaCuentaEgreso.add(comboCuentaEgreso);

        panelEgreso.add(filaCuentaEgreso);   

        JButton btnRegistrarEgreso = new JButton("Registrar egreso");
        btnRegistrarEgreso.addActionListener(e -> registrarEgreso());
        panelEgreso.add(btnRegistrarEgreso);

        panelEgreso.add(lblMensajeEgreso);

        // Contenedor central con ambas secciones 
        JPanel centro = new JPanel(new GridLayout(2, 1, 5, 5));
        centro.add(panelIngreso);
        centro.add(panelEgreso);

        add(centro, BorderLayout.CENTER);
    }

    private void cargarCuentas() {
        comboCuentaIngreso.removeAllItems();
        comboCuentaEgreso.removeAllItems();
        List<Cuenta> cuentas = cuentaController.obtenerCuentas();
        for (Cuenta c : cuentas) {
            comboCuentaIngreso.addItem(c);
            comboCuentaEgreso.addItem(c);
        }
    }

        private void actualizarLugaresDisponibles() {
         comboLugarDisponible.removeAllItems();

        // Verificar si el estacionamiento está lleno
        if (estController.estaLleno()) {
            lblPrimerLugarSugerido.setText("No hay lugares disponibles.");
            lblMensajeIngreso.setText("¡ESTACIONAMIENTO LLENO A SU MAXIMA CAPACIDAD! no se puede registrar ingreso.");
            return;
        }

        List<Lugar> lugares;
        String tipoSeleccionado = (String) comboTipoLugar.getSelectedItem();

        if ("ACCESIBLE".equals(tipoSeleccionado)) {
            lugares = estController.obtenerLugaresAccesiblesDisponibles();
        } else {
            lugares = estController.obtenerLugaresGeneralesDisponibles();
        }

        if (lugares.isEmpty()) {
            lblPrimerLugarSugerido.setText("No hay lugares disponibles para ese tipo.");
        } else {
            Lugar primero = lugares.get(0);
            lblPrimerLugarSugerido.setText(primero.getId());
            for (Lugar l : lugares) {
                comboLugarDisponible.addItem(l);
            }
        }
    }


    
    private void registrarIngreso() { 
    //Modificado para no tocar el modelo
    Cuenta cuenta = (Cuenta) comboCuentaIngreso.getSelectedItem();

    //Verificar si el estacionamiento está lleno (globalmente)
        if (estController.estaLleno()) {
        lblMensajeIngreso.setText("Estacionamiento lleno. No se puede registrar ingreso.");
        JOptionPane.showMessageDialog(
                this,
                "No hay más lugares disponibles en el estacionamiento.",
                "Estacionamiento lleno",
                JOptionPane.WARNING_MESSAGE
        );
        return;
        }

        Lugar lugar = (Lugar) comboLugarDisponible.getSelectedItem();

        if (cuenta == null || lugar == null) {
            lblMensajeIngreso.setText("Seleccione cuenta y lugar.");
            return;
        }

        if (ingresoEgresoController.tieneIngresoPendiente(cuenta)) {
        lblMensajeIngreso.setText("La cuenta ya tiene un ingreso, debe egresar para registrar otro.");
        return;
        }


        String mensaje = ingresoEgresoController.registrarIngreso(cuenta, lugar);
        lblMensajeIngreso.setText(mensaje);

        actualizarLugaresDisponibles();
    }

        private void registrarEgreso() {
        Cuenta cuenta = (Cuenta) comboCuentaEgreso.getSelectedItem();
        if (cuenta == null) {
            lblMensajeEgreso.setText("Seleccione una cuenta.");
            return;
        }

        if (!ingresoEgresoController.tieneIngresoPendiente(cuenta)) {
        lblMensajeEgreso.setText("La cuenta no tiene ingresos pendientes.");
        return;
        }

        
        // Modificado para no tocar el modelo
        Lugar lugar = ingresoEgresoController.obtenerLugarIngresoPendiente(cuenta);
        if (lugar == null) {
            lblMensajeEgreso.setText("No se encontró el lugar del ingreso pendiente.");
            return;
        }

        String mensaje = ingresoEgresoController.registrarEgreso(cuenta, lugar);
        lblMensajeEgreso.setText(mensaje);

        actualizarLugaresDisponibles();
    }

    // Método para refrescar la vista
    public void refrescar() {
        cargarCuentas();
        actualizarLugaresDisponibles();
    }


    // Clase interna para renderizar las cuentas en el JComboBox
    private static class CuentaComboRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                    int index, boolean isSelected,
                                                    boolean cellHasFocus) {

            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Cuenta c) {
                setText("Cuenta Nº " + c.getNumeroCuenta());
            }
            return this;
        }
    }

}

