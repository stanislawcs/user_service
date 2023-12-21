-- changeset: Stanislaw: create-users-roles-table

CREATE TABLE IF NOT EXISTS users_roles(
    user_id BIGINT NOT NULL ,
    role_id BIGINT NOT NULL,
    PRIMARY KEY(user_id,role_id),
    CONSTRAINT fk_users_users_roles FOREIGN KEY (user_id)
    REFERENCES users(id) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT fk_roles_users_roles FOREIGN KEY (role_id)
    REFERENCES roles(id) ON DELETE CASCADE ON UPDATE NO ACTION
)