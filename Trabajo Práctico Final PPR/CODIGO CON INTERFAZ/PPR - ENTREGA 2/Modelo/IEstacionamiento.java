package Modelo;

import java.util.List;

/* Esta interfaz define las operaciones mínimas que un estacionamiento
   debe poder realizar, sin importar cómo se implementen internamente */ 

public interface IEstacionamiento {
    // Agrega un lugar al estacionamiento
    void agregarLugar(Lugar lugar);

    // Devuelve la lista completa de lugares
    List<Lugar> getLugares();

    // Devuelve el primer lugar disponible según el tipo (accesible o general)
    Lugar buscarLugarDisponible(boolean accesible);

    // Devuelve la lista de lugares disponibles
    List<Lugar> getLugaresDisponibles();

    // Ingresa un vehículo y marca un lugar como ocupado
    boolean ingresarVehiculo(boolean accesible);

    // Retira un vehículo por su id y marca el lugar como disponible
    boolean retirarVehiculoPorId(String idLugar);
    
    // Busca un lugar por ID
    Lugar buscarLugarPorId(String idLugar);
    
    // Metodo si esta lleno el estacionamiento
    boolean estaLleno();
}