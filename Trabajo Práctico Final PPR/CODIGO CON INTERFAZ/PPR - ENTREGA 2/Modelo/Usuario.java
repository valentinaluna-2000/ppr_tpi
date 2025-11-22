package Modelo;

public class Usuario extends Persona {
    // Constructor
    public Usuario(int dni, String nombre, String apellido, String telefono, String email) {
        super(dni, nombre, apellido, telefono, email);
    }

    // Método sobrescrito, esta en la clase persona (Responsabilidad única)
    @Override
    public String obtenerDatos() {
        return super.obtenerDatos(); 
    }
}