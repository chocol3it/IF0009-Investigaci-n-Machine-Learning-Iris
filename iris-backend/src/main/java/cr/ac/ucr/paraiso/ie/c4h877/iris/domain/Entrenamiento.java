package cr.ac.ucr.paraiso.ie.c4h877.iris.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Registro de cada vez que se entrena el modelo y de su desempeño (P),
 * medido con validación cruzada.
 */
@Entity
@Table(name = "Entrenamiento")
public class Entrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrenamiento_id")
    private Integer id;

    @Column(nullable = false)
    private Integer k;

    @Column(name = "total_muestras", nullable = false)
    private Integer totalMuestras;

    /** Cuántas flores clasificó bien la validación cruzada. */
    @Column(nullable = false)
    private Integer aciertos;

    /** aciertos / totalMuestras, entre 0 y 1. */
    @Column(nullable = false)
    private Double exactitud;

    @Column(nullable = false)
    private LocalDateTime fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public Integer getTotalMuestras() {
        return totalMuestras;
    }

    public void setTotalMuestras(Integer totalMuestras) {
        this.totalMuestras = totalMuestras;
    }

    public Integer getAciertos() {
        return aciertos;
    }

    public void setAciertos(Integer aciertos) {
        this.aciertos = aciertos;
    }

    public Double getExactitud() {
        return exactitud;
    }

    public void setExactitud(Double exactitud) {
        this.exactitud = exactitud;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
