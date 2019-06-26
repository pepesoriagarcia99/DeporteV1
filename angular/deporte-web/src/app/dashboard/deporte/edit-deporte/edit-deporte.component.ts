import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material';

import { DeporteList } from '../../../interface/deprte.interface'
import { DeporteDto } from '../../../dto/deporte.dto'
import { DeporteService } from 'src/app/service/deporte.service';

@Component({
  selector: 'app-edit-deporte',
  templateUrl: './edit-deporte.component.html',
  styleUrls: ['./edit-deporte.component.scss']
})
export class EditDeporteComponent implements OnInit {
  id:string;
  nombre:string;
  participantes:number;
  requisitos:string;
  foto:string;
  descripcion:string;
  deporte:DeporteList;

  constructor(
    private service: DeporteService, 
    public dialogRef: MatDialogRef<EditDeporteComponent>,
    public snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit() {
    this.deporte = this.data.deporte;

    this.id = this.deporte.id;
    this.nombre = this.deporte.nombre;
    this.participantes = this.deporte.nParticipantes;
    this.descripcion = this.deporte.descripcion;
    this.requisitos = this.deporte.requisitos;
    this.foto = this.deporte.foto_id;
  }

  doEditDeporte(){
    let d = new DeporteDto(this.nombre, this.descripcion,this.participantes,this.requisitos,this.foto)

    this.service.editDeporte(this.id,d).subscribe(editDeporteResp => {
      this.dialogRef.close();
    }, error => {
      let m = this.snackBar.open('Hubo un error y no se edito el elemento', 'Cerrar',{duration: 2000,});  
    })
  }
  closeDialog(){
    this.dialogRef.close();
  }
}
