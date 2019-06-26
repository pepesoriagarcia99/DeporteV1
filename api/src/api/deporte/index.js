import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Deporte, { schema } from './model'

import { password as passwordAuth, master, token } from '../../services/passport'

const router = new Router()
const { nombre, descripcion, requisitos, nParticipantes, foto_id } = schema.tree

/**
 * @api {post} /deportes Create deporte
 * @apiName CreateDeporte
 * @apiGroup Deporte
 * @apiParam nombre Deporte's nombre.
 * @apiParam descripcion Deporte's descripcion.
 * @apiParam requisitos Deporte's requisitos.
 * @apiParam foto_id Deporte's foto_id.
 * @apiSuccess {Object} deporte Deporte's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Deporte not found.
 */
router.post('/',
  token({ required: true, roles: ['admin'] }),
  body({ nombre, descripcion, requisitos, nParticipantes, foto_id}),
  create)

/**
 * @api {get} /deportes Retrieve deportes
 * @apiName RetrieveDeportes
 * @apiGroup Deporte
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of deportes.
 * @apiSuccess {Object[]} rows List of deportes.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  master(),
  query(),
  index)

/**
 * @api {get} /deportes/:id Retrieve deporte
 * @apiName RetrieveDeporte
 * @apiGroup Deporte
 * @apiSuccess {Object} deporte Deporte's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Deporte not found.
 */
router.get('/:id',
  master(),
  show)

/**
 * @api {put} /deportes/:id Update deporte
 * @apiName UpdateDeporte
 * @apiGroup Deporte
 * @apiParam nombre Deporte's nombre.
 * @apiParam descripcion Deporte's descripcion.
 * @apiParam requisitos Deporte's requisitos.
 * @apiParam foto_id Deporte's foto_id.
 * @apiSuccess {Object} deporte Deporte's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Deporte not found.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  body({ nombre, descripcion, requisitos, foto_id, nParticipantes}),
  update)

/**
 * @api {delete} /deportes/:id Delete deporte
 * @apiName DeleteDeporte
 * @apiGroup Deporte
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Deporte not found.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

export default router
