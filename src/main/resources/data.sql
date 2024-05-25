insert into users(u_id, role, total_correct, user_id, user_name, user_pass) values (
	1,
	"ROLE_ADMIN",
	0,
	"41",
	"Admin_Ahnaf",
	"$2a$10$QAr7wwW52Pdiei6WYPbU6OPz0QcTVxBS4vLuOGxDPJTrrsFIjYmQe"
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	1, 
	'What is a correct syntax to output "Hello World" in Java?',  
	'echo "Hello World"',
	'printf("Hello World")',
	'System.out.println("Hello World")',
	3,
	-1,
	1
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	2, 
	'Java is short for "JavaScript."',  
	'True',
	'False',
	'Partially True',
	2,
	-1,
	1
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	3, 
	'How do you insert COMMENTS in Java code?',  
	'# This is a comment',
	'// This is a comment',
	'/* This is a comment',
	2,
	-1,
	1
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	4, 
	'Which data type is used to create a variable that should store text?',  
	'String',
	'Char',
	'Both',
	1,
	-1,
	1
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	5, 
	'How do you create a variable with the numeric value 5?',  
	'num x = 5',
	'float x = 5',
	'int x = 5',
	3,
	-1,
	1
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	6, 
	'How do you create a variable with the floating number 2.8?',  
	'num x = 2.8',
	'float x = 2.8',
	'int x = 2.8',
	2,
	-1,
	1
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	7, 
	'Which method can be used to find the length of a string?',  
	'getSize()',
	'length()',
	'size()',
	2,
	-1,
	1
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	8, 
	'Which operator is used to add together two values?',  
	'&&',
	'.add()',
	'+',
	3,
	-1,
	1
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	9, 
	'The value of a string variable can be surrounded by single quotes.',  
	'True',
	'False',
	'Maybe',
	2,
	-1,
	1
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	10, 
	'Which operator can be used to compare two values?',  
	'><',
	'&|',
	'==',
	3,
	-1,
	1
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	11, 
	'Which of the following is the correct syntax to add the header file in the C++ program?',  
	'#include<userdefined>',
	'#include "userdefined.h"',
	'Both',
	3,
	-1,
	2
);

insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	12, 
	'Which of the following is not a type of Constructor in C++?',  
	'Default constructor',
	'Parameterized constructor',
	'Friend constructor',
	3,
	-1,
	2
);
insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	13, 
	'Which of the following is the address operator?',  
	'&',
	'@',
	'*',
	1,
	-1,
	2
);
insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	14, 
	'C++ is a ___ type of language.',  
	'Low-level language',
	'Middle-level language',
	'High-level Language',
	2,
	-1,
	2
);
insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	15, 
	'Which of the following is the correct syntax for declaring the array?',  
	'int array[5];',
	'Array[5];',
	'init array []',
	1,
	-1,
	2
);
insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	16, 
	'Which type of memory is used by an Array in C++ programming language?',  
	'None-contiguous',
	'Contiguous',
	'Both 1 and 2',
	2,
	-1,
	2
);
insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	17, 
	'Which of the following is not an OOPS concept?',  
	'Encapsulation',
	'Exception',
	'Abstraction',
	2,
	-1,
	3
);
insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	18, 
	'Which feature of OOPS described the reusability of code?',  
	'Encapsulation',
	'Abstraction',
	'Inheritance',
	3,
	-1,
	3
);
insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	19, 
	'Which among the following feature is not in the general definition of OOPS?',  
	'Duplicate or Redundant Data',
	'Code reusability',
	'Modularity',
	1,
	-1,
	3
);
insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	20, 
	'Which function best describe the concept of polymorphism in programming languages?',  
	'Class member function',
	'Virtual function',
	'Inline function',
	2,
	-1,
	3
);
insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	21, 
	'Which function best describe the concept of polymorphism in programming languages?',  
	'Static function',
	'Virtual function',
	'Friend function',
	3,
	-1,
	3
);
insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	22, 
	'Which of the following OOP concept binds the code and data together and keeps them secure from the outside world?',  
	'Polymorphism',
	'Encapsulation',
	'Abstraction',
	2,
	-1,
	3
);
insert into questions(ques_id, title, optionA, optionB, optionC, ans, chosen, test_id)
values(
	23, 
	'Which member of the superclass is never accessible to the subclass?',  
	'Public member',
	'Protected member',
	'Private member',
	3,
	-1,
	3
);

insert into quiz(quiz_id, quiz_description, test_id) values (
	1,
	"This quiz covers a broad range of topics, from basic syntax and concepts to advanced features and techniques, ensuring a comprehensive assessment of your Java programming skills.",
	1
);
insert into quiz(quiz_id, quiz_description, test_id) values (
	2,
	"This quiz covers a broad range of topics, from basic syntax and concepts to advanced features and techniques, ensuring a comprehensive assessment of your C++ programming skills.",
	1
);

insert into dashboard(dashboard_id, ques_id, quiz_id) values(
	1,
	10,
	1
);

insert into test(test_id, test_name, test_description) values(
	1,
	"Java Programming",
	"This quiz covers a broad range of topics, from basic syntax and concepts to advanced features and techniques, ensuring a comprehensive assessment of your Java programming skills."
);
insert into test(test_id, test_name, test_description) values(
	2,
	"C++ Programming",
	"This quiz covers a broad range of topics, from basic syntax and concepts to advanced features and techniques, ensuring a comprehensive assessment of your C++ programming skills."
);
insert into test(test_id, test_name, test_description) values(
	3,
	"Object Oriented Programming",
	"Evaluate your understanding of OOP concepts, principles, and application in programming."
);