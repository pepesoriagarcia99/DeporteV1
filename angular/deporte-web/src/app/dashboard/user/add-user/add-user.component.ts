import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { MatTableDataSource, MatPaginator, MatDialog, MatSnackBar, MatDialogRef } from '@angular/material';
import { RegisterDto } from 'src/app/dto/register.dto';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {
  email:string;
  pass:string;
  name:string;
  seleccionadoType:string;
  seleccionadoRol:string;


  constructor(
    private service: UserService,
    public dialogRef: MatDialogRef<AddUserComponent>,
    public snackBar: MatSnackBar, 
  ) { }

  ngOnInit() {
  }

  doAddUser(){
    let u = new RegisterDto(this.email,this.pass,this.name,this.seleccionadoType, this.seleccionadoRol);

    this.service.addUser(u)
      .subscribe(addUserResp => {
        this.dialogRef.close();
      }, error => {
        console.log(u);
        let m = this.snackBar.open('Hubo un error y no se creo el elemento', 'Cerrar',{duration: 2000,});  
      })
  }
  closeDialog(){
    this.dialogRef.close();
  }

}
