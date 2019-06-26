import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';

import { ResponseContainer } from '../interface/response-container.interface'
import { GrupoDto } from '../dto/grupo.dto';

const Url = `${environment.apiUrl}`;

@Injectable({
  providedIn: 'root'
})
export class GrupoService {

  constructor(private http: HttpClient) { }

  getAllGrupo(): Observable<ResponseContainer> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.get<ResponseContainer>(`${Url}/grupos/all?access_token=${localStorage.getItem('token')}`, requestOptions);
  }

  addGrupo(addGrupoDto: GrupoDto): Observable<string> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.post<string>(`${Url}/grupos/admin?access_token=${localStorage.getItem('token')}`, addGrupoDto, requestOptions);
  }

  deleteGrupo(idGrupos:number):Observable<string> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.delete<string>(`${Url}/grupos/${idGrupos}?access_token=${localStorage.getItem('token')}`,  requestOptions);
  }


  editGrupo(idGrupos:string,editGrupoDto: GrupoDto):Observable<GrupoDto> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.put<GrupoDto>(`${Url}/grupos/${idGrupos}?access_token=${localStorage.getItem('token')}`, editGrupoDto, requestOptions);
  }
}
