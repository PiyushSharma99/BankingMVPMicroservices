**Banking Microservice**

To make sure everything work please connect with Nagarro VPN first.

Run the applications in the order given below to work everything fine:-

1.Eureka Server

2.Spring Cloud Config Server

3.Api Gateway

4.Customer Management Service

5.Account Management Service

Go to http://localhost:8761/ and checks if Account Management Service, Api Gateway and Customer Management 
Service are registered on it or not.

Follow the same steps for Customer Management Service.

API Gateway Configurations routing can be found in application.yml in api gateway directory.

As per requirement I have created the centralised configuration management.
It contains the db configuration for both customer and account services.
If anything fails must correct your local my sql configuration in Config Repo>application.yml.
And update the correct path of application.yml file in Spring Cloud Config Server's directory application.yml file.

For now it's given according to my local machine.
If Spring Cloud Config Server fails to start make sure to create local git repo for Config Repo.

Services API's with pojo (can check by hitting postman)

**CustomerMangementService** 

1. Add Customer

Post Method
```URL : http://<ip-address>:8081/customers```
Sample pojo : 
```
{
    "customerId" : "C2",
    "customerName" : "Test 2",
    "customerEmail" : "test2@dev.in",
    "customerPhone" : "9876543210",
    "customerPermanentAddress" : "Jabalpur, India",
    "customerIdentityDocument" : "Pan card"
}

```
2. Get All Customers

Get Method 
```URL : http://<ip-address>:8081/customers```

3. Get Single Customer 

Get Method
```URL : http://<ip-address>:8081/customers/<customer-id>```

4. Update Customer Details

Put Method 
```URL : http://<ip-address>:8081/customers/<customer-id>```
Sample Pojo: 
```
{
    "customerId" : "C2",
    "customerName" : "Test 2",
    "customerEmail" : "test2@dev.in",
    "customerPhone" : "9876543210",
    "customerPermanentAddress" : "Jabalpur, India",
    "customerIdentityDocument" : "Pan card"
}
```
5. Delete Customer : Associated account will also get deleted

Delete Method 
```URL : http://<ip-address>:8081/customers/<customer-id>```

**AccountMangementService** 

1. Add Account
Post Method
```URL : http://<ip-address>:8082/accounts/```
Sample Pojo: 
```
{ 
    "accountType" : "Savings",
    "balance" : 95000,
    "customerId" : "C2"
}
```

2. Add Money To Account : Check is done if customer is present

Put Method 
```URL : http://<ip-address>:8082/accounts/addmoney/<account-id>?amount=<amount>&customerId=<customer-id>```


3. Withdraw Money From Account : Check is done if customer is present
Put Method
```URL : http://<ip-address>:8082/accounts/withdraw/<account-id>?amount=<amount>&customerId=<customer-id>```


4. Get Account Details : It gives account details as well as customer details for each account.
Get Method
```URL : http://<ip-address>:8082/accounts/<account-id>```

5. Get Account Details by account id 
Get Method
```URL : http://<ip-address>:8082/accounts/<account-id>```

6. Get Account Details by customer id 
Get Method
```URL : http://<ip-address>:8082/accounts/<customer-id>```

7. Update Account Details 
Put Method
```URL : http://<ip-address>:8082/accounts/<account-id>```
Sample Pojo: 
```
{ 
    "accountType" : "Savings",
    "balance" : 100000000   
}
```

8. Delete Account 
Delete Method
```URL : http://<ip-address>:8082/accounts/<accountNo>```