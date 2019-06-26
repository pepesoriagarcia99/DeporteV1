import { Component, OnInit, Inject } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.scss']
})
export class DeleteUserComponent implements OnInit {

  constructor(
    private service: UserService, 
    public dialogRef: MatDialogRef<DeleteUserComponent>,
    public snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit() {
  }

  doDeleteUser(){
    this.service.deleteUser(this.data.idUser).subscribe(aport =>{
      this.dialogRef.close();
    }, error =>{
      let m = this.snackBar.open('Hubo un error y no se borro el elemento', 'Cerrar',{duration: 2000,});
    });
  }
  closeDialog(){
    this.dialogRef.close();
  }

}
