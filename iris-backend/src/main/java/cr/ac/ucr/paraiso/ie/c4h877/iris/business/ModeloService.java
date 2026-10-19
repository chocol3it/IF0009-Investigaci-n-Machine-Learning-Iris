package cr.ac.ucr.paraiso.ie.c4h877.iris.business;

import cr.ac.ucr.paraiso.ie.c4h877.iris.data.EntrenamientoRepository;
import cr.ac.ucr.paraiso.ie.c4h877.iris.data.MuestraRepository;
import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Entrenamiento;
import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Muestra;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import org.springframework.stereotype.Service;

/**
 * Único lugar del proyecto que usa Java-ML. Aquí ocurre el "aprendizaje":
 * se construye el modelo KNN con las flores de la base de datos, se mide qué
 * tan bien funciona y se usa para clasificar flores nuevas.
 */
@Service
public class ModeloService {

    /** La validación cruzada parte los datos en 10 grupos. */
    private static final int PARTICIONES = 10;

    /** Semilla fija: así la validación cruzada da el mismo resultado cada vez. */
    private static final long SEMILLA = 42;

    private final MuestraRepository muestraRepository;
    private final EntrenamientoRepository entrenamientoRepository;

    // El modelo entrenado vive en memoria mientras el backend esté encendido.
    private Classifier clasificador;
    private int kActual;

    public ModeloService(MuestraRepository muestraRepository,
            EntrenamientoRepository entrenamientoRepository) {
        this.muestraRepository = muestraRepository;
        this.entrenamientoRepository = entrenamientoRepository;
    }

    /**
     * Entrena el modelo KNN con todas las flores guardadas y mide su exactitud.
     * Es "synchronized" para que dos entrenamientos no se mezclen si llegan al mismo tiempo.
     */
    public synchronized Entrenamiento entrenar(int k) {
        List<Muestra> muestras = muestraRepository.findAll();
        if (k < 1 || k > muestras.size()) {
            throw new IllegalArgumentException(
                    "k debe estar entre 1 y " + muestras.size() + " (cantidad de flores).");
        }

        Dataset datos = construirDataset(muestras);

        // 1. Medir el desempeño con validación cruzada: cada flor se clasifica
        //    con un modelo entrenado SIN esa flor.
        CrossValidation validacion = new CrossValidation(new KNearestNeighbors(k));
        Map<Object, PerformanceMeasure> resultados =
                validacion.crossValidation(datos, PARTICIONES, new Random(SEMILLA));

        // Java-ML devuelve una medida por especie. Los verdaderos positivos (tp)
        // de cada especie son las flores de esa especie que el modelo acertó.
        int aciertos = 0;
        for (PerformanceMeasure medida : resultados.values()) {
            aciertos += (int) medida.tp;
        }

        // 2. Entrenar el modelo final con TODAS las flores. Es el que se usa para predecir.
        Classifier knn = new KNearestNeighbors(k);
        knn.buildClassifier(datos);
        this.clasificador = knn;
        this.kActual = k;

        // 3. Guardar el resultado en la base de datos.
        Entrenamiento entrenamiento = new Entrenamiento();
        entrenamiento.setK(k);
        entrenamiento.setTotalMuestras(muestras.size());
        entrenamiento.setAciertos(aciertos);
        entrenamiento.setExactitud((double) aciertos / muestras.size());
        entrenamiento.setFecha(LocalDateTime.now());
        return entrenamientoRepository.save(entrenamiento);
    }

    /** Devuelve la especie que predice el modelo para una flor con estas medidas. */
    public synchronized String clasificar(double sepaloLargo, double sepaloAncho,
            double petaloLargo, double petaloAncho) {
        if (clasificador == null) {
            throw new IllegalStateException("El modelo todavía no ha sido entrenado.");
        }
        Instance flor = new DenseInstance(
                new double[] { sepaloLargo, sepaloAncho, petaloLargo, petaloAncho });
        return (String) clasificador.classify(flor);
    }

    public synchronized int getKActual() {
        return kActual;
    }

    public List<Entrenamiento> listarEntrenamientos() {
        return entrenamientoRepository.findAllByOrderByIdDesc();
    }

    /**
     * Convierte las filas de la base de datos al formato que entiende Java-ML:
     * un Dataset de Instance. Cada Instance lleva las 4 medidas y su especie (etiqueta).
     */
    private Dataset construirDataset(List<Muestra> muestras) {
        Dataset datos = new DefaultDataset();
        for (Muestra m : muestras) {
            double[] medidas = { m.getSepaloLargo(), m.getSepaloAncho(),
                    m.getPetaloLargo(), m.getPetaloAncho() };
            datos.add(new DenseInstance(medidas, m.getEspecie()));
        }
        return datos;
    }
}
