Steps to initialise the application to work with your MySQL server:

- Step 1: Navigate to ...\src\main\resources\application.properties
- Step 2: Edit the url, username, and password to your database
- Step 3: Run the application

Note: Please run the application with an empty schema so ensure proper table creation

Sample objects under .json format for initial Customer, Driver, and Car creation

1. Customer:
{
"first_name": "Jacob",
"last_name": "Truong",
"phone_number": "0909090909",
"email": "s3878145@rmit.edu.vn",
"address": "RMIT Vietnam"
}

2. Driver:
{
"first_name": "Valentino",
"last_name": "Rossi",
"license_number": "123456789012",
"phone_number": "0909090946",
"rating": "4.67"
}

3. Car:
{
"vin": "1ZVHT82H485113456",
"make": "Liberty",
"colour": "Grey",
"convertible": "false",
"rating": "4.23",
"license_plate": "50E-23122",
"rpk": "2.5",
"having_driver": "false",
"available": "false"
}

**IMPORTANT: HAVE THE DRIVER SELECT A CAR BEFORE CREATING BOOKINGS OR CAR WILL BE UNAVAILABLE**

**DO NOT ATTEMPT TO CREATE INVOICES AND BOOKING USING BASIC POST REQUESTS AS THIS WOULD POSE LINKAGE ISSUES**