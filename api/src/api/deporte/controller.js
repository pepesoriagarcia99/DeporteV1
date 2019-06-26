import { success, notFound } from '../../services/response/'
import { Deporte } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Deporte.create(body)
    .then((deporte) => deporte.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Deporte.count(query)
    .then(count => Deporte.find(query, select, cursor)
      .then((deportes) => ({
        count,
        rows: deportes.map((deporte) => deporte.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Deporte.findById(params.id)
    .then(notFound(res))
    .then((deporte) => deporte ? deporte.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Deporte.findById(params.id)
    .then(notFound(res))
    .then((deporte) => deporte ? Object.assign(deporte, body).save() : null)
    .then((deporte) => deporte ? deporte.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Deporte.findById(params.id)
    .then(notFound(res))
    .then((deporte) => deporte ? deporte.remove() : null)
    .then(success(res, 204))
    .catch(next)
