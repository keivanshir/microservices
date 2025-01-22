# microservices
This is a simple transaction manager with multiple microservices

### transaction microservice
this microservice has controllers related to transactions.
### account microservice
this microservice has controllers related to accounts, customers, users.
### discovery
this microservice manages discovery of microservices using Eureka.
### gateway
this microservice manages gateway of the microservices and routes the incoming API calls.
### common
this is the entity area of the application and other common codes like security and exception.
### security
security is built via JWT and BASIC auth which you can choose between them with `app.auth.type` in related modules.
### documentation
> [!NOTE]
> there are two documentation endpoints one for transaction apis located:  `http://localhost:8083/swagger/swagger-ui.html`
> and the other one for account apis located: `http://localhost:8082/swagger/swagger-ui.html`




