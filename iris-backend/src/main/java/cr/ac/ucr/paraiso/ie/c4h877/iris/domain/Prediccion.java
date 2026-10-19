package cr.ac.ucr.paraiso.ie.c4h877.iris.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Una consulta hecha al modelo: las medidas que escribió el usuario,
 * la especie que respondió el modelo y con qué k lo hizo.
 */
@Entity
@Table(name = "Prediccion")
public class Prediccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prediccion_id")
    private Integer id;

    @Column(name = "sepalo_largo", nullable = false)
    private Double sepaloLargo;

    @Column(name = "sepalo_ancho", nullable = false)
    private Double sepaloAncho;

    @Column(name = "petalo_largo", nullable = false)
    private Double petaloLargo;

    @Column(name = "petalo_ancho", nullable = false)
    private Double petaloAncho;

    @Column(name = "especie_predicha", nullable = false, length = 20)
    private String especiePredicha;

    @Column(nullable = false)
    private Integer k;

    @Column(nullable = false)
    private LocalDateTime fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSepaloLargo() {
        return sepaloLargo;
    }

    public void setSepaloLargo(Double sepaloLargo) {
        this.sepaloLargo = sepaloLargo;
    }

    public Double getSepaloAncho() {
        return sepaloAncho;
    }

    public void setSepaloAncho(Double sepaloAncho) {
        this.sepaloAncho = sepaloAncho;
    }

    public Double getPetaloLargo() {
        return petaloLargo;
    }

    public void setPetaloLargo(Double petaloLargo) {
        this.petaloLargo = petaloLargo;
    }

    public Double getPetaloAncho() {
        return petaloAncho;
    }

    public void setPetaloAncho(Double petaloAncho) {
        this.petaloAncho = petaloAncho;
    }

    public String getEspeciePredicha() {
        return especiePredicha;
    }

    public void setEspeciePredicha(String especiePredicha) {
        this.especiePredicha = especiePredicha;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
