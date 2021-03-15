# stc

## Build and run:
mvn clean spring-boot:run

## Run tests:
mvn clean test

## API:
new record:
POST {"name": str, "secrets": str} to localhost:8080/information
returns {"id": int} of new record
"secrets" will be encrypted

retrieve record:
GET localhost:8080/information/{id}
decrypts and returns record #id as json:
{"name": str, "secrets": str}
