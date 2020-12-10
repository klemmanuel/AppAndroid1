import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { CardDocument, CardSchema } from './card.schema';
import { CardController } from './card.controller';
import { CardService } from './card.service';

@Module({
  imports: [
    MongooseModule.forRoot('mongodb://dbUser:dbPassword@cluster0-shard-00-00.cszy1.mongodb.net:27017,cluster0-shard-00-01.cszy1.mongodb.net:27017,cluster0-shard-00-02.cszy1.mongodb.net:27017/Cluster?ssl=true&replicaSet=atlas-x3cmc0-shard-0&authSource=admin&retryWrites=true&w=majority'),
    MongooseModule.forFeature([
      { name: CardDocument.name, schema: CardSchema },
    ]),
  ],
  controllers: [CardController],
  providers: [CardService],
})
export class CardModule {}
