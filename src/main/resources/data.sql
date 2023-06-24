 INSERT INTO product (id, name) VALUES (102030, 'Tarjeta Credito 1');
 INSERT INTO product (id, name) VALUES (103040, 'Tarjeta Credito 2');
 INSERT INTO product (id, name) VALUES (104050, 'Tarjeta Débito 1');
 INSERT INTO product (id, name) VALUES (105060, 'Tarjeta Débito 2');
 
 INSERT INTO card (balance,card_holder_name,due_date,card_number,product_id,status,id)
    VALUES (0,'A TEST NAME 2','2025-12-31',1020301767204036,102030,'INACTIVE',(select next value for card_sq));
INSERT INTO card (balance,card_holder_name,due_date,card_number,product_id,status,id)
    VALUES (0,'A TEST NAME 3','2025-12-31',1020301767204037,103040,'ACTIVE',(select next value for card_sq));
INSERT INTO card (balance,card_holder_name,due_date,card_number,product_id,status,id)
    VALUES (0,'A TEST NAME 4','2025-12-31',1020301767204038,104050,'ACTIVE',(select next value for card_sq));
INSERT INTO card (balance,card_holder_name,due_date,card_number,product_id,status,id)
    VALUES (0,'A TEST NAME 5','2025-12-31',1020301767204039,105060,'INACTIVE',(select next value for card_sq));
INSERT INTO card (balance,card_holder_name,due_date,card_number,product_id,status,id)
    VALUES (1000,'A TEST NAME 6','2025-12-31',1020301767204040,105060,'ACTIVE',(select next value for card_sq));
INSERT INTO card (balance,card_holder_name,due_date,card_number,product_id,status,id)
    VALUES (1500,'A TEST NAME 7','2025-12-31',1020301767204041,105060,'ACTIVE',(select next value for card_sq));
    
INSERT INTO transaction (id,transaction_value,debtor_card_number,date,status)
	VALUES (999,500,1020301767204037,'2023-06-23','ACTIVE')    
 