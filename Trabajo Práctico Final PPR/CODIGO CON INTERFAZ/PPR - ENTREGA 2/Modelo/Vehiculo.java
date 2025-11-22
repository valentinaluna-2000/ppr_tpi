package Modelo;

public class Vehiculo {
    // Atributos (encapsulamiento)
    private String aseguradora;
    private String modelo;
    private String marca;
    private String patente;

    // Constructor
    public Vehiculo(String aseguradora, String modelo, String marca, String patente) {
        this.aseguradora = aseguradora;
        this.modelo = modelo;
        this.marca = marca;
        this.patente = patente;
    }

    // Getters y setters
    public String getAseguradora() { return aseguradora; }
    public void setAseguradora(String aseguradora) { this.aseguradora = aseguradora; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    // Retorna los datos como String (Responsabilidad única)
    public String obtenerDatos() {
        return  "Patente: " + patente +
                "\nMarca: " + marca +
                "\nModelo: " + modelo +
                "\nAseguradora: " + aseguradora;
    }
}