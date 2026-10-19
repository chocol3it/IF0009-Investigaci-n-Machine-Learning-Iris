import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { IrisService } from '../../services/iris.service';
import { Especie, Muestra } from '../../models/iris.model';

const medidaValidators = [Validators.required, Validators.min(0.1), Validators.max(10)];

@Component({
  selector: 'app-muestra-list',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './muestra-list.component.html',
  styleUrl: './muestra-list.component.css',
})
export class MuestraListComponent implements OnInit {
  private fb = inject(NonNullableFormBuilder);
  private irisService = inject(IrisService);

  muestras = signal<Muestra[]>([]);
  mensaje = signal('');
  especies: Especie[] = ['Iris-setosa', 'Iris-versicolor', 'Iris-virginica'];

  form = this.fb.group({
    sepaloLargo: [0, medidaValidators],
    sepaloAncho: [0, medidaValidators],
    petaloLargo: [0, medidaValidators],
    petaloAncho: [0, medidaValidators],
    especie: this.fb.control<Especie>('Iris-setosa', Validators.required),
  });

  ngOnInit(): void {
    this.cargarMuestras();
  }

  cargarMuestras(): void {
    this.irisService.obtenerMuestras().subscribe({
      next: (data) => this.muestras.set(data),
      error: (err) => console.error('Error cargando muestras', err),
    });
  }

  // Cuenta cuántas flores hay de una especie (para el resumen de arriba).
  contar(especie: Especie): number {
    return this.muestras().filter((m) => m.especie === especie).length;
  }

  agregar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.mensaje.set('Revise las medidas: deben estar entre 0.1 y 10 cm.');
      return;
    }

    this.irisService.agregarMuestra(this.form.getRawValue()).subscribe({
      next: (nueva) => {
        this.mensaje.set(`Flor #${nueva.id} agregada. Reentrene el modelo para que la use.`);
        this.form.reset();
        this.cargarMuestras();
      },
      error: (err) => {
        console.error('Error al agregar la muestra', err);
        this.mensaje.set(err?.error?.detail ?? 'No se pudo agregar la muestra.');
      },
    });
  }
}
