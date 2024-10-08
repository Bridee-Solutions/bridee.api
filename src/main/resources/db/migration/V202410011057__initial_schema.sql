CREATE TABLE verification_token(
    id INT PRIMARY KEY AUTO_INCREMENT,
    valor VARCHAR(36) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    expires_at DATETIME NOT NULL,
    confirmed_at DATETIME,
    usuario_id int,
    CONSTRAINT UsuarioVerificationToken FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);