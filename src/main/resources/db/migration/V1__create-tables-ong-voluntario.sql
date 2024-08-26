-- Criação da tabela Ong
CREATE TABLE Ong (
                     id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Identificador da ONG
                     nome VARCHAR(255) NOT NULL,            -- Nome da ONG
                     email VARCHAR(255) NOT NULL UNIQUE,    -- Email da ONG (único)
                     telefone VARCHAR(20) NOT NULL          -- Telefone da ONG
);


-- Criação da tabela Voluntario
CREATE TABLE Voluntario (
                            id BIGINT PRIMARY KEY,            -- Identificador do voluntário
                            nome VARCHAR(255) NOT NULL,      -- Nome do voluntário
                            cpf VARCHAR(14) NOT NULL,        -- CPF do voluntário (inclui máscara de formatação)
                            funcao VARCHAR(255) NOT NULL,    -- Função do voluntário na ONG
                            ong_id BIGINT,                    -- Chave estrangeira para a tabela Ong
                            FOREIGN KEY (ong_id) REFERENCES Ong(id) ON DELETE SET NULL  -- Relaciona com a tabela Ong
);
