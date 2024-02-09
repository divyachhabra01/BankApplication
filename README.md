# Bank Application using Spring Security and JWT Token (Role based Authentication)
 ## Description

 This is a MVP of Bank Application that provides following services to the customers in a secured manner :
 * Register/Signup
 * Login
 * Create new Bank Account
 * Deposit Money in Account
 * Withdraw Money from Account
 * Check Bank Balance
 * View Transactions History
 * Get Account details
 * Get Logged In Customer Details

Customer data is stored in a local  MySQL database at the backend

> [!NOTE]  
> You don't need to create DB tables ,it will create itself once you build the Project.

## Authentication of APIs

For Authentication of APIs I have used Spring Security with JWT token .
# Authentication Scheme : Bearer Token
JWT token contains claims about user . Only authenticated users can access services of Bank Application as per the claims present in JWT token.
When user login for the irst time after successful register/signup. Spring Security will validate the user credentials if validation returns true it will going to  generate a JWT token and return that token as part of response header of API.
Example of Bearer token/ JWT token :
eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJCYW5rIiwic3ViIjoiSldUIFRva2VuIiwidXNlcm5hbWUiOiJEaXZ5YSIsImF1dGhvcml0aWVzIjoiIiwiaWF0IjoxNzA3NDA5MDE1LCJleHAiOjE3MDc0MzkwMTV9.fyaXxE5T-aDtITovOL9yONKfRhNhaviZUwGwr-2OwKg

This token contains 3 parts :
Header, Payload and Verify Signature 

![JWT](https://github.com/divyachhabra01/BankApplication/assets/85253378/9ca8f4ae-332d-4dd1-90b3-364256e678c0)

For more info you can visit JWT website (https://jwt.io/)

## APIs:
* POST API (/api/v1/register) : This is a public API for user registeration/SignUp.
* POST API (api/v1/login) : This API validates the user credentials and upon successful validation return a JWT token in response header as **Authorization** header .
* GET API (/api/v1/customers/current) : This is a secured API that require token for authentication . It return details of logged in customer.
* POST API (/api/v1/account) : This API is used for account creation . Only user who has role as USER can access this API.
* GET API (/api/v1/customers/1/accounts) This API return details of active account of customer based on customer Id and account number.Only user who has role as USER can access this API.
* GET API (/api/v1/1/accounts/PNB-SAVINGS-fba553cd/balance) : Using this API customer/user can view their bank balance.Only user who has role as USER can access this API.
* POST API (api/v1/1/accounts/PNB-SAVINGS-97941c5c/deposit?amount=5000.00&transactionMode=ONLINE&description=Testing) : This API deposit money in customer account.Only user who has role as USER can access this API.
* POST API (/api/v1/1/accounts/PNB-SAVINGS-61417251/withdraw?amount=200&transactionMode=ONLINE&description=Payment done) : This API is used for withdrawal of money from account.Only user who has role as USER can access this API.
* GET API (/api/v1/1/accounts/PNB-SAVINGS-61417251/transactions) : This API return transaction history as response for customer account.

## Testing
You can use Postman for testing of above service . I am sharing the link of my Postman collection here 

[Link](https://api.postman.com/collections/15194636-e0dcf691-f55d-4b65-87a5-bbb43c9cb0f9?access_key=PMAT-01HP7502CBGG73B6M7VFR7RVBC)

## Tools and Technology Used :

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Spring Security
* Hibernate
* MySQL
* Maven
* Hibernate
* JWT Token
* Lombok
* Postman

Open for suggestion and improvement :writing_hand:



