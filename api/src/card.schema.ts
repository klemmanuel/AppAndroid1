import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document } from 'mongoose';

@Schema({ collection: 'cards-gme' })
export class CardDocument extends Document {
  @Prop()
  job: string;

  @Prop()
  name: string;

  @Prop()
  description: string;
}

export const CardSchema = SchemaFactory.createForClass(CardDocument);
