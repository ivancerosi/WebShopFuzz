insert into categories (name) values ('bijela tehnika');

insert into subcategories (name, category_id) values
('hladnjaci', select c.category_id from categories c where c.name='bijela tehnika'),
('perilice', select c.category_id from categories c where c.name='bijela tehnika');

insert into products (description, name, price, subcategory_id) values
('Energetski razred E\nObujam rashladnog dijela 198 l ', 'LG GBP', 330, select sc.subcategory_id from subcategories sc where sc.name='hladnjaci'),
('KAPACITET:\nUkupno (l): 341', 'GORENJE RK', 569, select sc.subcategory_id from subcategories sc where sc.name='hladnjaci'),
('Smart inverterski kompresor (10 godina\njamstva na kompresor)', 'SAMSUNG RT', 879, select sc.subcategory_id from subcategories sc where sc.name='hladnjaci'),

('Perilica rublja, 8kg,/1400 okr. Inverter', 'HISENSE WQF', 289, select sc.subcategory_id from subcategories sc where sc.name='perilice'),
('Perilica-sušilica s parom,8/5kg,1200okr', 'LG F2D', 289, select sc.subcategory_id from subcategories sc where sc.name='perilice'),
('Perilica, rublja, 6kg, 1000okr', 'GORENJE WN', 249, select sc.subcategory_id from subcategories sc where sc.name='perilice');

insert into users(username, email, enabled, password) values
-- pass: springstudent1
('user1','user1@algebra.hr',true, '$2a$10$Dw6f8bnU4iKIIfAEMOSE/.u.WgoJDdhQU8kUn1OyhbJQ7pHcrhPMy'),
-- pass: springstudent2
('user2','user2@algebra.hr',true,'$2a$10$w2WGrAtuCpEIGkn6XfPjDeDaRPS5pZQ5Bczp/w8xOaQ/DWGswq0Xq'),
-- pass: springadmin
('admin','webshopadmin@algebra.hr',true, '$2a$10$GnzsgWdE1b0xCMNicyiEquBf4vt6MHWuJNdhnhd57jOBfHoB0QqOa');

insert into authorities(authority, username) values
('ROLE_SHOPPER', 'user1'),
('ROLE_SHOPPER', 'user2'),
('ROLE_ADMIN', 'admin');

insert into notifications(created_at, message, read_at, title, viewed, username) values
('2024-07-14 10:15:23', 'Welcome to WebShop', null, 'Welcome', false, 'user1'),
('2024-07-10 17:54:26', 'Welcome to WebShop', '2024-07-10 18:01:05', 'Welcome', true, 'user2'),
('2024-07-08 11:22:12', 'Admin account created', '2024-07-10 10:01:05', 'Notification', true, 'admin');

insert into user_connections(username, ip_address, last_connection) values
('user1', '141.227.136.148', '2024-07-14 13:01:05'),
('user2', '131.108.119.176', '2024-07-14 15:01:05'),
('admin', '193.175.5.201', '2024-07-11 10:01:05');

insert into shopping_cart(session_id, username) values
('5235-a6de-a43a-00a1','user1');

insert into cart_items(cart_id, quantity, product_id) values
    (select sc.cart_id from shopping_cart sc where sc.session_id='5235-a6de-a43a-00a1',
    1,
    select p.product_id from products p where p.name='LG GBP'),

    (select sc.cart_id from shopping_cart sc where sc.session_id='5235-a6de-a43a-00a1',
    1,
    select p.product_id from products p where p.name='HISENSE WQF')
;

insert into orders(payment_method, purchase_date, total_amount, username) values
    ('CASH ON DELIVERY', '2024-07-14 20:00:00', 1200, 'user2')
;

insert into order_items(order_id, product_id, quantity, price) values
    (
        (select o.order_id from orders o where o.username='user2' limit 1),
        (select p.product_id from products p where p.name='SAMSUNG RT'),
        1,
        (select p.price from products p where p.name='SAMSUNG RT')
    ),
    (
         (select o.order_id from orders o where o.username='user2' limit 1),
         (select p.product_id from products p where p.name='GORENJE WN'),
         1,
         (select p.price from products p where p.name='GORENJE WN')
    )
;