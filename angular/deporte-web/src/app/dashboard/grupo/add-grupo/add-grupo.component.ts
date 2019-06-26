import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialogRef, MatDatepickerInputEvent } from '@angular/material';
import { MatTableDataSource, MatPaginator, MatDialog, MatSnackBar } from '@angular/material';
import { ResponseContainer } from 'src/app/interface/response-container.interface';
import { DeporteList } from 'src/app/interface/deprte.interface';
import { DeporteService } from 'src/app/service/deporte.service';
import { UserService } from 'src/app/service/user.service';
import { UserList } from 'src/app/interface/user.interface';
import { GrupoService } from 'src/app/service/grupo.service';
import { GrupoDto } from 'src/app/dto/grupo.dto';

@Component({
  selector: 'app-add-grupo',
  templateUrl: './add-grupo.component.html',
  styleUrls: ['./add-grupo.component.scss']
})
export class AddGrupoComponent implements OnInit {
  startDate = Date.now();
  fecha: string;

  listaResponse: ResponseContainer;
  listaDeporte: DeporteList[];
  listaUser: UserList[];

  seleccionadoDeporte:string;
  titulo:string;
  seleccionadoUser:string;
  descripcion:string;
  localizacion:string;
  //fecha:string;
  locEnlace:string

  constructor(
    private userService: UserService, 
    private serviceDeporte: DeporteService, 
    private serviceGrupo: GrupoService,
    public snackBar: MatSnackBar, 
    public dialog: MatDialog, 
    private router: Router,
    public dialogRef: MatDialogRef<AddGrupoComponent>
  ) { }

  ngOnInit() {

    this.listDeporte();
    this.listUser();
  }

  addEvent(event: MatDatepickerInputEvent<Date>) {
    var date = new Date(`${event.value}`);
    // request a weekday along with a long date
    var options = { day: '2-digit', month: '2-digit', year: 'numeric' };
    console.log(date.toLocaleString('es-ES', options));
    this.fecha = date.toLocaleString('es-ES', options);
  }
  mapa(){
    if(this.localizacion === null){
      window.open("https://www.google.com/maps", "_blank")
    }
    else{
      this.locEnlace = this.localizacion.split(" ").join("+");
      window.open("https://www.google.com/maps/place/"+this.locEnlace, "_blank")
    }
  }

  listDeporte() {
    this.serviceDeporte.getAllDeporte().subscribe(lista => {
      this.listaResponse = lista;
      this.listaDeporte = this.listaResponse.rows;
      //console.error(this.listaDeporte);
    }, error => {
      console.error(error);
    });
  }
  listUser() {
    this.userService.getAllUser().subscribe(lista => {
      //console.error(lista);
      this.listaResponse = lista;
      this.listaUser = this.listaResponse.rows;
      //console.error(this.listaUser);
    }, error => {
      console.error(error);
    });
  }
  doAddGrupo(){
    console.log("date: "+this.fecha);

    this.serviceGrupo.addGrupo(new GrupoDto(this.seleccionadoDeporte,this.titulo,this.seleccionadoUser,this.descripcion,this.localizacion,this.fecha))
      .subscribe(addGrupoResp => {
        this.dialogRef.close();
      }, error => {
        let m = this.snackBar.open('Hubo un error y no se creo el elemento', 'Cerrar',{duration: 2000,});  
      })
  }
  closeDialog(){
    this.dialogRef.close();
  }
}
