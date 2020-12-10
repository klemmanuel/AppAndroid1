import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Post,
  Query,
} from '@nestjs/common';
import { CardService } from './card.service';
import { Card } from './Card';

@Controller('cards')
export class CardController {
  constructor(private readonly cardService: CardService) {}

  @Get()
  getCard(@Query('name') name): Promise<Card[]> {
    if (name) {
      return this.cardService.getCardsOf(name);
    }
    return this.cardService.getAllCards();
  }

  @Post()
  createCard(@Body() newCard: Card): Promise<Card | undefined> {
    return this.cardService.addCard(newCard);
  }

  @Get('/:name')
  getBook(@Param('name') title): Promise<Card | undefined> {
    return this.cardService.getCard(title);
  }

  @Get()
  getHello(): string {
    return this.cardService.getHello();
  }

  @Delete('/:name')
  deleteBook(@Param('name') title): Promise<void> {
    return this.cardService.deleteCard(title);
  }
}
