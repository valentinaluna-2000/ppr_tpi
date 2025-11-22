package Vista;

import Controlador.ControladorCuenta;
import Controlador.ControladorEstacionamiento;
import Controlador.ControladorIngresoEgreso;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {

    private VistaUsuariosVehiculos vistaUsuariosVehiculos;
    private VistaCuentas vistaCuentas;
    private VistaEstacionamiento vistaEstacionamiento;
    private VistaIngresosEgresos vistaIngresosEgresos;

    public VistaPrincipal(ControladorEstacionamiento estController,
                          ControladorIngresoEgreso ingresoEgresoController,
                          ControladorCuenta cuentaController) {

        setTitle("Estacionamiento");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        // Crear instancias de cada vista
        vistaUsuariosVehiculos = new VistaUsuariosVehiculos(cuentaController);
        vistaCuentas = new VistaCuentas(cuentaController, ingresoEgresoController);
        vistaEstacionamiento = new VistaEstacionamiento(estController);
        vistaIngresosEgresos = new VistaIngresosEgresos(estController, ingresoEgresoController, cuentaController);

        // Agregar pestañas
        tabs.addTab("Crear cuenta", vistaUsuariosVehiculos);
        tabs.addTab("Cuentas y movimientos", vistaCuentas);
        tabs.addTab("Estacionamiento", vistaEstacionamiento);
        tabs.addTab("Ingresos / Egresos", vistaIngresosEgresos);

        // IMPORTANTE: cuando cambio de pestaña refresco la que necesita datos actualizados
        tabs.addChangeListener(e -> {
            int index = tabs.getSelectedIndex();
            String name = tabs.getTitleAt(index);

            if ("Cuentas y movimientos".equals(name)) {
                vistaCuentas.refrescar();
            } 
            else if ("Estacionamiento".equals(name)) {
                vistaEstacionamiento.refrescar();
            } 
            else if ("Ingresos / Egresos".equals(name)) {
                vistaIngresosEgresos.refrescar();
            }
        });

        add(tabs, BorderLayout.CENTER);
    }

    
}
