package cr.ac.ucr.paraiso.ie.c4h877.iris.business;

import cr.ac.ucr.paraiso.ie.c4h877.iris.data.MuestraRepository;
import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Muestra;
import cr.ac.ucr.paraiso.ie.c4h877.iris.dto.MuestraDTO;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MuestraService {

    private final MuestraRepository muestraRepository;

    public MuestraService(MuestraRepository muestraRepository) {
        this.muestraRepository = muestraRepository;
    }

    @Transactional(readOnly = true)
    public List<Muestra> listar() {
        return muestraRepository.findAll();
    }

    /**
     * Agrega una flor etiquetada. El modelo NO cambia hasta que se vuelva a entrenar:
     * así se puede mostrar en clase que más experiencia requiere reentrenar.
     */
    @Transactional
    public Muestra agregar(MuestraDTO dto) {
        Muestra muestra = new Muestra(dto.sepaloLargo(), dto.sepaloAncho(),
                dto.petaloLargo(), dto.petaloAncho(), dto.especie());
        return muestraRepository.save(muestra);
    }
}
