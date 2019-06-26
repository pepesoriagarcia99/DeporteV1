import request from 'supertest'
import { apiRoot } from '../../config'
import express from '../../services/express'
import routes, { Deporte } from '.'

const app = () => express(apiRoot, routes)

let deporte

beforeEach(async () => {
  deporte = await Deporte.create({})
})

test('POST /deportes 201', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ nombre: 'test', descripcion: 'test', requisitos: 'test', foto_id: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.nombre).toEqual('test')
  expect(body.descripcion).toEqual('test')
  expect(body.requisitos).toEqual('test')
  expect(body.foto_id).toEqual('test')
})

test('GET /deportes 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /deportes/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${deporte.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(deporte.id)
})

test('GET /deportes/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /deportes/:id 200', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${deporte.id}`)
    .send({ nombre: 'test', descripcion: 'test', requisitos: 'test', foto_id: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(deporte.id)
  expect(body.nombre).toEqual('test')
  expect(body.descripcion).toEqual('test')
  expect(body.requisitos).toEqual('test')
  expect(body.foto_id).toEqual('test')
})

test('PUT /deportes/:id 404', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ nombre: 'test', descripcion: 'test', requisitos: 'test', foto_id: 'test' })
  expect(status).toBe(404)
})

test('DELETE /deportes/:id 204', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${deporte.id}`)
  expect(status).toBe(204)
})

test('DELETE /deportes/:id 404', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})
