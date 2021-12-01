begin;

-- tabela pessoa
insert into pessoa values
    (default, '111.222.333-44', 'lucas lima', 'rua 1', 7, 'bairro 1', 'conceição dos Ouros', 'MG', '(35)99213-6012'),
    (default, '555.666.777-88', 'William de Paula', 'rua 2', 6, 'bairro 2', 'Itajuba', 'RJ', '(35)99724-9006'),
    (default, '456.123.789-74', 'Magda', 'rua 30', 468, 'Quilombo', 'Conceição dos Ouros', 'SP', '(35)98460-3031'),
    (default, '159.753.741-89', 'Lucy', 'rua 50', 754, 'bairro 2', 'Pouso Alegre', 'MG', '(35)99093-5464'),
    (default, '999.101.111-12', 'Vitor', 'rua 10', 7, 'bairro 1', 'Santa Rita do Sapucai', 'SP', '(35)99192-6673'),
    (default, '000.000.000-00', 'Admin', 'rua', 365, 'bairro', 'cidade', 'MG', '(00)0000-0000');

-- tabela administrador
insert into administrador values
    (default, 'admin@admin.com', 'admin', (select id from pessoa P where P.cpf = '000.000.000-00'));

-- tabela leitor
insert into leitor values
    (default, 1201910111, 'lucaslima@gmail.com', 1234, (select id from pessoa P where P.cpf = '111.222.333-44')),
    (default, 1201910300, 'williampaula@gmail.com', 789654, (select id from pessoa P where P.cpf = '555.666.777-88')),
    (default, 1201910400, 'vitao@gmail.com', 159753, (select id from pessoa P where P.cpf = '999.101.111-12'));

-- tabela bibliotecário
    insert into bibliotecario values
        (default, 16109230, 'magda@vituallibrary.com', 1234, (select id from pessoa P where P.cpf = '456.123.789-74')),
        (default, 78596423, 'lucy@vituallibrary.com', 987654321, (select id from pessoa P where P.cpf = '159.753.741-89'));

-- tabela Autor
insert into autor values
    (default, 'Sun Tzu', 'A arte da guerra', 'As oportunidades multiplicam-se à medida que são agarradas.'),
    (default, 'John Ronald Reuel Tolkien', 'O Hobbit', 'Você pode encontrar as coisas que perdeu, mas nunca as que abandonou.'),
    (default, 'Neil Gaiman', 'Coraline', 'Contos de fadas são mais que verdade; não porque nos dizem que dragões existem, mas porque eles nos dizem que dragões podem ser derrotados.');

-- tabela editora
insert into editora values
    (default, 'faleconosco@gruponovoseculo.com.br', 'NOVO SECULO EDITORA E DISTRIBUIDORA LTDA.', 'EDITORA NOVO SECULO'),
    (default, 'info@emartinsfontes.com.br', 'MARTINS EDITORA LIVRARIA LTDA.', 'MARTINS FONTES'),
    (default, 'rocco@rocco.com.br', 'EDITORA ROCCO LTDA.', 'ROCCO');

-- tabela genero
insert into genero values
    (default, default, 'Fantasia', 'Genero onde encontramos criaturas e alegorias fora do comun, onde a grande tramas fantasticas acontecem'),
    (default, default, 'Tratado', 'Genero atribuidos a livros e outros tipos de arquivo (físico ou não) de um documento real'),
    (default, default, 'Terror', 'Com sua finalidade de causar medo e assustar, os filmes e cenas que te fazem ter pesadelos... Então CUIDADO!!!');

-- tabela livro
insert into livro values
    (default, '9788533613379', 'O senhor dos Aneis - As duas torres', 'Em uma terra fantástica e única, um hobbit recebe de presente de seu tio um anel mágico e maligno que precisa ser destruído antes que caia nas mãos do mal. Para isso, o hobbit Frodo tem um caminho árduo pela frente, onde encontra perigo, medo e seres bizarros. Ao seu lado para o cumprimento desta jornada, ele aos poucos pode contar com outros hobbits, um elfo, um anão, dois humanos e um mago, totalizando nove seres que formam a Sociedade do Anel.', 438, default,(select id from editora E where E.id = 2), (select id from genero G where G.codigo = 100), 2),
    (default, '9788542805093', 'A arte da guerra','O que faz de um tratado militar, escrito por volta de 500 a.C., manter-se atual a ponto de ser publicado praticamente no mundo todo até os dias de hoje? Você verá que, em A arte da guerra, as estratégias transmitidas pelo general chinês Sun Tzu carregam um profundo conhecimento da natureza humana. Elas transcendem os limites dos campos de batalha e alcançam o contexto das pequenas ou grandes lutas cotidianas, sejam em ambientes competitivos – como os do mundo corporativo – sejam nos desafios internos, em que temos de encarar nossas próprias dificuldades. Se você não conhece a si mesmo nem o inimigo, sucumbirá a todas as batalhas. Sun Tzu', 158, default, (select id from editora E where E.id = 1), (select id from genero G where G.codigo = 101), 1),
    (default, '9788532516268', 'Caroline', 'Certas portas não devem ser abertas. E Coraline descobre isso pouco tempo depois de chegar com os pais à sua nova casa, um apartamento em um casarão antigo ocupado por vizinhos excêntricos e envolto por uma névoa insistente, um mundo de estranhezas e magia, o tipo de universo que apenas Neil Gaiman pode criar. Ao abrir uma porta misteriosa na sala de casa, a menina se depara com um lugar macabro e fascinante. Ali, naquele outro mundo, seus outros pais são criaturas muito pálidas, com botões negros no lugar dos olhos, sempre dispostos a lhe dar atenção, fazer suas comidas preferidas e mostrar os brinquedos mais divertidos. Coraline enfim se sente... em casa. Mas essa sensação logo desaparece, quando ela descobre que o lugar guarda mistérios e perigos, e a menina se dá conta de que voltar para sua verdadeira casa vai ser muito mais difícil ― e assustador ― do que imaginava.', 160, default, (select id from editora E where E.id = 3), (select id from genero G where G.codigo = 102), 3);

-- tabela exemplar
insert into exemplar values
    (default, 4, 'BOM', (select id from livro L where L.isbn = '9788533613379')),
    (default, 2, 'RUIM', (select id from livro L where L.isbn = '9788542805093')),
    (default, 3, 'OTIMO', (select id from livro L where L.isbn = '9788542805093')),
    (default, 6, 'PESSIMO', (select id from livro L where L.isbn = '9788533613379')),
    (default, 1, 'REGULAR', (select id from livro L where L.isbn = '9788532516268'));
