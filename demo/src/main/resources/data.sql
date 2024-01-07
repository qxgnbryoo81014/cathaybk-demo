DROP TABLE currency;

-- 建立currency資料表
CREATE TABLE currency (
    currency_code CHAR(3) PRIMARY KEY,
    currency_name VARCHAR(50)
);

-- 插入幣別資料
INSERT INTO currency (currency_code, currency_name) VALUES ('USD', '美元');
INSERT INTO currency (currency_code, currency_name) VALUES ('EUR', '歐元');
INSERT INTO currency (currency_code, currency_name) VALUES ('JPY', '日圓');
INSERT INTO currency (currency_code, currency_name) VALUES ('GBP', '英鎊');
INSERT INTO currency (currency_code, currency_name) VALUES ('AUD', '澳幣');
INSERT INTO currency (currency_code, currency_name) VALUES ('CAD', '加幣');
INSERT INTO currency (currency_code, currency_name) VALUES ('CHF', '瑞士法郎');
INSERT INTO currency (currency_code, currency_name) VALUES ('CNY', '人民幣');
INSERT INTO currency (currency_code, currency_name) VALUES ('HKD', '港幣');
INSERT INTO currency (currency_code, currency_name) VALUES ('NZD', '紐幣');
INSERT INTO currency (currency_code, currency_name) VALUES ('SGD', '新加坡幣');
INSERT INTO currency (currency_code, currency_name) VALUES ('KRW', '韓元');
INSERT INTO currency (currency_code, currency_name) VALUES ('SEK', '瑞典克朗');
