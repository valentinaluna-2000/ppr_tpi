package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import Controlador.ControladorEstacionamiento;
import Modelo.Lugar;

public class VistaEstacionamiento extends JPanel {

    private final ControladorEstacionamiento estController;
    private JTable tablaLugares;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstadoEstacionamiento;
    private JTextField txtBuscarId;

    public VistaEstacionamiento(ControladorEstacionamiento estController) {
        this.estController = estController;
        inicializarComponentes();
        cargarTodosLosLugares();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(5, 5));

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Tipo", "Estado", "Fecha/hora"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaLugares = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaLugares);

        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTodos = new JButton("Todos");
        JButton btnDisponibles = new JButton("Disponibles");
        JButton btnGenDisp = new JButton("Generales disponibles");
        JButton btnAccDisp = new JButton("Accesibles disponibles");

        btnTodos.addActionListener(e -> cargarTodosLosLugares());
        btnDisponibles.addActionListener(e -> cargarLugaresDisponibles());
        btnGenDisp.addActionListener(e -> cargarLugaresGeneralesDisponibles());
        btnAccDisp.addActionListener(e -> cargarLugaresAccesiblesDisponibles());

        txtBuscarId = new JTextField(6);
        JButton btnBuscar = new JButton("Buscar Lugar por ID");
        btnBuscar.addActionListener(e -> buscarLugarPorId());

        arriba.add(btnTodos);
        arriba.add(btnDisponibles);
        arriba.add(btnGenDisp);
        arriba.add(btnAccDisp);
        arriba.add(new JLabel("ID:"));
        arriba.add(txtBuscarId);
        arriba.add(btnBuscar);

        lblEstadoEstacionamiento = new JLabel(" ");
        lblEstadoEstacionamiento.setForeground(Color.RED);
        lblEstadoEstacionamiento.setHorizontalAlignment(SwingConstants.CENTER);

        add(arriba, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(lblEstadoEstacionamiento, BorderLayout.SOUTH);
    }

    private void cargarEnTabla(List<Lugar> lugares) {
    modeloTabla.setRowCount(0);
        for (Lugar l : lugares) {
            modeloTabla.addRow(new Object[]{
                    l.getId(),
                    l.getTipo(),
                    l.getEstado(),
                    l.getFechaHora()
            });
        }

        boolean lleno = estController.obtenerLugaresDisponibles().isEmpty();
        if (lleno) {
            lblEstadoEstacionamiento.setText("ESTACIONAMIENTO LLENO NO SE PERMITEN MÁS INGRESOS");
        } else {
            lblEstadoEstacionamiento.setText(" ");
        }
    }


        // Método para cargar todos los lugares en la tabla
        private void cargarTodosLosLugares() {
            cargarEnTabla(estController.obtenerTodosLosLugares());
        }

        private void cargarLugaresDisponibles() {
            cargarEnTabla(estController.obtenerLugaresDisponibles());
        }

        private void cargarLugaresGeneralesDisponibles() {
            cargarEnTabla(estController.obtenerLugaresGeneralesDisponibles());
        }

        private void cargarLugaresAccesiblesDisponibles() {
            cargarEnTabla(estController.obtenerLugaresAccesiblesDisponibles());
        }

        // Método para buscar lugar por ID y mostrarlo en la tabla 
        private void buscarLugarPorId() {
        String id = txtBuscarId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese un ID para buscar.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Lugar l = estController.buscarLugarPorId(id);

        if (l != null) {
            modeloTabla.setRowCount(0);
            modeloTabla.addRow(new Object[]{
                    l.getId(),
                    l.getTipo(),
                    l.getEstado(),
                    l.getFechaHora()
            });
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontró un lugar con ese ID.",
                    "Sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }



    // Método para refrescar la vista
    public void refrescar() {
        cargarTodosLosLugares();
    }
}
