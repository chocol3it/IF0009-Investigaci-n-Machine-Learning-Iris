package cr.ac.ucr.paraiso.ie.c4h877.iris.config;

import cr.ac.ucr.paraiso.ie.c4h877.iris.business.ModeloService;
import cr.ac.ucr.paraiso.ie.c4h877.iris.data.MuestraRepository;
import cr.ac.ucr.paraiso.ie.c4h877.iris.domain.Muestra;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Se ejecuta una vez al arrancar el backend:
 * 1. Si la tabla Muestra está vacía, carga las 150 flores de resources/data/iris.data.
 * 2. Entrena el modelo para que esté listo antes de la primera predicción.
 */
@Component
public class DatosIniciales implements CommandLineRunner {

    private final MuestraRepository muestraRepository;
    private final ModeloService modeloService;
    private final int kInicial;

    public DatosIniciales(MuestraRepository muestraRepository, ModeloService modeloService,
            @Value("${app.modelo.k-inicial}") int kInicial) {
        this.muestraRepository = muestraRepository;
        this.modeloService = modeloService;
        this.kInicial = kInicial;
    }

    @Override
    public void run(String... args) throws Exception {
        if (muestraRepository.count() == 0) {
            List<Muestra> muestras = leerIrisData();
            muestraRepository.saveAll(muestras);
            System.out.println("Datos iniciales: se cargaron " + muestras.size() + " flores de iris.data");
        }
        modeloService.entrenar(kInicial);
        System.out.println("Modelo KNN entrenado con k = " + kInicial);
    }

    /** Lee iris.data. Cada línea tiene el formato: 5.1,3.5,1.4,0.2,Iris-setosa */
    private List<Muestra> leerIrisData() throws Exception {
        List<Muestra> muestras = new ArrayList<>();
        InputStream archivo = getClass().getResourceAsStream("/data/iris.data");
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(archivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.isBlank()) {
                    continue; // el archivo termina con una línea vacía
                }
                String[] partes = linea.split(",");
                muestras.add(new Muestra(
                        Double.parseDouble(partes[0]),
                        Double.parseDouble(partes[1]),
                        Double.parseDouble(partes[2]),
                        Double.parseDouble(partes[3]),
                        partes[4]));
            }
        }
        return muestras;
    }
}
