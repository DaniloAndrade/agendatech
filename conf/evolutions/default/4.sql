# --- !Ups

alter table evento add column aprovado tinyint default 0;

# --- !Downs

alter table evento drop column aprovado;