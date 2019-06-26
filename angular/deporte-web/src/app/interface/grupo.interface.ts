export interface GrupoList {
    id: string;
    id_deporte: {
        nombre:string;
        nParticipantes:string;
        id:string;
    };
    creador_id:{
        _id:string;
        picture:string;
        email:string;
    };
    titulo:string;
    descripcion:string;
    fotos:string[];
    integrantes:string[];
    localizacion:string;
    fecha:string;
    bloqueado:boolean;
    createdAt:string;
    updatedAt:string;
}
