export interface Grupo {
    id:string;
    id_deporte:string;
    creador_id:string;
    titulo:string;
    descripcion:string;
    fotos:string[];
    integrantes:string[];
    localizacion:string[];
    fecha:string[];
    bloqueado:boolean;
    createdAt:string;
    updatedAt:string;
}