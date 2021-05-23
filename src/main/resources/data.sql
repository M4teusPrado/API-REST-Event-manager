
--INSERT BASE USER
    --ADMIN
INSERT INTO BASE_USER (email, name) VALUES ('admin1@gmail.com', 'admin1');
INSERT INTO BASE_USER (email, name) VALUES ('admin1@gmail.com', 'admin2');
INSERT INTO BASE_USER (email, name) VALUES ('admin2@gmail.com', 'admin3');
    --ATTENDEE
INSERT INTO BASE_USER (email, name) VALUES ('attendee1@gmail.com', 'attendee1');
INSERT INTO BASE_USER (email, name) VALUES ('attendee2@gmail.com', 'attendee2');
INSERT INTO BASE_USER (email, name) VALUES ('attendee2@gmail.com', 'attendee3');

    --INSERT ADMIN 
INSERT INTO ADMIN (phone_number, user_id) VALUES ('(15) 99721-4824', 1);
INSERT INTO ADMIN (phone_number, user_id) VALUES ('(15) 99757-4225', 2);
INSERT INTO ADMIN (phone_number, user_id) VALUES ('(15) 99705-4126', 3);

    --INSERT ATTENDEE 
INSERT INTO ATTENDEE (balance, user_id) VALUES (100, 4);
INSERT INTO ATTENDEE (balance, user_id) VALUES (200, 5);
INSERT INTO ATTENDEE (balance, user_id) VALUES (300, 6);

    --INSERT PLACE
INSERT INTO PLACE (address, name) VALUES ('Rua Ana Marina do espirito santo, 126', 'Sorocaba');
INSERT INTO PLACE (address, name) VALUES ('Rua General Carneiro, 30', 'Votorantim');
INSERT INTO PLACE (address, name) VALUES ('Rua Paulo Jesus, 50', 'Sorocaba');

    --INSERT EVENT
INSERT INTO EVENT (
        name, 
        description,
        start_date, 
        end_date, 
        start_time, 
        end_time, 
        email_contact, 
        amount_payed_tickets, 
        amount_free_tickets, 
        price_tickets, 
        admin_user_id
) 
    VALUES (
        'halloween',
        'Dia das Bruxas é uma celebração observada em vários países, principalmente no mundo anglófono, em 31 de outubro, véspera da festa cristã ocidental do Dia de Todos os Santos.',
        '2021-02-16',
        '2021-02-25',
        '00:00:00',
        '18:30:00',
        'abrilfest@gmail.com',
        760,
        500,
        25,
        1
);

INSERT INTO EVENT (
        name, 
        description,
        start_date, 
        end_date, 
        start_time, 
        end_time, 
        email_contact, 
        amount_payed_tickets, 
        amount_free_tickets, 
        price_tickets, 
        admin_user_id
) 
    VALUES (
        'Abril fest',
        'Festa do Peão de Araçoiaba da Serra, e será realizado do dia 01 a 11 de abril de 2021 no Recinto de Exposições no Bairro Rio Verde.',
        '2021-02-17',
        '2021-02-18',
        '00:00:00',
        '00:00:03',
        'halloween@gmail.com',
        7160,
        500,
        45,
        2
);



INSERT INTO EVENT (
        name, 
        description,
        start_date, 
        end_date, 
        start_time, 
        end_time, 
        email_contact, 
        amount_payed_tickets, 
        amount_free_tickets, 
        price_tickets, 
        admin_user_id
) 
    VALUES (
        'Festa junina',
        'As festas juninas no Brasil são, em sua essência, multiculturais.',
        '2021-07-01',
        '2021-07-18',
        '18:00:00',
        '23:30:00',
        'festajunina@gmail.com',
        80000,
        500,
        15,
        3
);
