INSERT INTO app_user (id, username, password, enabled, failed_login_attempts) VALUES
(user_id_seq.nextval, 'admin', '{bcrypt}$2a$10$MQN2pb8IoYWnq3vJNckW7.QISXwueuvJD12KQOcGwgYFu9A4/QCe.', true, 0),
(user_id_seq.nextval, 'user', '{bcrypt}$2a$10$MQN2pb8IoYWnq3vJNckW7.QISXwueuvJD12KQOcGwgYFu9A4/QCe.', true, 0),
(user_id_seq.nextval, 'user2', '{bcrypt}$2a$10$MQN2pb8IoYWnq3vJNckW7.QISXwueuvJD12KQOcGwgYFu9A4/QCe.', true, 0);

INSERT INTO role(id, name) VALUES
(role_id_seq.nextval, 'ROLE_VIEWER'),
(role_id_seq.nextval, 'ROLE_ADMIN');

INSERT INTO user_role(user_id,role_id) VALUES
(1, 2),
(2, 1),
(3, 1);

INSERT INTO authority(id, permission) VALUES
(authority_id_seq.nextval, 'READ'),
(authority_id_seq.nextval, 'CREATE'),
(authority_id_seq.nextval, 'UPDATE'),
(authority_id_seq.nextval, 'DELETE');

INSERT INTO role_authority(role_id, authority_id) VALUES
(1, 1),
(2, 1),
(2, 2),
(2, 3),
(2, 4);