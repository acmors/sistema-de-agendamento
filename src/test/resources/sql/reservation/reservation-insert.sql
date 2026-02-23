
INSERT INTO tb_user(id, email, password, role) values (100, 'marcolas@gmail.com', '12345678', 'ADMIN');
INSERT INTO tb_user(id, email, password, role) values (200, 'jhon@gmail.com', '12345678', 'USER');
INSERT INTO tb_user(id, email, password, role) values (300, 'pietro@gmail.com', '12345678', 'USER');

INSERT INTO client(id, name, cpf, id_usuario) values (10, 'jhon hernandes', '86225140050', 200);
INSERT INTO client(id, name, cpf, id_usuario) values (20, 'pietro augusto', '60397635079',300);

INSERT INTO room(id, number, type, price_per_day, status) values (100, 250, 'STANDARD', 45.5, 'AVAILABLE');
INSERT INTO room(id, number, type, price_per_day, status) values (120, 255, 'STANDARD', 45.50, 'AVAILABLE');
INSERT INTO room(id, number, type, price_per_day, status) values (130, 260, 'STANDARD', 45.50, 'AVAILABLE');
INSERT INTO room(id, number, type, price_per_day, status) values (200, 350, 'DELUXE', 75.2, 'AVAILABLE');
INSERT INTO room(id, number, type, price_per_day, status) values (300, 450, 'DELUXE', 75.2, 'RESERVED');

INSERT INTO reservation(id, check_in, checkout, total_value, reservation_status, client_id, room_id)
values (100, '2026-02-25', '2026-03-05', 364.0, 'APPROVED', 20, 100);

INSERT INTO reservation(id, check_in, checkout, total_value, reservation_status, client_id, room_id)
values (110, '2026-02-25', '2026-03-05', 364.0, 'CANCELED', 20, 130);

INSERT INTO reservation(id, check_in, checkout, total_value, reservation_status, client_id, room_id)
values (200, '2026-03-05', '2026-03-06', 75.2, 'APPROVED', 10, 200);
