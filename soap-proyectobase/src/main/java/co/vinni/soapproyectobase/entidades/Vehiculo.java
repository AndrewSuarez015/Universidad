package co.vinni.soapproyectobase.entidades;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate; // Importar LocalDate
import java.time.temporal.ChronoUnit; // Importar ChronoUnit

@Data
@NoArgsConstructor
public class Vehiculo implements Serializable {
    private static long contadorId = 0;
    private long id;
    private String placa;
    private String tipo;
    private String municipioRegistro;
    private double precioCompra;
    private double valorActual;
    private LocalDate fechaCompra; // Nuevo campo para la fecha de compra
    private static final double TASA_DEPRECIACION = 0.10; // 10% anual
    private static final double TASA_IMPUESTO = 0.10; // 10% del valor actual

    public Vehiculo(String placa, String tipo, String municipioRegistro, double precioCompra, LocalDate fechaCompra) {
        this.id = ++contadorId;
        this.placa = placa;
        this.tipo = tipo;
        this.municipioRegistro = municipioRegistro;
        this.precioCompra = precioCompra;
        this.valorActual = precioCompra; // Inicializar con el precio de compra
        this.fechaCompra = fechaCompra; // Asignar la fecha de compra
        depreciar(); // Aplicar depreciación inicial
    }

    private void calcularValorActual() {
        // Calcular la diferencia en años entre la fecha de compra y la fecha actual
        long years = ChronoUnit.YEARS.between(fechaCompra, LocalDate.now());
        // Aplicar la depreciación por cada año
        for (int i = 0; i < years; i++) {
            this.valorActual -= this.valorActual * TASA_DEPRECIACION;
        }
    }

    // Método para calcular el impuesto
    public double calcularImpuesto() {
        return this.valorActual * TASA_IMPUESTO;
    }

    public void depreciar() {
        calcularValorActual(); // Calcular el valor actual del vehículo
    }
}