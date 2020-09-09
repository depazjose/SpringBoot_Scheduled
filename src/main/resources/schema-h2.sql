DROP TABLE IF EXISTS book_data;

CREATE TABLE book_data (
  id bigint AUTO_INCREMENT PRIMARY KEY,
  isbn bigint,
  name varchar(100),
  quantity int,
  available boolean,
  autor varchar(100),
  properties varchar(1000),
  start_sale_date timestamp,
  status varchar(50),
  UNIQUE(isbn)
);