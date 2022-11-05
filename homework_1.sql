CREATE TABLE department (id SERIAL PRIMARY KEY, 
						 name VARCHAR(100), 
						 isProfit BOOLEAN);
						 
INSERT INTO department (name, isProfit) VALUES ('Бухгалтерия', 'f');
INSERT INTO department (name, isProfit) VALUES ('Кредитный отдел', 't');
INSERT INTO department (name, isProfit) VALUES ('Отдел продаж', 't');
INSERT INTO department (name, isProfit) VALUES ('Правление', 'f');

CREATE TABLE employee (id SERIAL PRIMARY KEY, 
					   full_name VARCHAR(100), 
					   salary INT, 
					   department_id INT REFERENCES department (id));

INSERT INTO employee (full_name, salary, department_id) 
VALUES ('Петров Иван', 30000, 3);
INSERT INTO employee (full_name, salary, department_id) 
VALUES ('Иванова Наталья', 50000, 1);
INSERT INTO employee (full_name, salary, department_id)
VALUES ('Мирских Петр', 100000, 4);
INSERT INTO employee (full_name, salary, department_id) 
VALUES ('Улюкаев Владимир', 200000, 4);
INSERT INTO employee (full_name, salary, department_id) 
VALUES ('Заморский Виктор', 70000, 2);

SELECT *
FROM employee
WHERE employee.department_id = 4;

SELECT SUM(salary) as Суммарная_зарплата
FROM employee;

SELECT e.full_name, d.isProfit
FROM employee as e inner join department as d on e.department_id = d.id;

SELECT *
FROM employee as e
WHERE e.salary BETWEEN 10000 AND 100000;

DELETE FROM employee
WHERE full_name = 'Мирских Петр';

UPDATE department SET name = 'Депозитный отдел', isProfit = 'f'
WHERE id = 2;

SELECT *
FROM employee
WHERE LOWER (full_name) LIKE '%иван%';

SELECT d.name, AVG (e.salary)
FROM employee as e inner join department as d on e.department_id = d.id
GROUP BY d.name;

DROP TABLE department, employee;