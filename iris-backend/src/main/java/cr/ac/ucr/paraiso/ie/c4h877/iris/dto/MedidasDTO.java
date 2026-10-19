package cr.ac.ucr.paraiso.ie.c4h877.iris.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/** Las cuatro medidas (en cm) que llegan desde el formulario "Predecir". */
public record MedidasDTO(
        @NotNull(message = "El largo del sépalo es obligatorio")
        @DecimalMin(value = "0.1", message = "Debe ser al menos 0.1 cm")
        @DecimalMax(value = "10.0", message = "Debe ser como máximo 10 cm")
        Double sepaloLargo,

        @NotNull(message = "El ancho del sépalo es obligatorio")
        @DecimalMin(value = "0.1", message = "Debe ser al menos 0.1 cm")
        @DecimalMax(value = "10.0", message = "Debe ser como máximo 10 cm")
        Double sepaloAncho,

        @NotNull(message = "El largo del pétalo es obligatorio")
        @DecimalMin(value = "0.1", message = "Debe ser al menos 0.1 cm")
        @DecimalMax(value = "10.0", message = "Debe ser como máximo 10 cm")
        Double petaloLargo,

        @NotNull(message = "El ancho del pétalo es obligatorio")
        @DecimalMin(value = "0.1", message = "Debe ser al menos 0.1 cm")
        @DecimalMax(value = "10.0", message = "Debe ser como máximo 10 cm")
        Double petaloAncho) {
}
