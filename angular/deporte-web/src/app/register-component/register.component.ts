import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import { RegisterDto } from '../dto/register.dto';
import {MatSnackBar} from '@angular/material';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  email: string;
  password: string;
  name: string;
  seleccionadoType: string;
  
  constructor(private authService: AuthService, private router: Router,public snackBar: MatSnackBar) { }

  ngOnInit() {
  }

  /*
  doRegister() {
    const registerDto = new RegisterDto(this.email, this.password, this.name, this.seleccionadoType);
    this.authService.register(registerDto).subscribe(registerResp => {
      console.log(registerResp);
      this.authService.setLoginData(registerResp);
      if(registerResp.user.role == "admin"){
        this.router.navigate(['inicio']);
      }
      else{
        let m = this.snackBar.open(registerResp.user.name+' tu cuenta ya esta creada, pero debes ser admin para entrar', 'Cerrar',{duration: 2000,});
      }
    }, error => {
      console.log('Error en peticion');
      let m = this.snackBar.open('Error en peticion', 'Cerrar',{duration: 2000,});
    });
  }

  */

}
