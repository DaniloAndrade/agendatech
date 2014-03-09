# --- !Ups

alter table evento add column data_de_inicio date;
alter table evento add column data_de_fim date;

# --- !Downs

alter table evento drop column data_de_inicio;
alter table evento drop column data_de_fim;