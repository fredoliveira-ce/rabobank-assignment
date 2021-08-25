# Power of Attorney Service (rabobank-assignment)
> A Power of Attorney is used when someone (grantor) wants to give access to his/her account to someone else (grantee). This could be read access or write access. In this way the grantee can read/write in the grantors account. Notice that this is a simplified version of reality.

### Building the project
1. Setup JDK 16
```shell script
sudo apt install openjdk-16-jdk
```

2. Build and run tests
```shell script
./gradlew clean build componentTest
```

### Run application
```shell script
docker-compose -f stack.yml up
```
```shell script
./gradlew bootRun
```

### API Documentation
http://localhost:8080/swagger-ui.html

## Instructions
> The user service would be implemented in other service, so there is an initial load in mongo-init/init.js. Check it out!

 1- Request a token
```shell script
curl --request POST \
  --url http://localhost:8080/login \
  --header 'content-type: application/json' \
  --data '{
	"username": "admin",
	"password": "pwd"
}'
```
 2- Create an account
```shell script
curl --request POST \
  --url http://localhost:8080/api/accounts \
  --header 'authorization: TOKEN_TO_REPLACE' \
  --header 'content-type: application/json' \
  --data '{
  "balance": 0,
  "holder_document": "50293847502",
  "holder_name": "Randall Gordon",
  "number": "5678",
  "type": "PAYMENT_ACCOUNT"
}'
```
 3- Login with the user interested in give the power of attorney -> randall.gordon
```shell script
curl --request POST \
  --url http://localhost:8080/login \
  --header 'content-type: application/json' \
  --data '{
	"username": "randall.gordon",
	"password": "usr"
}'
```

 4- Create a power of attorney
```shell script
curl --request POST \
  --url http://localhost:8080/api/powerofattorneys/authorize \
  --header 'authorization: TOKEN_TO_REPLACE' \
  --header 'content-type: application/json' \
  --data '{
  "account_type": "PAYMENT_ACCOUNT",
  "authorization": "WRITE",
  "grantee_document": "109327486"
}'
```

 5- Login with the authorized user -> peggy.burns
```shell script
curl --request POST \
  --url http://localhost:8080/login \
  --header 'content-type: application/json' \
  --data '{
	"username": "peggy.burns",
	"password": "usr"
}'
```


6- Fetch accounts which the logged user (randall.gordon) has access for
```shell script
curl --request GET \
  --url http://localhost:8080/api/powerofattorneys \
  --header 'authorization: TOKEN_TO_REPLACE' \
  --header 'content-type: application/json'
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
- [ ] Deploy application in some container orchestration.
- [ ] Configure PMD and spotbugs
