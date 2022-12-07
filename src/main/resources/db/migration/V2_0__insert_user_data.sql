INSERT INTO app_user(username, password, enabled) VALUES
('admin', '{bcrypt}$2a$10$MQN2pb8IoYWnq3vJNckW7.QISXwueuvJD12KQOcGwgYFu9A4/QCe.', true);

INSERT INTO role(name) VALUES
('ROLE_VIEWER'),
('ROLE_ADMIN');

INSERT INTO user_role(user_id, role_id) VALUES
(1, 2);

INSERT INTO authority(permission) VALUES
('READ'),
('CREATE'),
('UPDATE'),
('DELETE');

INSERT INTO role_authority(role_id, authority_id) VALUES
(1, 1),
(2, 1),
(2, 2),
(2, 3),
(2, 4);