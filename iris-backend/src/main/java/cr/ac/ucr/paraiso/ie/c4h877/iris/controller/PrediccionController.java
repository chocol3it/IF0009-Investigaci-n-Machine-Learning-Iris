package cr.ac.ucr.paraiso.ie.c4h877.iris.controller;

import cr.ac.ucr.paraiso.ie.c4h877.iris.business.PrediccionService;
import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Prediccion;
import cr.ac.ucr.paraiso.ie.c4h877.iris.dto.MedidasDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/predicciones")
public class PrediccionController {

    private final PrediccionService prediccionService;

    public PrediccionController(PrediccionService prediccionService) {
        this.prediccionService = prediccionService;
    }

    @PostMapping
    public ResponseEntity<Prediccion> predecir(@Valid @RequestBody MedidasDTO medidas) {
        return ResponseEntity.ok(prediccionService.predecir(medidas));
    }

    @GetMapping
    public ResponseEntity<List<Prediccion>> listar() {
        return ResponseEntity.ok(prediccionService.listar());
    }
}
