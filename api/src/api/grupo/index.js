import { Router } from 'express'
import { middleware as query, Schema } from 'querymen'
import { middleware as body } from 'bodymen'
import { create, index, show, update, destroy, unirseGrupo, salirseGrupo, bloquear, desbloquear, allMe, all, createAdmin, indexPublico, showPublico} from './controller'
import { schema } from './model'
export Grupo, { schema } from './model'

import { password as passwordAuth, master, token } from '../../services/passport'

const router = new Router()
const { id_deporte, creador_id, titulo, descripcion, 
  fotos, integrantes, localizacion, fecha, bloqueado } = schema.tree

const grupoSchema = new Schema({
  titulo: {
    type: RegExp,
    paths: ['titulo']
  },
  deporte: {
    type: RegExp,
    paths: ['id_deporte']
  },
  fecha: {
    type: RegExp,
    paths: ['fecha']
  }
})

/**
 * @api {get} /grupos/publico Retrieve public-grupos
 * @apiName RetrieveGrupos
 * @apiGroup Grupo
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of grupos.
 * @apiSuccess {Object[]} rows List of grupos.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/publico',
  master(),
  query(grupoSchema),
  indexPublico)

  /**
 * @api {get} /grupos/publico/:id Retrieve public-grupo
 * @apiName RetrieveGrupo
 * @apiGroup Grupo
 * @apiSuccess {Object} grupo Grupo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Grupo not found.
 */
  router.get('/publico/:id',
  master(),
  showPublico)

/**
 * @api {post} /grupos Create grupo
 * @apiName CreateGrupo
 * @apiGroup Grupo
 * @apiParam id_deporte Grupo's id_deporte.
 * @apiParam titulo Grupo's titulo.
 * @apiParam descripcion Grupo's descripcion.
 * @apiParam fotos Grupo's fotos.
 * @apiParam integrantes Grupo's integrantes.
 * @apiParam localizacion Grupo's localizacion.
 * @apiParam fecha Grupo's fecha.
 * @apiParam bloqueado Grupo's bloqueado.
 * @apiSuccess {Object} grupo Grupo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Grupo not found.
 */
router.post('/',
  token({ required: true, roles: ['user','admin'] }),
  body({ id_deporte, titulo, descripcion, localizacion, fecha }),
  create)


  /**
 * @api {post} /grupos/admin Create grupo-admin
 * @apiName CreateGrupo
 * @apiGroup Grupo
 * @apiParam id_deporte Grupo's id_deporte.
 * @apiParam creador_id Grupo's creador_id.
 * @apiParam titulo Grupo's titulo.
 * @apiParam descripcion Grupo's descripcion.
 * @apiParam fotos Grupo's fotos.
 * @apiParam integrantes Grupo's integrantes.
 * @apiParam localizacion Grupo's localizacion.
 * @apiParam fecha Grupo's fecha.
 * @apiParam bloqueado Grupo's bloqueado.
 * @apiSuccess {Object} grupo Grupo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Grupo not found.
 */
  router.post('/admin',
  token({ required: true, roles: ['admin'] }),
  body({ id_deporte, creador_id, titulo, descripcion, localizacion, fecha }),
  createAdmin)

/**
 * @api {get} /grupos/all Retrieve grupos
 * @apiName RetrieveGrupos
 * @apiGroup Grupo
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of grupos.
 * @apiSuccess {Object[]} rows List of grupos.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/all',
  token({ required: true, roles: ['admin'] }),
  query(),
  all)

  /**
 * @api {get} /grupos Retrieve grupos
 * @apiName RetrieveGrupos
 * @apiGroup Grupo
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of grupos.
 * @apiSuccess {Object[]} rows List of grupos.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */

router.get('/',
  token({ required: true, roles: ['user','admin'] }),
  query(grupoSchema),
  index)

/**
 * @api {get} /grupos/me Retrieve grupos
 * @apiName RetrieveGrupos
 * @apiGroup Grupo
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of grupos.
 * @apiSuccess {Object[]} rows List of grupos.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/me',
  token({ required: true, roles: ['user','admin'] }),
  query(),
  allMe)

/**
 * @api {get} /grupos/:id Retrieve grupo
 * @apiName RetrieveGrupo
 * @apiGroup Grupo
 * @apiSuccess {Object} grupo Grupo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Grupo not found.
 */
router.get('/:id',
token({ required: true, roles: ['user','admin'] }),
  show)

/**
 * @api {put} /grupos/:id Update grupo
 * @apiName UpdateGrupo
 * @apiGroup Grupo
 * @apiParam id_deporte Grupo's id_deporte.
 * @apiParam titulo Grupo's titulo.
 * @apiParam descripcion Grupo's descripcion.
 * @apiParam fotos Grupo's fotos.
 * @apiParam integrantes Grupo's integrantes.
 * @apiParam localizacion Grupo's localizacion.
 * @apiParam fecha Grupo's fecha.
 * @apiParam bloqueado Grupo's bloqueado.
 * @apiSuccess {Object} grupo Grupo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Grupo not found.
 */
router.put('/:id',
  token({ required: true, roles: ['user','admin'] }),
  body({ id_deporte, titulo, descripcion, localizacion, fecha }),
  update)

/**
 * @api {delete} /grupos/:id Delete grupo
 * @apiName DeleteGrupo
 * @apiGroup Grupo
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Grupo not found.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin', 'user'] }),
  destroy)

   /**
 * @api {post} /grupos/bloquear/:id bloquear grupo
 * @apiName bloquearGrupo
 * @apiGroup Grupo
 * @apiSuccess {Object} grupo Grupo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Grupo not found.
 */
  router.post('/bloquear/:id',
  token({ required: true, roles: ['admin','user'] }),
  bloquear)
  
  
  /**
 * @api {delete} /grupos/desbloquear/:id desbloquear grupo
 * @apiName desbloquearGrupo
 * @apiGroup Grupo
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Grupo not found.
 */
  router.delete('/desbloquear/:id',
  token({ required: true, roles: ['admin'] }),
  desbloquear)
  
  
  /**
 * @api {post} /grupos/join/:id join grupo
 * @apiName joinGrupo
 * @apiGroup Grupo
 * @apiSuccess {Object} grupo Grupo's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Grupo not found.
 */
  router.post('/join/:id',
  token({ required: true, roles: ['user','admin'] }),
  unirseGrupo)
  
  /**
 * @api {delete} /grupos/exit/:id exit grupo
 * @apiName exitGrupo
 * @apiGroup Grupo
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Grupo not found.
 */
  router.delete('/exit/:id',
  token({ required: true, roles: ['user','admin'] }),
  salirseGrupo)
  //fotos

export default router
