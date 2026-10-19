package cr.ac.ucr.paraiso.ie.c4h877.iris.business;

import cr.ac.ucr.paraiso.ie.c4h877.iris.data.PrediccionRepository;
import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Prediccion;
import cr.ac.ucr.paraiso.ie.c4h877.iris.dto.MedidasDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrediccionService {

    private final ModeloService modeloService;
    private final PrediccionRepository prediccionRepository;

    public PrediccionService(ModeloService modeloService,
            PrediccionRepository prediccionRepository) {
        this.modeloService = modeloService;
        this.prediccionRepository = prediccionRepository;
    }

    /** Le pregunta al modelo la especie y guarda la consulta en el historial. */
    @Transactional
    public Prediccion predecir(MedidasDTO medidas) {
        String especie = modeloService.clasificar(medidas.sepaloLargo(), medidas.sepaloAncho(),
                medidas.petaloLargo(), medidas.petaloAncho());

        Prediccion prediccion = new Prediccion();
        prediccion.setSepaloLargo(medidas.sepaloLargo());
        prediccion.setSepaloAncho(medidas.sepaloAncho());
        prediccion.setPetaloLargo(medidas.petaloLargo());
        prediccion.setPetaloAncho(medidas.petaloAncho());
        prediccion.setEspeciePredicha(especie);
        prediccion.setK(modeloService.getKActual());
        prediccion.setFecha(LocalDateTime.now());
        return prediccionRepository.save(prediccion);
    }

    @Transactional(readOnly = true)
    public List<Prediccion> listar() {
        return prediccionRepository.findAllByOrderByIdDesc();
    }
}
