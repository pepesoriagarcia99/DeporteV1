export class DeporteDto {
    nombre: string;
    descripcion:string;
    requisitos:String;
    nParticipantes:number;
    foto_id:string;

    constructor(nombre:string, descripcion:string, nParticipantes:number,requisitos:String, foto_id:string) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nParticipantes = nParticipantes;
        this.requisitos = requisitos;
        this.foto_id = foto_id;
    }
}