import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IrisService } from '../../services/iris.service';
import { Entrenamiento } from '../../models/iris.model';

@Component({
  selector: 'app-modelo-panel',
  imports: [CommonModule, FormsModule],
  templateUrl: './modelo-panel.component.html',
  styleUrl: './modelo-panel.component.css',
})
export class ModeloPanelComponent implements OnInit {
  private irisService = inject(IrisService);

  k = 5;
  entrenamientos = signal<Entrenamiento[]>([]);
  mensaje = signal('');
  entrenando = signal(false);

  ngOnInit(): void {
    this.cargarEntrenamientos();
  }

  cargarEntrenamientos(): void {
    this.irisService.obtenerEntrenamientos().subscribe({
      next: (data) => this.entrenamientos.set(data),
      error: (err) => console.error('Error cargando entrenamientos', err),
    });
  }

  entrenar(): void {
    this.entrenando.set(true);
    this.mensaje.set('');
    this.irisService.entrenar(this.k).subscribe({
      next: (resultado) => {
        this.mensaje.set(
          `Modelo entrenado con k = ${resultado.k}: acertó ${resultado.aciertos} de ${resultado.totalMuestras} flores.`,
        );
        this.entrenando.set(false);
        this.cargarEntrenamientos();
      },
      error: (err) => {
        console.error('Error al entrenar', err);
        this.mensaje.set(err?.error?.detail ?? 'No se pudo entrenar el modelo.');
        this.entrenando.set(false);
      },
    });
  }
}
