# Power of Attorney Service (rabobank-assignment)
> A Power of Attorney is used when someone (grantor) wants to give access to his/her account to someone else (grantee). This could be read access or write access. In this way the grantee can read/write in the grantors account. Notice that this is a simplified version of reality.

### Building the project
1. Setup JDK 16
```bash
sudo apt install openjdk-16-jdk
```

2. Build and run tests
```bash
./gradlew clean build componentTest
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

## Instructions
 1- Request token
```
curl --request POST \
  --url http://localhost:8080/login \
  --header 'content-type: application/json' \
  --data '{
	"username": "peggy.burns",
	"password": "usr"
}'
```
 2- Create account
```
curl --request POST \
  --url http://localhost:8080/api/accounts \
  --header 'authorization: TOKEN_EXTRACTED_FROM_HEADER_AFTER_LOGIN' \
  --header 'content-type: application/json' \
  --data '{
  "balance": 0,
  "holder_document": "50293847502",
  "holder_name": "Randall Gordon",
  "number": "5678",
  "type": "PAYMENT_ACCOUNT"
}
'
```
 3- Create power of attorney
```
curl --request POST \
  --url http://localhost:8080/api/powerofattorneys/authorize \
  --header 'authorization: TOKEN_EXTRACTED_FROM_HEADER_AFTER_LOGIN' \
  --header 'content-type: application/json' \
  --data '{
  "account_type": "SAVINGS_ACCOUNT",
  "authorization": "WRITE",
  "grantee_document": "50293847502"
}'
```
 4- Login with the authorized user -> randall.gordon
```
curl --request POST \
  --url http://localhost:8080/login \
  --header 'content-type: application/json' \
  --data '{
	"username": "randall.gordon",
	"password": "usr"
}'
```
 5- Request accounts the logged user (randall.gordon) has access for
```
curl --request GET \
  --url http://localhost:8080/api/powerofattorneys \
  --header 'authorization: TOKEN_EXTRACTED_FROM_HEADER_AFTER_LOGIN' \
  --header 'content-type: application/json' \
```


### Checklist to be ready to go to production
- [x] Being able to collect metrics.
- [ ] Being able to monitor the application's health.
- [x] Being able to collect and index logs.
- [x] Has CI process to run the tests before go to the main branch.
- [x] Containerized application.
- [x] Generate release and docker image.
- [ ] Has a pipeline to build a new release and deploy it.

### TODO
- [ ] Encrypt user password.
- [ ] Extract secrets from the code.
- [ ] Deploy application in some container orchestration
