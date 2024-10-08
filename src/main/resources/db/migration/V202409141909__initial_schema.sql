CREATE TABLE usuario_role(
    id int primary key auto_increment,
    usuario_id int,
    role_id int,
    CONSTRAINT fkUsuarioRoles FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fkRolesUsuario FOREIGN KEY (role_id) REFERENCES role(id)
);