import { success, notFound } from '../../services/response/'
import { Grupo } from '.'
import { User } from '../user'

//apartir de token consigue user_id
export const create = ({ user, bodymen: { body } }, res, next) =>
  Grupo.create({ ...body, creador_id: user.id})
    .then((grupo) => grupo.view(true))
    .then(success(204))
    .catch(next)

//el administrador tiene que enviar el id del usuario
export const createAdmin = ({ bodymen: { body } }, res, next) =>
Grupo.create(body)
    .then((grupo) => grupo.view(true))
    .then(success(res, 201))
    .catch(next)

//todos admin
export const all = ({ querymen: { query, select, cursor } }, res, next) =>
Grupo.count(query)
    .then(count => Grupo
      .find(query, select, cursor)
      .populate('id_deporte', 'id nombre nParticipantes')
      .populate('creador_id', 'id email picture')
      .then((grupo) => ({
        count,
        rows: grupo.map((grupo) => grupo.view())
      }))
    )
    .then(success(res))
    .catch(next)

//consulta con todos los datos
export const index = ({user, querymen: { query, select, cursor } }, res, next) =>
Grupo.count({ $and: [ { integrantes: {$ne: user.id} },{creador_id: {$nin :user.id}},query ] })
    .then(count => Grupo.find({ $and: [  { integrantes: {$ne: user.id} },{creador_id: {$nin :user.id}},query ] }, select, cursor)
.populate('id_deporte', 'id nombre nParticipantes')
  .populate('creador_id', 'id email picture')
    .then((grupos) => ({
      count,
      rows: grupos.map((grupo) => grupo.view())
    }))
  )
  .then(success(res))
  .catch(next)

  //consulta con todos los datos masterkey
export const indexPublico = ({querymen: { query, select, cursor } }, res, next) =>
Grupo.count( query )
    .then(count => Grupo.find(query, select, cursor)
  .populate('id_deporte', 'id nombre nParticipantes')
  .populate('creador_id', 'id email picture')
    .then((grupos) => ({
      count,
      rows: grupos.map((grupo) => grupo.view())
    }))
  )
  .then(success(res))
  .catch(next)
    //get one publico
  export const showPublico = ({ params }, res, next) =>
  Grupo.findById(params.id)
  .populate('id_deporte', 'id nombre nParticipantes foto_id requisitos')
  .populate('creador_id', 'id email picture')
  .populate('integrantes', 'id email picture')
    .then(notFound(res))
    .then((grupo) => grupo ? grupo.view() : null)
    
    .then(success(res))
    .catch(next)


  //todos mis grupos
  export const allMe = ({user, querymen: { query, select, cursor } }, res, next) =>
  Grupo.count({ $or: [ {integrantes: {$in :user.id}}, {creador_id: {$in :user.id}} ] })
    .then(count => Grupo
      .find({ $or: [ {integrantes: {$in :user.id}}, {creador_id: {$in :user.id}} ] }, select, cursor)
    .populate('id_deporte', 'id nombre nParticipantes')
    .populate('creador_id', 'id email picture')
      .then((grupos) => ({
        count,
        rows: grupos.map((grupo) => grupo.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Grupo.findById(params.id)
  .populate('id_deporte', 'id nombre nParticipantes foto_id requisitos')
  .populate('creador_id', 'id email picture')
  .populate('integrantes', 'id email picture')
    .then(notFound(res))
    .then((grupo) => grupo ? grupo.view() : null)
    
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Grupo.findById(params.id)
    .then(notFound(res))
    .then((grupo) => grupo ? Object.assign(grupo, body).save() : null)
    .then((grupo) => grupo ? grupo.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Grupo.findById(params.id)
    .then(notFound(res))
    .then((grupo) => grupo ? grupo.remove() : null)
    .then(success(res, 204))
    .catch(next)

  //bloquear
  export const bloquear = ({params}, res, next) =>
  Grupo.findByIdAndUpdate( params.id, {bloqueado: true}, {new : true}) //new true
    .then(success(res, 200))
    .catch(next)
  //desbloquear
  
  export const desbloquear = ({params}, res, next) =>
  Grupo.findByIdAndUpdate( params.id, {bloqueado: false}, {new : true})
    .then(success(res, 200))
    .catch(next)

  //unirse
  export const unirseGrupo = ({ user, params }, res, next) =>
  Grupo.findByIdAndUpdate( params.id, {$addToSet: {integrantes: user.id}}, {new : true})
    .then(success(res, 204))
    .catch(next)
  //salirse
  export const salirseGrupo = ({ user, params}, res, next) =>
  Grupo.findByIdAndUpdate( params.id, {$pull: {integrantes: user.id}}, {new : true})
    .then(success(res, 204))
    .catch(next)
  //fotos

  //grupos en los que este dicho user
  export const gruposPorUser = ({params}, res, next) =>
  Grupo.find({integrantes: {$in: params.id }}, {new : true})
    .then(success(res, 200))
    .catch(next)