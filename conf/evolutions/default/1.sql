

# --- !Ups

create table evento (
  id                        integer auto_increment not null,
  email_para_contato        varchar(255),
  estado                    varchar(12),
  descricao                 text,
  site                      varchar(255),
  twitter                   varchar(255),
  nome                      varchar(255),
  constraint ck_evento_estado check (estado in ('BAHIA','SAO_PAULO','MINAS_GERAIS')),
  constraint pk_evento primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table evento;

SET FOREIGN_KEY_CHECKS=1;

