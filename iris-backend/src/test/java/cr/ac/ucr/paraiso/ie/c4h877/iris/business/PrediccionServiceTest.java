package cr.ac.ucr.paraiso.ie.c4h877.iris.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cr.ac.ucr.paraiso.ie.c4h877.iris.data.PrediccionRepository;
import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Prediccion;
import cr.ac.ucr.paraiso.ie.c4h877.iris.dto.MedidasDTO;

@ExtendWith(MockitoExtension.class)
class PrediccionServiceTest {

    @Mock
    private ModeloService modeloService;

    @Mock
    private PrediccionRepository prediccionRepository;

    @InjectMocks
    private PrediccionService prediccionService;

    @Test
    void predecir_GuardaLaEspecieQueRespondeElModelo() {
        MedidasDTO medidas = new MedidasDTO(6.3, 3.3, 6.0, 2.5);
        when(modeloService.clasificar(6.3, 3.3, 6.0, 2.5)).thenReturn("Iris-virginica");
        when(modeloService.getKActual()).thenReturn(5);
        when(prediccionRepository.save(any(Prediccion.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Prediccion resultado = prediccionService.predecir(medidas);

        assertEquals("Iris-virginica", resultado.getEspeciePredicha());
        assertEquals(5, resultado.getK());
        assertEquals(6.3, resultado.getSepaloLargo());
        assertNotNull(resultado.getFecha());
        verify(prediccionRepository).save(any(Prediccion.class));
    }
}
