import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { IrisService } from '../../services/iris.service';
import { Medidas, Prediccion } from '../../models/iris.model';

// Validadores que se repiten en las cuatro medidas: obligatoria y entre 0.1 y 10 cm.
const medidaValidators = [Validators.required, Validators.min(0.1), Validators.max(10)];

@Component({
  selector: 'app-prediccion-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './prediccion-form.component.html',
  styleUrl: './prediccion-form.component.css',
})
export class PrediccionFormComponent {
  private fb = inject(NonNullableFormBuilder);
  private irisService = inject(IrisService);

  resultado = signal<Prediccion | null>(null);
  mensaje = signal('');
  enviando = signal(false);

  form = this.fb.group({
    sepaloLargo: [0, medidaValidators],
    sepaloAncho: [0, medidaValidators],
    petaloLargo: [0, medidaValidators],
    petaloAncho: [0, medidaValidators],
  });

  // Ejemplos tomados del archivo iris.data (filas 1, 51, 101 y 71) para la demo.
  ejemplos: { nombre: string; medidas: Medidas }[] = [
    { nombre: 'Fila 1 (setosa)', medidas: { sepaloLargo: 5.1, sepaloAncho: 3.5, petaloLargo: 1.4, petaloAncho: 0.2 } },
    { nombre: 'Fila 51 (versicolor)', medidas: { sepaloLargo: 7.0, sepaloAncho: 3.2, petaloLargo: 4.7, petaloAncho: 1.4 } },
    { nombre: 'Fila 101 (virginica)', medidas: { sepaloLargo: 6.3, sepaloAncho: 3.3, petaloLargo: 6.0, petaloAncho: 2.5 } },
    { nombre: 'Fila 71 (versicolor difícil)', medidas: { sepaloLargo: 5.9, sepaloAncho: 3.2, petaloLargo: 4.8, petaloAncho: 1.8 } },
  ];

  usarEjemplo(medidas: Medidas): void {
    this.form.setValue(medidas);
    this.resultado.set(null);
    this.mensaje.set('');
  }

  predecir(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.mensaje.set('Revise las medidas: deben estar entre 0.1 y 10 cm.');
      return;
    }

    this.enviando.set(true);
    this.mensaje.set('');
    this.irisService.predecir(this.form.getRawValue()).subscribe({
      next: (prediccion) => {
        this.resultado.set(prediccion);
        this.enviando.set(false);
      },
      error: (err) => {
        console.error('Error al predecir', err);
        this.mensaje.set(err?.error?.detail ?? 'No se pudo conectar con el backend.');
        this.enviando.set(false);
      },
    });
  }
}
