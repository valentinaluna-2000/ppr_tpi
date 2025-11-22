package Controlador;

import Modelo.Cuenta;
import Modelo.Usuario;
import Modelo.Vehiculo;

import java.util.List;

public class ControladorCuenta {
    // Atributo (encapsulamiento)
    private final GestorCuenta gestor;

    public ControladorCuenta(GestorCuenta gestor) {
        this.gestor = gestor;
    }

    // Crear una nueva cuenta
    public Cuenta crearCuenta(Usuario usuario, Vehiculo vehiculo) {
        return gestor.crearCuenta(usuario, vehiculo);
    }

    // Listar todas las cuentas
    public List<Cuenta> obtenerCuentas() {
        return gestor.obtenerCuentas();
    }

    // Buscar una cuenta por número
    public Cuenta buscarPorNumero(int numero) {
        return gestor.buscarPorNumero(numero);
    }

    
    // Actualizar datos del usuario asociado a una cuenta
    public void actualizarDatosUsuario(Cuenta cuenta,
    String nombre,
    String apellido,
    String telefono,
    String email) {
    gestor.actualizarDatosUsuario(cuenta, nombre, apellido, telefono, email);
    }

    // Actualizar solo la aseguradora del vehículo asociado a la cuenta
     public void actualizarAseguradoraVehiculo(Cuenta cuenta, String nuevaAseguradora) {
     gestor.actualizarAseguradoraVehiculo(cuenta, nuevaAseguradora);
    }
    

}