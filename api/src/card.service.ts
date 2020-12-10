import { Injectable } from '@nestjs/common';
import { Card } from './Card';
import { InjectModel } from '@nestjs/mongoose';
import { CardDocument} from './card.schema';
import { Model } from 'mongoose';

@Injectable()
export class CardService {
  constructor(
      @InjectModel(CardDocument.name)
      private readonly cardRepository: Model<CardDocument>,
  ) {}
  getHello(): string {
    return 'Hello World!';
  }
  addCard(card: Card): Promise<Card | any> {
    return this.cardRepository.create(card);
  }

  getCard(name: string): Promise<Card | undefined> {
    return this.cardRepository.findOne({ title: name }).exec();
  }

  deleteCard(name: string): Promise<any> {
    return this.cardRepository.remove({ title: name }).exec();
  }

  getCardsOf(author: string): Promise<Card[] | any> {
    return this.cardRepository.find({ author }).exec();
  }

  getAllCards(): Promise<Card[] | any> {
    return this.cardRepository.find().exec();
  }

  getTotalNumberOfCards(): Promise<any>{
    return this.cardRepository.countDocuments().exec();
  }

  Clear() {
    return this.cardRepository.deleteMany({}).exec();
  }
}
