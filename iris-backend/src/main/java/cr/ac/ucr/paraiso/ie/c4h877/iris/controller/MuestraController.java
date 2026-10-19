package cr.ac.ucr.paraiso.ie.c4h877.iris.controller;

import cr.ac.ucr.paraiso.ie.c4h877.iris.business.MuestraService;
import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Muestra;
import cr.ac.ucr.paraiso.ie.c4h877.iris.dto.MuestraDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/muestras")
public class MuestraController {

    private final MuestraService muestraService;

    public MuestraController(MuestraService muestraService) {
        this.muestraService = muestraService;
    }

    @GetMapping
    public ResponseEntity<List<Muestra>> listar() {
        return ResponseEntity.ok(muestraService.listar());
    }

    @PostMapping
    public ResponseEntity<Muestra> agregar(@Valid @RequestBody MuestraDTO muestra) {
        return ResponseEntity.ok(muestraService.agregar(muestra));
    }
}
