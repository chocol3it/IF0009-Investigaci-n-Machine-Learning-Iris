import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Entrenamiento, Medidas, Muestra, Prediccion } from '../models/iris.model';

@Injectable({
  providedIn: 'root',
})
export class IrisService {
  private http = inject(HttpClient);
  private readonly baseUrl = environment.API_URL;

  // Envía las 4 medidas y el backend responde con la especie que predijo el modelo.
  predecir(medidas: Medidas): Observable<Prediccion> {
    return this.http.post<Prediccion>(`${this.baseUrl}predicciones`, medidas);
  }

  obtenerPredicciones(): Observable<Prediccion[]> {
    return this.http.get<Prediccion[]>(`${this.baseUrl}predicciones`);
  }

  obtenerMuestras(): Observable<Muestra[]> {
    return this.http.get<Muestra[]>(`${this.baseUrl}muestras`);
  }

  agregarMuestra(muestra: Muestra): Observable<Muestra> {
    return this.http.post<Muestra>(`${this.baseUrl}muestras`, muestra);
  }

  // Vuelve a entrenar el modelo con el valor de k indicado.
  entrenar(k: number): Observable<Entrenamiento> {
    return this.http.post<Entrenamiento>(`${this.baseUrl}modelo/entrenar?k=${k}`, {});
  }

  obtenerEntrenamientos(): Observable<Entrenamiento[]> {
    return this.http.get<Entrenamiento[]>(`${this.baseUrl}modelo/entrenamientos`);
  }
}
