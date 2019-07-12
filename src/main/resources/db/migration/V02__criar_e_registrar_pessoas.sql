CREATE TABLE pessoa(
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    ativo TINYINT(1) NOT NULL,
    logradouro VARCHAR(70),
    numero VARCHAR(4),
    complemento VARCHAR(30),
    bairro VARCHAR(20),
    cep VARCHAR(8),
    cidade VARCHAR(30),
    estado VARCHAR(2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
