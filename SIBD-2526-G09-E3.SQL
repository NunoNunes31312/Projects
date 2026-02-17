-- ---------------------------------------------------------------------------- 
-- LEI - 2025/26 - Etapa 3 do Projeto
-- ------
-- Grupo 09
-- Dinis Fernandes – nº63787 – TP14
-- Tiago Wu – nº63696 – TP14
-- Nuno Nunes – nº63731 - TP12
-- ------
-- Contribuições:
-- Dinis Fernandes - 40% - Discussão de ideias, formulação de queries, teste de código, verificação de resultados, correção de erros
-- Tiago Wu - 35% - Discussão de ideias, formulação de queries, formulação de inserts para teste, teste de código, verificação de resultados 
-- Nuno Nunes - 25% - Discussão de ideias, formulação de queries, teste de código, verificação de resultados 
-- ----------------------------------------------------------------------------

-- ----------------------------------------------------------------------------
-- Disponibilidade do grupo para a aferição de conhecimentos:
-- Dia 9, terça, 13h-14h30: não
-- Dia 9, terça, 16h30-18h: não
-- Dia 10, quarta, 17h-18h30: sim
-- Dia 12, sexta: 17h-18h30: sim   
-- Dia 15, segunda: 13h30-15h: não
-- Dia 16, terça: 13h-14h30: não
-- Dia 16, terça: 15h30-16h30: sim
-- Dia 16, terça: 16h30-18h: sim
-- ----------------------------------------------------------------------------

-- 1 --------------------------------------------------------------------------
-- Username e e-mail dos utilizadores nascidos em 1990, que têm como artista
-- favorito Day6, e que, durante o ano de 2025, registaram a posse de um ou mais
-- álbuns interpretados por esse artista. O EAN-13, título, e ano de lançamento
-- dos álbuns também devem ser mostrados, bem como o número de dias que
-- passaram desde a data dos registos de posse. A ordenação do resultado deve
-- ser ascendente pelo username dos utilizadores e descendente pelo número de
-- dias que passaram desde os registos de posse e pelo EAN-13 dos álbuns.
-- -----------------------------------------------------------------------------

SELECT UT.username AS UTILIZADOR, UT.email AS EMAIL, AL1.ean AS EAN_13, AL1.titulo AS ALBUM, AL1.ano AS ANO_ALBUM, TRUNC(CURRENT_DATE - PO1.desde) AS NUMERO_DIAS
FROM utilizador UT, possui PO1, album AL1, artista AR1
WHERE (UT.artista = AR1.isni)
AND (PO1.album = AL1.ean)
AND (PO1.utilizador = UT.username)
AND (UT.nascimento = 1990)
AND (AR1.isni = AL1.artista)
AND (AR1.nome = 'Day6')
AND (UT.username IN (SELECT PO2.utilizador
                     FROM possui PO2, artista AR2, album AL2
                     WHERE (EXTRACT(YEAR FROM PO2.desde) = 2025)
                     AND (PO2.album = AL2.ean)
                     AND (AL2.artista = AR2.isni)
                     AND (AR2.nome = 'Day6')))
ORDER BY UTILIZADOR ASC, NUMERO_DIAS DESC, EAN_13 DESC;

-- 2 -------------------------------------------------------------------------
-- Username dos utilizadores com endereço de e-mail no gmail.com que, considerando
-- apenas os registos de posse efetuados entre 2000 e 2020, ou não possuem
-- álbuns interpretados pelo artista Dire Straits, ou possuem no máximo 
-- três álbuns desse artista. Adicionalmente, os utilizadores não podem possuir 
-- álbuns do tipo single, seja qual for o artista e o ano do registo. O resultado
-- deve vir ordenado pelo username de forma ascendente. 
-- ---------------------------------------------------------------------------- 

SELECT DISTINCT UT.username AS UTILIZADOR
FROM utilizador UT, possui PO1, album AL1
WHERE (UT.email LIKE '%@gmail.com')
AND (PO1.album = AL1.ean)
AND (AL1.tipo <> 'single')
AND (UT.username = PO1.utilizador)
AND (UT.username NOT IN (SELECT PO2.utilizador
                         FROM artista AT, possui PO2, album AL2
                         WHERE (AT.nome = 'Dire Straits')
                         AND (AT.isni = AL2.artista)
                         AND (PO2.album = AL2.ean)
                         AND (EXTRACT(YEAR FROM PO2.desde) BETWEEN 2000 AND 2020)
                         GROUP BY PO2.utilizador
                         HAVING COUNT(PO2.album) > 3))
ORDER BY UTILIZADOR ASC;

-- 3 --------------------------------------------------------------------------
-- Nome e ano de início de atividade de artistas tais que todos os utilizadores
-- nascidos de 2000 em diante tenham registado a posse de pelo menos um álbum
-- interpretado por esses artistas, com as seguintes restrições adicionais: só 
-- artistas que tenham lançado um ou mais álbuns nos dois últimos anos, e os 
-- registos de posse dos utilizadores têm de ter sido feitos entre as 12h e as 19h59.
-- O resultado deve vir ordenado por nome de artista de forma ascendente e pelo
-- seu ano de início de atividade de forma descendente.
-- ----------------------------------------------------------------------------

SELECT DISTINCT AR1.nome AS ARTISTA, AR1.inicio AS INICIO
FROM artista AR1
WHERE EXISTS (SELECT *
              FROM album AL1
              WHERE AL1.artista = AR1.isni
              AND AL1.ano >= EXTRACT(YEAR FROM SYSDATE) - 2)
AND NOT EXISTS (SELECT *
                FROM utilizador UT1
                WHERE (UT1.nascimento >= 2000)
                AND AR1.isni NOT IN (SELECT AL2.artista
                                     FROM possui PO1, album AL2
                                     WHERE (PO1.utilizador = UT1.username)
                                     AND (PO1.album = AL2.ean)
                                     AND (AR1.isni = AL2.artista)
                                     AND TO_CHAR(PO1.desde, 'HH24:MI') BETWEEN '12:00' AND '19:59'))
ORDER BY ARTISTA ASC, INICIO DESC;

-- 4 --------------------------------------------------------------------------
-- Username dos utilizadores com mais registos de posse de álbuns em suporte 
-- vinil em cada ano, separadamente para utilizadores nascidos nas décadas de 
-- 1980 e 1990, devendo a década de nascimento dos utilizadores e o número total 
-- de registos de posse de álbuns em cada ano também aparecer no resultado. A 
-- ordenação do resultado deve ser descendente pelo ano e ascendente pela década
-- de nascimento dos utilizadores. No caso de haver mais do que um utilizador
-- nascido na mesma década e com o mesmo número máximo de registos de 
-- posse de álbuns num ano, devem ser mostrados todos esses utilizadores.
--  ----------------------------------------------------------------------------

SELECT TO_CHAR(PO1.desde, 'YYYY') AS ANO, PO1.utilizador AS UTILIZADOR, decada_de_ano(UT1.nascimento) as DECADA, COUNT(*) AS COUNT
FROM possui PO1, album AL1, utilizador UT1
WHERE (PO1.album = AL1.ean)
AND (PO1.utilizador = UT1.username)
AND (AL1.suporte = 'vinil')
AND decada_de_ano(UT1.nascimento) IN (1980, 1990)
GROUP BY PO1.utilizador, TO_CHAR(PO1.desde, 'YYYY'), decada_de_ano(UT1.nascimento)
HAVING (COUNT(*) = (SELECT MAX(COUNT(*))
                    FROM possui PO2, album AL2, utilizador UT2
                    WHERE (PO2.album = AL2.ean)
                    AND (PO2.utilizador = UT2.username)
                    AND (AL2.suporte = 'vinil')
                    AND (TO_CHAR(PO1.desde, 'YYYY') = TO_CHAR(PO2.desde, 'YYYY'))
                    AND decada_de_ano(UT1.nascimento) = decada_de_ano(UT2.nascimento)
                    GROUP BY PO2.utilizador))
ORDER BY ANO DESC, DECADA ASC;