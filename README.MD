# Betvictor assignment

- Java 17 is required to be installed.
- To start the application, run the following Docker command in the Task folder:

```bash
docker-compose up -d
```

## Sample request

- Processor microservice
```
curl -X GET "http://localhost:8080/betvictor/text?p=5&l=long"
```

- Repository microservice
```
curl -X GET "http://localhost:8090/betvictor/history"
```
