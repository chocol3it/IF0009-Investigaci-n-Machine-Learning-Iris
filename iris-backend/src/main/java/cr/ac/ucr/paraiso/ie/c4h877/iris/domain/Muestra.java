package cr.ac.ucr.paraiso.ie.c4h877.iris.domain;

import jakarta.persistence.*;

/**
 * Una flor de entrenamiento: sus cuatro medidas (en cm) y su especie conocida.
 * Estas filas son la "experiencia" (E) de la que aprende el modelo.
 */
@Entity
@Table(name = "Muestra")
public class Muestra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "muestra_id")
    private Integer id;

    @Column(name = "sepalo_largo", nullable = false)
    private Double sepaloLargo;

    @Column(name = "sepalo_ancho", nullable = false)
    private Double sepaloAncho;

    @Column(name = "petalo_largo", nullable = false)
    private Double petaloLargo;

    @Column(name = "petalo_ancho", nullable = false)
    private Double petaloAncho;

    @Column(nullable = false, length = 20)
    private String especie;

    public Muestra() {
    }

    public Muestra(Double sepaloLargo, Double sepaloAncho, Double petaloLargo, Double petaloAncho,
            String especie) {
        this.sepaloLargo = sepaloLargo;
        this.sepaloAncho = sepaloAncho;
        this.petaloLargo = petaloLargo;
        this.petaloAncho = petaloAncho;
        this.especie = especie;
    }

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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
}
