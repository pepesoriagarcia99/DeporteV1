import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatToolbarModule,MatIconModule, MatCardModule, MatButtonModule, MatListModule, MatProgressBarModule, MatMenuModule, MatOptionModule, MatNativeDateModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';

import { DashboardComponent } from './dashboard.component';
import { DashboardRoutes } from './dashboard.routing';



import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatTableModule} from '@angular/material/table';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatDividerModule} from '@angular/material/divider';
import {MatDialogModule, MAT_DIALOG_DEFAULT_OPTIONS} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSelectModule} from '@angular/material/select';
import {MatDatepickerModule} from '@angular/material/datepicker';

import { FormsModule } from '@angular/forms';
import { ListaUserComponent } from './user/lista-user/lista-user.component';
import { ListaDeporteComponent } from './deporte/lista-deporte/lista-deporte.component';
import { ListaGrupoComponent } from './grupo/lista-grupo/lista-grupo.component';
import { AddDeporteComponent } from './deporte/add-deporte/add-deporte.component';
import { DeleteDeporteComponent } from './deporte/delete-deporte/delete-deporte.component';
import { EditDeporteComponent } from './deporte/edit-deporte/edit-deporte.component';
import { AddGrupoComponent } from './grupo/add-grupo/add-grupo.component';
import { DeleteGrupoComponent } from './grupo/delete-grupo/delete-grupo.component';
import { EditGrupoComponent } from './grupo/edit-grupo/edit-grupo.component';
import { AddUserComponent } from './user/add-user/add-user.component';
import { DeleteUserComponent } from './user/delete-user/delete-user.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(DashboardRoutes),
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatListModule,
    MatProgressBarModule,
    MatMenuModule,
    FlexLayoutModule,
    FormsModule,

    MatToolbarModule,
    MatInputModule,
    //AppRoutingModule,
    MatFormFieldModule,
    MatDividerModule,
    MatDialogModule,
    
    MatExpansionModule,
    MatTableModule,
    MatSnackBarModule,
    MatPaginatorModule,
    MatSelectModule,
    MatOptionModule,
    MatDatepickerModule,
    MatNativeDateModule,

  ],
  declarations: [ DashboardComponent, ListaUserComponent, ListaDeporteComponent, 
    ListaGrupoComponent, AddDeporteComponent, DeleteDeporteComponent, EditDeporteComponent,
     AddGrupoComponent, DeleteGrupoComponent, EditGrupoComponent, AddUserComponent, DeleteUserComponent, EditUserComponent ],
  entryComponents: [AddDeporteComponent, DeleteDeporteComponent, EditDeporteComponent,
    AddGrupoComponent, DeleteGrupoComponent, EditGrupoComponent,
    AddUserComponent, DeleteUserComponent, EditUserComponent]
})

export class DashboardModule {}
