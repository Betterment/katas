School Attendance List
======================

In order to keep track of all the students in a particular school, the principal asked the teachers in charge of grades 1-3 to produce a list of students in their respective classes. Unfortunately the teachers all produced lists of students in different formats, one used pipe delimited, one used comma delimited, and another one used space delimited as follows:

Teacher for grade 1 (pipe)

`LastName | FirstName | MiddleInitial | Gender | FavoriteColor | DateOfBirth`

Teacher for grade 2 (comma)

`LastName, FirstName, Gender, FavoriteColor, DateOfBirth`

Teacher for grade 3 (space)

`LastName FirstName MiddleInitial Gender DateOfBirth FavoriteColor`

The principal would like to have a program that can read all the 3 above files and produce the output:

`LastName FirstName Gender Birthdate Grade FavoriteColor`

In the following 3 types of orderings
Output 1 – sorted by gender (females before males) then by last name ascending.
Output 2 – sorted by birth date, ascending.
Output 3 – sorted by last name, descending.
