CREATE SEQUENCE clientsCT START 1;
CREATE SEQUENCE casesCT START 1;

CREATE TABLE bank_accounts(
                              PRIMARY KEY (bank_account_id),
                              bank_account_id INT UNIQUE NOT NULL CHECK ( bank_account_id >= 0 ),
                              bank_account_balance INT NOT NULL
);

CREATE TABLE firms(
                      PRIMARY KEY (firm_id),
                      FOREIGN KEY (firm_bank_account_id) REFERENCES bank_accounts(bank_account_id)  ON DELETE NO ACTION,
                      firm_id INT UNIQUE NOT NULL CHECK ( firm_id >= 0 ),
                      firm_name VARCHAR(100) NOT NULL,
                      firm_address VARCHAR(100) NOT NULL,
                      firm_chairman_id INT UNIQUE NOT NULL,
                      firm_profit INT NOT NULL,
                      firm_bank_account_id INT UNIQUE NOT NULL
);

CREATE TABLE lawyers(
                        PRIMARY KEY (lawyer_id),
                        FOREIGN KEY (lawyer_firm_id) REFERENCES firms(firm_id) ON DELETE NO ACTION,
                        lawyer_id INT UNIQUE NOT NULL CHECK ( lawyer_id >= 0 ),
                        lawyer_name VARCHAR(100) NOT NULL,
                        lawyer_age INT NOT NULL CHECK ( lawyer_age >= 18 ),
                        lawyer_firm_id INT NOT NULL
);

CREATE TABLE clients(
                        PRIMARY KEY (client_id),
                        client_id INT UNIQUE NOT NULL CHECK ( client_id >= 0 ),
                        client_name VARCHAR(100) NOT NULL,
                        client_age INT NOT NULL CHECK ( client_age >= 18 )
);

CREATE TABLE cases(
                      PRIMARY KEY (case_id),
                      FOREIGN KEY (case_lawyer_id) REFERENCES lawyers(lawyer_id)  ON DELETE NO ACTION,
                      case_id INT UNIQUE NOT NULL CHECK ( case_id >= 0 ),
                      case_name VARCHAR(100) UNIQUE NOT NULL,
                      case_lawyer_id INT NOT NULL,
                      case_cost INT NOT NULL,
                      case_status BOOLEAN NOT NULL
);

CREATE TABLE transactions(
                             PRIMARY KEY (transaction_id),
                             FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(bank_account_id)  ON DELETE NO ACTION,
                             FOREIGN KEY (client_id) REFERENCES clients(client_id)  ON DELETE NO ACTION,
                             FOREIGN KEY (case_id) REFERENCES cases(case_id)  ON DELETE NO ACTION,
                             transaction_id INT UNIQUE NOT NULL CHECK ( transaction_id >= 0 ),
                             amount INT NOT NULL CHECK ( amount > 0 ),
                             bank_account_id INT NOT NULL,
                             client_id INT NOT NULL,
                             case_id INT NOT NULL
);


CREATE TABLE clients_cases(
                      PRIMARY KEY (client_id, case_id),
                      FOREIGN KEY (client_id) REFERENCES clients(client_id)  ON DELETE NO ACTION,
                      FOREIGN KEY (case_id) REFERENCES cases(case_id)  ON DELETE NO ACTION,
                      client_id INT NOT NULL CHECK ( client_id >= 0 ),
                      case_id INT NOT NULL CHECK ( case_id >= 0 )
);

INSERT INTO bank_accounts (bank_account_id, bank_account_balance) VALUES
(1, 200000),
(100, 750000),
(200, 390000),
(15, 140000),
(330, 90000);

INSERT INTO firms (firm_id, firm_name, firm_address, firm_chairman_id, firm_profit, firm_bank_account_id) VALUES
(1, 'Novosibirsk Law Firm', 'Red Avenue 22', 1, 350000, 1),
(2, 'Moscow Law Firm', 'Bolshaya Lubyanka 3', 51, 1800000, 100),
(3, 'Saint Petersburg Law Board', 'Nevsky Avenue 34', 121, 920000, 200),
(4, 'Tomsk Law Firm', 'Sovetskaya Street 12', 31, 220000, 15),
(5, 'Vladivostok Law College', 'Gogolya Street 37', 91, 190000, 330);

INSERT INTO lawyers (lawyer_id, lawyer_name, lawyer_age, lawyer_firm_id) VALUES
(1, 'Sokolov Ivan', 53, 1),
(2, 'Samoylov Artem', 27, 1),
(3, 'Bulgakov Alexey', 44, 1),
(4, 'Kirsanova Olga', 36, 1),
(5, 'Krupina Inga', 40, 1),

(51, 'Palkin Vladimir', 61, 2),
(52, 'Maksimov Petr', 45, 2),
(53, 'Krylov Evgeniy', 31, 2),
(54, 'Kotsubenko Zhanna', 49, 2),
(55, 'Volodina Ekaterina', 29, 2),

(121, 'Krutoy Lavrenti', 68, 3),
(122, 'Papanov Bogdan', 33, 3),
(123, 'Lobova Beatrisa', 47, 3),
(124, 'Dyrbova Inga', 70, 3),
(125, 'Koycheva Madgalina', 39,  3),

(31, 'Sukhorukov Pavel', 63, 4),

(91, 'Lomova Tatiana', 42, 5);

INSERT INTO clients (client_id, client_name, client_age) VALUES
(nextval('clientsCT'), 'Komkov Valery', 45),
(nextval('clientsCT'), 'Leonova Daria', 24),
(nextval('clientsCT'), 'Stravinsky Georgiy', 71),
(nextval('clientsCT'), 'Stravinskaya Julia', 69),
(nextval('clientsCT'), 'Golovachyova Elena', 31);

INSERT INTO cases (case_id, case_name, case_lawyer_id, case_cost, case_status) VALUES
(nextval('casesCT'), 'Fraud', 3, 3000, true),
(nextval('casesCT'), 'Theft', 4, 5000, true),
(nextval('casesCT'), 'Divorce', 1, 12000, true),
(nextval('casesCT'), 'Trespass', 5, 22000, false),
(nextval('casesCT'), 'Bribery', 5, 30000, true);

INSERT INTO clients_cases (client_id, case_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 3),
(5, 4),
(5, 5);

INSERT INTO transactions VALUES
(0, 1000, 1, 1, 1),
(1, 5000, 1, 1, 1),
(2, 25000, 1, 2, 2);

SELECT case_id, lawyer_name -- ��� ������ ��� ����������� ����
FROM cases JOIN lawyers ON case_lawyer_id = lawyer_id
WHERE case_id = 1;

SELECT lawyers.lawyer_name, COUNT(case_lawyer_id) as cases -- ���������� ��� � ������
FROM (cases JOIN lawyers ON case_lawyer_id = lawyer_id)
GROUP BY lawyers.lawyer_name
ORDER BY cases DESC;

SELECT case_id, COUNT(client_id) as participants -- ���������� ���������� � ����
FROM clients_cases
GROUP BY  case_id
ORDER BY participants DESC;

SELECT firm_name, COUNT(case_id) -- ���������� ��� � ��.�����
FROM (cases JOIN lawyers ON case_lawyer_id = lawyer_id JOIN firms ON lawyer_firm_id = firm_id)
GROUP BY firm_name;

SELECT client_name, client_age  -- �������-����������
FROM clients
WHERE client_age > 65
ORDER BY client_age DESC;

SELECT firm_name, lawyer_name -- ����� ������������� ���� ����
FROM (firms JOIN lawyers ON firm_chairman_id = lawyer_id)
ORDER BY lawyer_age DESC;

SELECT firm_name, bank_account_balance -- ������ � ����� ��� ����
FROM (firms INNER JOIN bank_accounts ON firm_bank_account_id = bank_account_id)
ORDER BY bank_account_balance DESC;

SELECT lawyer_name, cases.case_id, case_cost, SUM(amount) as Payed -- ������������ ����
FROM (transactions JOIN cases ON cases.case_id = transactions.case_id JOIN lawyers ON case_lawyer_id = lawyer_id)
GROUP BY lawyer_name, cases.case_id, case_cost
HAVING case_cost - SUM(amount) > 0;

SELECT client_name, SUM(amount) -- ����� ���� ����� ��� ��������
FROM (transactions JOIN clients ON transactions.client_id = clients.client_id)
GROUP BY clients.client_id;

SELECT bank_accounts.bank_account_id, COUNT(transaction_id) -- ���������� ���������� �� ���������� �����
FROM transactions JOIN bank_accounts ON transactions.bank_account_id = bank_accounts.bank_account_id
GROUP BY bank_accounts.bank_account_id;

SELECT * FROM cases WHERE case_cost > (SELECT AVG(case_cost) FROM cases);
-------------------------------------------------------------------------------------

SELECT from_price, max_price, count(case_name) FROM ( --outer with generate
    SELECT from_price, from_price + 5000 AS max_price
    FROM generate_series (0, 20000, 5000) from_price) AS t
    LEFT JOIN cases ON (cases.case_cost >= from_price) AND (case_cost <= max_price)
GROUP BY from_price, max_price;

SELECT lawyer_name, lawyer_age, case_name, case_cost FROM lawyers --cross
CROSS JOIN cases WHERE case_cost > lawyer_age * 1000
ORDER BY lawyer_id;

SELECT * FROM transactions;
SELECT * FROM clients_cases;
SELECT * FROM bank_accounts;
SELECT * FROM lawyers;
SELECT * FROM firms;
SELECT * FROM cases;
SELECT * FROM clients;


DROP SEQUENCE casesCT;
DROP SEQUENCE clientsCT;

DROP TABLE transactions;
DROP TABLE clients_cases;
DROP TABLE cases;
DROP TABLE lawyers;
DROP TABLE firms;
DROP TABLE clients;
DROP TABLE bank_accounts;