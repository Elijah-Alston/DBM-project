
Page
2
of 2
DBMS Project
This project is geared to giving students practice in the analysis, design and implementation steps of the
database development process. Teams of 2-3 are allowed.
Suppose there is a company that supports swimming teams for youth. The company provides coaches for
participating swimmers from the surrounding communities. Different kinds of services such as private
lessons, classes, practices, meet supports, etc., are provided. Currently, there are 15 coaches and around
450 swimmers in 10 levels of competency and competition. Please help the company build the database to
support a small portion of these functions in a drastically simplified way. Below are the steps for helping
the company build the database.
1. Create a database called swimmingorg in PostgreSQL.
2. There is a file called CreateTables.sql.txt. Please create the required tables in database
swimmingorg based on the format/requirements specified in the file CreateTables.sql.txt.
3. There is a file called “Data.sql.txt”. Please populate the database swimmingorg using the data in
Data.sql.txt.
4. Write a Java program (e.g., PostgresqlSwimmingorg) to allow users to obtain information about
the data in the database swimmingorg (Note students are not allowed to obtain the data from the
database by directly writing the SQL statements and executing them in the terminal. Students must
write a program, e.g., java program to talk with the database swimmingorg and query the data in
the program, e.g., java program).
Submission: Each team needs to submit the source code, final report, and presentation slides to Canvas.
The final report must be formatted in a reasonable manner (Please include the information of your team
member in your submission).
Hints (explanations) about the program:
The program has 4 choices, and it allow user to enter the choice number (1, 2, 3, 4). Then the program will
execute the corresponding part based on the use’s choice. The following session illustrates the output
requirements.
Java JDBC PostgreSQL swimmingorg
Connected to PostgreSQL database!
Reading car records...
Please enter your choice:
1. Names and phones of all swimmers currently in level (of id) 3:
2. Name(s) of caretakers who are the primary (main) caretakers of at least two
swimmers:
3. Names of all caretakers who have volunteered for the task 'Recording' but not the
task 'Officiating':
4. quit
>1
Your result (The following is the sample format)
Names and phones of all swimmers currently in level (of id) 3:
fname | lname | phone
XXXXX | XXXXX | XXX-XXX-XXX
Please enter your choice:
1. Names and phones of all swimmers currently in level (of id) 3:
2. Name(s) of caretakers who are the primary (main) caretakers of at least two
swimmers:
3. Names of all caretakers who have volunteered for the task 'Recording' but not the
task 'Officiating':
4. quit
>2
Your result (The following is the sample format)
Name(s) of caretakers who are the primary (main) caretakers of at least two swimmers:
fname | phone
XXXXX | XXX-XXX-XXX
Please enter your choice:
1. Names and phones of all swimmers currently in level (of id) 3:
2. Name(s) of caretakers who are the primary (main) caretakers of at least two
swimmers:
3. Names of all caretakers who have volunteered for the task 'Recording' but not the
task 'Officiating':
4. quit
>3
Your result (The following is the sample format)
Names of all caretakers who have volunteered for the task 'Recording' but not the
task 'Officiating':
fname | lname
XXXXX | XXXxx
Please enter your choice:
1. Names and phones of all swimmers currently in level (of id) 3:
2. Name(s) of caretakers who are the primary (main) caretakers of at least two
swimmers:
3. Names of all caretakers who have volunteered for the task 'Recording' but not the
task 'Officiating':
4. quit
>4
Your result (The following is the sample format)
Exit
