INSERT INTO `vagas_db`.`calcadas`
(`numero`,
`cep`,
`rua`,
`latitude`,
`longitude`,
`user_id`)
VALUES
(5001,
1245,
'Av. dos Estados',
-23.6441964,
-46.5306079,
1);

INSERT INTO `vagas_db`.`calcadas`
(`numero`,
`cep`,
`rua`,
`latitude`,
`longitude`,
`user_id`)
VALUES
(246,
1245,
'R. dos Aliados',
-23.6421226,
-46.5296987,
2);


INSERT INTO `vagas_db`.`calcadas`
(`numero`,
`cep`,
`rua`,
`latitude`,
`longitude`,
`user_id`)
VALUES
(20,
1245,
'R. Bari',
-23.6221699,
-46.5352677,
3);


INSERT into horarios
(EVENT_DAY,
start_time,
end_time,
calcada_id) VALUES
("MONDAY",
"150000",
"160000",
1);

INSERT into horarios
(EVENT_DAY,
start_time,
end_time,
calcada_id) VALUES
("MONDAY",
"180000",
"190000",
1);

INSERT into horarios
(EVENT_DAY,
start_time,
end_time,
calcada_id) VALUES
("TUESDAY",
"090000",
"080000",
1);

INSERT into horarios
(EVENT_DAY,
start_time,
end_time,
calcada_id) VALUES
("FRIDAY",
"130000",
"140000",
2);
