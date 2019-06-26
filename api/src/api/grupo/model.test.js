import { Grupo } from '.'

let grupo

beforeEach(async () => {
  grupo = await Grupo.create({ id_deporte: 'test', titulo: 'test', descripcion: 'test', fotos: 'test', integrantes: 'test', localizacion: 'test', fecha: 'test', bloqueado: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = grupo.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(grupo.id)
    expect(view.id_deporte).toBe(grupo.id_deporte)
    expect(view.titulo).toBe(grupo.titulo)
    expect(view.descripcion).toBe(grupo.descripcion)
    expect(view.fotos).toBe(grupo.fotos)
    expect(view.integrantes).toBe(grupo.integrantes)
    expect(view.localizacion).toBe(grupo.localizacion)
    expect(view.fecha).toBe(grupo.fecha)
    expect(view.bloqueado).toBe(grupo.bloqueado)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = grupo.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(grupo.id)
    expect(view.id_deporte).toBe(grupo.id_deporte)
    expect(view.titulo).toBe(grupo.titulo)
    expect(view.descripcion).toBe(grupo.descripcion)
    expect(view.fotos).toBe(grupo.fotos)
    expect(view.integrantes).toBe(grupo.integrantes)
    expect(view.localizacion).toBe(grupo.localizacion)
    expect(view.fecha).toBe(grupo.fecha)
    expect(view.bloqueado).toBe(grupo.bloqueado)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
