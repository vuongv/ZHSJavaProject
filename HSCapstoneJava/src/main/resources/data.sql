INSERT INTO CUSTOMER(ADDRESS, CELL_PHONE, CITY, EMAIL, HOME_PHONE, NAME, POSTAL, PROVINCE)
 VALUES ('123 playground street', '(416) 857-0231','Brampton','vuongv@sheridancollege.ca','(123) 456-1234','Henry','L6S5H7','Ontario');
 
 INSERT INTO WORK_ORDER(WORK_ORDER_ID, ORDER_DATE, ORDER_COST, APPOINTMENT_DATE, APPOINTMENT_TIME, WORKER, SERVICE) 
VALUES (1,'2020-12-20',1000,'2021-04-01','10:00:00', 'Kap3', 'Chimney cleaning'),(2,'2020-12-20',1000,'2021-04-01','10:00:00', 'Kap2', 'Chimney Installation') ;

 

INSERT INTO CUSTOMER_WORK_ORDERS(CUSTOMER_ID,WORK_ORDERS_WORK_ORDER_ID) 
VALUES(1,1),(1,2);

INSERT INTO WORK_WORKER(NAME) VALUES ('Kap3');
INSERT INTO WORK_WORKER(NAME) VALUES ('Kap2');
INSERT INTO WORK_WORKER(NAME) VALUES ('Henry');
INSERT INTO WORK_WORKER(NAME) VALUES ('Amazon');
INSERT INTO WORK_WORKER(NAME) VALUES ('Zesus');

INSERT INTO WORK_SERVICE(SERVICE_COST,SERVICE_DESCRIPTION, SERVICE_DURATION, SERVICE_NAME) 
VALUES (50,'Chimney cleaning',1,'Chimney cleaning');
INSERT INTO WORK_SERVICE(SERVICE_COST,SERVICE_DESCRIPTION, SERVICE_DURATION, SERVICE_NAME) 
VALUES (2000,'Chimney Installation',4,'Chimney Installation');
INSERT INTO WORK_SERVICE(SERVICE_COST,SERVICE_DESCRIPTION, SERVICE_DURATION, SERVICE_NAME) 
VALUES (75,'Chimney Inspection',1,'Chimney Inspection');
INSERT INTO WORK_SERVICE(SERVICE_COST,SERVICE_DESCRIPTION, SERVICE_DURATION, SERVICE_NAME) 
VALUES (25,'Chimney Consultation',1,'Chimney Consultation');
INSERT INTO WORK_SERVICE(SERVICE_COST,SERVICE_DESCRIPTION, SERVICE_DURATION, SERVICE_NAME) 
VALUES (1000,'Chimney Repair',3,'Chimney Repair');
INSERT INTO WORK_SERVICE(SERVICE_COST,SERVICE_DESCRIPTION, SERVICE_DURATION, SERVICE_NAME) 
VALUES (0,'Free Cookies',1,'Free Cookies');

INSERT INTO User (username, encrypted_Password, enabled) VALUES
('HSFHarry', '$2a$10$68/.Rt4lh4wKxPDmWADgQOWJROVJKOxSD.VplNoXnksEJjy9A/QeC', 1),
('HSFEmployee', '$2a$10$VLX9U74XZYCfx3e.zcDZVO7AmGovsupE8blEAC9ySYrnbHK7vm4GW', 1),
('HSFDevRoot', '$2a$10$ujmJaR0wI9nbB0/1sIygp.b3pR4.hfGbE/uY5j2cZP4xyF3Dk821O', 1);

INSERT INTO Role (rolename) VALUES ('ROLE_ADMIN'),
('ROLE_EMPLOYEE');
INSERT INTO User_Roles VALUES (1, 1), (1, 2), (2, 2), (3,1), (3,2);

INSERT INTO Testimonial (SERVICE_NAME, USER_EMAIL, USER_NAME, USER_TESTIMONIAL, TO_DISPLAY) VALUES ('Chimney Installation', 'seby_97@yahoo.ca', 'Sebastian Koehler','i love cookies and fireplaces!', TRUE);
INSERT INTO Testimonial (SERVICE_NAME, USER_EMAIL, USER_NAME, USER_TESTIMONIAL, TO_DISPLAY) VALUES ('Chimney cleaning', 'TEST_97@yahoo.ca', 'TEST Koehler','I LOVE FIREPLACES!', FALSE);

INSERT INTO Image(IMAGE_NAME,FULL_FILE_PATH, TO_DISPLAY) VALUES 
('Apex Predator.png','/images/gallery/Apex Predator.png', true),
('apple.png','/images/gallery/apple.png', true),
('apple3.png','/images/gallery/apple3.png', true),
('apple4.png', '/images/gallery/apple4.png', true),
('diamond.png', '/images/gallery/diamond.png', true),
('donkey.png','/images/gallery/donkey.png', true),
('donkey3.png', '/images/gallery/donkey3.png', true),
('fc.png', '/images/gallery/fc.png', true),
('jays.jpg', '/images/gallery/jays.jpg', true),
('leafs.png', '/images/gallery/leafs.png', true),
('marlies.jpeg', '/images/gallery/marlies.jpeg', true),
('masters.png', '/images/gallery/masters.png', true),
('raptors.jpg', '/images/gallery/raptors.jpg', true);




