package cr.ac.ucr.paraiso.ie.c4h877.iris.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Prediccion;

public interface PrediccionRepository extends JpaRepository<Prediccion, Integer> {

    // Las predicciones más recientes primero.
    List<Prediccion> findAllByOrderByIdDesc();
}
