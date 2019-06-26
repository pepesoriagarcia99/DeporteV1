import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Foto, { schema } from './model'

const router = new Router()
const { url } = schema.tree

/**
 * @api {post} /fotos Create foto
 * @apiName CreateFoto
 * @apiGroup Foto
 * @apiParam url Foto's url.
 * @apiSuccess {Object} foto Foto's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Foto not found.
 */
router.post('/',
  body({ url }),
  create)

/**
 * @api {get} /fotos Retrieve fotos
 * @apiName RetrieveFotos
 * @apiGroup Foto
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of fotos.
 * @apiSuccess {Object[]} rows List of fotos.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(),
  index)

/**
 * @api {get} /fotos/:id Retrieve foto
 * @apiName RetrieveFoto
 * @apiGroup Foto
 * @apiSuccess {Object} foto Foto's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Foto not found.
 */
router.get('/:id',
  show)

/**
 * @api {put} /fotos/:id Update foto
 * @apiName UpdateFoto
 * @apiGroup Foto
 * @apiParam url Foto's url.
 * @apiSuccess {Object} foto Foto's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Foto not found.
 */
router.put('/:id',
  body({ url }),
  update)

/**
 * @api {delete} /fotos/:id Delete foto
 * @apiName DeleteFoto
 * @apiGroup Foto
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Foto not found.
 */
router.delete('/:id',
  destroy)

export default router
