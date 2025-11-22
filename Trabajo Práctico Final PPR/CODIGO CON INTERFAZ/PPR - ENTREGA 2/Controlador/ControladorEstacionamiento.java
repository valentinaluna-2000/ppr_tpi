package Controlador;

import Modelo.*;
import java.util.List;
import java.util.stream.Collectors;

public class ControladorEstacionamiento {
    // Atributo (encapsulamiento)
    private final IEstacionamiento estacionamiento;

    public ControladorEstacionamiento(IEstacionamiento estacionamiento) {
        this.estacionamiento = estacionamiento;
    }

    // Todos los lugares
    public List<Lugar> obtenerTodosLosLugares() {
        return estacionamiento.getLugares();
    }

    // Todos los lugares disponibles
    public List<Lugar> obtenerLugaresDisponibles() {
        return estacionamiento.getLugaresDisponibles();
    }

    // PARADIGMA FUNCIONAL
    // Solo generales disponibles
    public List<Lugar> obtenerLugaresGeneralesDisponibles() {
        return estacionamiento.getLugaresDisponibles()
                              .stream()
                              .filter(l -> l.getTipo() == TipoLugar.GENERAL)
                              .collect(Collectors.toList());
    }

    // Solo accesibles disponibles
    public List<Lugar> obtenerLugaresAccesiblesDisponibles() {
        return estacionamiento.getLugaresDisponibles()
                              .stream()
                              .filter(l -> l.getTipo() == TipoLugar.ACCESIBLE)
                              .collect(Collectors.toList());

    }
    
    // Busca el lugar por ID
    public Lugar buscarLugarPorId(String id) {
    return estacionamiento.buscarLugarPorId(id);
    }
    
    // Método tomado de la clase estacionamiento 
    public boolean estaLleno() {
    return estacionamiento.estaLleno();
    }


}
