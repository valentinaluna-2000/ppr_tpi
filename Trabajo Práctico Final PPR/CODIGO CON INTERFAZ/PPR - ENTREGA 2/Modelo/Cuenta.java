package Modelo;

public class Cuenta {
    // Atributos (encapsulamiento)
    private int numeroCuenta;
    private Usuario usuario;  
    private Vehiculo vehiculo;
    private String fechaAlta;
    private String fechaBaja;
    private float saldo;

    public Cuenta(int numeroCuenta, Usuario usuario, Vehiculo vehiculo, String fechaAlta, String fechaBaja, float saldo) {
        this.numeroCuenta = numeroCuenta;
        this.usuario = usuario;
        this.vehiculo = vehiculo;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.saldo = saldo;
    }

    // Getters y setters
    public int getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(int numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public String getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(String fechaAlta) { this.fechaAlta = fechaAlta; }

    public String getFechaBaja() { return fechaBaja; }
    public void setFechaBaja(String fechaBaja) { this.fechaBaja = fechaBaja; }

    public float getSaldo() { return saldo; }
    public void setSaldo(float saldo) { this.saldo = saldo; } 

    // Método central para obtener los datos de un asociado (Responsabilidad única)
    private String obtenerDatosAsociado(Object asociado, String titulo) {
        if (asociado == null) {
            return "No hay " + titulo.toLowerCase() + " asociado a esta cuenta.";
        }

        String datos = "";

        if (asociado instanceof Usuario) {
            datos = ((Usuario) asociado).obtenerDatos();
        } else if (asociado instanceof Vehiculo) {
            datos = ((Vehiculo) asociado).obtenerDatos();
        } else {
            // Por si en el futuro pasás otro tipo inesperado
            datos = asociado.toString();
        }

        return "\n--- " + titulo + " ---\n" + datos;
    }

    // Métodos públicos que usan el método central
    public String obtenerDatosUsuario() {
        return obtenerDatosAsociado(usuario, "Información del Usuario Asociado");
    }

    public String obtenerDatosVehiculo() {
        return obtenerDatosAsociado(vehiculo, "Información del Vehículo Asociado");
    }

    // Devuelve la información de la cuenta en formato texto
    public String obtenerDatosCuenta() {
        return "\n=== Datos de la Cuenta ===" +
               "\nNúmero de Cuenta: " + numeroCuenta +
               "\nFecha de Alta: " + fechaAlta +
               "\nFecha de Baja: " + fechaBaja +
               "\nSaldo: $" + saldo;
    }

    // Verificar si la cuenta está activa (fechaBaja es null o vacía)
    public boolean estaActiva() {
        return fechaBaja == null || fechaBaja.isEmpty();
    }

    // Agregar saldo a la cuenta
    public void acreditarSaldo(float monto) {
        if (monto > 0) {
            saldo += monto;
        }
    }

    // Debitar saldo de la cuenta si hay suficiente saldo disponible
    public boolean debitarSaldo(float monto) {
        if (monto > 0 && saldo >= monto) {
            saldo -= monto;
            return true;
        }
        return false;
    }

    // Devuelve toda la información de la cuenta, usuario y vehículo en formato texto
    public String obtenerDatosCompletos() {
        return obtenerDatosCuenta()
             + obtenerDatosUsuario()
             + obtenerDatosVehiculo();
    }
}