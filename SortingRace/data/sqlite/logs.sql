BEGIN TRANSACTION;
DROP TABLE IF EXISTS `SIMULATION`;
CREATE TABLE IF NOT EXISTS `SIMULATION` (
	`id`        INTEGER PRIMARY KEY AUTOINCREMENT,
	`timestamp`	TEXT NOT NULL,
	`sort_type`          TEXT NOT NULL,
	`max_size`	INTEGER NOT NULL);
INSERT INTO `SIMULATION` values (null,2022-05-19 225825.72,QuickSort, 2000000);
INSERT INTO `SIMULATION` values (null,2022-05-19 225834.815,HeapSort, 1000000);
COMMIT;