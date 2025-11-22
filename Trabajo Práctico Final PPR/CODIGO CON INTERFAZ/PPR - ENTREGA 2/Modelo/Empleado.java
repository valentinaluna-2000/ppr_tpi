package Modelo;

// Clase empleado que hereda de persona, no se utiliza por el momento, pero puede ser util en el futuro. 

public class Empleado extends Persona {

    // Atributos específicos
    private String cargo;
    private double salario;

    // Constructor
    public Empleado(int dni, String nombre, String apellido, String telefono, String email, String cargo, double salario) {
        super(dni, nombre, apellido, telefono, email);
        this.cargo = cargo;
        this.salario = salario;
    }

    // Getters y setters 
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    // Método sobrescrito
    @Override
    public String obtenerDatos() {
        return super.obtenerDatos() +
               "\nCargo: " + cargo +
               "\nSalario: $" + salario;
    }
}