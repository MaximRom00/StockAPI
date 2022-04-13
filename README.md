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
Cryptocurrency data prices are got from coingecko.com and are saved in db. By default we have 50 crypto currency in usd in db. Also every 60 second info about crypto currency are updated and saved in db.

Get requests:
- /api/crypto/lowestPrice - the most cheaper crypto;
- /api/crypto/highestPrice - the most expensive crypto;
- /api/crypto?symbol= - get list crypto by size;
- /api/crypto?symbol= - get crypto by symbol;
- /api/crypto/report - get crypto in csv file.(ex: https://github.com/MaximRom00/StockAPI/blob/main/crypto.csv);
- /api/crypto/mail/report - get crypto file on email.

Also you can save new user and create crypto account for this user. You can have on your crypto account: crypto in 6 currency(USD, EUR, RUB, CNY, PLN, GBP).


# Documentation for API
https://www.coingecko.com/en/api/documentation

<p align="center">
<img " src="https://logovectordl.com/wp-content/uploads/2021/11/coingecko-logo-vector.png" width="275" height="175"> </p>
                                                                                                                   

                                                                                                                   
# Run 
1. Download the zip file or clone the repository: 

   git clone hhttps://github.com/MaximRom00/StockAPI.git

2. Create Mysql database. Set username and password in src/main/resources/application.properties file.
 
3. Run this application using maven: Run the app using maven:
```
mvn spring-boot:run
```
The app will start running at http://localhost:8081.
