### Problem Description

#### Write a program in Java to figure out when the last customer will leave the bar. Goal is to get customers out of the restaurant as fast as possible given the constraints.

 - No if-statements or for-loops are allowed
 - The goal is to abuse the strategy pattern and functional programming concepts to death to find a happy medium of when they’re useful

A bar is defined as the number of seats available, and the time the bar closes.  A customer is defined by the time they arrive at the bar, how long they will sit down for, and their behavior on whether or not they sit down at the bar. To keep things simple, time will be represented as an integer. The bar opens at 0, and then closes at the specified time.



#### Customer behavior
##### Applies to all customers
If the customer arrives > the restaurant closing time, they cannot sit down. If there is an open seat for the customer, they will sit down.

##### Refuse line customer
If there are any people waiting in line to sit down when this person enters the bar, they will leave.

##### Short lines customer
If there are <= 3 people in line, this person will stick around.

##### Turnover customer
At the time the customers enter the bar, this customer looks at the max time remaining for the customers sitting down. If the max(remaining time of sitting customers) <= 5 they will sit down.


#### Timing details
Actions happen instantly. For example, if a customer arrives at a completely open bar at time 0, they will be sitting down as of time zero. If a customer arrives at time 0, sits down for 3 time units, someone else can occupy the seat starting at time 3.

Also, this is arbitrary, but to make sure everyone is making the same assumptions, the order of operations is
 1.  Customers leave the bar
 2.  In line customers sit down
 3.  Incoming customers assess whether or not they’ll stay

For example, if 
 1. customer A is leaving the bar at time 3
 2. customer B is already in line and therefore gets seated at time 3
 3. customer C arrives at time 3

Then customer C waits until A leaves, and B is seated before evaluating whether or not to get in line.


#### What about people arriving at the same time?
“Refuse lines” customers get in line and are seated before “Short lines” customers. “Short lines” customers before “Turnover” customers. If two customers of the same type arrive at the same time, the customer with the shorter seating time gets seated first. If customers of the same type and same seating time arrive at the same time, just pick one.

When customers arrive at the same time, they get served in order. For example, if all the seats at the bar are full and there is no line, and a “refuse lines” and “short lines” customer arrive at the same time, the “short lines” customer assess the bar as if the “refuse lines” is already in line.


#### Examples

##### #1 Working out the timing details
Bar: 1 seat, closes at 10
Customers:
 1. Arrives at 0, Sits for 3, Refuses lines
 2. Arrives at 3, Sits for 4, Refuses lines
 3. Arrives at 7, Sits for 2, Refuses lines
 4. Arrives at 9, Sits for 2, Refuses lines

```
0-----1-----2-----3-----4-----5-----6-----7-----8-----9-----10-----11
|----- #1---------|
                  |------------#2----------|
                                           |----#3-----|
                                                       |------#4-----|
```

Output: 11



##### #2 Customers arriving at the same time
Bar: 1 seat, closes at 10
Customers:
 1.  Arrives at 0, Sits for 3, Turnover
 2.  Arrives at 0, Sits for 2, Short lines
 3.  Arrives at 0, Sits for 6, Refuse lines
 4.  Arrives at 0, Sits for 2, Refuse Lines

```
0-----1-----2-----3-----4-----5-----6-----7-----8-----9-----10-----11
|----- #3---------------------------|
                                    |---#4------|
                                                |----#2-----|
```

Customer #4 leaves because customer #3 is sitting for more than 5 time units.
Output: 10


##### #3 The bar closes early
Bar: 1 seats, closes at 7
Customers:
  1.  Arrives at 0, Sits for 3, Turnover

Output: 3




