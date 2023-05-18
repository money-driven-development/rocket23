
CREATE DATABASE file_list;
grant all privileges on *.* to 'root'@'%' identified by '1234';
flush privileges;
USE file_list;
CREATE TABLE file_list (
                          file_id bigint not null auto_increment,
                          created_at datetime,
                          modified_at datetime,
                          filename varchar(255) not null,
                          path varchar(255) not null,
                          server_type varchar(255),
                          uuid varchar(255) not null,
                          primary key (file_id)
);
