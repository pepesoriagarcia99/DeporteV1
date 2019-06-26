import { Foto } from '.'

let foto

beforeEach(async () => {
  foto = await Foto.create({ url: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = foto.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(foto.id)
    expect(view.url).toBe(foto.url)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = foto.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(foto.id)
    expect(view.url).toBe(foto.url)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
