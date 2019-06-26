import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource, MatPaginator, MatDialog, MatSnackBar } from '@angular/material';

import { ResponseContainer } from 'src/app/interface/response-container.interface';
import { GrupoList } from '../../../interface/grupo.interface'
import { GrupoService } from '../../../service/grupo.service';
import { AddGrupoComponent } from '../add-grupo/add-grupo.component';
import { DeleteGrupoComponent } from '../delete-grupo/delete-grupo.component';
import { EditGrupoComponent } from '../edit-grupo/edit-grupo.component';

//import { Title } from '@angular/platform-browser';


@Component({
  selector: 'app-lista-grupo',
  templateUrl: './lista-grupo.component.html',
  styleUrls: ['./lista-grupo.component.scss']
})
export class ListaGrupoComponent implements OnInit {
  bloqueado:boolean;

  listaResponse: ResponseContainer;
  listaGruposRes: GrupoList[];
  //titleFilter: any = { title: '' };
  displayedColumns: string[] = ['estado', 'titulo', 'deporte', 'user', 'fecha', 'fecha_creacion', 'acciones'];
  dataSource = new MatTableDataSource<GrupoList[]>();

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private Service: GrupoService,
    public snackBar: MatSnackBar, 
    public dialog: MatDialog, 
    private router: Router,
    //private title: Title
  ) { }

  ngOnInit() {
    //this.title.setTitle('CofTriana - Inicio');

    if(localStorage.getItem('token') == null){
      this.router.navigate(['']);
    }

    this.listAllGrupo();
  }

  listAllGrupo(){
    this.Service.getAllGrupo().subscribe(lista => {
      this.listaResponse = lista;
      this.listaGruposRes = this.listaResponse.rows;
      //bloqueado = 
      this.dataSource = new MatTableDataSource<GrupoList[]>(this.listaResponse.rows);
      this.dataSource.paginator = this.paginator;

      //console.log(this.listaUsuariosRes);

    }, error => {
      console.error(error);
    });
  }
  openDialogAddGrupo(){
    const dialogo = this.dialog.open(AddGrupoComponent);

    dialogo.afterClosed().subscribe(result => {
      this.listAllGrupo();
    });
  }
  openDialogDeleteGrupo(idGrupo:string){
    const dialogo = this.dialog.open(DeleteGrupoComponent,{data: { idGrupo: idGrupo }});

    dialogo.afterClosed().subscribe(result => {
      this.listAllGrupo();
    });
  }
  openDialoEditGrupo(grupo:GrupoList){
    const dialogo = this.dialog.open(EditGrupoComponent,{data: { grupo: grupo},});

    dialogo.afterClosed().subscribe(result => {
      this.listAllGrupo();
    });
  }
  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
