// Las tres especies del dataset Iris, escritas igual que en el archivo iris.data.
export type Especie = 'Iris-setosa' | 'Iris-versicolor' | 'Iris-virginica';

// Las cuatro medidas de una flor, en centímetros.
export interface Medidas {
  sepaloLargo: number;
  sepaloAncho: number;
  petaloLargo: number;
  petaloAncho: number;
}

// Una flor de entrenamiento: sus medidas y su especie ya conocida (etiqueta).
export interface Muestra extends Medidas {
  id?: number;
  especie: Especie;
}

// Una consulta hecha al modelo y lo que respondió.
export interface Prediccion extends Medidas {
  id: number;
  especiePredicha: Especie;
  k: number;
  fecha: string;
}

// Un entrenamiento del modelo y su desempeño medido con validación cruzada.
export interface Entrenamiento {
  id: number;
  k: number;
  totalMuestras: number;
  aciertos: number;
  exactitud: number;
  fecha: string;
}
