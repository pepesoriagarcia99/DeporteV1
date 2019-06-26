import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';

import { DeporteList } from '../interface/deprte.interface'
import { ResponseContainer } from '../interface/response-container.interface'
import { Deporte } from '../interface/add-edit-deporte.interface';
import { DeporteDto } from '../dto/deporte.dto';

const Url = `${environment.apiUrl}`;
const masterKey = `${environment.masterKey}` 

@Injectable({
  providedIn: 'root'
})
export class DeporteService {

  constructor(private http: HttpClient) { }

  getAllDeporte(): Observable<ResponseContainer> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.get<ResponseContainer>(`${Url}/deportes?access_token=${masterKey}`, requestOptions);
  }

  addDeporte(addDeporteDto: DeporteDto): Observable<Deporte> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.post<Deporte>(`${Url}/deportes?access_token=${localStorage.getItem('token')}`, addDeporteDto, requestOptions);
  }
  
  deleteDeporte(idDeporte:number):Observable<string> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.delete<string>(`${Url}/deportes/${idDeporte}?access_token=${localStorage.getItem('token')}`,  requestOptions);
  }

  editDeporte(idDeporte:string,editDeporteDto: DeporteDto):Observable<Deporte> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.put<Deporte>(`${Url}/deportes/${idDeporte}?access_token=${localStorage.getItem('token')}`, editDeporteDto, requestOptions);
  }


}
