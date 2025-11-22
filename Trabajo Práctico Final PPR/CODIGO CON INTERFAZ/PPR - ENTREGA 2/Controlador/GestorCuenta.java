package Controlador;
import Modelo.Cuenta;
import Modelo.Usuario;
import Modelo.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorCuenta {
    // Simulamos una base de datos con una lista en memoria
    private List<Cuenta> cuentas = new ArrayList<>();
    private int siguienteNumeroCuenta = 1;
    
    // Método para crear una nueva cuenta con usuario y vehículo asociado
    public Cuenta crearCuenta(Usuario usuario, Vehiculo vehiculo) {
        String fechaAlta = LocalDate.now().toString();
        String fechaBaja = null;
        float saldoInicial = 0f;
    
        Cuenta cuenta = new Cuenta(
            siguienteNumeroCuenta++,
            usuario,
            vehiculo,
            fechaAlta,
            fechaBaja,
            saldoInicial
        );

        cuentas.add(cuenta);
        return cuenta;
    }

    public List<Cuenta> obtenerCuentas() {
        return new ArrayList<>(cuentas);
    }
    
    //Modifica datos del usuario asociado a la cuenta (menos DNI)
    public void actualizarDatosUsuario(Cuenta cuenta,
    String nombre,
    String apellido,
    String telefono,
    String email) {
    if (cuenta == null || cuenta.getUsuario() == null) return;
    
    Usuario u = cuenta.getUsuario();
    u.setNombre(nombre);
    u.setApellido(apellido);
    u.setTelefono(telefono);
    u.setEmail(email);
    }

    // Modifica solo la aseguradora del vehículo asociado a la cuenta
    public void actualizarAseguradoraVehiculo(Cuenta cuenta, String nuevaAseguradora) {
    if (cuenta == null || cuenta.getVehiculo() == null) return;

    Vehiculo v = cuenta.getVehiculo();
    v.setAseguradora(nuevaAseguradora);
    }


    // Método funcional para obtener la primera cuenta que coincida con el número dado
    public Cuenta buscarPorNumero(int numero) {
        return cuentas.stream()
                .filter(c -> c.getNumeroCuenta() == numero)
                .findFirst()
                .orElse(null);
    }
}