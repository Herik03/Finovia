CREATE SEQUENCE IF NOT EXISTS wertpapier_seq START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS kurs_seq START WITH 1500 INCREMENT BY 1;

ALTER SEQUENCE kurs_seq RESTART WITH 1500;