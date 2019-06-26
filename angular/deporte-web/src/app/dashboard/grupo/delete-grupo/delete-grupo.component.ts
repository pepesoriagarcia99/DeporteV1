import { Component, OnInit, Inject  } from '@angular/core';
import { MatDialogRef, MatSnackBar} from '@angular/material';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GrupoService } from 'src/app/service/grupo.service';

@Component({
  selector: 'app-delete-grupo',
  templateUrl: './delete-grupo.component.html',
  styleUrls: ['./delete-grupo.component.scss']
})
export class DeleteGrupoComponent implements OnInit {

  constructor(
    private service: GrupoService, 
    public dialogRef: MatDialogRef<DeleteGrupoComponent>,
    public snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit() {
  }

  doDeleteDeporte(){
    this.service.deleteGrupo(this.data.idGrupo).subscribe(aporte =>{
      this.dialogRef.close();
    }, error =>{
      let m = this.snackBar.open('Hubo un error y no se borro el elemento', 'Cerrar',{duration: 2000,});
    });
  }
  closeDialog(){
    this.dialogRef.close();
  }

}
