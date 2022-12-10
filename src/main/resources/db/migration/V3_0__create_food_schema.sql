CREATE TABLE food_type (
  id bigserial PRIMARY KEY,
  type varchar(45) DEFAULT NULL
);

CREATE TABLE food (
  id bigserial PRIMARY KEY,
  name varchar(45) DEFAULT NULL,
  type_id bigint NOT NULL,
  price decimal(8,2) DEFAULT NULL,
  rating int DEFAULT 0,
  image varchar(200) DEFAULT '',
  net_mass decimal(8, 2) DEFAULT 0,
  protein decimal(3, 2) DEFAULT 0,
  carbohydrates decimal(3, 2) DEFAULT 0,
  fat decimal(3, 2) DEFAULT 0,
  created_date timestamp DEFAULT CURRENT_TIMESTAMP,
  modified_date timestamp DEFAULT NULL,
  FOREIGN KEY (type_id) REFERENCES food_type (id)
);