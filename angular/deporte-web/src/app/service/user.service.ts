import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';

import { ResponseContainer } from '../interface/response-container.interface'
import { RegisterDto } from '../dto/register.dto';
import { LoginResponse } from '../interface/login.interface';
import { EditUserDto } from '../dto/edit-user.dto';


const Url = `${environment.apiUrl}`;
const masterKey = `${environment.masterKey}`

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUser(): Observable<ResponseContainer> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.get<ResponseContainer>(`${Url}/users?access_token=${localStorage.getItem('token')}`, requestOptions);
  }

  addUser(addUser: RegisterDto): Observable<LoginResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.post<LoginResponse>(`${Url}/users?access_token=${masterKey}`, addUser, requestOptions);
  }

  deleteUser(idUser:number):Observable<string> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.delete<string>(`${Url}/users/${idUser}?access_token=${localStorage.getItem('token')}`,  requestOptions);
  }

  editUser(idUser:string,editUserDto: EditUserDto):Observable<EditUserDto> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.put<RegisterDto>(`${Url}/users/${idUser}?access_token=${localStorage.getItem('token')}`, editUserDto, requestOptions);
  }
}
