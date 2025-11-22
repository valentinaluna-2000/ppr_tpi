package Modelo;

import java.time.LocalDateTime;

public class IngresoEgreso {
    // Atributos (encapsulamiento)
    private int numeroTransaccion;
    private LocalDateTime fechaYHoraIngreso;
    private LocalDateTime fechaYHoraEgreso;
    private String dominio;
    private Lugar lugar;
    private Cuenta cuenta;
    private String tipoMovimiento;

    public IngresoEgreso(int numeroTransaccion, String dominio, Lugar lugar, Cuenta cuenta, String tipo) {
        this.numeroTransaccion = numeroTransaccion;
        this.dominio = dominio;
        this.lugar = lugar;
        this.cuenta = cuenta;
        this.tipoMovimiento = tipo;
    }

    // Getters y setters
    public int getNumeroTransaccion() { return numeroTransaccion; }
    public String getDominio() { return dominio; }
    public Lugar getLugar() { return lugar; }
    public Cuenta getCuenta() { return cuenta; }
    public String getTipoMovimiento() { return tipoMovimiento; }

    public LocalDateTime getFechaYHoraIngreso() { return fechaYHoraIngreso; }
    public void setFechaYHoraIngreso(LocalDateTime fechaYHoraIngreso) { this.fechaYHoraIngreso = fechaYHoraIngreso; }

    public LocalDateTime getFechaYHoraEgreso() { return fechaYHoraEgreso; }
    public void setFechaYHoraEgreso(LocalDateTime fechaYHoraEgreso) { this.fechaYHoraEgreso = fechaYHoraEgreso; }
}