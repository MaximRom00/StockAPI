# StockAPI
Stack:
- JDK 11,
- Spring Boot,
- Spring Data Jpa,
- Spring Web,
- Spring Email,
- MySql,
- Jackson,
- Maven.
 
# Introduction
Cryptocurrency data prices are obtained from coingecko.com. This data is stored in a database. By default we have 50 crypto currency in usd in db. Also every 60 second info about crypto currency are updated and saved in db.

GET requests:
- /api/crypto/lowestPrice - the most cheaper crypto;
- /api/crypto/highestPrice - the most expensive crypto;
- /api/crypto/list?size= - get list crypto by size;
- /api/crypto?symbol= - get crypto by symbol;
- /api/crypto/report - get crypto in csv file. ([example file](https://github.com/MaximRom00/StockAPI/blob/main/crypto.csv));

Also you can save new user and create crypto account for this user. You can have on your crypto account: crypto in 6 currency(USD, EUR, RUB, CNY, PLN, GBP).

GET Request:
- /api/crypto/mail/accountReport - get all accounts in csv file and this file will sent on email. ([example file](https://github.com/MaximRom00/StockAPI/blob/main/cryptoAccount.csv)).

POST Requests:
- /api/crypto/save_user - save new user,
- /api/crypto/save_account?email=max@max.com&crypto_short=dogecoin&amount=1&currency=eur - save new account with currency eur,
- /api/crypto/save_account?email=max@max.com&crypto_short=dogecoin&amount=1&currency=eur - save new account by default currency is usd.
 
DELETE Requests:
- /api/crypto/delete?email - delete user by email,
- /api/crypto/deleteAccount?id= - delete account by id.

# Documentation for API
https://www.coingecko.com/en/api/documentation

<p align="center">
<img  src="https://bitexpert.io/wp-content/uploads/2022/01/coingecko-.png" width="450" height="275"> 
                                                                                                                   </p>                                                     
                                                                                                                   
# Run 
                                                                                                                   
1. Download the zip file or clone the repository: 

   git clone hhttps://github.com/MaximRom00/StockAPI.git

2. Create Mysql database. Set username and password in src/main/resources/application.properties file.
 
3. Run this application using maven: Run the app using maven:
```
mvn spring-boot:run
```
The app will start running at http://localhost:8081.                                                                                                              
