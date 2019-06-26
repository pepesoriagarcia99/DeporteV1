import request from 'supertest'
import { apiRoot } from '../../config'
import express from '../../services/express'
import routes, { Grupo } from '.'

const app = () => express(apiRoot, routes)

let grupo

beforeEach(async () => {
  grupo = await Grupo.create({})
})

test('POST /grupos 201', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ id_deporte: 'test', titulo: 'test', descripcion: 'test', fotos: 'test', integrantes: 'test', localizacion: 'test', fecha: 'test', bloqueado: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.id_deporte).toEqual('test')
  expect(body.titulo).toEqual('test')
  expect(body.descripcion).toEqual('test')
  expect(body.fotos).toEqual('test')
  expect(body.integrantes).toEqual('test')
  expect(body.localizacion).toEqual('test')
  expect(body.fecha).toEqual('test')
  expect(body.bloqueado).toEqual('test')
})

test('GET /grupos 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /grupos/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${grupo.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(grupo.id)
})

test('GET /grupos/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /grupos/:id 200', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${grupo.id}`)
    .send({ id_deporte: 'test', titulo: 'test', descripcion: 'test', fotos: 'test', integrantes: 'test', localizacion: 'test', fecha: 'test', bloqueado: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(grupo.id)
  expect(body.id_deporte).toEqual('test')
  expect(body.titulo).toEqual('test')
  expect(body.descripcion).toEqual('test')
  expect(body.fotos).toEqual('test')
  expect(body.integrantes).toEqual('test')
  expect(body.localizacion).toEqual('test')
  expect(body.fecha).toEqual('test')
  expect(body.bloqueado).toEqual('test')
})

test('PUT /grupos/:id 404', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ id_deporte: 'test', titulo: 'test', descripcion: 'test', fotos: 'test', integrantes: 'test', localizacion: 'test', fecha: 'test', bloqueado: 'test' })
  expect(status).toBe(404)
})

test('DELETE /grupos/:id 204', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${grupo.id}`)
  expect(status).toBe(204)
})

test('DELETE /grupos/:id 404', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})
