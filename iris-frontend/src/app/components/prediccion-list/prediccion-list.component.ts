import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IrisService } from '../../services/iris.service';
import { Prediccion } from '../../models/iris.model';

@Component({
  selector: 'app-prediccion-list',
  imports: [CommonModule],
  templateUrl: './prediccion-list.component.html',
  styleUrl: './prediccion-list.component.css',
})
export class PrediccionListComponent implements OnInit {
  private irisService = inject(IrisService);

  predicciones = signal<Prediccion[]>([]);

  ngOnInit(): void {
    this.irisService.obtenerPredicciones().subscribe({
      next: (data) => this.predicciones.set(data),
      error: (err) => console.error('Error cargando predicciones', err),
    });
  }
}
