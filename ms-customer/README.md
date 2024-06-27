# Swagger Project - OpenAPI 3.0

Esta es una version de un API creada con mongo DB, usando programacion reactiva y programación funcional para una mantenimiento de clientes.

## Instalación

1. Abrir con su ID preferido e instalar las dependencias.
2. El aplicativo esta conectado a un cluster de mongodb Atlas en la nube.

## Uso

### Customer (the customer API Methods)
- **GET - customer all (without parameters):**
    ````
    *** METHOD: GET ***
    URL: http://localhost:8080/api/v1/customer/list
    ````
- **GET - find by id customer:** change number **667c425ed3fc7a6ce9e1b892** for the customer id
    ````
    *** METHOD: GET ***
    URL: http://localhost:8080/api/v1/customer/findbyid/667c42cbd3fc7a6ce9e1b893
    ````
- **POST - save customer with body in JSON:**
    ````
    *** METHOD: POST ***
    URL: http://localhost:8080/api/v1/customer/registre
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
- **PUT - update customer with body in JSON and variable path (667c42cbd3fc7a6ce9e1b893):**
    ````
    *** METHOD: PUT ***
    URL: http://localhost:8080/api/v1/customer/updatebyid/667c42cbd3fc7a6ce9e1b893
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
- **DELETE - delete customer with variable path (667c42cbd3fc7a6ce9e1b893):**
    ````
    *** METHOD: DELETE ***
    URL: http://localhost:8080/api/v1/customer/deletebyid/667c42cbd3fc7a6ce9e1b893
    ````