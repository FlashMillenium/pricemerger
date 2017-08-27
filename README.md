# Pricemerger
This project merge lists of old Prices with new Prices.

Every element of Price have this field:
- id in Database
- product code
- price number (one price can have several prices)
- number of departmen where this price operates
- date begin 
- date end
- value of the price expressed in the smallest currency

So field of Price class look's like:
```java
long id;
String product_code;
int number;
int depart;
Date begin;
Date end;
long value;
```
__In one time only one price can be active on certain product, price number and department number__

Rule for merge:
- if old list don't have price for this product, or period of the prices don't collide, then add new price for product
- if collision detected, resolve this on this rule:
	* if price value for product is the same, expand the period
	* if price value differ, create new price and shrink period of the old price

Example of merge:

Price having:

|Product\_code|Number | Depart |       Begin       |        End        |   value  |
|:-----------:|:-----:|:------:|:-----------------:|:-----------------:|:--------:|
|122856       |1      |    1   |01.01.2013 00:00:00|31.01.2013 23:59:59| 11000    |
|122856       |2      |    1   |10.01.2013 00:00:00|20.01.2013 23:59:59| 99000    |
|6654         |1      |    2   |01.01.2013 00:00:00|31.01.2013 00:00:00| 5000     |

New price:

|Product\_code|Number | Depart |       Begin       |        End        |   value  |
|:-----------:|:-----:|:------:|:-----------------:|:-----------------:|:--------:|
|122856       |1      |    1   |20.01.2013 00:00:00|20.02.2013 23:59:59| 11000    |
|122856       |2      |    1   |15.01.2013 00:00:00|25.01.2013 23:59:59| 92000    |
|6654         |1      |    2   |12.01.2013 00:00:00|13.01.2013 00:00:00| 4000     |

Result:

|Product\_code|Number | Depart |       Begin       |        End        |   value  |
|:-----------:|:-----:|:------:|:-----------------:|:-----------------:|:--------:|
|122856       |1      |    1   |01.01.2013 00:00:00|20.02.2013 23:59:59| 11000    |
|122856       |2      |    1   |10.01.2013 00:00:00|15.01.2013 00:00:00| 99000    |
|122856       |2      |    1   |15.01.2013 00:00:00|25.01.2013 23:59:59| 92000    |
|6654         |1      |    2   |01.01.2013 00:00:00|12.01.2013 00:00:00|  5000    |
|6654         |1      |    2   |12.01.2013 00:00:00|13.01.2013 00:00:00|  4000    |
|6654         |1      |    2   |13.01.2013 00:00:00|31.01.2013 00:00:00|  5000    |
