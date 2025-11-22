package Controlador;

import Modelo.Cuenta;
import Modelo.Lugar;
import Modelo.IngresoEgreso;

import java.util.List;
import java.util.stream.Collectors;

public class ControladorIngresoEgreso {
    // Atributo (encapsulamiento)
    private GestorIngresoEgreso gestor;

    public ControladorIngresoEgreso(GestorIngresoEgreso gestor) {
        this.gestor = gestor;
    }

    // Devuelve un mensaje para que la VISTA lo muestre
    public String registrarIngreso(Cuenta cuenta, Lugar lugar) {
        gestor.registrarIngreso(cuenta, lugar);
        return "Ingreso registrado. Lugar: " + lugar.getId();
    }

    public String registrarEgreso(Cuenta cuenta, Lugar lugar) {
        gestor.registrarEgreso(cuenta, lugar);
        return "Egreso registrado. Lugar: " + lugar.getId();
    }

    // Devuelve TODOS los movimientos
    public List<IngresoEgreso> obtenerMovimientos() {
        return gestor.getMovimientos();
    }

    // Movimientos filtrados por cuenta
    // PARADIGMA FUNCIONAL
    public List<IngresoEgreso> obtenerMovimientosPorCuenta(Cuenta cuenta) {
        return gestor.getMovimientos()
                .stream()
                .filter(mov -> mov.getCuenta().equals(cuenta))
                .collect(Collectors.toList());
        }

    // Verificar si una cuenta posee un ingreso pendiente
    public boolean tieneIngresoPendiente(Cuenta cuenta) {
        List<IngresoEgreso> movimientos = obtenerMovimientosPorCuenta(cuenta);
        int ingresos = 0;
        int egresos = 0;

        for (IngresoEgreso mov : movimientos) {
            if ("Ingreso".equalsIgnoreCase(mov.getTipoMovimiento())) {
                ingresos++;
            } else if ("Egreso".equalsIgnoreCase(mov.getTipoMovimiento())) {
                egresos++;
            }
        }
        return ingresos > egresos;
    }
    
    public Lugar obtenerLugarIngresoPendiente(Cuenta cuenta) {
        List<IngresoEgreso> movimientos = obtenerMovimientosPorCuenta(cuenta);
        for (int i = movimientos.size() - 1; i >= 0; i--) {
            IngresoEgreso mov = movimientos.get(i);
            if ("Ingreso".equalsIgnoreCase(mov.getTipoMovimiento())) {
                return mov.getLugar();
            }
        }
        return null;
    }

}