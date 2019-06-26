import { Component, OnInit, Inject  } from '@angular/core';
import { MatDialogRef, MatSnackBar} from '@angular/material';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

import { DeporteService } from 'src/app/service/deporte.service';

@Component({
  selector: 'app-delete-deporte',
  templateUrl: './delete-deporte.component.html',
  styleUrls: ['./delete-deporte.component.scss']
})
export class DeleteDeporteComponent implements OnInit {


  constructor(
    private service: DeporteService, 
    public dialogRef: MatDialogRef<DeleteDeporteComponent>,
    public snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit() {
  }
  doDeleteDeporte(){
    this.service.deleteDeporte(this.data.idDeporte).subscribe(aport =>{
      this.dialogRef.close();
    }, error =>{
      let m = this.snackBar.open('Hubo un error y no se borro el elemento', 'Cerrar',{duration: 2000,});
    });
  }
  closeDialog(){
    this.dialogRef.close();
  }
}
