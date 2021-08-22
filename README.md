# rabobank-assignment

### Building the project
1. Setup JDK 16
```bash
sudo apt install openjdk-16-jdk
```

2. Build and run tests
```bash
./gradlew clean build
```

### Run application
```
docker-compose -f stack.yml up
```
```
./gradlew bootRun
```

### API Documentation
http://localhost:8080/swagger-ui.html

### Request token
```
curl --request POST \
  --url http://localhost:8080/login \
  --header 'content-type: application/json' \
  --data '{
	"username": "user",
	"password": "usr"
}'
```
### Create account
```
curl --request POST \
  --url http://localhost:8080/api/accounts \
  --header 'authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiJ9.1ugDHJpyOh-Z1N-ArVeuJhHMel_ehwTlYePyKtc2Go8PHMf7YBnFns5-yhgoJD0zitS3HbmIx1hUAWYnrXRsQQ' \
  --header 'content-type: application/json' \
  --data '{
  "balance": 0,
  "holder_document": "02456494311",
  "holder_name": "Fred Oliveira",
  "number": "1234",
  "type": "PAYMENT_ACCOUNT"
}'
```
### Create power of attorney
```
curl --request POST \
  --url http://localhost:8080/api/powerofattorneys \
  --header 'authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIn0.l4h41rwkFmjnRyUIea3xtypzzFHanRMIieOy4ZD_1B-nu6v2ydHx7uuNhMzcbq-FFAoh98VZJYRWzfut63e3jg' \
  --header 'content-type: application/json' \
  --data '{
  "account_number": "1234",
  "authorization": "READ",
	"account_type": "PAYMENT_ACCOUNT",
  "grantee_name": "Fred",
  "grantor_name": "Mateus"
}'
```

### Checklist to be ready to go to production
- [x] Being able to collect metrics.
- [ ] Being able to monitor the application's health.
- [x] Being able to collect and index logs.
- [x] Has CI process to run the tests before go to the main branch.
- [ ] Containerized application.
- [ ] Has a pipeline to build a new release and deploy it.

### TODO
[] Encrypt password
