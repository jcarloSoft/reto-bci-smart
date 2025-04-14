# BCI Smart User Management API

Este proyecto es una **API REST** para la gestión de usuarios y sus teléfonos, desarrollada con **Spring WebFlux**, **Java 17+**, y base de datos **PostgreSQL**.
El proyecto sigue un enfoque reactivo utilizando **Project Reactor**, lo que permite un manejo eficiente de peticiones asíncronas.

## Características

- Registro de usuarios con validaciones.
- Generación de token JWT al crear usuario.
- Consulta de todos los usuarios registrados.
- Manejo de excepciones global personalizado.
- Relación de usuarios con múltiples teléfonos.
- Uso de Reactive Repositories para operaciones no bloqueantes.

## Tecnologías Utilizadas

- Java 17+
- Spring Boot 3
- Spring WebFlux
- Spring Data R2DBC
- PostgreSQL
- JWT (Json Web Token)
- Lombok
- Reactor Core

## Endpoints

| Método | Endpoint            | Descripción                 |
| ------ | ------------------- | --------------------------- |
| POST   | `/api/users/create` | Crear un nuevo usuario.     |
| GET    | `/api/users`        | Obtener todos los usuarios. |

### Ejemplo de Request para crear usuario

```json
POST /api/users/create
{
  "name": "Juan Pérez",
  "email": "juan.perez@mail.com",
  "password": "Peru123456",
  "phones": [
    {
      "phoneNumber": "123456789",
      "cityCode": "1",
      "countryCode": "57"
    }
  ]
}
```

### Ejemplo de Response al crear usuario

```json
{
  "id": "uuid-generado",
  "created": "2025-04-14T12:00:00",
  "modified": "2025-04-14T12:00:00",
  "lastLogin": "2025-04-14T12:00:00",
  "token": "jwt-token-generado",
  "isActive": true,
  "name": "Juan Pérez",
  "email": "juan.perez@mail.com",
  "phones": [
    {
      "phoneNumber": "123456789",
      "cityCode": "1",
      "countryCode": "57"
    }
  ]
}
```

### SCRIPT SQL:

```sql
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS phones;

CREATE TABLE users (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(30) NOT NULL,
    create_at TIMESTAMP NOT NULL,
    update_at TIMESTAMP NOT NULL,
    ultimate_session TIMESTAMP NOT NULL
);

CREATE TABLE phones (
    id UUID PRIMARY KEY NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    city_code VARCHAR(5) NOT NULL,
    country_code VARCHAR(5) NOT NULL,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
```

###Manejo de Errores
| http status | message |
| ------ | ------------------- |
|400 |Bad Request: Error de validaciones de campos.|
|409 |Conflict: El correo ya está registrado.|
|500 |Internal Server Error: Errores internos no controlados.

## Autor

Desarrollado por [Juan Carlos Hilario Ramírez].

```

```
