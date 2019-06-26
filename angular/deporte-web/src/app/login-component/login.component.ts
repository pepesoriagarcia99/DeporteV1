import { Component, OnInit } from '@angular/core';

import {AuthService} from './../service/auth.service'
import {LoginDto} from './../dto/login.dto'

import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-login-component',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  email: string;
  password: string;

  constructor(
    private authService: AuthService,
    private router: Router,
    public snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.authService.delteLoginData();
  }

  doLogin() {
    const loginDto = new LoginDto(this.email, this.password);
    this.authService.login(loginDto).subscribe(loginResp => {
      console.log(loginResp);
      if(loginResp.user.locked == true){
        let m = this.snackBar.open('Este usuario esta bloqueado, solicite desbloqueo', 'Cerrar',{duration: 5000,});
      }
      else{
        this.authService.setLoginData(loginResp);
        if(loginResp.user.role == "admin"){
          this.router.navigate(['inicio']);
        }
        else{
          let m = this.snackBar.open('Tienes que ser admin para hacer login', 'Cerrar',{duration: 2000,});
        }
      }
    }, error => {
      console.log('Error en peticion');
      let m = this.snackBar.open('Error en peticion', 'Cerrar',{duration: 2000,});
    });

    console.log(this.email);
    console.log(this.password);

  }

}
