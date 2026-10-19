import { Routes } from '@angular/router';
import { PrediccionFormComponent } from './components/prediccion-form/prediccion-form.component';
import { PrediccionListComponent } from './components/prediccion-list/prediccion-list.component';
import { ModeloPanelComponent } from './components/modelo-panel/modelo-panel.component';
import { MuestraListComponent } from './components/muestra-list/muestra-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'predecir', pathMatch: 'full' },
  { path: 'predecir', component: PrediccionFormComponent },
  { path: 'historial', component: PrediccionListComponent },
  { path: 'modelo', component: ModeloPanelComponent },
  { path: 'muestras', component: MuestraListComponent },
  { path: '**', redirectTo: 'predecir' },
];
