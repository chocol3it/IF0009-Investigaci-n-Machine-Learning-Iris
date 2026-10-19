package cr.ac.ucr.paraiso.ie.c4h877.iris;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cr.ac.ucr.paraiso.ie.c4h877.iris.business.ModeloService;
import cr.ac.ucr.paraiso.ie.c4h877.iris.data.MuestraRepository;

/**
 * Levanta la aplicación completa con H2 en memoria y comprueba que
 * DatosIniciales cargó las 150 flores y dejó el modelo entrenado.
 */
@SpringBootTest
class IrisApplicationTests {

	@Autowired
	private MuestraRepository muestraRepository;

	@Autowired
	private ModeloService modeloService;

	@Test
	void alArrancar_CargaIrisDataYEntrenaElModelo() {
		assertEquals(150, muestraRepository.count());
		assertEquals(5, modeloService.getKActual());
		assertFalse(modeloService.listarEntrenamientos().isEmpty());
		assertEquals("Iris-setosa", modeloService.clasificar(5.1, 3.5, 1.4, 0.2));
	}

}
