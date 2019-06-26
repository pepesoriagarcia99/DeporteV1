import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';

import {LoginDto} from '../dto/login.dto'
import {RegisterDto} from '../dto/register.dto'
import {LoginResponse} from '../interface/login.interface'

const authUrl = `${environment.apiUrl}`;
const masterKey = `${environment.masterKey}`

const requestOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(loginDto: LoginDto): Observable<LoginResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Basic `+btoa(`${loginDto.email}:${loginDto.password}`),
        'Access-Control-Allow-Origin': '*'
      })
    }

    return this.http.post<LoginResponse>(`${authUrl}/auth?access_token=${masterKey}`, null, requestOptions);
  }

  register(registerDto: RegisterDto): Observable<LoginResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    }

    return this.http.post<LoginResponse>(`${authUrl}/users?access_token=${masterKey}`, registerDto, requestOptions);
  }

  setLoginData(loginResponse: LoginResponse) {
    localStorage.setItem('token', loginResponse.token);
    localStorage.setItem('name', loginResponse.user.name);
    localStorage.setItem('picture', loginResponse.user.picture);    
    localStorage.setItem('email', loginResponse.user.email);
    localStorage.setItem('type', loginResponse.user.type);
    localStorage.setItem('role', loginResponse.user.role)
  }
  delteLoginData() {
    localStorage.clear();
  }
}
