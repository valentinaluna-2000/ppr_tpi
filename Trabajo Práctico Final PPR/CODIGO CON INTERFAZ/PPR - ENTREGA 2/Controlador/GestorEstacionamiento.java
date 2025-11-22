package Controlador;

import Modelo.*;

public class GestorEstacionamiento {
    // Crea un nuevo estacionamiento con la cantidad indicada de lugares llamando
    public IEstacionamiento crearEstacionamiento(int cantidadGeneral, int cantidadAccesible) {
        Estacionamiento estacionamiento = new Estacionamiento();

        agregarLugares(estacionamiento, cantidadGeneral, TipoLugar.GENERAL, "G");
        agregarLugares(estacionamiento, cantidadAccesible, TipoLugar.ACCESIBLE, "A");

        return estacionamiento;
    }

    // DRY: método privado reutilizable para agregar lugares
    private void agregarLugares(Estacionamiento estacionamiento, int cantidad, TipoLugar tipo, String prefijo) {
        for (int i = 1; i <= cantidad; i++) {
            estacionamiento.agregarLugar(new Lugar(prefijo + i, Estado.DISPONIBLE, tipo));
        }
    }
}