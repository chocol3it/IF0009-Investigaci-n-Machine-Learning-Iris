package cr.ac.ucr.paraiso.ie.c4h877.iris.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cr.ac.ucr.paraiso.ie.c4h877.iris.data.EntrenamientoRepository;
import cr.ac.ucr.paraiso.ie.c4h877.iris.data.MuestraRepository;
import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Entrenamiento;
import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Muestra;

@ExtendWith(MockitoExtension.class)
class ModeloServiceTest {

    @Mock
    private MuestraRepository muestraRepository;

    @Mock
    private EntrenamientoRepository entrenamientoRepository;

    @InjectMocks
    private ModeloService modeloService;

    /**
     * 20 flores inventadas para la prueba, fáciles de separar:
     * 10 "setosa" con pétalos pequeños y 10 "virginica" con pétalos grandes.
     */
    private List<Muestra> floresDePrueba() {
        List<Muestra> flores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            flores.add(new Muestra(5.0 + i * 0.05, 3.4, 1.4, 0.2, "Iris-setosa"));
            flores.add(new Muestra(6.5 + i * 0.05, 3.0, 5.8, 2.2, "Iris-virginica"));
        }
        return flores;
    }

    @Test
    void entrenar_KFueraDeRango_LanzaExcepcion() {
        when(muestraRepository.findAll()).thenReturn(floresDePrueba());

        assertThrows(IllegalArgumentException.class, () -> modeloService.entrenar(0));
        assertThrows(IllegalArgumentException.class, () -> modeloService.entrenar(21));

        verify(entrenamientoRepository, never()).save(any(Entrenamiento.class));
    }

    @Test
    void entrenar_DatosSeparables_GuardaEntrenamientoConTodosLosAciertos() {
        when(muestraRepository.findAll()).thenReturn(floresDePrueba());
        when(entrenamientoRepository.save(any(Entrenamiento.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Entrenamiento resultado = modeloService.entrenar(3);

        assertEquals(3, resultado.getK());
        assertEquals(20, resultado.getTotalMuestras());
        assertEquals(20, resultado.getAciertos());
        assertEquals(1.0, resultado.getExactitud());
        verify(entrenamientoRepository).save(any(Entrenamiento.class));
    }

    @Test
    void clasificar_DespuesDeEntrenar_RetornaLaEspecieMasCercana() {
        when(muestraRepository.findAll()).thenReturn(floresDePrueba());
        when(entrenamientoRepository.save(any(Entrenamiento.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));
        modeloService.entrenar(3);

        assertEquals("Iris-setosa", modeloService.clasificar(5.1, 3.5, 1.3, 0.3));
        assertEquals("Iris-virginica", modeloService.clasificar(6.7, 3.1, 5.6, 2.1));
        assertEquals(3, modeloService.getKActual());
    }

    @Test
    void clasificar_SinEntrenar_LanzaExcepcion() {
        assertThrows(IllegalStateException.class,
                () -> modeloService.clasificar(5.1, 3.5, 1.4, 0.2));
    }
}
