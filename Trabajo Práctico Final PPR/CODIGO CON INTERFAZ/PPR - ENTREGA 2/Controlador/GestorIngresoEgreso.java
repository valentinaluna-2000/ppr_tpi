package Controlador;

import Modelo.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorIngresoEgreso {
    // Lista de movimientos registrados
    private final List<IngresoEgreso> movimientos;

    public GestorIngresoEgreso() {
        this.movimientos = new ArrayList<>();
    }

    // Método general (DRY)
    private void registrarMovimiento(Cuenta cuenta, Lugar lugar, String tipo, Estado nuevoEstado) {

        IngresoEgreso movimiento = new IngresoEgreso(
                movimientos.size() + 1,
                lugar.getId(),
                lugar,
                cuenta,
                tipo
        );

        // Setea la hora según el tipo de movimiento
        if (tipo.equals("Ingreso")) {
            movimiento.setFechaYHoraIngreso(LocalDateTime.now());
        } else {
            movimiento.setFechaYHoraEgreso(LocalDateTime.now());
        }

        // Cambia el estado del lugar (OCUPADO o DISPONIBLE)
        lugar.setEstado(nuevoEstado);

        // Agrega el movimiento a la lista
        movimientos.add(movimiento);
    }

    // Métodos públicos 
    public void registrarIngreso(Cuenta cuenta, Lugar lugar) {
        registrarMovimiento(cuenta, lugar, "Ingreso", Estado.OCUPADO);
    }

    public void registrarEgreso(Cuenta cuenta, Lugar lugar) {
        registrarMovimiento(cuenta, lugar, "Egreso", Estado.DISPONIBLE);
    }

    // Devuelve una copia de la lista de movimientos
    public List<IngresoEgreso> getMovimientos() {
        return new ArrayList<>(movimientos);
    }


    
}