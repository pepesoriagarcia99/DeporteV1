import { Deporte } from '.'

let deporte

beforeEach(async () => {
  deporte = await Deporte.create({ nombre: 'test', descripcion: 'test', requisitos: 'test', foto_id: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = deporte.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(deporte.id)
    expect(view.nombre).toBe(deporte.nombre)
    expect(view.descripcion).toBe(deporte.descripcion)
    expect(view.requisitos).toBe(deporte.requisitos)
    expect(view.foto_id).toBe(deporte.foto_id)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = deporte.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(deporte.id)
    expect(view.nombre).toBe(deporte.nombre)
    expect(view.descripcion).toBe(deporte.descripcion)
    expect(view.requisitos).toBe(deporte.requisitos)
    expect(view.foto_id).toBe(deporte.foto_id)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
