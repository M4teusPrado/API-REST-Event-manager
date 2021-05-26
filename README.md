# Events_API_AC1

### Participantes
- Mateus da Silva do Prado - 190477
- Felipe Lima de Carvalho - 190190


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


## Link do Projeto no Heroku

Projeto: https://api-event-poo.herokuapp.com/
