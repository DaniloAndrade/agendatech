# --- !Ups

alter table evento add column caminho_imagem varchar (255);

# --- !Downs

alter table evento drop column caminho_imagem;