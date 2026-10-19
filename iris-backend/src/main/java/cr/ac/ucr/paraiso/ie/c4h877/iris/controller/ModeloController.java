package cr.ac.ucr.paraiso.ie.c4h877.iris.controller;

import cr.ac.ucr.paraiso.ie.c4h877.iris.business.ModeloService;
import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Entrenamiento;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/modelo")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    // POST /api/v1/modelo/entrenar?k=5
    @PostMapping("/entrenar")
    public ResponseEntity<Entrenamiento> entrenar(@RequestParam(defaultValue = "5") int k) {
        return ResponseEntity.ok(modeloService.entrenar(k));
    }

    @GetMapping("/entrenamientos")
    public ResponseEntity<List<Entrenamiento>> listarEntrenamientos() {
        return ResponseEntity.ok(modeloService.listarEntrenamientos());
    }
}
