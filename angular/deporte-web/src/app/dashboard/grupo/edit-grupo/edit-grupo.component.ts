import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource, MatPaginator, MatDialog, MatSnackBar, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { GrupoService } from 'src/app/service/grupo.service';
import { GrupoList } from 'src/app/interface/grupo.interface';
import { GrupoDto } from 'src/app/dto/grupo.dto';

@Component({
  selector: 'app-edit-grupo',
  templateUrl: './edit-grupo.component.html',
  styleUrls: ['./edit-grupo.component.scss']
})
export class EditGrupoComponent implements OnInit {
  seleccionadoDeporte:string;
  titulo:string;
  seleccionadoUser:string;
  descripcion:string;
  localizacion:string;
  fecha:string;

  grupo:GrupoList;
  id:string;

  constructor(
    private service: GrupoService,
    public dialogRef: MatDialogRef<EditGrupoComponent>,
    public snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit() {
    this.grupo = this.data.grupo;

    this.id = this.grupo.id;
    this.seleccionadoDeporte = this.grupo.id_deporte.id;
    this.titulo = this.grupo.titulo;
    this.seleccionadoUser = this.grupo.creador_id._id;
    this.descripcion = this.grupo.descripcion;
    this.localizacion = this.grupo.localizacion;
    this.fecha = this.grupo.fecha;
  }
  doEditDeporte(){
    let g = new GrupoDto(this.seleccionadoDeporte,this.titulo,this.seleccionadoUser,this.descripcion,this.localizacion,this.fecha);

    this.service.editGrupo(this.id,g).subscribe(editGrupoResp => {
      this.dialogRef.close();
    }, error => {
      let m = this.snackBar.open('Hubo un error y no se edito el elemento', 'Cerrar',{duration: 2000,});  
    })
  }
  closeDialog(){
    this.dialogRef.close();
  }

}
