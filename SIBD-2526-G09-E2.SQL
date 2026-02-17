-- ---------------------------------------------------------------------------- 
-- LEI - 2025/26 - Etapa 2 do Projeto
-- ------
-- Grupo 09
-- Dinis Fernandes – nº63787 – TP14
-- Tiago Wu – nº63696 – TP14
-- Nuno Nunes – nº63731 - TP12
-- ------
-- Contribuições:
-- Dinis Fernandes - 34% - Discussão de ideias; formulação de tabelas iniciais; elaboração do script; reformulação e reorganização das tabelas. 
-- Tiago Wu - 33% - Discussão de ideias; formulação de tabelas iniciais; elaboração do script; reformulação e reorganização das tabelas.
-- Nuno Nunes - 33% - Discussão de ideias; formulação de tabelas iniciais; elaboração do script.
-- 
-- Nota: divisão de tarefas semelhante entre todos.
-- ---------------------------------------------------------------------------- 
-- RIAs não suportadas:
--
-- RIA-08: O ano de lançamento de um álbum interpretado por um artista tem de ser
--  posterior ou igual ao ano de início de atividade desse artista.
--
-- RIA-09: O ano de lançamento de um álbum tem de ser posterior ou igual ao ano de 
--  início de atividade de todos os artistas que interpretam as canções nas 
--  faixas desse álbum.
--
-- RIA-10: Solista AND Grupo COVER Artista.
--
-- RIA-15: A data de nascimento de um utilizador tem de ser 13 ou mais anos anterior
--  à data atual, bastando fazer a verificação aquando de criação da sua conta
--  no sistema de informação.
--
-- RIA-16: O ano da data de adição de uma versão de álbum à coleção de um utilizador
--  tem de ser posterior ou igual ao ano de lançamento do álbum.
-- 
-- RIA-17: A data de adição de uma versão de álbum à coleção de um utilizador tem
--  de ser posterior ou igual à data em que o utilizador perfez 13 anos.
-- ---------------------------------------------------------------------------- 
-- RIAs extra (participações totais)
--
-- RIA-19: Todos os grupos têm pelo menos um membro.
--
-- RIA-20: Todos os álbuns têm pelo menos um suporte físico.
--
-- RIA-21: Todos os álbuns em suporte físico têm pelo menos uma versão.
-- ---------------------------------------------------------------------------- 

DROP TABLE lista_refere;
DROP TABLE utilizador_possui;
DROP TABLE versao_album;
DROP TABLE album_em_suporte_fisico;
DROP TABLE album;
DROP TABLE suporte_fisico;
DROP TABLE lista;
DROP TABLE utilizador;
DROP TABLE membro;
DROP TABLE grupo;
DROP TABLE solista;
DROP TABLE artista;

-- ---------------------------------------------------------------------------- 

CREATE TABLE artista (
    isni CHAR(16),   -- RIA-07
    nome_artista VARCHAR(50)    CONSTRAINT nn_artista_nome NOT NULL,
    ano_inicio DATE             CONSTRAINT nn_artista_ano_inicio NOT NULL,
    --
    CONSTRAINT pk_artista
        PRIMARY KEY (isni),
    --
    CONSTRAINT ck_artista_isni
        CHECK (REGEXP_LIKE (isni, '^[[:digit:]]+$'))
);

-- ---------------------------------------------------------------------------- 

CREATE TABLE solista (
    isni_solista,
    --
    CONSTRAINT pk_solista
        PRIMARY KEY (isni_solista),
    --
    CONSTRAINT fk_solista_isni_solista
        FOREIGN KEY (isni_solista) 
        REFERENCES artista
        ON DELETE CASCADE
);

-- ---------------------------------------------------------------------------- 

CREATE TABLE grupo (
    isni_grupo,
    --
    CONSTRAINT pk_grupo
        PRIMARY KEY (isni_grupo),
    --
    CONSTRAINT fk_grupo_isni_grupo
        FOREIGN KEY (isni_grupo) 
        REFERENCES artista 
        ON DELETE CASCADE
); 

-- ---------------------------------------------------------------------------- 

CREATE TABLE membro (
    solista,
    grupo,
    --
    CONSTRAINT pk_membro
        PRIMARY KEY (solista, grupo),
    --
    CONSTRAINT fk_membro_solista
        FOREIGN KEY (solista) 
        REFERENCES solista (isni_solista),
    --
    CONSTRAINT fk_membro_grupo
        FOREIGN KEY (grupo) 
        REFERENCES grupo (isni_grupo)
);

-- ---------------------------------------------------------------------------- 

CREATE TABLE utilizador ( 
    username VARCHAR(50),
    email VARCHAR(50)           CONSTRAINT nn_utilizador_email NOT NULL, 
    palavra_passe VARCHAR(50)   CONSTRAINT nn_utilizador_palavra_passe NOT NULL,
    data_nascimento DATE        CONSTRAINT nn_utilizador_data_nascimento NOT NULL,
    artista_favorito,
    --
    CONSTRAINT pk_utilizador
        PRIMARY KEY (username),
    --
    CONSTRAINT fk_utilizador_artista_fav
        FOREIGN KEY (artista_favorito) 
        REFERENCES artista (isni),
    --
    CONSTRAINT un_utilizador_email
        UNIQUE (email),   -- RIA-13
    --
    CONSTRAINT ck_utlizador_email
        CHECK (REGEXP_LIKE (email, '^[[:alnum:].+-]+@[[:alnum:].-]+\.[[:alpha:]]{2,}$')),
    --
    CONSTRAINT ck_utilizador_username
        CHECK (REGEXP_LIKE (username, '^[[:alnum:]]+$')),   -- RIA-14
    --
    CONSTRAINT ck_utilizador_palavra_passe
        CHECK (REGEXP_LIKE (palavra_passe, '^[!-~]+$'))   -- Permitir apenas palavras-passe com caracteres ASCII, sem espaços
);

-- ---------------------------------------------------------------------------- 

CREATE TABLE lista (
    nome_lista VARCHAR(50),
    utilizador,
    --
    CONSTRAINT pk_lista
        PRIMARY KEY (utilizador, nome_lista),
    --
    CONSTRAINT fk_lista_username
        FOREIGN KEY (utilizador) 
        REFERENCES utilizador (username)
        ON DELETE CASCADE
);

-- ---------------------------------------------------------------------------- 

CREATE TABLE versao_album (
    ean_13 CHAR(13),   -- RIA-12
    designacao VARCHAR(50)  CONSTRAINT nn_versao_designacao NOT NULL,
    --
    CONSTRAINT pk_versao_album
        PRIMARY KEY (ean_13),
    --
    CONSTRAINT ck_versao_album_ean_13
        CHECK (REGEXP_LIKE (ean_13, '^[[:digit:]]+$'))   -- RIA-12
);

-- ---------------------------------------------------------------------------- 

CREATE TABLE utilizador_possui (
    utilizador,
    versao_album,
    data_adicao DATE    CONSTRAINT nn_possui_data NOT NULL,
    --
    CONSTRAINT pk_utilizador_possui
        PRIMARY KEY(versao_album, utilizador), 
    --
    CONSTRAINT fk_possui_utilizador
        FOREIGN KEY (utilizador) 
        REFERENCES utilizador (username),
    --
    CONSTRAINT fk_possui_versao
        FOREIGN KEY (versao_album) 
        REFERENCES versao_album (ean_13)
);

-- ---------------------------------------------------------------------------- 

CREATE TABLE suporte_fisico (
    tipo VARCHAR(50),
    --
    CONSTRAINT pk_suporte_fisico
        PRIMARY KEY (tipo),
    --
    CONSTRAINT ck_suporte_fisico_tipo
        CHECK (REGEXP_LIKE(tipo, '^(CD|vinil|cassete)$'))   -- RIA-11
);

-- ---------------------------------------------------------------------------- 

CREATE TABLE album (
    mbid CHAR(36),   -- RIA-01
    titulo VARCHAR(50)          CONSTRAINT nn_album_titulo NOT NULL,
    tipo VARCHAR(50)            CONSTRAINT nn_album_tipo NOT NULL,
    ano_lancamento NUMBER(4)    CONSTRAINT nn_album_ano_lancamento NOT NULL,
    artista,
    --
    CONSTRAINT pk_album
        PRIMARY KEY (mbid),
    --
    CONSTRAINT fk_album_isni
        FOREIGN KEY (artista) 
        REFERENCES artista (isni),
    --
    CONSTRAINT ck_album_tipo
        CHECK (REGEXP_LIKE(tipo, '^(single|EP|LP)$'))   -- RIA-02
);

-- ---------------------------------------------------------------------------- 

CREATE TABLE album_em_suporte_fisico (
    album,
    suporte_fisico,
    --
    CONSTRAINT pk_album_em_suporte_fisico
        PRIMARY KEY(album, suporte_fisico),
    --
    CONSTRAINT fk_album_suporte_album
        FOREIGN KEY (album) 
        REFERENCES album (mbid)
        ON DELETE CASCADE,
    --
    CONSTRAINT fk_album_suporte_suporte
        FOREIGN KEY (suporte_fisico) 
        REFERENCES suporte_fisico (tipo)
        ON DELETE CASCADE
);

-- ---------------------------------------------------------------------------- 

CREATE TABLE lista_refere (
    album,
    lista,
    utilizador,
    --
    CONSTRAINT pk_lista_refere
        PRIMARY KEY (album, lista, utilizador),
    --
    CONSTRAINT fk_lista_refere_album
        FOREIGN KEY (album) 
        REFERENCES album (mbid),
    --
    CONSTRAINT fk_lista_refere_utilizador
        FOREIGN KEY (lista, utilizador) 
        REFERENCES lista (nome_lista, utilizador)
);

-- ---------------------------------------------------------------------------- 

ALTER TABLE versao_album ADD (
    album           CONSTRAINT nn_versao_album NOT NULL,
    suporte_fisico  CONSTRAINT nn_versao_suporte_fisico NOT NULL,
    --
    CONSTRAINT fk_versao_mbid_tipo
        FOREIGN KEY (album, suporte_fisico) 
        REFERENCES album_em_suporte_fisico (album, suporte_fisico)
);

-- ---------------------------------------------------------------------------- 

-- artista
INSERT INTO artista (isni, nome_artista, ano_inicio)
    VALUES ('0000000374081629', 'Martelo Rebelde', TO_DATE('2016-10-18', 'YYYY-MM-DD'));
INSERT INTO artista (isni, nome_artista, ano_inicio)
    VALUES ('0000000627182818', 'Donald Truck', TO_DATE('2017-07-20', 'YYYY-MM-DD'));
INSERT INTO artista (isni, nome_artista, ano_inicio)
    VALUES ('1234567890123456', 'Caravana', TO_DATE('1987-10-18', 'YYYY-MM-DD'));
INSERT INTO artista (isni, nome_artista, ano_inicio)
    VALUES ('5674567891234560', 'Plastica', TO_DATE('1981-07-20', 'YYYY-MM-DD'));

-- ---------------------------------------------------------------------------- 

-- utilizador   
INSERT INTO utilizador (username, email, palavra_passe, data_nascimento, artista_favorito) 
    VALUES ('Jose', 'jose.pedro@gmail.com', 'Jose_007', TO_DATE('1999-12-23', 'YYYY-MM-DD'),'0000000374081629');
INSERT INTO utilizador (username, email, palavra_passe, data_nascimento) 
    VALUES ('Adriana', 'adriana.iloveyou@sapo.pt', 'tiktok2012', TO_DATE('2012-03-14', 'YYYY-MM-DD'));

-- ---------------------------------------------------------------------------- 

-- solista
INSERT INTO solista (isni_solista)
    VALUES ('0000000374081629');
INSERT INTO solista (isni_solista)
    VALUES ('0000000627182818');

-- ---------------------------------------------------------------------------- 

-- grupo
INSERT INTO grupo (isni_grupo)
    VALUES ('5674567891234560');
INSERT INTO grupo (isni_grupo)
    VALUES ('1234567890123456');

-- ---------------------------------------------------------------------------- 

-- album
INSERT INTO album (mbid, titulo, tipo, ano_lancamento, artista) 
    VALUES ('1a4b2d53-9f67-4b19-aab2-58d8b4ef01a1', 'Smells Like Bean Spirit', 'single', 1991, '1234567890123456');
INSERT INTO album (mbid, titulo, tipo, ano_lancamento, artista) 
    VALUES ('93e012af-6b52-4d2c-b223-ccab3d44a918', '67', 'LP', 2020, '5674567891234560');

-- ---------------------------------------------------------------------------- 

-- suporte fisico
INSERT INTO suporte_fisico (tipo)
    VALUES ('CD');
INSERT INTO suporte_fisico (tipo)
    VALUES ('vinil');

-- ---------------------------------------------------------------------------- 

-- album_em_suporte_fisico
INSERT INTO album_em_suporte_fisico (album, suporte_fisico)
    VALUES ('93e012af-6b52-4d2c-b223-ccab3d44a918', 'vinil');
INSERT INTO album_em_suporte_fisico (album, suporte_fisico)
    VALUES ('1a4b2d53-9f67-4b19-aab2-58d8b4ef01a1', 'CD');

-- ---------------------------------------------------------------------------- 

-- versao album
INSERT INTO versao_album (ean_13, designacao, album, suporte_fisico)
    VALUES ('4006381333939', 'Shiny', '93e012af-6b52-4d2c-b223-ccab3d44a918', 'vinil');
INSERT INTO versao_album (ean_13, designacao, album, suporte_fisico)
    VALUES ('5449000000996', 'Goofy Ahh', '1a4b2d53-9f67-4b19-aab2-58d8b4ef01a1', 'CD');

-- ---------------------------------------------------------------------------- 

-- utilizador_possui
INSERT INTO utilizador_possui (utilizador, versao_album, data_adicao)
    VALUES ('Jose', '4006381333939', TO_DATE('2015-10-18', 'YYYY-MM-DD'));
INSERT INTO utilizador_possui (utilizador, versao_album, data_adicao)
    VALUES ('Adriana', '5449000000996', TO_DATE('2020-10-18', 'YYYY-MM-DD'));

-- ---------------------------------------------------------------------------- 

-- lista
INSERT INTO lista (nome_lista, utilizador)
    VALUES ('80s Classics','Jose');
INSERT INTO lista (nome_lista, utilizador)
    VALUES ('TikTok Trends <3', 'Adriana');

-- ---------------------------------------------------------------------------- 

-- lista_refere
INSERT INTO lista_refere (album, lista, utilizador)
    VALUES ('93e012af-6b52-4d2c-b223-ccab3d44a918', '80s Classics', 'Jose');
INSERT INTO lista_refere (album, lista, utilizador)
    VALUES ('1a4b2d53-9f67-4b19-aab2-58d8b4ef01a1', 'TikTok Trends <3', 'Adriana');

-- ---------------------------------------------------------------------------- 

-- membro
INSERT INTO membro (solista, grupo)
    VALUES ('0000000374081629', '5674567891234560');  
INSERT INTO membro (solista, grupo)
    VALUES ('0000000627182818', '5674567891234560');
INSERT INTO membro (solista, grupo)
    VALUES ('0000000627182818', '1234567890123456');