# Bidea Factory: Booking API

## Descripción

La presente aplicación está diseñada para gestionar reservas y validar códigos de descuento con una API externa.

Autor: 2024 - Leonardo Raul Molina Diaz

## Requisitos Previos

- **Java JDK 17 o superior**
- **Maven 3.6 o superior**
- **PostgreSQL**

## Configuración del Entorno

### 1. Clonar el Repositorio

Clona el repositorio del proyecto desde GitHub:

```bash
git clone 
```

### 2. Configurar PostgreSQL

Tener una instancia de PostgreSQL en ejecución y crea una base de datos para la aplicación. 


### 3. Configurar la Aplicación

Editar el archivo `src/main/resources/application.yml` para configurar la conexión a la base de datos PostgreSQL. Reemplaza los valores de ejemplo con los detalles de la base de datos.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/db_name
    username: user
    password: password
```

## Ejecutar la Aplicación

### 1. Compilar el proyecto

Compilar el proyecto Maven:

```bash
mvn clean install
```

### 2. Iniciar la Aplicación

Iniciar la aplicación Spring Boot:

```bash
mvn spring-boot:run
```

La aplicación se ejecutará en `http://localhost:8080` por defecto.

## Probar la Aplicación

### 1. Crear una Reserva

Enviar una solicitud POST al endpoint `/book` para crear una reserva.

#### Ejemplo con `curl`

```bash
curl -X POST http://localhost:8080/book \
-H "Content-Type: application/json" \
-d '{
  "id": "14564088-4",
  "name": "Gonzalo",
  "lastname": "Pérez",
  "age": 33,
  "phoneNumber": "56988123222",
  "startDate": "2024-07-22",
  "endDate": "2024-07-22",
  "houseId": "213132",
  "discountCode": "D0542A23"
}'
```

### 2. Validar el Código de Descuento

La aplicación verificará el código de descuento utilizando una API externa. `https://sbv2bumkomidlxwffpgbh4k6jm0ydskh.lambda-url.us-east-1.on.aws/`.

### 3. Revisar Respuestas

La respuesta será en el siguiente formato:

- **Éxito**:

```json
{
  "code": 200,
  "message": "Book Accepted"
}
```

- **Error**:

```json
{
  "statusCode": 400,
  "error": "Bad Request",
  "message": "Mensaje de error específico"
}
```

## Pruebas Unitarias

Para ejecutar las pruebas unitarias, usar el siguiente comando:

```bash
mvn test
```

Esto ejecutará todas las pruebas definidas en el proyecto y mostrará los resultados en la consola.