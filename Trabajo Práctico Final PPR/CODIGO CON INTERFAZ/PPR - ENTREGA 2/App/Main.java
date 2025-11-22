package App;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import Controlador.ControladorIngresoEgreso;
import Controlador.ControladorCuenta;
import Controlador.ControladorEstacionamiento;
import Controlador.GestorEstacionamiento;
import Controlador.GestorIngresoEgreso;
import Controlador.GestorCuenta;
import Modelo.IEstacionamiento;
import Vista.VistaPrincipal;

// Clase main donde arranca todo el programa
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            GestorEstacionamiento gestorEst = new GestorEstacionamiento();

            //Valores dados al estacioanamiento por defecto
            int cantGenerales = 20;    
            int cantAccesibles = 5;

            try {
                String genStr = JOptionPane.showInputDialog(
                        null,
                        "Cantidad de lugares GENERALES:",
                        "Configurar estacionamiento",
                        JOptionPane.QUESTION_MESSAGE
                );

                String accStr = JOptionPane.showInputDialog(
                        null,
                        "Cantidad de lugares ACCESIBLES:",
                        "Configurar estacionamiento",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (genStr != null && accStr != null) {
                    cantGenerales = Integer.parseInt(genStr.trim());
                    cantAccesibles = Integer.parseInt(accStr.trim());
                }

            } catch (NumberFormatException e) {
                
            }

            // Crear estacionamiento según cantidades ingresadas
            IEstacionamiento est = gestorEst.crearEstacionamiento(cantGenerales, cantAccesibles);

            // Gestores
            GestorIngresoEgreso gestorIE = new GestorIngresoEgreso();
            GestorCuenta gestorCuenta = new GestorCuenta();

            // Controladores
            ControladorEstacionamiento estController = new ControladorEstacionamiento(est);
            ControladorIngresoEgreso ingresoEgresoController = new ControladorIngresoEgreso(gestorIE);
            ControladorCuenta cuentaController = new ControladorCuenta(gestorCuenta);

            // Vista principal
            VistaPrincipal vista = new VistaPrincipal(estController, ingresoEgresoController, cuentaController);
            vista.setVisible(true);

        });
    }
}
