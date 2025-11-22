package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Estacionamiento implements IEstacionamiento {
    // Atributos (encapsulamiento)
    private final List<Lugar> lugares;

    public Estacionamiento() {
        lugares = new ArrayList<>();
    }

    // Métodos públicos
    @Override
    public void agregarLugar(Lugar lugar) {
        lugares.add(lugar);
    }

    @Override
    public List<Lugar> getLugares() {
        return lugares;
    }

    // Regla en Prolog: lugarDisponible(Lugar) :- estado(Lugar, disponible).
    public boolean lugarDisponible(Lugar lugar) {
        return lugar.getEstado() == Estado.DISPONIBLE;
    }

    // Regla en Prolog: existeLugarDisponible(E) :- lugar(L, E), lugarDisponible(L).
    public boolean existeLugarDisponible() {
        return lugares.stream().anyMatch(this::lugarDisponible);
    }

    // Regla en Prolog: estaLleno(E) :- not(existeLugarDisponible(E)).
    public boolean estaLleno() {
        return !existeLugarDisponible();
    }

    // Devuelve el primer lugar disponible según tipo
    // PARADIGMA FUNCIONAL
    @Override
    public Lugar buscarLugarDisponible(boolean accesible) {
        return lugares.stream()
                .filter(l -> (l.getTipo() == TipoLugar.ACCESIBLE) == accesible)
                .filter(this::lugarDisponible)
                .findFirst()
                .orElse(null);
    }

    // Devuelve todos los lugares disponibles 
    // PARADIGMA FUNCIONAL
    @Override
    public List<Lugar> getLugaresDisponibles() {
        return lugares.stream()
                .filter(this::lugarDisponible)
                .collect(Collectors.toList());
    }

    // Métodos optimizados con DRY
    // Cambia el estado de un lugar si está en el estado adecuado
    private boolean cambiarEstadoLugar(Lugar lugar, Estado estadoObjetivo) {
        if (lugar == null) return false;

        boolean debeEstarDisponible = (estadoObjetivo == Estado.OCUPADO);

        if (lugarDisponible(lugar) == debeEstarDisponible) {
            lugar.setEstado(estadoObjetivo);
            return true;
        }
        return false;
    }

    @Override
    public boolean ingresarVehiculo(boolean accesible) {
        Lugar lugar = buscarLugarDisponible(accesible);
        return cambiarEstadoLugar(lugar, Estado.OCUPADO);
    }

    @Override
    public boolean retirarVehiculoPorId(String idLugar) {
        Lugar lugar = buscarLugarPorId(idLugar);
        return cambiarEstadoLugar(lugar, Estado.DISPONIBLE);
    }

    // Métodos reutilizables (DRY)
    // Busca un lugar por ID (paradigma funcional)
    @Override
    public Lugar buscarLugarPorId(String idLugar) {
        return lugares.stream()
                .filter(l -> l.getId().equals(idLugar))
                .findFirst()
                .orElse(null);
    }
}