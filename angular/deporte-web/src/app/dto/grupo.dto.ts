export class GrupoDto {
    id_deporte:string;
    titulo:String;
    creador_id:string;
    descripcion:string;
    localizacion:string;
    fecha:string;

    constructor(
        id_deporte:string,
        titulo:String,
        creador_id:string,
        descripcion:string,
        localizacion:string,
        fecha:string
    ) {
        this.id_deporte = id_deporte;
        this.titulo = titulo;
        this.creador_id = creador_id;
        this.descripcion = descripcion;
        this.localizacion = localizacion;
        this.fecha = fecha;
    }
}