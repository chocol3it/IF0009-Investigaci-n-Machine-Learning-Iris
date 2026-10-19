# Clasificador Iris con Java-ML

Curso: IF0009 - Desarrollo de Software IV (II-2026)

Aplicación web que predice la especie de una flor Iris (setosa, versicolor o virginica) a partir
de 4 medidas, usando el algoritmo KNN de la librería Java-ML. Los datos se guardan en una base H2.

## Requisitos

- Java 21 y Maven
- Node.js 22.22.3 o superior
- Java-ML 0.1.7 ya viene en `iris-backend/lib`

## Cómo ejecutar

Backend (desde `iris-backend`):

```
mvn spring-boot:run
```

Queda en http://localhost:8080. Al iniciar carga las 150 flores de `iris.data` y entrena el modelo con k = 5.

Frontend (desde `iris-frontend`):

```
npm install
npm start
```

Abrir http://localhost:4200.

Consola de H2: http://localhost:8080/h2-console (URL `jdbc:h2:file:./data/irisdb`, usuario `sa`, sin contraseña).

## Endpoints

- POST /api/v1/predicciones: predice la especie
- GET /api/v1/predicciones: historial de predicciones
- GET /api/v1/muestras: flores de entrenamiento
- POST /api/v1/muestras: agrega una flor
- POST /api/v1/modelo/entrenar?k=5: reentrena el modelo
- GET /api/v1/modelo/entrenamientos: historial de entrenamientos

## Pruebas

```
cd iris-backend
mvn test
```

## Fuentes

- Abeel, T., Van de Peer, Y. y Saeys, Y. (2009). Java-ML: A Machine Learning Library. Journal of Machine Learning Research, 10, 931-934.
- Fisher, R. (1936). Iris [Dataset]. UCI Machine Learning Repository. https://doi.org/10.24432/C56C76
- Java-ML: https://github.com/AbeelLab/javaml
