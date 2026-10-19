package cr.ac.ucr.paraiso.ie.c4h877.iris.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Entrenamiento;

public interface EntrenamientoRepository extends JpaRepository<Entrenamiento, Integer> {

    // Los entrenamientos más recientes primero.
    List<Entrenamiento> findAllByOrderByIdDesc();
}
