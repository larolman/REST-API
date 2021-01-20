ALTER table restaurante ADD aberto tinyint(1) not null;
UPDATE restaurante set aberto = FALSE;