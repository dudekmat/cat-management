CREATE TABLE app_user (
  id bigserial PRIMARY KEY,
  username varchar(50) NOT NULL,
  password char(68) NOT NULL,
  enabled boolean NOT NULL,
  failed_login_attempts int DEFAULT 0
);

CREATE TABLE role (
  id bigserial PRIMARY KEY,
  name varchar(50) DEFAULT NULL
);

CREATE TABLE user_role (
  user_id bigint NOT NULL REFERENCES app_user(id) ON UPDATE CASCADE ON DELETE CASCADE,
  role_id bigint NOT NULL REFERENCES role(id) ON UPDATE CASCADE ON DELETE CASCADE,

  CONSTRAINT user_role_pkey PRIMARY KEY (user_id,role_id)
);

CREATE TABLE authority (
  id bigserial PRIMARY KEY,
  permission varchar(50) DEFAULT NULL
);

CREATE TABLE role_authority (
  role_id bigint NOT NULL REFERENCES role(id) ON UPDATE CASCADE ON DELETE CASCADE,
  authority_id bigint NOT NULL REFERENCES authority(id) ON UPDATE CASCADE ON DELETE CASCADE,

  CONSTRAINT role_authority_pkey PRIMARY KEY (role_id,authority_id)
);