# Items

Desarrollo marketplace para items

## Tecnologías y librerias usadas

-   Spring webflux
-   Java 17
-   MongoDB
-   Redis
-   Cognito

## ¿Cómo ejecutar?

Clona el repositorio o descarga el repositorio en formato ZIP

**items**

  1. Ingresa a la ruta `cd items` desde la consola
  2. Allí se encontrará el docker compose que creará los contenedores para Redis y Mongo use el comando `sudo docker-compose up -d`
  3. Por consiguiente es necesario que cree una base de datos llamada `meli` y una collection llamada `items`
  4. Ejecuta el comando `mvn clean package` y por último `mvn spring-boot:run`.
  5. Se ejecutará por defecto en [localhost:8081]().

## ¿Cómo usar?

Puede acceder a http://localhost:8081/api/swagger-ui/index.html para encontrar la documentación del proyecto y verificar las APIs creadas


Se adjunta postman
