import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource, MatPaginator, MatDialog, MatSnackBar } from '@angular/material';

import { DeporteService } from '../../../service/deporte.service'
import { ResponseContainer } from '../../../interface/response-container.interface'
import { DeporteList } from '../../../interface/deprte.interface'
import { AddDeporteComponent } from '../../deporte/add-deporte/add-deporte.component';
import { DeleteDeporteComponent } from '../../deporte/delete-deporte/delete-deporte.component';
import { EditDeporteComponent } from '../edit-deporte/edit-deporte.component';

import { DeporteDto } from '../../../dto/deporte.dto'

@Component({
  selector: 'app-lista-deporte',
  templateUrl: './lista-deporte.component.html',
  styleUrls: ['./lista-deporte.component.scss']
})
export class ListaDeporteComponent implements OnInit {
  listaResponse: ResponseContainer;
  listaDeportesRes: DeporteList[];
  displayedColumns: string[] = ['foto', 'nombre', 'participantes', 'fecha', 'acciones'];
  dataSource = new MatTableDataSource<DeporteList[]>();
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private service: DeporteService, 
    public snackBar: MatSnackBar, 
    public dialog: MatDialog, 
    private router: Router
  ) { }

  ngOnInit() {
    if(localStorage.getItem('token') == null){
      this.router.navigate(['']);
    }

    this.listDeporte();
  }

  listDeporte() {
    this.service.getAllDeporte().subscribe(lista => {
      this.listaResponse = lista;
      this.listaDeportesRes = this.listaResponse.rows;
      this.dataSource = new MatTableDataSource<DeporteList[]>(this.listaResponse.rows);
      this.dataSource.paginator = this.paginator;
      // this.dataSource = lista;
      //console.log(this.listaUsuariosRes);

    }, error => {
      console.error(error);
    });
  }
  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  openDialogAddDeporte(){
    const dialogo = this.dialog.open(AddDeporteComponent);

    dialogo.afterClosed().subscribe(result => {
      this.listDeporte();
    });
  }
  openDialogDeleteDeporte(idDeporte:number){
    console.log(idDeporte);
    const dialogo = this.dialog.open(DeleteDeporteComponent,{data: { idDeporte: idDeporte }});

    dialogo.afterClosed().subscribe(result => {
      this.listDeporte();
    });
  }
  openDialoEditDeporte(deporte:DeporteList){
    const dialogo = this.dialog.open(EditDeporteComponent,{data: { deporte: deporte},});

    dialogo.afterClosed().subscribe(result => {
      this.listDeporte();
    });
  }
}
