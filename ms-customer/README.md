# Swagger Project - OpenAPI 3.0

Esta es una version de un API creada con mongo DB, usando programacion reactiva y programación funcional para una mantenimiento de clientes.

## Instalación

1. Abrir con su ID preferido e instalar las dependencias.
2. El aplicativo esta conectado a un cluster de mongodb Atlas en la nube.

## Uso

### Customer (the customerEntity API Methods)
- **GET - customerEntity all (without parameters):**
    ````
    *** METHOD: GET ***
    URL: http://localhost:8080/api/v1/customerEntity/list
    ````
- **GET - find by id customerEntity:** change number **667c425ed3fc7a6ce9e1b892** for the customerEntity id
    ````
    *** METHOD: GET ***
    URL: http://localhost:8080/api/v1/customerEntity/findbyid/667c42cbd3fc7a6ce9e1b893
    ````
- **POST - save customerEntity with body in JSON:**
    ````
    *** METHOD: POST ***
    URL: http://localhost:8080/api/v1/customerEntity/registre
    ````
    ````
    {
        "id": "667c425ed3fc7a6ce9e1b892",
        "name": "Joseph",
        "lastName": "Magallanes",
        "documentType": "DNI",
        "documentNumber": "77885471",
        "clientType": "PERSONAL"
    }
    ````
- **PUT - update customerEntity with body in JSON and variable path (667c42cbd3fc7a6ce9e1b893):**
    ````
    *** METHOD: PUT ***
    URL: http://localhost:8080/api/v1/customerEntity/updatebyid/667c42cbd3fc7a6ce9e1b893
    ````
    ````
    {
        "name": "Joseph",
        "lastName": "Magallanes",
        "documentType": "DNI",
        "documentNumber": "77885471",
        "clientType": "PERSONAL"
    }
    ````
- **DELETE - delete customerEntity with variable path (667c42cbd3fc7a6ce9e1b893):**
    ````
    *** METHOD: DELETE ***
    URL: http://localhost:8080/api/v1/customerEntity/deletebyid/667c42cbd3fc7a6ce9e1b893
    ````