import mongoose, { Schema } from 'mongoose'

const deporteSchema = new Schema({
  nombre: {
    type: String
  },
  descripcion: {
    type: String
  },
  requisitos: {
    type: String
  },
  nParticipantes: {
    type: Number
  },
  foto_id: {
    type: String
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

deporteSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      nombre: this.nombre,
      descripcion: this.descripcion,
      requisitos: this.requisitos,
      nParticipantes: this.nParticipantes,
      foto_id: this.foto_id,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Deporte', deporteSchema)

export const schema = model.schema
export default model
