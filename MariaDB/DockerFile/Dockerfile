FROM mariadb:10

ENV MARIADB_ROOT_PASSWORD=1234
#COPY ./Config/setDB.sh /docker-entrypoing-initdb.d/00-setdb.sh
COPY setDB.sql /docker-entrypoint-initdb.d/01-setdb.sql

RUN echo lower_case_table_names=1 >> /etc/mysql/conf.d/docker.cnf
RUN echo default-time-zone='+9:00' >> /etc/mysql/conf.d/docker.cnf
RUN echo collation-server = utf8mb4_unicode_ci >> /etc/mysql/conf.d/docker.cnf
RUN echo collation-server = utf8mb4_0900_ai_ci >> /etc/mysql/conf.d/docker.cnf
RUN echo character-set-server = utf8mb4 >> /etc/mysql/conf.d/docker.cnf
RUN echo skip-character-set-client-handshake >> /etc/mysql/conf.d/docker.cnf

EXPOSE 3306
