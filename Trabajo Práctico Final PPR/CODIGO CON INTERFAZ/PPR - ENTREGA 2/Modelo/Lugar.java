package Modelo;

import java.time.LocalDateTime;

public class Lugar {
    // Atributos (encapsulamiento) 
    private String id;                    
    private Estado estado;                
    private final TipoLugar tipo;         
    private LocalDateTime fechaHora;      

    // Constructores: inicializar un lugar con su ID, estado y tipo, asignando la fecha y hora actual
    public Lugar(String id, Estado estado, TipoLugar tipo) {
        this.id = id;
        this.estado = estado;
        this.tipo = tipo;
        this.fechaHora = LocalDateTime.now();   // Guarda la fecha y hora en que se creó el objeto
    }

    // Accesores: permiten acceder o modificar los valores de los atributos de forma controlada
    public String getId() { return id;}
    public void setId(String id) { this.id = id; }

    public Estado getEstado() { return estado; }
    // Permite cambiar el estado del lugar (ej: de DISPONIBLE a OCUPADO)
    public void setEstado(Estado estado) { this.estado = estado; }

    public TipoLugar getTipo() { return tipo; }

    public LocalDateTime getFechaHora() { return fechaHora; }

    // Método toString (para imprimir) (Responsabilidad única)
    @Override
    public String toString() {
        return "Lugar ID:" + id + '\'' +
            ", estado=" + estado +
            ", tipo =" + tipo +
            ", fecha y hora= " + fechaHora + " }";
    }
}