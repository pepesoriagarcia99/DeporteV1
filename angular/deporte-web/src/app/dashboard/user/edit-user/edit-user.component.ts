import { Component, OnInit, Inject } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { RegisterDto } from 'src/app/dto/register.dto';
import { UserList } from 'src/app/interface/user.interface';
import { EditUserDto } from 'src/app/dto/edit-user.dto';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {
  id:string;
  email:string;
  name:string;
  seleccionadoType:string;

  user:UserList;

  constructor(
    private service: UserService,
    public dialogRef: MatDialogRef<EditUserComponent>,
    public snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit() {
    this.user = this.data.user;

    this.id = this.user.id;
    this.email = this.user.email;
    this.name = this.user.name;
    this.seleccionadoType = this.user.type;
  }

  doEditUser(){
    let u = new EditUserDto(this.email,this.name,this.seleccionadoType);
    
    this.service.editUser(this.id,u).subscribe(editUserResp => {
      this.dialogRef.close();
    }, error => {
      let m = this.snackBar.open('Hubo un error y no se edito el elemento', 'Cerrar',{duration: 2000,});  
    })
  }
  closeDialog(){
    this.dialogRef.close();
  }

}
