import mongoose, { Schema } from 'mongoose'

const grupoSchema = new Schema({
  id_deporte: {
    type: Schema.ObjectId,
    ref: 'Deporte'
  },
  creador_id:{
    type: Schema.ObjectId,
    ref: 'User'
  },
  titulo: {
    type: String
  },
  descripcion: {
    type: String
  },
  fotos: [{
    type: Schema.ObjectId,
    ref: 'foto'
  }],
  integrantes: [{
    type: Schema.ObjectId,
    ref: 'User'
  }],
  localizacion: {
    type: String
  },
  fecha: {
    type: String,
    default: Date.now
  },
  bloqueado: {
    type: Boolean,
    default: false
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

grupoSchema.methods = {
  view (full) {
    const view = {
    


      id: this.id,
      //id_deporte: this.id_deporte.view(full),
      id_deporte: this.id_deporte,
      creador_id: this.creador_id,
      titulo: this.titulo,
      descripcion: this.descripcion,
      fotos: this.fotos,
      integrantes: this.integrantes,
      localizacion: this.localizacion,
      fecha: this.fecha,
      bloqueado: this.bloqueado,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Grupo', grupoSchema)

export const schema = model.schema
export default model
