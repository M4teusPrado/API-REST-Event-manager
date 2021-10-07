# API REST DESENVOLVIDA PARA TRABALHO DE POO II

## Link do Projeto no Heroku

Projeto: https://api-event-poo.herokuapp.com/

## Escopo

![ModeloConceitual](https://user-images.githubusercontent.com/59894662/136452598-783f001f-1e53-40df-818c-2f5d239bc911.png)

### Pontuação por Tarefa concluída:

- [x]  Cadastro
- [x]  Alteração
- [x]  Remoção
- [x]  Pesquisa Por Id
- [x]  Publicação no Heroku: 1 Ponto 
    - [x]  usando o PostgreSQL.
- [x]  Arquitetura em Camadas Usando DTOs
- [x]  Arquitetura paginada
    - [x]  Listagem Paginada 
    - [x]  Pesquisas Diversas Paginadas (filtros): Nome, Por Local do Evento, Data de Início e Descrição

## REGRAS DE NEGOCIOS

Desenvolver um sistema para controlar eventos.

Um evento pode ser criado por qualquer usuário administrador. Ao criar um evento o usuário administrador deverá definir a quantidade de ingressos gratuitos, quantidade de ingressos pagos, valor do ingresso pago.

Um evento poderá ser realizado em um ou mais lugares. E um lugar poderá ser usado por zero ou mais eventos, porém em datas e horários diferentes. Ao alterar o local ou data de um evento, verificar se isso é possível. Não será possível alterar as informações do evento após a sua realização. Um evento que já tenha ingressos vendidos não poderá ser removido. Um local não poderá ser removido se ele já foi usado por um evento.

Um participante poderá fazer a sua inscrição (adquirir ingressos) em qualquer evento cadastrado, respeitando o limite de participantes de cada evento ou a data de realização do evento. Não é possível adquirir um ingresso de um evento que ocorreu no passado.

Existem dois tipos de ingressos: Pago e Gratuito. Um ingresso pago deverá ter o valor pago no momento da compra. O valor do ingresso pago pode ser alterado a qualquer momento. Porém os valores dos ingressos pagos já vendidos não deverão ser alterados. Armazenar a data de venda dos ingressos e caso um ingresso seja removido/devolvido, esse poderá ser vendido novamente para o evento. O valor do ingresso pago entrará como saldo para do participante que comprou o ingresso. Não será possível remover/devolver um ingresso a partir data de início do evento.




### JSON para salvar entidades via POSTMAN

**Admin**
       
    {
      "name": "Admin",
      "email": "Admin@gmail.com",
      "phoneNumber": "15 99761-4525"
    }

**Attendee**
       
    {
      "name": "Attendee",
      "email": "Attendee@gmail.com",
      balance: 125
    }

**Attendee**
       
    {
      "name": "Praça de eventos",
      "address": "Rua General Carneiro, 126 - Jardim Cristal"
    }

**Event**
       
    {
        "name": "halloween",
        "description": "Dia das Bruxas é uma celebração observada em vários países, principalmente no mundo anglófono, em 31 de outubro, véspera da festa cristã ocidental do Dia de Todos os Santos.",
        "place": "São Paulo",
        "startDate": "2021-02-16",
        "endDate": "2021-02-25",
        "startTime": "00:00:00",
        "endTime": "00:30:00",
        "emailContact": "halloween@gmail.com",
        "amountFreeTickets": 500,
        "amountPayedTickets": 750,
        "priceTickets": 25.5,
        "admin": {
            "id": 1
        }
    }


**Ticket**
       {
               "idAttendee": 4,
               "typeTicket": "pago"
       }


