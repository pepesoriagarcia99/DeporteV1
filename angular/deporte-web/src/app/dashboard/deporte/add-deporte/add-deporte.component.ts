import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';

import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material';

import { DeporteDto } from '../../../dto/deporte.dto'
import { DeporteService } from 'src/app/service/deporte.service';

@Component({
  selector: 'app-add-deporte',
  templateUrl: './add-deporte.component.html',
  styleUrls: ['./add-deporte.component.scss']
})
export class AddDeporteComponent implements OnInit {
  nombre: string;
  participantes: number;
  descripcion: string;
  requisitos:string;
  foto:string;

  constructor(
    private router: Router,
    public snackBar: MatSnackBar,
    private service: DeporteService, 
    public dialogRef: MatDialogRef<AddDeporteComponent>
  ) { }

  ngOnInit() {
  }

  doAddDeporte(){
      this.service.addDeporte(new DeporteDto(this.nombre,this.descripcion,this.participantes,this.requisitos,this.foto))
      .subscribe(addDeporteResp => {
        this.dialogRef.close();
      }, error => {
        let m = this.snackBar.open('Hubo un error y no se creo el elemento', 'Cerrar',{duration: 2000,});  
      })
  }
  closeDialog(){
    this.dialogRef.close();
  }
}
