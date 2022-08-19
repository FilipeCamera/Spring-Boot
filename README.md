# Spring-Boot

Estudando construção de API com Spring Boot + Security + PostgresSQL.


## Roles

- [x] ADMIN
- [x] USER
- [x] CREATOR


## Rotas com Segurança

```
  AUTHENTICATED ROLE(ADMIN, USER) GET /user
```

```
  AUTHENTICATED ROLE(ADMIN) OR ID == AUTHENTICATED.ID PUT /user/:id
```

```
  AUTHENTICATED ROLE(ADMIN, USER) GET /user/:id
```
