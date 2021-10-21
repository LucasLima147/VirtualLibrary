create table pessoa(
    id serial primary key,
    cpf character varying (15) unique not null,
    nome character varying(100) not null,
    rua character varying(200) not null,
    numero integer not null,
    bairro character varying(100) not null,
    cidade character varying(50) not null,
    estado character varying(20) not null,
    telefone character varying(15) not null
);
alter table pessoa add constraint ck_estado check (estado in('AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 'RR', 'RO', 'RJ', 'RN', 'RS', 'SC', 'SP', 'SE', 'TO'));


create table administrador(
	id serial not null,
	email character varying(100) not null,
    senha character varying(30) not null,
    pessoa_id integer not null references pessoa(id) on update cascade on delete cascade
);


create table leitor(
    id serial primary key,
    matricula integer unique not null,
    email character varying(100) not null,
    senha character varying(30) not null,
    pessoa_id integer not null references pessoa(id) on update cascade on delete cascade
);

create table bibliotecario(
    id serial primary key,
    registro integer unique not null,
    email character varying(100) not null,
    senha character varying(30) not null,
    pessoa_id integer not null references pessoa(id) on update cascade on delete cascade
);

create table autor(
    id serial primary key,
    nome character varying (100) not null,
    obra character varying (100) not null,
    frase character varying(200)
);

create table editora(
    id serial primary key,
    email character varying(50) not null,
    razaoSocial character varying(100) not null,
    nomeFantasia character varying(100) not null
);


create sequence nextCodigoGenero start 100;
create table genero(
    id serial primary key,
    codigo integer not null unique default nextval('nextCodigoGenero'),
    nome character varying(30) not null,
    descricao character varying(500) not null
);

create table livro(
    id serial primary key,
    isbn bigint not null unique,
    titulo character varying(100) not null,
    sinopse character varying (2000) not null,
    numPaginas integer not null,
    ativo boolean not null default true,
    editora_id integer not null references editora(id) on update cascade on delete cascade,
    genero_id integer not null references genero(id) on update cascade on delete cascade,
    autor_id integer not null references autor(id) on update cascade on delete cascade
);

create table exemplar(
    id serial primary key,
    edicao integer not null,
    estadoConservacao character varying (7) not null,
    livro_id integer not null references livro(id) on update cascade on delete cascade 
);
alter table exemplar add constraint ck_estadoConservacao check( estadoConservacao in ('OTIMO', 'BOM', 'REGULAR', 'RUIM', 'PESSIMO'));

create sequence nextCodigoEmprestimo start 100;
create table emprestimo(
    id serial primary key,
    codigo integer not null unique default nextval('nextCodigoEmprestimo'),
    dataRealizacao timestamp without time zone default null,
    leitor_id integer not null references leitor(id) on update cascade on delete cascade
);
create table emprestimo_exemplar(
    id serial primary key,
    datadevolucao timestamp without time zone default now()+'7 days',
    devolvido boolean default false not null,
    dataEfetivaDevolucao timestamp without time zone default null,
    multa money not null default 0.00,
    emprestimo_id integer not null references emprestimo(id) on update cascade on delete cascade,
    exemplar_id integer not null references exemplar(id) on update cascade on delete cascade
);

create table entrega(
    id serial primary key,
    dataSolicitacao timestamp without time zone default now() not null,
    dataEntrega timestamp without time zone default null,
    entregue boolean  default null,
    leitor_id integer not null references leitor(id) on update cascade on delete cascade,
    emprestimo_id integer not null references emprestimo(id) on update cascade on delete cascade
);

create table recolhimento(
    id serial primary key,
    dataSolicitacao timestamp without time zone default now() not null,
    dataRecolhimento timestamp without time zone default null,
    recolhido boolean default null,
    leitor_id integer not null references leitor(id) on update cascade on delete cascade,
    emprestimo_id integer not null references emprestimo(id) on update cascade on delete cascade
);