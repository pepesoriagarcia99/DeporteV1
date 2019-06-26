import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource, MatPaginator, MatDialog, MatSnackBar } from '@angular/material';

import { UserList } from '../../../interface/user.interface'
import { UserService } from '../../../service/user.service'
import { ResponseContainer } from '../../../interface/response-container.interface'
import { AddUserComponent } from '../add-user/add-user.component';
import { DeleteUserComponent } from '../delete-user/delete-user.component';
import { EditUserComponent } from '../edit-user/edit-user.component';


@Component({
  selector: 'app-lista-user',
  templateUrl: './lista-user.component.html',
  styleUrls: ['./lista-user.component.scss']
})
export class ListaUserComponent implements OnInit {
  listaResponse: ResponseContainer;
  listaUsuariosRes: UserList[];
  displayedColumns: string[] = ['foto', 'username', 'email', 'type', 'role','acciones'];
  dataSource = new MatTableDataSource<UserList[]>();
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private userService: UserService, 
    public snackBar: MatSnackBar, 
    public dialog: MatDialog, 
    private router: Router) { }

  ngOnInit() {
    if(localStorage.getItem('token') == null){
      this.router.navigate(['']);
    }

    this.listUser();
  }

  listUser() {
    this.userService.getAllUser().subscribe(lista => {
      this.listaResponse = lista;
      this.listaUsuariosRes = this.listaResponse.rows;
      this.dataSource = new MatTableDataSource<UserList[]>(this.listaResponse.rows);
      this.dataSource.paginator = this.paginator;
      // this.dataSource = lista;
      //console.log(this.listaUsuariosRes);

    }, error => {
      console.error(error);
    });
  }
  openDialogAddUser(){
    const dialogo = this.dialog.open(AddUserComponent);

    dialogo.afterClosed().subscribe(result => {
      this.listUser();
    });
  }
  openDialogDeleteUser(idUser:string){
    const dialogo = this.dialog.open(DeleteUserComponent,{data: { idUser: idUser }});

    dialogo.afterClosed().subscribe(result => {
      this.listUser();
    });
  }
  openDialoEditUser(user:UserList){
    const dialogo = this.dialog.open(EditUserComponent,{data: { user: user},});

    dialogo.afterClosed().subscribe(result => {
      this.listUser();
    });
  }
  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
