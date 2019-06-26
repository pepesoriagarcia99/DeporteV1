import { Routes } from '@angular/router';
import { ListaUserComponent } from './user/lista-user/lista-user.component'

import { DashboardComponent } from './dashboard.component';
import { ListaDeporteComponent } from './deporte/lista-deporte/lista-deporte.component'
import { ListaGrupoComponent } from './grupo/lista-grupo/lista-grupo.component'


export const DashboardRoutes: Routes = [
  {path: '',component: DashboardComponent},
  {path: 'user',component: ListaUserComponent},
  {path: 'groups',component: ListaGrupoComponent},
  {path: 'sport',component: ListaDeporteComponent}
];
