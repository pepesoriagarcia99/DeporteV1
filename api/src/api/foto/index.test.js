import request from 'supertest'
import { apiRoot } from '../../config'
import express from '../../services/express'
import routes, { Foto } from '.'

const app = () => express(apiRoot, routes)

let foto

beforeEach(async () => {
  foto = await Foto.create({})
})

test('POST /fotos 201', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ url: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.url).toEqual('test')
})

test('GET /fotos 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /fotos/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${foto.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(foto.id)
})

test('GET /fotos/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /fotos/:id 200', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${foto.id}`)
    .send({ url: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(foto.id)
  expect(body.url).toEqual('test')
})

test('PUT /fotos/:id 404', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ url: 'test' })
  expect(status).toBe(404)
})

test('DELETE /fotos/:id 204', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${foto.id}`)
  expect(status).toBe(204)
})

test('DELETE /fotos/:id 404', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})
