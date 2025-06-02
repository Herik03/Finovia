DELETE FROM ausschuettung;
DELETE FROM transaktion;
DELETE FROM kurs;

DELETE FROM etf;
DELETE FROM anleihe;
DELETE FROM aktie;

DELETE FROM wertpapier;

INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (1, 'Shanghai Composite ETF 1', 'ETF001');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (1, 'Emittent1', 'Shanghai Composite');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (2, 'Nikkei 225 ETF 2', 'ETF002');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (2, 'Emittent2', 'Nikkei 225');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (3, 'Nikkei 225 ETF 3', 'ETF003');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (3, 'Emittent3', 'Nikkei 225');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (4, 'Hang Seng ETF 4', 'ETF004');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (4, 'Emittent4', 'Hang Seng');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (5, 'Hang Seng ETF 5', 'ETF005');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (5, 'Emittent5', 'Hang Seng');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (6, 'Shanghai Composite ETF 6', 'ETF006');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (6, 'Emittent6', 'Shanghai Composite');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (7, 'S&P 500 ETF 7', 'ETF007');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (7, 'Emittent7', 'S&P 500');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (8, 'EURO STOXX 50 ETF 8', 'ETF008');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (8, 'Emittent8', 'EURO STOXX 50');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (9, 'EURO STOXX 50 ETF 9', 'ETF009');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (9, 'Emittent9', 'EURO STOXX 50');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (10, 'FTSE 100 ETF 10', 'ETF010');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (10, 'Emittent10', 'FTSE 100');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (11, 'FTSE 100 ETF 11', 'ETF011');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (11, 'Emittent11', 'FTSE 100');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (12, 'FTSE 100 ETF 12', 'ETF012');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (12, 'Emittent12', 'FTSE 100');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (13, 'MSCI World ETF 13', 'ETF013');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (13, 'Emittent13', 'MSCI World');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (14, 'S&P 500 ETF 14', 'ETF014');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (14, 'Emittent14', 'S&P 500');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (15, 'S&P 500 ETF 15', 'ETF015');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (15, 'Emittent15', 'S&P 500');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (16, 'EURO STOXX 50 ETF 16', 'ETF016');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (16, 'Emittent16', 'EURO STOXX 50');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (17, 'Hang Seng ETF 17', 'ETF017');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (17, 'Emittent17', 'Hang Seng');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (18, 'NASDAQ 100 ETF 18', 'ETF018');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (18, 'Emittent18', 'NASDAQ 100');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (19, 'NASDAQ 100 ETF 19', 'ETF019');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (19, 'Emittent19', 'NASDAQ 100');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (20, 'Shanghai Composite ETF 20', 'ETF020');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (20, 'Emittent20', 'Shanghai Composite');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (21, 'DAX 40 ETF 21', 'ETF021');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (21, 'Emittent21', 'DAX 40');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (22, 'DAX 40 ETF 22', 'ETF022');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (22, 'Emittent22', 'DAX 40');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (23, 'DAX 40 ETF 23', 'ETF023');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (23, 'Emittent23', 'DAX 40');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (24, 'FTSE 100 ETF 24', 'ETF024');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (24, 'Emittent24', 'FTSE 100');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (25, 'Shanghai Composite ETF 25', 'ETF025');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (25, 'Emittent25', 'Shanghai Composite');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (26, 'MSCI World ETF 26', 'ETF026');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (26, 'Emittent26', 'MSCI World');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (27, 'FTSE 100 ETF 27', 'ETF027');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (27, 'Emittent27', 'FTSE 100');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (28, 'Russell 2000 ETF 28', 'ETF028');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (28, 'Emittent28', 'Russell 2000');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (29, 'Hang Seng ETF 29', 'ETF029');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (29, 'Emittent29', 'Hang Seng');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (30, 'Hang Seng ETF 30', 'ETF030');
INSERT INTO etf (wertpapier_id, emittent, index) VALUES (30, 'Emittent30', 'Hang Seng');
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (31, 'Staatsanleihe 2025', 'BND001');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (31, 'Staat1', 3.36, '2032-05-26', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (32, 'Staatsanleihe 2026', 'BND002');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (32, 'Staat2', 4.14, '2032-05-26', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (33, 'Staatsanleihe 2027', 'BND003');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (33, 'Staat3', 4.47, '2035-05-26', 1000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (34, 'Staatsanleihe 2028', 'BND004');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (34, 'Staat4', 1.92, '2027-05-28', 1000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (35, 'Staatsanleihe 2029', 'BND005');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (35, 'Staat5', 2.52, '2032-05-26', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (36, 'Staatsanleihe 2030', 'BND006');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (36, 'Staat1', 1.03, '2029-05-27', 500);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (37, 'Staatsanleihe 2031', 'BND007');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (37, 'Staat2', 3.5, '2035-05-26', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (38, 'Staatsanleihe 2032', 'BND008');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (38, 'Staat3', 1.77, '2034-05-26', 1000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (39, 'Staatsanleihe 2033', 'BND009');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (39, 'Staat4', 3.02, '2035-05-26', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (40, 'Staatsanleihe 2034', 'BND010');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (40, 'Staat5', 2.42, '2027-05-28', 1000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (41, 'Staatsanleihe 2025', 'BND011');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (41, 'Staat1', 3.48, '2034-05-26', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (42, 'Staatsanleihe 2026', 'BND012');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (42, 'Staat2', 4.26, '2034-05-26', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (43, 'Staatsanleihe 2027', 'BND013');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (43, 'Staat3', 2.43, '2034-05-26', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (44, 'Staatsanleihe 2028', 'BND014');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (44, 'Staat4', 3.56, '2029-05-27', 1000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (45, 'Staatsanleihe 2029', 'BND015');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (45, 'Staat5', 3.75, '2035-05-26', 1000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (46, 'Staatsanleihe 2030', 'BND016');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (46, 'Staat1', 3.21, '2026-05-28', 500);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (47, 'Staatsanleihe 2031', 'BND017');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (47, 'Staat2', 4.95, '2027-05-28', 500);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (48, 'Staatsanleihe 2032', 'BND018');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (48, 'Staat3', 3.2, '2027-05-28', 500);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (49, 'Staatsanleihe 2033', 'BND019');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (49, 'Staat4', 3.3, '2033-05-26', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (50, 'Staatsanleihe 2034', 'BND020');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (50, 'Staat5', 2.19, '2034-05-26', 1000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (51, 'Staatsanleihe 2025', 'BND021');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (51, 'Staat1', 3.95, '2033-05-26', 1000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (52, 'Staatsanleihe 2026', 'BND022');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (52, 'Staat2', 3.61, '2032-05-26', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (53, 'Staatsanleihe 2027', 'BND023');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (53, 'Staat3', 4.24, '2027-05-28', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (54, 'Staatsanleihe 2028', 'BND024');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (54, 'Staat4', 1.67, '2027-05-28', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (55, 'Staatsanleihe 2029', 'BND025');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (55, 'Staat5', 2.72, '2031-05-27', 500);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (56, 'Staatsanleihe 2030', 'BND026');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (56, 'Staat1', 2.35, '2026-05-28', 500);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (57, 'Staatsanleihe 2031', 'BND027');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (57, 'Staat2', 0.99, '2030-05-27', 1000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (58, 'Staatsanleihe 2032', 'BND028');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (58, 'Staat3', 4.27, '2035-05-26', 1000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (59, 'Staatsanleihe 2033', 'BND029');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (59, 'Staat4', 0.92, '2030-05-27', 2000);
INSERT INTO wertpapier (wertpapier_id, name, symbol) VALUES (60, 'Staatsanleihe 2034', 'BND030');
INSERT INTO anleihe (wertpapier_id, emittent, kupon, laufzeit, nennwert) VALUES (60, 'Staat5', 3.18, '2029-05-27', 500);

-- Sequenzzähler anpassen
SELECT setval('wertpapier_seq', (SELECT MAX(wertpapier_id) FROM wertpapier));

--Kursdaten
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low, symbol) VALUES
                                                                                                     (1, 1, '2025-04-01 00:00:00', 107.55, 107.81, 108.09, 107.06, 'ETF001'),
                                                                                                     (2, 1, '2025-04-02 00:00:00', 101.17, 99.53, 101.3, 99.39, 'ETF001'),
                                                                                                     (3, 1, '2025-04-03 00:00:00', 101.7, 100.15, 102.0, 99.56, 'ETF001'),
                                                                                                     (4, 1, '2025-04-04 00:00:00', 90.06, 90.52, 90.76, 89.63, 'ETF001'),
                                                                                                     (5, 1, '2025-04-05 00:00:00', 91.31, 91.07, 91.51, 90.43, 'ETF001'),
                                                                                                     (6, 1, '2025-04-06 00:00:00', 103.47, 101.98, 104.17, 101.39, 'ETF001'),
                                                                                                     (7, 1, '2025-04-07 00:00:00', 94.62, 96.12, 96.82, 94.11, 'ETF001'),
                                                                                                     (8, 1, '2025-04-08 00:00:00', 93.08, 92.47, 94.01, 92.09, 'ETF001'),
                                                                                                     (9, 1, '2025-04-09 00:00:00', 92.67, 93.6, 93.74, 92.31, 'ETF001'),
                                                                                                     (10, 1, '2025-04-10 00:00:00', 103.62, 102.31, 104.16, 102.09, 'ETF001'),
                                                                                                     (11, 1, '2025-04-11 00:00:00', 108.13, 109.89, 110.15, 107.57, 'ETF001'),
                                                                                                     (12, 1, '2025-04-12 00:00:00', 98.78, 99.53, 99.7, 97.93, 'ETF001'),
                                                                                                     (13, 1, '2025-04-13 00:00:00', 98.48, 98.16, 98.97, 97.63, 'ETF001'),
                                                                                                     (14, 1, '2025-04-14 00:00:00', 108.52, 110.36, 111.0, 107.88, 'ETF001'),
                                                                                                     (15, 1, '2025-04-15 00:00:00', 93.72, 93.47, 93.84, 92.98, 'ETF001'),
                                                                                                     (16, 1, '2025-04-16 00:00:00', 100.79, 102.18, 102.93, 100.27, 'ETF001'),
                                                                                                     (17, 1, '2025-04-17 00:00:00', 90.68, 91.01, 92.01, 89.95, 'ETF001'),
                                                                                                     (18, 1, '2025-04-18 00:00:00', 93.9, 92.83, 94.22, 92.37, 'ETF001'),
                                                                                                     (19, 1, '2025-04-19 00:00:00', 90.33, 90.03, 91.09, 89.79, 'ETF001'),
                                                                                                     (20, 1, '2025-04-20 00:00:00', 106.58, 106.93, 107.21, 105.78, 'ETF001'),
                                                                                                     (21, 2, '2025-04-01 00:00:00', 96.7, 97.14, 97.56, 96.01, 'ETF002'),
                                                                                                     (22, 2, '2025-04-02 00:00:00', 104.7, 103.7, 105.49, 103.5, 'ETF002'),
                                                                                                     (23, 2, '2025-04-03 00:00:00', 105.45, 107.27, 107.41, 104.49, 'ETF002'),
                                                                                                     (24, 2, '2025-04-04 00:00:00', 108.85, 110.21, 110.42, 108.62, 'ETF002'),
                                                                                                     (25, 2, '2025-04-05 00:00:00', 105.82, 107.41, 108.07, 105.32, 'ETF002'),
                                                                                                     (26, 2, '2025-04-06 00:00:00', 100.28, 101.7, 102.23, 99.42, 'ETF002'),
                                                                                                     (27, 2, '2025-04-07 00:00:00', 97.24, 96.22, 98.12, 95.48, 'ETF002'),
                                                                                                     (28, 2, '2025-04-08 00:00:00', 91.51, 91.88, 91.95, 91.02, 'ETF002'),
                                                                                                     (29, 2, '2025-04-09 00:00:00', 106.93, 107.4, 107.79, 106.62, 'ETF002'),
                                                                                                     (30, 2, '2025-04-10 00:00:00', 95.99, 97.95, 98.87, 95.84, 'ETF002'),
                                                                                                     (31, 2, '2025-04-11 00:00:00', 107.16, 108.42, 109.2, 106.79, 'ETF002'),
                                                                                                     (32, 2, '2025-04-12 00:00:00', 100.2, 98.65, 100.32, 97.71, 'ETF002'),
                                                                                                     (33, 2, '2025-04-13 00:00:00', 105.55, 107.44, 108.04, 104.83, 'ETF002'),
                                                                                                     (34, 2, '2025-04-14 00:00:00', 92.22, 92.9, 92.95, 92.2, 'ETF002'),
                                                                                                     (35, 2, '2025-04-15 00:00:00', 95.28, 95.71, 95.94, 94.71, 'ETF002'),
                                                                                                     (36, 2, '2025-04-16 00:00:00', 105.48, 104.51, 105.56, 103.7, 'ETF002'),
                                                                                                     (37, 2, '2025-04-17 00:00:00', 92.47, 93.12, 93.99, 92.04, 'ETF002'),
                                                                                                     (38, 2, '2025-04-18 00:00:00', 100.61, 100.93, 101.28, 99.96, 'ETF002'),
                                                                                                     (39, 2, '2025-04-19 00:00:00', 94.17, 94.19, 94.91, 93.93, 'ETF002'),
                                                                                                     (40, 2, '2025-04-20 00:00:00', 96.92, 98.16, 98.55, 96.38, 'ETF002'),
                                                                                                     (41, 3, '2025-04-01 00:00:00', 95.25, 95.93, 96.23, 94.99, 'ETF003'),
                                                                                                     (42, 3, '2025-04-02 00:00:00', 102.77, 101.3, 102.92, 101.01, 'ETF003'),
                                                                                                     (43, 3, '2025-04-03 00:00:00', 95.32, 95.81, 96.27, 95.18, 'ETF003'),
                                                                                                     (44, 3, '2025-04-04 00:00:00', 96.48, 97.8, 97.99, 95.73, 'ETF003'),
                                                                                                     (45, 3, '2025-04-05 00:00:00', 101.42, 102.92, 102.96, 101.34, 'ETF003'),
                                                                                                     (46, 3, '2025-04-06 00:00:00', 93.57, 94.38, 94.44, 93.03, 'ETF003'),
                                                                                                     (47, 3, '2025-04-07 00:00:00', 107.03, 106.25, 107.64, 105.7, 'ETF003'),
                                                                                                     (48, 3, '2025-04-08 00:00:00', 91.99, 92.55, 92.72, 91.16, 'ETF003'),
                                                                                                     (49, 3, '2025-04-09 00:00:00', 90.7, 92.4, 92.56, 90.36, 'ETF003'),
                                                                                                     (50, 3, '2025-04-10 00:00:00', 109.43, 109.24, 109.94, 109.11, 'ETF003'),
                                                                                                     (51, 3, '2025-04-11 00:00:00', 106.56, 105.88, 107.42, 105.43, 'ETF003'),
                                                                                                     (52, 3, '2025-04-12 00:00:00', 100.71, 101.39, 102.01, 100.6, 'ETF003'),
                                                                                                     (53, 3, '2025-04-13 00:00:00', 90.51, 90.0, 91.04, 89.19, 'ETF003'),
                                                                                                     (54, 3, '2025-04-14 00:00:00', 98.0, 99.19, 99.64, 97.79, 'ETF003'),
                                                                                                     (55, 3, '2025-04-15 00:00:00', 107.18, 108.7, 109.1, 106.74, 'ETF003'),
                                                                                                     (56, 3, '2025-04-16 00:00:00', 101.03, 102.83, 103.43, 100.33, 'ETF003'),
                                                                                                     (57, 3, '2025-04-17 00:00:00', 105.09, 106.58, 107.13, 104.93, 'ETF003'),
                                                                                                     (58, 3, '2025-04-18 00:00:00', 90.44, 89.68, 90.98, 88.98, 'ETF003'),
                                                                                                     (59, 3, '2025-04-19 00:00:00', 93.49, 94.9, 95.84, 92.68, 'ETF003'),
                                                                                                     (60, 3, '2025-04-20 00:00:00', 92.22, 93.72, 94.68, 91.64, 'ETF003'),
                                                                                                     (61, 4, '2025-04-01 00:00:00', 98.32, 97.22, 98.64, 96.91, 'ETF004'),
                                                                                                     (62, 4, '2025-04-02 00:00:00', 91.52, 92.0, 92.28, 90.99, 'ETF004'),
                                                                                                     (63, 4, '2025-04-03 00:00:00', 92.4, 92.85, 93.03, 91.59, 'ETF004'),
                                                                                                     (64, 4, '2025-04-04 00:00:00', 92.27, 93.02, 93.08, 92.01, 'ETF004'),
                                                                                                     (65, 4, '2025-04-05 00:00:00', 100.85, 101.19, 101.55, 100.1, 'ETF004'),
                                                                                                     (66, 4, '2025-04-06 00:00:00', 101.26, 99.29, 102.1, 98.63, 'ETF004'),
                                                                                                     (67, 4, '2025-04-07 00:00:00', 99.11, 97.56, 99.12, 96.62, 'ETF004'),
                                                                                                     (68, 4, '2025-04-08 00:00:00', 102.86, 103.09, 103.19, 102.53, 'ETF004'),
                                                                                                     (69, 4, '2025-04-09 00:00:00', 95.93, 97.34, 98.16, 95.18, 'ETF004'),
                                                                                                     (70, 4, '2025-04-10 00:00:00', 109.17, 107.6, 110.11, 106.77, 'ETF004'),
                                                                                                     (71, 4, '2025-04-11 00:00:00', 106.85, 107.79, 108.1, 106.2, 'ETF004'),
                                                                                                     (72, 4, '2025-04-12 00:00:00', 94.01, 93.35, 94.41, 92.61, 'ETF004'),
                                                                                                     (73, 4, '2025-04-13 00:00:00', 102.72, 104.55, 105.03, 102.23, 'ETF004'),
                                                                                                     (74, 4, '2025-04-14 00:00:00', 102.9, 104.37, 105.15, 102.5, 'ETF004'),
                                                                                                     (75, 4, '2025-04-15 00:00:00', 101.64, 102.56, 103.24, 101.02, 'ETF004'),
                                                                                                     (76, 4, '2025-04-16 00:00:00', 101.29, 99.67, 101.5, 99.02, 'ETF004'),
                                                                                                     (77, 4, '2025-04-17 00:00:00', 90.7, 89.53, 90.81, 89.34, 'ETF004'),
                                                                                                     (78, 4, '2025-04-18 00:00:00', 103.72, 102.14, 103.96, 101.27, 'ETF004'),
                                                                                                     (79, 4, '2025-04-19 00:00:00', 95.57, 95.45, 95.63, 94.74, 'ETF004'),
                                                                                                     (80, 4, '2025-04-20 00:00:00', 108.36, 106.85, 108.76, 106.0, 'ETF004'),
                                                                                                     (81, 5, '2025-04-01 00:00:00', 104.56, 104.72, 105.71, 104.16, 'ETF005'),
                                                                                                     (82, 5, '2025-04-02 00:00:00', 98.15, 98.92, 99.09, 97.97, 'ETF005'),
                                                                                                     (83, 5, '2025-04-03 00:00:00', 99.43, 100.58, 101.11, 99.17, 'ETF005'),
                                                                                                     (84, 5, '2025-04-04 00:00:00', 108.12, 109.4, 109.77, 107.23, 'ETF005'),
                                                                                                     (85, 5, '2025-04-05 00:00:00', 91.55, 89.87, 92.13, 89.25, 'ETF005'),
                                                                                                     (86, 5, '2025-04-06 00:00:00', 100.03, 99.11, 100.2, 98.19, 'ETF005'),
                                                                                                     (87, 5, '2025-04-07 00:00:00', 94.72, 95.16, 95.68, 94.22, 'ETF005'),
                                                                                                     (88, 5, '2025-04-08 00:00:00', 107.47, 108.97, 109.12, 106.7, 'ETF005'),
                                                                                                     (89, 5, '2025-04-09 00:00:00', 92.37, 91.43, 93.36, 90.5, 'ETF005'),
                                                                                                     (90, 5, '2025-04-10 00:00:00', 104.53, 105.16, 106.01, 104.24, 'ETF005'),
                                                                                                     (91, 5, '2025-04-11 00:00:00', 102.79, 101.88, 103.22, 101.78, 'ETF005'),
                                                                                                     (92, 5, '2025-04-12 00:00:00', 91.53, 90.53, 92.5, 89.87, 'ETF005'),
                                                                                                     (93, 5, '2025-04-13 00:00:00', 103.53, 103.44, 103.86, 103.1, 'ETF005'),
                                                                                                     (94, 5, '2025-04-14 00:00:00', 92.71, 93.07, 93.31, 92.25, 'ETF005'),
                                                                                                     (95, 5, '2025-04-15 00:00:00', 104.88, 106.88, 107.05, 104.51, 'ETF005'),
                                                                                                     (96, 5, '2025-04-16 00:00:00', 90.98, 90.31, 91.8, 89.97, 'ETF005'),
                                                                                                     (97, 5, '2025-04-17 00:00:00', 109.47, 110.71, 111.61, 109.13, 'ETF005'),
                                                                                                     (98, 5, '2025-04-18 00:00:00', 91.99, 90.59, 92.69, 89.71, 'ETF005'),
                                                                                                     (99, 5, '2025-04-19 00:00:00', 92.9, 92.55, 92.99, 91.57, 'ETF005'),
                                                                                                     (100, 5, '2025-04-20 00:00:00', 90.98, 92.72, 92.92, 89.98, 'ETF005'),
                                                                                                     (101, 6, '2025-04-01 00:00:00', 95.78, 97.34, 97.42, 95.54, 'ETF006'),
                                                                                                     (102, 6, '2025-04-02 00:00:00', 102.54, 102.84, 103.22, 102.08, 'ETF006'),
                                                                                                     (103, 6, '2025-04-03 00:00:00', 94.11, 92.42, 94.73, 91.47, 'ETF006'),
                                                                                                     (104, 6, '2025-04-04 00:00:00', 95.82, 94.81, 96.67, 94.1, 'ETF006'),
                                                                                                     (105, 6, '2025-04-05 00:00:00', 97.35, 95.55, 98.22, 94.64, 'ETF006'),
                                                                                                     (106, 6, '2025-04-06 00:00:00', 107.32, 107.52, 107.82, 106.74, 'ETF006'),
                                                                                                     (107, 6, '2025-04-07 00:00:00', 104.15, 103.88, 104.49, 103.15, 'ETF006'),
                                                                                                     (108, 6, '2025-04-08 00:00:00', 93.91, 95.23, 95.73, 93.55, 'ETF006'),
                                                                                                     (109, 6, '2025-04-09 00:00:00', 105.3, 105.51, 106.0, 104.33, 'ETF006'),
                                                                                                     (110, 6, '2025-04-10 00:00:00', 90.41, 88.73, 90.79, 88.4, 'ETF006'),
                                                                                                     (111, 6, '2025-04-11 00:00:00', 106.2, 107.23, 108.2, 105.5, 'ETF006'),
                                                                                                     (112, 6, '2025-04-12 00:00:00', 93.22, 92.37, 93.34, 91.46, 'ETF006'),
                                                                                                     (113, 6, '2025-04-13 00:00:00', 95.9, 97.88, 98.41, 95.71, 'ETF006'),
                                                                                                     (114, 6, '2025-04-14 00:00:00', 102.09, 103.64, 104.16, 102.09, 'ETF006'),
                                                                                                     (115, 6, '2025-04-15 00:00:00', 102.87, 104.18, 104.54, 102.26, 'ETF006'),
                                                                                                     (116, 6, '2025-04-16 00:00:00', 93.48, 93.15, 93.7, 92.59, 'ETF006'),
                                                                                                     (117, 6, '2025-04-17 00:00:00', 107.54, 106.44, 107.8, 106.12, 'ETF006'),
                                                                                                     (118, 6, '2025-04-18 00:00:00', 103.48, 102.17, 103.7, 101.55, 'ETF006'),
                                                                                                     (119, 6, '2025-04-19 00:00:00', 97.29, 97.27, 98.23, 97.25, 'ETF006'),
                                                                                                     (120, 6, '2025-04-20 00:00:00', 92.27, 93.81, 94.62, 91.87, 'ETF006'),
                                                                                                     (121, 7, '2025-04-01 00:00:00', 108.57, 110.18, 110.42, 108.46, 'ETF007'),
                                                                                                     (122, 7, '2025-04-02 00:00:00', 90.27, 88.3, 90.59, 87.6, 'ETF007'),
                                                                                                     (123, 7, '2025-04-03 00:00:00', 100.74, 100.15, 101.16, 99.72, 'ETF007'),
                                                                                                     (124, 7, '2025-04-04 00:00:00', 104.76, 103.05, 105.03, 102.82, 'ETF007'),
                                                                                                     (125, 7, '2025-04-05 00:00:00', 109.12, 108.99, 109.88, 108.02, 'ETF007'),
                                                                                                     (126, 7, '2025-04-06 00:00:00', 95.0, 93.48, 95.81, 92.95, 'ETF007'),
                                                                                                     (127, 7, '2025-04-07 00:00:00', 108.07, 106.96, 108.3, 106.07, 'ETF007'),
                                                                                                     (128, 7, '2025-04-08 00:00:00', 104.04, 104.43, 105.23, 103.88, 'ETF007'),
                                                                                                     (129, 7, '2025-04-09 00:00:00', 93.72, 93.81, 94.37, 93.03, 'ETF007'),
                                                                                                     (130, 7, '2025-04-10 00:00:00', 94.01, 93.84, 94.34, 93.28, 'ETF007'),
                                                                                                     (131, 7, '2025-04-11 00:00:00', 109.59, 108.29, 110.04, 107.82, 'ETF007'),
                                                                                                     (132, 7, '2025-04-12 00:00:00', 97.62, 96.69, 98.06, 96.69, 'ETF007'),
                                                                                                     (133, 7, '2025-04-13 00:00:00', 107.0, 107.66, 108.39, 106.12, 'ETF007'),
                                                                                                     (134, 7, '2025-04-14 00:00:00', 98.68, 100.22, 100.44, 97.91, 'ETF007'),
                                                                                                     (135, 7, '2025-04-15 00:00:00', 102.71, 103.47, 103.49, 101.82, 'ETF007'),
                                                                                                     (136, 7, '2025-04-16 00:00:00', 90.63, 90.88, 91.4, 90.2, 'ETF007'),
                                                                                                     (137, 7, '2025-04-17 00:00:00', 108.55, 109.86, 109.96, 108.25, 'ETF007'),
                                                                                                     (138, 7, '2025-04-18 00:00:00', 95.52, 96.88, 97.77, 95.45, 'ETF007'),
                                                                                                     (139, 7, '2025-04-19 00:00:00', 102.42, 100.82, 102.86, 100.49, 'ETF007'),
                                                                                                     (140, 7, '2025-04-20 00:00:00', 95.19, 93.44, 95.67, 93.15, 'ETF007'),
                                                                                                     (141, 8, '2025-04-01 00:00:00', 97.43, 96.74, 97.83, 96.69, 'ETF008'),
                                                                                                     (142, 8, '2025-04-02 00:00:00', 90.16, 90.1, 90.44, 89.62, 'ETF008'),
                                                                                                     (143, 8, '2025-04-03 00:00:00', 103.89, 103.62, 104.29, 102.9, 'ETF008'),
                                                                                                     (144, 8, '2025-04-04 00:00:00', 107.87, 107.31, 108.27, 107.17, 'ETF008'),
                                                                                                     (145, 8, '2025-04-05 00:00:00', 98.66, 97.2, 99.31, 96.55, 'ETF008'),
                                                                                                     (146, 8, '2025-04-06 00:00:00', 101.41, 102.62, 103.2, 101.25, 'ETF008'),
                                                                                                     (147, 8, '2025-04-07 00:00:00', 93.38, 94.27, 94.6, 92.51, 'ETF008'),
                                                                                                     (148, 8, '2025-04-08 00:00:00', 105.48, 106.65, 107.3, 104.72, 'ETF008'),
                                                                                                     (149, 8, '2025-04-09 00:00:00', 99.77, 97.9, 100.28, 96.94, 'ETF008'),
                                                                                                     (150, 8, '2025-04-10 00:00:00', 94.9, 95.17, 95.62, 94.34, 'ETF008'),
                                                                                                     (151, 8, '2025-04-11 00:00:00', 100.05, 101.44, 101.78, 99.64, 'ETF008'),
                                                                                                     (152, 8, '2025-04-12 00:00:00', 109.14, 110.92, 111.43, 108.68, 'ETF008'),
                                                                                                     (153, 8, '2025-04-13 00:00:00', 93.62, 92.27, 94.54, 91.43, 'ETF008'),
                                                                                                     (154, 8, '2025-04-14 00:00:00', 103.58, 101.91, 103.84, 101.54, 'ETF008'),
                                                                                                     (155, 8, '2025-04-15 00:00:00', 90.6, 92.39, 92.57, 90.27, 'ETF008'),
                                                                                                     (156, 8, '2025-04-16 00:00:00', 95.13, 95.1, 96.06, 95.04, 'ETF008'),
                                                                                                     (157, 8, '2025-04-17 00:00:00', 102.88, 103.03, 103.3, 102.03, 'ETF008'),
                                                                                                     (158, 8, '2025-04-18 00:00:00', 100.55, 101.82, 102.31, 100.41, 'ETF008'),
                                                                                                     (159, 8, '2025-04-19 00:00:00', 97.44, 96.6, 97.89, 96.11, 'ETF008'),
                                                                                                     (160, 8, '2025-04-20 00:00:00', 98.03, 98.87, 99.51, 97.84, 'ETF008'),
                                                                                                     (161, 9, '2025-04-01 00:00:00', 100.54, 99.91, 100.58, 99.41, 'ETF009'),
                                                                                                     (162, 9, '2025-04-02 00:00:00', 93.45, 91.79, 94.15, 91.63, 'ETF009'),
                                                                                                     (163, 9, '2025-04-03 00:00:00', 109.09, 110.59, 111.2, 109.07, 'ETF009'),
                                                                                                     (164, 9, '2025-04-04 00:00:00', 90.2, 88.49, 90.47, 88.09, 'ETF009'),
                                                                                                     (165, 9, '2025-04-05 00:00:00', 94.23, 92.85, 94.87, 92.49, 'ETF009'),
                                                                                                     (166, 9, '2025-04-06 00:00:00', 108.97, 108.4, 109.46, 108.2, 'ETF009'),
                                                                                                     (167, 9, '2025-04-07 00:00:00', 105.99, 105.86, 106.79, 105.24, 'ETF009'),
                                                                                                     (168, 9, '2025-04-08 00:00:00', 104.34, 103.36, 104.44, 103.24, 'ETF009'),
                                                                                                     (169, 9, '2025-04-09 00:00:00', 97.05, 95.33, 97.22, 95.12, 'ETF009'),
                                                                                                     (170, 9, '2025-04-10 00:00:00', 97.69, 97.39, 97.72, 96.61, 'ETF009'),
                                                                                                     (171, 9, '2025-04-11 00:00:00', 109.45, 110.11, 110.44, 109.27, 'ETF009'),
                                                                                                     (172, 9, '2025-04-12 00:00:00', 93.49, 95.0, 95.51, 93.05, 'ETF009'),
                                                                                                     (173, 9, '2025-04-13 00:00:00', 98.53, 98.29, 99.17, 98.1, 'ETF009'),
                                                                                                     (174, 9, '2025-04-14 00:00:00', 98.01, 99.32, 99.78, 97.85, 'ETF009'),
                                                                                                     (175, 9, '2025-04-15 00:00:00', 107.95, 109.36, 109.44, 107.48, 'ETF009'),
                                                                                                     (176, 9, '2025-04-16 00:00:00', 106.36, 107.24, 107.28, 106.34, 'ETF009'),
                                                                                                     (177, 9, '2025-04-17 00:00:00', 105.54, 105.4, 106.41, 104.79, 'ETF009'),
                                                                                                     (178, 9, '2025-04-18 00:00:00', 93.36, 95.34, 95.73, 92.51, 'ETF009'),
                                                                                                     (179, 9, '2025-04-19 00:00:00', 105.85, 107.1, 107.46, 105.46, 'ETF009'),
                                                                                                     (180, 9, '2025-04-20 00:00:00', 96.67, 95.86, 97.15, 95.3, 'ETF009'),
                                                                                                     (181, 10, '2025-04-01 00:00:00', 100.86, 100.87, 101.51, 99.91, 'ETF010'),
                                                                                                     (182, 10, '2025-04-02 00:00:00', 104.58, 103.35, 105.17, 102.85, 'ETF010'),
                                                                                                     (183, 10, '2025-04-03 00:00:00', 98.15, 96.67, 98.9, 95.98, 'ETF010'),
                                                                                                     (184, 10, '2025-04-04 00:00:00', 109.05, 107.74, 109.57, 107.29, 'ETF010'),
                                                                                                     (185, 10, '2025-04-05 00:00:00', 102.49, 101.11, 103.33, 101.07, 'ETF010'),
                                                                                                     (186, 10, '2025-04-06 00:00:00', 107.12, 109.03, 109.2, 107.0, 'ETF010'),
                                                                                                     (187, 10, '2025-04-07 00:00:00', 102.39, 103.24, 103.45, 102.14, 'ETF010'),
                                                                                                     (188, 10, '2025-04-08 00:00:00', 94.05, 92.92, 94.44, 92.39, 'ETF010'),
                                                                                                     (189, 10, '2025-04-09 00:00:00', 92.38, 91.68, 92.96, 91.19, 'ETF010'),
                                                                                                     (190, 10, '2025-04-10 00:00:00', 101.13, 102.47, 103.01, 100.99, 'ETF010'),
                                                                                                     (191, 10, '2025-04-11 00:00:00', 93.36, 91.37, 94.2, 90.49, 'ETF010'),
                                                                                                     (192, 10, '2025-04-12 00:00:00', 93.53, 91.78, 93.58, 91.27, 'ETF010'),
                                                                                                     (193, 10, '2025-04-13 00:00:00', 103.01, 101.43, 103.04, 100.67, 'ETF010'),
                                                                                                     (194, 10, '2025-04-14 00:00:00', 109.67, 111.63, 111.83, 109.12, 'ETF010'),
                                                                                                     (195, 10, '2025-04-15 00:00:00', 91.5, 89.7, 91.64, 89.28, 'ETF010'),
                                                                                                     (196, 10, '2025-04-16 00:00:00', 93.62, 92.0, 93.94, 91.38, 'ETF010'),
                                                                                                     (197, 10, '2025-04-17 00:00:00', 102.32, 100.65, 102.84, 99.73, 'ETF010'),
                                                                                                     (198, 10, '2025-04-18 00:00:00', 93.43, 93.56, 93.83, 92.56, 'ETF010'),
                                                                                                     (199, 10, '2025-04-19 00:00:00', 105.29, 103.86, 105.36, 103.75, 'ETF010'),
                                                                                                     (200, 10, '2025-04-20 00:00:00', 102.77, 104.03, 104.99, 102.09, 'ETF010'),
                                                                                                     (201, 11, '2025-04-01 00:00:00', 105.89, 104.82, 106.77, 104.2, 'ETF011'),
                                                                                                     (202, 11, '2025-04-02 00:00:00', 91.28, 92.79, 93.51, 90.51, 'ETF011'),
                                                                                                     (203, 11, '2025-04-03 00:00:00', 105.6, 103.89, 105.97, 103.85, 'ETF011'),
                                                                                                     (204, 11, '2025-04-04 00:00:00', 104.26, 104.74, 105.68, 103.3, 'ETF011'),
                                                                                                     (205, 11, '2025-04-05 00:00:00', 93.0, 91.65, 93.64, 91.46, 'ETF011'),
                                                                                                     (206, 11, '2025-04-06 00:00:00', 101.34, 103.09, 103.14, 101.28, 'ETF011'),
                                                                                                     (207, 11, '2025-04-07 00:00:00', 96.79, 95.81, 97.09, 95.61, 'ETF011'),
                                                                                                     (208, 11, '2025-04-08 00:00:00', 95.62, 96.74, 96.84, 94.88, 'ETF011'),
                                                                                                     (209, 11, '2025-04-09 00:00:00', 91.49, 92.81, 93.03, 91.46, 'ETF011'),
                                                                                                     (210, 11, '2025-04-10 00:00:00', 94.15, 93.1, 94.73, 92.61, 'ETF011'),
                                                                                                     (211, 11, '2025-04-11 00:00:00', 99.57, 101.22, 102.18, 99.23, 'ETF011'),
                                                                                                     (212, 11, '2025-04-12 00:00:00', 95.65, 94.81, 96.28, 94.35, 'ETF011'),
                                                                                                     (213, 11, '2025-04-13 00:00:00', 103.65, 101.67, 104.13, 101.22, 'ETF011'),
                                                                                                     (214, 11, '2025-04-14 00:00:00', 102.11, 100.92, 102.39, 100.69, 'ETF011'),
                                                                                                     (215, 11, '2025-04-15 00:00:00', 96.69, 95.72, 96.97, 95.36, 'ETF011'),
                                                                                                     (216, 11, '2025-04-16 00:00:00', 107.25, 105.57, 107.8, 105.5, 'ETF011'),
                                                                                                     (217, 11, '2025-04-17 00:00:00', 97.63, 95.85, 98.09, 95.49, 'ETF011'),
                                                                                                     (218, 11, '2025-04-18 00:00:00', 104.97, 105.86, 106.6, 103.99, 'ETF011'),
                                                                                                     (219, 11, '2025-04-19 00:00:00', 107.96, 108.66, 109.62, 107.2, 'ETF011'),
                                                                                                     (220, 11, '2025-04-20 00:00:00', 99.1, 101.04, 101.85, 98.49, 'ETF011'),
                                                                                                     (221, 12, '2025-04-01 00:00:00', 90.98, 89.04, 91.92, 88.28, 'ETF012'),
                                                                                                     (222, 12, '2025-04-02 00:00:00', 105.19, 105.21, 105.68, 104.8, 'ETF012'),
                                                                                                     (223, 12, '2025-04-03 00:00:00', 91.27, 89.48, 91.9, 89.23, 'ETF012'),
                                                                                                     (224, 12, '2025-04-04 00:00:00', 98.32, 100.32, 101.07, 97.36, 'ETF012'),
                                                                                                     (225, 12, '2025-04-05 00:00:00', 102.64, 101.96, 102.73, 101.29, 'ETF012'),
                                                                                                     (226, 12, '2025-04-06 00:00:00', 102.94, 103.85, 104.4, 102.69, 'ETF012'),
                                                                                                     (227, 12, '2025-04-07 00:00:00', 98.04, 99.65, 99.76, 98.02, 'ETF012'),
                                                                                                     (228, 12, '2025-04-08 00:00:00', 95.48, 94.5, 95.83, 94.32, 'ETF012'),
                                                                                                     (229, 12, '2025-04-09 00:00:00', 102.41, 101.21, 103.28, 100.98, 'ETF012'),
                                                                                                     (230, 12, '2025-04-10 00:00:00', 105.27, 104.33, 105.89, 104.12, 'ETF012'),
                                                                                                     (231, 12, '2025-04-11 00:00:00', 90.37, 91.85, 91.88, 90.01, 'ETF012'),
                                                                                                     (232, 12, '2025-04-12 00:00:00', 108.28, 108.62, 108.97, 107.79, 'ETF012'),
                                                                                                     (233, 12, '2025-04-13 00:00:00', 106.72, 107.47, 107.65, 106.31, 'ETF012'),
                                                                                                     (234, 12, '2025-04-14 00:00:00', 105.6, 106.79, 107.31, 105.33, 'ETF012'),
                                                                                                     (235, 12, '2025-04-15 00:00:00', 107.62, 109.12, 110.09, 107.19, 'ETF012'),
                                                                                                     (236, 12, '2025-04-16 00:00:00', 91.78, 93.49, 94.06, 91.13, 'ETF012'),
                                                                                                     (237, 12, '2025-04-17 00:00:00', 97.93, 99.44, 100.15, 97.23, 'ETF012'),
                                                                                                     (238, 12, '2025-04-18 00:00:00', 101.66, 101.38, 102.02, 101.12, 'ETF012'),
                                                                                                     (239, 12, '2025-04-19 00:00:00', 99.65, 100.7, 101.68, 98.72, 'ETF012'),
                                                                                                     (240, 12, '2025-04-20 00:00:00', 109.93, 110.1, 110.76, 109.75, 'ETF012'),
                                                                                                     (241, 13, '2025-04-01 00:00:00', 96.51, 97.82, 98.23, 96.14, 'ETF013'),
                                                                                                     (242, 13, '2025-04-02 00:00:00', 93.67, 93.93, 94.22, 93.17, 'ETF013'),
                                                                                                     (243, 13, '2025-04-03 00:00:00', 104.1, 105.69, 105.85, 103.52, 'ETF013'),
                                                                                                     (244, 13, '2025-04-04 00:00:00', 95.68, 95.15, 96.16, 94.29, 'ETF013'),
                                                                                                     (245, 13, '2025-04-05 00:00:00', 102.27, 101.41, 102.56, 100.65, 'ETF013'),
                                                                                                     (246, 13, '2025-04-06 00:00:00', 91.07, 92.45, 92.49, 91.01, 'ETF013'),
                                                                                                     (247, 13, '2025-04-07 00:00:00', 96.34, 96.59, 96.79, 96.06, 'ETF013'),
                                                                                                     (248, 13, '2025-04-08 00:00:00', 107.42, 105.58, 108.03, 105.12, 'ETF013'),
                                                                                                     (249, 13, '2025-04-09 00:00:00', 92.55, 92.67, 93.4, 91.64, 'ETF013'),
                                                                                                     (250, 13, '2025-04-10 00:00:00', 97.9, 96.38, 98.02, 95.63, 'ETF013'),
                                                                                                     (251, 13, '2025-04-11 00:00:00', 95.88, 95.62, 95.91, 95.16, 'ETF013'),
                                                                                                     (252, 13, '2025-04-12 00:00:00', 99.04, 100.16, 101.09, 98.25, 'ETF013'),
                                                                                                     (253, 13, '2025-04-13 00:00:00', 103.31, 102.91, 104.11, 101.93, 'ETF013'),
                                                                                                     (254, 13, '2025-04-14 00:00:00', 99.85, 101.7, 102.1, 98.93, 'ETF013'),
                                                                                                     (255, 13, '2025-04-15 00:00:00', 102.4, 102.3, 102.46, 101.79, 'ETF013'),
                                                                                                     (256, 13, '2025-04-16 00:00:00', 101.42, 102.51, 102.8, 100.79, 'ETF013'),
                                                                                                     (257, 13, '2025-04-17 00:00:00', 107.36, 106.67, 107.98, 106.17, 'ETF013'),
                                                                                                     (258, 13, '2025-04-18 00:00:00', 93.35, 92.97, 93.35, 92.72, 'ETF013'),
                                                                                                     (259, 13, '2025-04-19 00:00:00', 93.9, 92.21, 94.65, 91.65, 'ETF013'),
                                                                                                     (260, 13, '2025-04-20 00:00:00', 92.64, 91.34, 93.36, 91.14, 'ETF013'),
                                                                                                     (261, 14, '2025-04-01 00:00:00', 107.84, 107.79, 108.65, 106.89, 'ETF014'),
                                                                                                     (262, 14, '2025-04-02 00:00:00', 90.92, 91.65, 92.47, 90.09, 'ETF014'),
                                                                                                     (263, 14, '2025-04-03 00:00:00', 103.72, 103.19, 103.83, 102.55, 'ETF014'),
                                                                                                     (264, 14, '2025-04-04 00:00:00', 94.05, 94.39, 94.75, 93.17, 'ETF014'),
                                                                                                     (265, 14, '2025-04-05 00:00:00', 104.87, 104.72, 105.58, 103.95, 'ETF014'),
                                                                                                     (266, 14, '2025-04-06 00:00:00', 97.12, 97.54, 97.58, 96.56, 'ETF014'),
                                                                                                     (267, 14, '2025-04-07 00:00:00', 93.9, 94.35, 94.78, 93.1, 'ETF014'),
                                                                                                     (268, 14, '2025-04-08 00:00:00', 95.16, 97.05, 97.51, 94.62, 'ETF014'),
                                                                                                     (269, 14, '2025-04-09 00:00:00', 106.6, 107.17, 107.67, 106.19, 'ETF014'),
                                                                                                     (270, 14, '2025-04-10 00:00:00', 92.07, 90.79, 92.45, 90.68, 'ETF014'),
                                                                                                     (271, 14, '2025-04-11 00:00:00', 100.24, 101.26, 101.77, 99.4, 'ETF014'),
                                                                                                     (272, 14, '2025-04-12 00:00:00', 90.52, 89.13, 90.8, 89.01, 'ETF014'),
                                                                                                     (273, 14, '2025-04-13 00:00:00', 93.02, 94.68, 95.04, 93.01, 'ETF014'),
                                                                                                     (274, 14, '2025-04-14 00:00:00', 100.23, 99.9, 101.19, 99.04, 'ETF014'),
                                                                                                     (275, 14, '2025-04-15 00:00:00', 90.18, 88.23, 90.64, 87.35, 'ETF014'),
                                                                                                     (276, 14, '2025-04-16 00:00:00', 95.18, 93.92, 95.44, 93.5, 'ETF014'),
                                                                                                     (277, 14, '2025-04-17 00:00:00', 97.46, 96.83, 98.32, 96.74, 'ETF014'),
                                                                                                     (278, 14, '2025-04-18 00:00:00', 98.36, 100.33, 101.23, 97.47, 'ETF014'),
                                                                                                     (279, 14, '2025-04-19 00:00:00', 103.8, 103.62, 103.95, 103.58, 'ETF014'),
                                                                                                     (280, 14, '2025-04-20 00:00:00', 94.88, 94.93, 95.7, 94.76, 'ETF014'),
                                                                                                     (281, 15, '2025-04-01 00:00:00', 103.55, 104.4, 104.47, 103.29, 'ETF015'),
                                                                                                     (282, 15, '2025-04-02 00:00:00', 108.52, 107.83, 108.62, 107.13, 'ETF015'),
                                                                                                     (283, 15, '2025-04-03 00:00:00', 98.63, 97.25, 99.42, 96.6, 'ETF015'),
                                                                                                     (284, 15, '2025-04-04 00:00:00', 97.11, 97.44, 97.98, 96.31, 'ETF015'),
                                                                                                     (285, 15, '2025-04-05 00:00:00', 91.97, 90.86, 92.72, 89.97, 'ETF015'),
                                                                                                     (286, 15, '2025-04-06 00:00:00', 90.55, 92.27, 92.61, 90.23, 'ETF015'),
                                                                                                     (287, 15, '2025-04-07 00:00:00', 97.04, 98.55, 98.67, 96.1, 'ETF015'),
                                                                                                     (288, 15, '2025-04-08 00:00:00', 107.11, 106.54, 107.23, 105.63, 'ETF015'),
                                                                                                     (289, 15, '2025-04-09 00:00:00', 95.02, 97.02, 97.34, 94.24, 'ETF015'),
                                                                                                     (290, 15, '2025-04-10 00:00:00', 95.55, 94.87, 95.61, 94.48, 'ETF015'),
                                                                                                     (291, 15, '2025-04-11 00:00:00', 90.88, 88.93, 91.01, 88.42, 'ETF015'),
                                                                                                     (292, 15, '2025-04-12 00:00:00', 104.51, 103.23, 105.11, 103.12, 'ETF015'),
                                                                                                     (293, 15, '2025-04-13 00:00:00', 103.86, 105.23, 105.49, 103.42, 'ETF015'),
                                                                                                     (294, 15, '2025-04-14 00:00:00', 107.84, 109.15, 109.22, 107.47, 'ETF015'),
                                                                                                     (295, 15, '2025-04-15 00:00:00', 109.62, 111.14, 111.45, 109.15, 'ETF015'),
                                                                                                     (296, 15, '2025-04-16 00:00:00', 94.6, 93.73, 95.33, 93.52, 'ETF015'),
                                                                                                     (297, 15, '2025-04-17 00:00:00', 100.96, 99.2, 101.01, 98.63, 'ETF015'),
                                                                                                     (298, 15, '2025-04-18 00:00:00', 102.36, 104.24, 105.12, 101.85, 'ETF015'),
                                                                                                     (299, 15, '2025-04-19 00:00:00', 106.4, 104.43, 107.18, 104.08, 'ETF015'),
                                                                                                     (300, 15, '2025-04-20 00:00:00', 96.7, 98.43, 98.83, 95.89, 'ETF015'),
                                                                                                     (301, 16, '2025-04-01 00:00:00', 107.15, 106.26, 107.52, 106.2, 'ETF016'),
                                                                                                     (302, 16, '2025-04-02 00:00:00', 106.35, 106.23, 107.11, 105.72, 'ETF016'),
                                                                                                     (303, 16, '2025-04-03 00:00:00', 100.55, 101.03, 101.6, 100.11, 'ETF016'),
                                                                                                     (304, 16, '2025-04-04 00:00:00', 107.58, 109.35, 109.93, 107.18, 'ETF016'),
                                                                                                     (305, 16, '2025-04-05 00:00:00', 93.32, 95.23, 95.31, 92.62, 'ETF016'),
                                                                                                     (306, 16, '2025-04-06 00:00:00', 96.86, 96.29, 97.68, 95.83, 'ETF016'),
                                                                                                     (307, 16, '2025-04-07 00:00:00', 101.58, 101.61, 101.85, 101.36, 'ETF016'),
                                                                                                     (308, 16, '2025-04-08 00:00:00', 94.01, 92.19, 94.46, 92.11, 'ETF016'),
                                                                                                     (309, 16, '2025-04-09 00:00:00', 104.75, 104.23, 105.52, 103.96, 'ETF016'),
                                                                                                     (310, 16, '2025-04-10 00:00:00', 103.2, 103.04, 103.29, 102.7, 'ETF016'),
                                                                                                     (311, 16, '2025-04-11 00:00:00', 108.13, 108.24, 108.62, 107.31, 'ETF016'),
                                                                                                     (312, 16, '2025-04-12 00:00:00', 105.98, 106.24, 106.32, 105.73, 'ETF016'),
                                                                                                     (313, 16, '2025-04-13 00:00:00', 93.48, 92.28, 94.39, 91.6, 'ETF016'),
                                                                                                     (314, 16, '2025-04-14 00:00:00', 102.71, 104.04, 104.41, 102.3, 'ETF016'),
                                                                                                     (315, 16, '2025-04-15 00:00:00', 106.25, 105.61, 106.63, 104.98, 'ETF016'),
                                                                                                     (316, 16, '2025-04-16 00:00:00', 105.92, 104.56, 106.11, 104.51, 'ETF016'),
                                                                                                     (317, 16, '2025-04-17 00:00:00', 90.02, 89.59, 90.57, 89.49, 'ETF016'),
                                                                                                     (318, 16, '2025-04-18 00:00:00', 104.43, 104.92, 105.42, 103.88, 'ETF016'),
                                                                                                     (319, 16, '2025-04-19 00:00:00', 96.11, 95.73, 96.48, 94.78, 'ETF016'),
                                                                                                     (320, 16, '2025-04-20 00:00:00', 104.7, 105.82, 106.59, 104.2, 'ETF016'),
                                                                                                     (321, 17, '2025-04-01 00:00:00', 97.27, 97.7, 98.41, 96.69, 'ETF017'),
                                                                                                     (322, 17, '2025-04-02 00:00:00', 109.46, 110.25, 110.51, 109.01, 'ETF017'),
                                                                                                     (323, 17, '2025-04-03 00:00:00', 93.06, 94.86, 94.9, 92.54, 'ETF017'),
                                                                                                     (324, 17, '2025-04-04 00:00:00', 98.51, 98.36, 98.85, 97.51, 'ETF017'),
                                                                                                     (325, 17, '2025-04-05 00:00:00', 102.41, 103.35, 103.91, 102.22, 'ETF017'),
                                                                                                     (326, 17, '2025-04-06 00:00:00', 95.0, 97.0, 97.48, 94.03, 'ETF017'),
                                                                                                     (327, 17, '2025-04-07 00:00:00', 105.99, 107.55, 108.1, 105.34, 'ETF017'),
                                                                                                     (328, 17, '2025-04-08 00:00:00', 93.68, 94.04, 94.88, 93.15, 'ETF017'),
                                                                                                     (329, 17, '2025-04-09 00:00:00', 103.2, 101.52, 103.51, 100.72, 'ETF017'),
                                                                                                     (330, 17, '2025-04-10 00:00:00', 108.66, 109.51, 110.19, 107.76, 'ETF017'),
                                                                                                     (331, 17, '2025-04-11 00:00:00', 94.92, 96.34, 97.34, 94.53, 'ETF017'),
                                                                                                     (332, 17, '2025-04-12 00:00:00', 99.67, 100.86, 101.66, 99.46, 'ETF017'),
                                                                                                     (333, 17, '2025-04-13 00:00:00', 109.72, 110.48, 110.58, 109.6, 'ETF017'),
                                                                                                     (334, 17, '2025-04-14 00:00:00', 108.38, 108.21, 108.91, 107.94, 'ETF017'),
                                                                                                     (335, 17, '2025-04-15 00:00:00', 99.78, 99.31, 99.81, 98.62, 'ETF017'),
                                                                                                     (336, 17, '2025-04-16 00:00:00', 107.09, 108.21, 109.17, 106.16, 'ETF017'),
                                                                                                     (337, 17, '2025-04-17 00:00:00', 104.63, 103.95, 105.11, 103.56, 'ETF017'),
                                                                                                     (338, 17, '2025-04-18 00:00:00', 101.28, 102.54, 103.38, 100.42, 'ETF017'),
                                                                                                     (339, 17, '2025-04-19 00:00:00', 91.19, 89.43, 91.89, 88.74, 'ETF017'),
                                                                                                     (340, 17, '2025-04-20 00:00:00', 94.58, 94.15, 94.78, 93.21, 'ETF017'),
                                                                                                     (341, 18, '2025-04-01 00:00:00', 97.46, 98.09, 99.07, 97.17, 'ETF018'),
                                                                                                     (342, 18, '2025-04-02 00:00:00', 96.43, 95.12, 96.66, 94.3, 'ETF018'),
                                                                                                     (343, 18, '2025-04-03 00:00:00', 106.39, 105.79, 107.03, 105.6, 'ETF018'),
                                                                                                     (344, 18, '2025-04-04 00:00:00', 94.56, 96.44, 96.85, 94.14, 'ETF018'),
                                                                                                     (345, 18, '2025-04-05 00:00:00', 105.25, 105.89, 106.64, 105.21, 'ETF018'),
                                                                                                     (346, 18, '2025-04-06 00:00:00', 99.4, 100.81, 101.75, 98.71, 'ETF018'),
                                                                                                     (347, 18, '2025-04-07 00:00:00', 97.39, 96.62, 97.55, 96.5, 'ETF018'),
                                                                                                     (348, 18, '2025-04-08 00:00:00', 104.72, 103.5, 105.06, 103.15, 'ETF018'),
                                                                                                     (349, 18, '2025-04-09 00:00:00', 97.54, 95.69, 97.84, 95.09, 'ETF018'),
                                                                                                     (350, 18, '2025-04-10 00:00:00', 105.71, 107.11, 107.11, 105.1, 'ETF018'),
                                                                                                     (351, 18, '2025-04-11 00:00:00', 90.79, 89.7, 91.1, 89.33, 'ETF018'),
                                                                                                     (352, 18, '2025-04-12 00:00:00', 101.84, 102.43, 102.7, 101.36, 'ETF018'),
                                                                                                     (353, 18, '2025-04-13 00:00:00', 92.84, 91.02, 93.47, 90.83, 'ETF018'),
                                                                                                     (354, 18, '2025-04-14 00:00:00', 90.93, 90.58, 91.2, 90.55, 'ETF018'),
                                                                                                     (355, 18, '2025-04-15 00:00:00', 106.98, 107.7, 108.56, 106.83, 'ETF018'),
                                                                                                     (356, 18, '2025-04-16 00:00:00', 107.51, 108.75, 109.24, 107.33, 'ETF018'),
                                                                                                     (357, 18, '2025-04-17 00:00:00', 98.12, 99.52, 100.25, 97.16, 'ETF018'),
                                                                                                     (358, 18, '2025-04-18 00:00:00', 103.22, 103.39, 103.57, 102.35, 'ETF018'),
                                                                                                     (359, 18, '2025-04-19 00:00:00', 107.78, 109.37, 109.51, 107.28, 'ETF018'),
                                                                                                     (360, 18, '2025-04-20 00:00:00', 106.74, 105.98, 107.48, 104.98, 'ETF018'),
                                                                                                     (361, 19, '2025-04-01 00:00:00', 103.44, 102.63, 103.73, 102.41, 'ETF019'),
                                                                                                     (362, 19, '2025-04-02 00:00:00', 95.28, 94.45, 95.86, 93.53, 'ETF019'),
                                                                                                     (363, 19, '2025-04-03 00:00:00', 105.48, 105.59, 106.19, 104.83, 'ETF019'),
                                                                                                     (364, 19, '2025-04-04 00:00:00', 93.5, 95.41, 95.93, 92.55, 'ETF019'),
                                                                                                     (365, 19, '2025-04-05 00:00:00', 97.29, 98.74, 99.21, 97.15, 'ETF019'),
                                                                                                     (366, 19, '2025-04-06 00:00:00', 96.91, 98.54, 98.9, 96.55, 'ETF019'),
                                                                                                     (367, 19, '2025-04-07 00:00:00', 96.32, 96.03, 96.45, 95.18, 'ETF019'),
                                                                                                     (368, 19, '2025-04-08 00:00:00', 91.64, 91.45, 92.47, 91.21, 'ETF019'),
                                                                                                     (369, 19, '2025-04-09 00:00:00', 96.97, 97.59, 98.29, 96.57, 'ETF019'),
                                                                                                     (370, 19, '2025-04-10 00:00:00', 94.45, 92.47, 95.21, 92.4, 'ETF019'),
                                                                                                     (371, 19, '2025-04-11 00:00:00', 103.68, 103.21, 104.67, 103.19, 'ETF019'),
                                                                                                     (372, 19, '2025-04-12 00:00:00', 101.28, 100.28, 101.46, 99.77, 'ETF019'),
                                                                                                     (373, 19, '2025-04-13 00:00:00', 103.46, 103.6, 103.65, 103.1, 'ETF019'),
                                                                                                     (374, 19, '2025-04-14 00:00:00', 90.68, 91.63, 92.05, 90.14, 'ETF019'),
                                                                                                     (375, 19, '2025-04-15 00:00:00', 105.21, 106.87, 107.04, 104.75, 'ETF019'),
                                                                                                     (376, 19, '2025-04-16 00:00:00', 96.8, 96.79, 96.87, 96.12, 'ETF019'),
                                                                                                     (377, 19, '2025-04-17 00:00:00', 109.73, 107.99, 109.82, 107.36, 'ETF019'),
                                                                                                     (378, 19, '2025-04-18 00:00:00', 105.78, 106.33, 106.95, 105.09, 'ETF019'),
                                                                                                     (379, 19, '2025-04-19 00:00:00', 101.64, 102.25, 102.99, 100.66, 'ETF019'),
                                                                                                     (380, 19, '2025-04-20 00:00:00', 96.74, 97.09, 97.97, 96.59, 'ETF019'),
                                                                                                     (381, 20, '2025-04-01 00:00:00', 91.07, 91.13, 91.89, 90.88, 'ETF020'),
                                                                                                     (382, 20, '2025-04-02 00:00:00', 91.51, 92.29, 92.52, 90.52, 'ETF020'),
                                                                                                     (383, 20, '2025-04-03 00:00:00', 90.74, 88.91, 91.32, 88.09, 'ETF020'),
                                                                                                     (384, 20, '2025-04-04 00:00:00', 99.87, 100.73, 100.87, 99.6, 'ETF020'),
                                                                                                     (385, 20, '2025-04-05 00:00:00', 95.92, 97.68, 98.47, 95.2, 'ETF020'),
                                                                                                     (386, 20, '2025-04-06 00:00:00', 100.2, 100.95, 101.5, 99.65, 'ETF020'),
                                                                                                     (387, 20, '2025-04-07 00:00:00', 96.65, 96.61, 97.34, 96.22, 'ETF020'),
                                                                                                     (388, 20, '2025-04-08 00:00:00', 97.72, 96.55, 98.69, 95.83, 'ETF020'),
                                                                                                     (389, 20, '2025-04-09 00:00:00', 106.42, 105.69, 107.41, 105.15, 'ETF020'),
                                                                                                     (390, 20, '2025-04-10 00:00:00', 95.78, 94.46, 96.44, 93.64, 'ETF020'),
                                                                                                     (391, 20, '2025-04-11 00:00:00', 107.68, 107.25, 108.15, 106.34, 'ETF020'),
                                                                                                     (392, 20, '2025-04-12 00:00:00', 94.47, 92.52, 94.69, 92.08, 'ETF020'),
                                                                                                     (393, 20, '2025-04-13 00:00:00', 96.97, 95.59, 97.91, 95.18, 'ETF020'),
                                                                                                     (394, 20, '2025-04-14 00:00:00', 92.09, 93.03, 93.11, 91.94, 'ETF020'),
                                                                                                     (395, 20, '2025-04-15 00:00:00', 101.64, 103.14, 104.1, 101.31, 'ETF020'),
                                                                                                     (396, 20, '2025-04-16 00:00:00', 91.19, 93.09, 93.49, 90.98, 'ETF020'),
                                                                                                     (397, 20, '2025-04-17 00:00:00', 93.55, 92.23, 94.09, 91.84, 'ETF020'),
                                                                                                     (398, 20, '2025-04-18 00:00:00', 104.46, 102.88, 104.9, 102.86, 'ETF020'),
                                                                                                     (399, 20, '2025-04-19 00:00:00', 97.95, 97.33, 98.21, 96.37, 'ETF020'),
                                                                                                     (400, 20, '2025-04-20 00:00:00', 106.63, 107.09, 108.05, 106.6, 'ETF020'),
                                                                                                     (401, 21, '2025-04-01 00:00:00', 90.67, 88.77, 91.48, 87.89, 'ETF021'),
                                                                                                     (402, 21, '2025-04-02 00:00:00', 108.64, 108.7, 109.65, 107.86, 'ETF021'),
                                                                                                     (403, 21, '2025-04-03 00:00:00', 93.43, 92.32, 93.82, 91.68, 'ETF021'),
                                                                                                     (404, 21, '2025-04-04 00:00:00', 104.0, 103.2, 104.3, 102.3, 'ETF021'),
                                                                                                     (405, 21, '2025-04-05 00:00:00', 109.51, 109.58, 110.07, 109.28, 'ETF021'),
                                                                                                     (406, 21, '2025-04-06 00:00:00', 109.42, 108.53, 109.55, 108.42, 'ETF021'),
                                                                                                     (407, 21, '2025-04-07 00:00:00', 104.67, 106.59, 107.27, 104.18, 'ETF021'),
                                                                                                     (408, 21, '2025-04-08 00:00:00', 98.1, 97.6, 98.71, 96.82, 'ETF021'),
                                                                                                     (409, 21, '2025-04-09 00:00:00', 91.93, 93.13, 93.46, 91.06, 'ETF021'),
                                                                                                     (410, 21, '2025-04-10 00:00:00', 106.44, 108.01, 108.78, 105.62, 'ETF021'),
                                                                                                     (411, 21, '2025-04-11 00:00:00', 92.03, 90.09, 92.6, 89.47, 'ETF021'),
                                                                                                     (412, 21, '2025-04-12 00:00:00', 93.14, 92.24, 93.83, 91.99, 'ETF021'),
                                                                                                     (413, 21, '2025-04-13 00:00:00', 104.14, 102.74, 104.99, 102.66, 'ETF021'),
                                                                                                     (414, 21, '2025-04-14 00:00:00', 105.45, 105.09, 106.4, 104.79, 'ETF021'),
                                                                                                     (415, 21, '2025-04-15 00:00:00', 99.03, 98.87, 99.05, 98.19, 'ETF021'),
                                                                                                     (416, 21, '2025-04-16 00:00:00', 101.05, 102.17, 102.68, 100.75, 'ETF021'),
                                                                                                     (417, 21, '2025-04-17 00:00:00', 94.73, 93.37, 95.35, 93.27, 'ETF021'),
                                                                                                     (418, 21, '2025-04-18 00:00:00', 91.35, 90.78, 92.32, 90.5, 'ETF021'),
                                                                                                     (419, 21, '2025-04-19 00:00:00', 103.86, 104.99, 105.56, 103.79, 'ETF021'),
                                                                                                     (420, 21, '2025-04-20 00:00:00', 90.88, 89.82, 91.61, 89.57, 'ETF021'),
                                                                                                     (421, 22, '2025-04-01 00:00:00', 101.71, 101.88, 102.23, 101.01, 'ETF022'),
                                                                                                     (422, 22, '2025-04-02 00:00:00', 91.09, 89.21, 91.57, 88.53, 'ETF022'),
                                                                                                     (423, 22, '2025-04-03 00:00:00', 91.15, 90.69, 91.58, 90.48, 'ETF022'),
                                                                                                     (424, 22, '2025-04-04 00:00:00', 95.39, 93.8, 95.45, 93.39, 'ETF022'),
                                                                                                     (425, 22, '2025-04-05 00:00:00', 103.02, 103.92, 104.11, 102.25, 'ETF022'),
                                                                                                     (426, 22, '2025-04-06 00:00:00', 95.8, 97.54, 98.36, 95.22, 'ETF022'),
                                                                                                     (427, 22, '2025-04-07 00:00:00', 93.89, 94.86, 95.69, 93.8, 'ETF022'),
                                                                                                     (428, 22, '2025-04-08 00:00:00', 103.58, 104.0, 104.9, 102.74, 'ETF022'),
                                                                                                     (429, 22, '2025-04-09 00:00:00', 93.81, 92.84, 94.39, 91.88, 'ETF022'),
                                                                                                     (430, 22, '2025-04-10 00:00:00', 109.15, 109.2, 109.52, 108.53, 'ETF022'),
                                                                                                     (431, 22, '2025-04-11 00:00:00', 96.32, 97.05, 97.94, 96.21, 'ETF022'),
                                                                                                     (432, 22, '2025-04-12 00:00:00', 109.91, 108.2, 110.85, 107.36, 'ETF022'),
                                                                                                     (433, 22, '2025-04-13 00:00:00', 109.06, 108.82, 109.59, 108.32, 'ETF022'),
                                                                                                     (434, 22, '2025-04-14 00:00:00', 101.17, 102.93, 103.48, 100.97, 'ETF022'),
                                                                                                     (435, 22, '2025-04-15 00:00:00', 90.48, 90.3, 91.23, 89.56, 'ETF022'),
                                                                                                     (436, 22, '2025-04-16 00:00:00', 102.78, 103.2, 103.34, 102.06, 'ETF022'),
                                                                                                     (437, 22, '2025-04-17 00:00:00', 109.16, 110.33, 111.1, 108.36, 'ETF022'),
                                                                                                     (438, 22, '2025-04-18 00:00:00', 98.09, 97.85, 98.36, 97.53, 'ETF022'),
                                                                                                     (439, 22, '2025-04-19 00:00:00', 98.35, 100.31, 100.98, 98.29, 'ETF022'),
                                                                                                     (440, 22, '2025-04-20 00:00:00', 106.21, 105.42, 106.6, 105.11, 'ETF022'),
                                                                                                     (441, 23, '2025-04-01 00:00:00', 97.43, 95.5, 98.41, 95.25, 'ETF023'),
                                                                                                     (442, 23, '2025-04-02 00:00:00', 98.03, 97.62, 98.95, 96.89, 'ETF023'),
                                                                                                     (443, 23, '2025-04-03 00:00:00', 104.16, 105.56, 105.96, 104.05, 'ETF023'),
                                                                                                     (444, 23, '2025-04-04 00:00:00', 104.94, 105.57, 105.9, 104.6, 'ETF023'),
                                                                                                     (445, 23, '2025-04-05 00:00:00', 94.08, 94.39, 95.14, 93.73, 'ETF023'),
                                                                                                     (446, 23, '2025-04-06 00:00:00', 91.76, 89.81, 92.52, 89.42, 'ETF023'),
                                                                                                     (447, 23, '2025-04-07 00:00:00', 100.33, 101.56, 101.83, 99.67, 'ETF023'),
                                                                                                     (448, 23, '2025-04-08 00:00:00', 95.65, 93.96, 96.18, 93.92, 'ETF023'),
                                                                                                     (449, 23, '2025-04-09 00:00:00', 94.16, 92.68, 94.74, 91.95, 'ETF023'),
                                                                                                     (450, 23, '2025-04-10 00:00:00', 96.97, 97.4, 97.51, 96.3, 'ETF023'),
                                                                                                     (451, 23, '2025-04-11 00:00:00', 92.24, 91.55, 92.58, 90.81, 'ETF023'),
                                                                                                     (452, 23, '2025-04-12 00:00:00', 105.25, 103.32, 105.39, 103.16, 'ETF023'),
                                                                                                     (453, 23, '2025-04-13 00:00:00', 97.8, 96.51, 98.64, 96.49, 'ETF023'),
                                                                                                     (454, 23, '2025-04-14 00:00:00', 95.39, 97.31, 97.92, 94.69, 'ETF023'),
                                                                                                     (455, 23, '2025-04-15 00:00:00', 106.05, 107.16, 107.8, 105.14, 'ETF023'),
                                                                                                     (456, 23, '2025-04-16 00:00:00', 108.44, 106.71, 108.82, 105.83, 'ETF023'),
                                                                                                     (457, 23, '2025-04-17 00:00:00', 108.94, 108.9, 109.8, 108.54, 'ETF023'),
                                                                                                     (458, 23, '2025-04-18 00:00:00', 95.53, 94.11, 95.99, 93.8, 'ETF023'),
                                                                                                     (459, 23, '2025-04-19 00:00:00', 95.88, 96.2, 97.1, 95.19, 'ETF023'),
                                                                                                     (460, 23, '2025-04-20 00:00:00', 106.57, 107.64, 107.78, 105.71, 'ETF023'),
                                                                                                     (461, 24, '2025-04-01 00:00:00', 93.01, 93.74, 94.38, 92.5, 'ETF024'),
                                                                                                     (462, 24, '2025-04-02 00:00:00', 108.0, 106.64, 108.94, 106.04, 'ETF024'),
                                                                                                     (463, 24, '2025-04-03 00:00:00', 107.62, 107.36, 108.16, 107.09, 'ETF024'),
                                                                                                     (464, 24, '2025-04-04 00:00:00', 104.69, 103.28, 105.19, 102.3, 'ETF024'),
                                                                                                     (465, 24, '2025-04-05 00:00:00', 98.25, 96.72, 98.49, 96.22, 'ETF024'),
                                                                                                     (466, 24, '2025-04-06 00:00:00', 96.95, 97.68, 98.18, 96.5, 'ETF024'),
                                                                                                     (467, 24, '2025-04-07 00:00:00', 102.45, 102.79, 103.61, 101.97, 'ETF024'),
                                                                                                     (468, 24, '2025-04-08 00:00:00', 98.42, 99.61, 100.06, 97.47, 'ETF024'),
                                                                                                     (469, 24, '2025-04-09 00:00:00', 103.44, 103.73, 104.39, 102.51, 'ETF024'),
                                                                                                     (470, 24, '2025-04-10 00:00:00', 102.11, 102.73, 103.3, 101.33, 'ETF024'),
                                                                                                     (471, 24, '2025-04-11 00:00:00', 98.14, 98.07, 98.66, 97.59, 'ETF024'),
                                                                                                     (472, 24, '2025-04-12 00:00:00', 95.49, 93.58, 95.49, 93.06, 'ETF024'),
                                                                                                     (473, 24, '2025-04-13 00:00:00', 102.92, 101.42, 103.22, 101.27, 'ETF024'),
                                                                                                     (474, 24, '2025-04-14 00:00:00', 95.32, 94.94, 96.04, 94.72, 'ETF024'),
                                                                                                     (475, 24, '2025-04-15 00:00:00', 90.35, 91.33, 92.22, 90.32, 'ETF024'),
                                                                                                     (476, 24, '2025-04-16 00:00:00', 106.51, 105.79, 107.33, 105.39, 'ETF024'),
                                                                                                     (477, 24, '2025-04-17 00:00:00', 94.09, 93.73, 94.82, 93.71, 'ETF024'),
                                                                                                     (478, 24, '2025-04-18 00:00:00', 106.82, 107.94, 108.04, 106.01, 'ETF024'),
                                                                                                     (479, 24, '2025-04-19 00:00:00', 106.76, 106.36, 107.63, 106.04, 'ETF024'),
                                                                                                     (480, 24, '2025-04-20 00:00:00', 103.76, 102.4, 104.03, 101.84, 'ETF024'),
                                                                                                     (481, 25, '2025-04-01 00:00:00', 93.69, 92.9, 93.82, 92.24, 'ETF025'),
                                                                                                     (482, 25, '2025-04-02 00:00:00', 102.18, 102.2, 102.8, 101.96, 'ETF025'),
                                                                                                     (483, 25, '2025-04-03 00:00:00', 100.6, 100.02, 101.21, 99.99, 'ETF025'),
                                                                                                     (484, 25, '2025-04-04 00:00:00', 93.11, 94.78, 95.69, 92.94, 'ETF025'),
                                                                                                     (485, 25, '2025-04-05 00:00:00', 94.39, 93.33, 94.65, 93.11, 'ETF025'),
                                                                                                     (486, 25, '2025-04-06 00:00:00', 109.87, 108.56, 110.71, 108.45, 'ETF025'),
                                                                                                     (487, 25, '2025-04-07 00:00:00', 94.71, 93.43, 95.28, 93.43, 'ETF025'),
                                                                                                     (488, 25, '2025-04-08 00:00:00', 91.51, 91.85, 91.98, 91.31, 'ETF025'),
                                                                                                     (489, 25, '2025-04-09 00:00:00', 108.49, 110.47, 110.71, 107.7, 'ETF025'),
                                                                                                     (490, 25, '2025-04-10 00:00:00', 93.58, 92.53, 93.86, 92.02, 'ETF025'),
                                                                                                     (491, 25, '2025-04-11 00:00:00', 95.98, 94.69, 96.03, 94.18, 'ETF025'),
                                                                                                     (492, 25, '2025-04-12 00:00:00', 100.59, 102.53, 102.9, 99.83, 'ETF025'),
                                                                                                     (493, 25, '2025-04-13 00:00:00', 105.84, 104.42, 106.74, 103.63, 'ETF025'),
                                                                                                     (494, 25, '2025-04-14 00:00:00', 104.98, 103.81, 105.76, 103.08, 'ETF025'),
                                                                                                     (495, 25, '2025-04-15 00:00:00', 100.87, 101.85, 102.27, 100.82, 'ETF025'),
                                                                                                     (496, 25, '2025-04-16 00:00:00', 96.47, 96.6, 96.61, 96.31, 'ETF025'),
                                                                                                     (497, 25, '2025-04-17 00:00:00', 97.15, 96.58, 97.36, 95.9, 'ETF025'),
                                                                                                     (498, 25, '2025-04-18 00:00:00', 105.46, 106.18, 106.95, 105.43, 'ETF025'),
                                                                                                     (499, 25, '2025-04-19 00:00:00', 99.65, 99.11, 100.26, 98.42, 'ETF025'),
                                                                                                     (500, 25, '2025-04-20 00:00:00', 93.79, 93.39, 93.96, 92.55, 'ETF025'),
                                                                                                     (501, 26, '2025-04-01 00:00:00', 107.81, 109.21, 109.5, 107.02, 'ETF026'),
                                                                                                     (502, 26, '2025-04-02 00:00:00', 108.62, 109.0, 109.33, 108.28, 'ETF026'),
                                                                                                     (503, 26, '2025-04-03 00:00:00', 96.68, 94.88, 97.15, 94.56, 'ETF026'),
                                                                                                     (504, 26, '2025-04-04 00:00:00', 104.59, 103.46, 105.15, 103.27, 'ETF026'),
                                                                                                     (505, 26, '2025-04-05 00:00:00', 99.93, 100.84, 101.58, 99.59, 'ETF026'),
                                                                                                     (506, 26, '2025-04-06 00:00:00', 97.33, 95.53, 97.56, 95.33, 'ETF026'),
                                                                                                     (507, 26, '2025-04-07 00:00:00', 93.03, 91.98, 93.35, 91.49, 'ETF026'),
                                                                                                     (508, 26, '2025-04-08 00:00:00', 104.1, 104.72, 105.7, 103.63, 'ETF026'),
                                                                                                     (509, 26, '2025-04-09 00:00:00', 100.06, 101.05, 101.68, 99.46, 'ETF026'),
                                                                                                     (510, 26, '2025-04-10 00:00:00', 99.64, 98.23, 100.4, 97.68, 'ETF026'),
                                                                                                     (511, 26, '2025-04-11 00:00:00', 101.81, 100.32, 102.31, 100.06, 'ETF026'),
                                                                                                     (512, 26, '2025-04-12 00:00:00', 91.52, 92.43, 93.06, 91.18, 'ETF026'),
                                                                                                     (513, 26, '2025-04-13 00:00:00', 99.5, 100.53, 101.37, 98.69, 'ETF026'),
                                                                                                     (514, 26, '2025-04-14 00:00:00', 104.63, 104.43, 105.54, 103.63, 'ETF026'),
                                                                                                     (515, 26, '2025-04-15 00:00:00', 92.84, 93.86, 94.84, 92.52, 'ETF026'),
                                                                                                     (516, 26, '2025-04-16 00:00:00', 99.33, 97.74, 99.97, 96.83, 'ETF026'),
                                                                                                     (517, 26, '2025-04-17 00:00:00', 90.53, 92.38, 93.26, 90.2, 'ETF026'),
                                                                                                     (518, 26, '2025-04-18 00:00:00', 97.98, 99.23, 99.49, 97.78, 'ETF026'),
                                                                                                     (519, 26, '2025-04-19 00:00:00', 107.3, 105.47, 107.63, 105.37, 'ETF026'),
                                                                                                     (520, 26, '2025-04-20 00:00:00', 108.89, 107.34, 109.56, 106.51, 'ETF026'),
                                                                                                     (521, 27, '2025-04-01 00:00:00', 91.18, 89.21, 91.21, 89.06, 'ETF027'),
                                                                                                     (522, 27, '2025-04-02 00:00:00', 109.18, 110.36, 111.0, 108.4, 'ETF027'),
                                                                                                     (523, 27, '2025-04-03 00:00:00', 106.3, 104.72, 106.43, 104.41, 'ETF027'),
                                                                                                     (524, 27, '2025-04-04 00:00:00', 102.18, 102.27, 102.71, 101.19, 'ETF027'),
                                                                                                     (525, 27, '2025-04-05 00:00:00', 95.5, 96.29, 96.56, 94.53, 'ETF027'),
                                                                                                     (526, 27, '2025-04-06 00:00:00', 96.37, 97.17, 98.15, 95.54, 'ETF027'),
                                                                                                     (527, 27, '2025-04-07 00:00:00', 98.07, 97.93, 98.86, 97.4, 'ETF027'),
                                                                                                     (528, 27, '2025-04-08 00:00:00', 91.42, 93.11, 93.21, 91.39, 'ETF027'),
                                                                                                     (529, 27, '2025-04-09 00:00:00', 109.75, 111.35, 112.12, 109.43, 'ETF027'),
                                                                                                     (530, 27, '2025-04-10 00:00:00', 92.06, 91.55, 92.47, 90.75, 'ETF027'),
                                                                                                     (531, 27, '2025-04-11 00:00:00', 95.13, 96.28, 96.45, 94.75, 'ETF027'),
                                                                                                     (532, 27, '2025-04-12 00:00:00', 105.32, 106.04, 106.13, 104.84, 'ETF027'),
                                                                                                     (533, 27, '2025-04-13 00:00:00', 91.47, 90.25, 92.39, 89.76, 'ETF027'),
                                                                                                     (534, 27, '2025-04-14 00:00:00', 107.97, 109.1, 109.19, 107.56, 'ETF027'),
                                                                                                     (535, 27, '2025-04-15 00:00:00', 98.15, 96.25, 98.62, 95.53, 'ETF027'),
                                                                                                     (536, 27, '2025-04-16 00:00:00', 92.49, 91.06, 93.49, 90.7, 'ETF027'),
                                                                                                     (537, 27, '2025-04-17 00:00:00', 102.97, 102.24, 103.63, 101.32, 'ETF027'),
                                                                                                     (538, 27, '2025-04-18 00:00:00', 91.75, 90.58, 92.55, 89.93, 'ETF027'),
                                                                                                     (539, 27, '2025-04-19 00:00:00', 104.87, 106.63, 107.16, 104.09, 'ETF027'),
                                                                                                     (540, 27, '2025-04-20 00:00:00', 100.64, 100.14, 100.98, 99.79, 'ETF027'),
                                                                                                     (541, 28, '2025-04-01 00:00:00', 107.26, 108.75, 109.71, 106.77, 'ETF028'),
                                                                                                     (542, 28, '2025-04-02 00:00:00', 98.77, 97.97, 99.18, 97.32, 'ETF028'),
                                                                                                     (543, 28, '2025-04-03 00:00:00', 94.22, 94.23, 95.09, 94.15, 'ETF028'),
                                                                                                     (544, 28, '2025-04-04 00:00:00', 100.06, 98.98, 100.6, 98.63, 'ETF028'),
                                                                                                     (545, 28, '2025-04-05 00:00:00', 109.04, 110.72, 111.06, 108.16, 'ETF028'),
                                                                                                     (546, 28, '2025-04-06 00:00:00', 97.17, 98.79, 98.86, 96.41, 'ETF028'),
                                                                                                     (547, 28, '2025-04-07 00:00:00', 106.5, 108.42, 108.68, 105.53, 'ETF028'),
                                                                                                     (548, 28, '2025-04-08 00:00:00', 90.93, 92.27, 92.71, 90.32, 'ETF028'),
                                                                                                     (549, 28, '2025-04-09 00:00:00', 90.94, 92.44, 93.09, 90.52, 'ETF028'),
                                                                                                     (550, 28, '2025-04-10 00:00:00', 106.3, 104.91, 106.91, 104.67, 'ETF028'),
                                                                                                     (551, 28, '2025-04-11 00:00:00', 98.06, 97.42, 98.3, 97.12, 'ETF028'),
                                                                                                     (552, 28, '2025-04-12 00:00:00', 101.2, 102.04, 102.46, 101.16, 'ETF028'),
                                                                                                     (553, 28, '2025-04-13 00:00:00', 94.97, 94.72, 95.02, 94.49, 'ETF028'),
                                                                                                     (554, 28, '2025-04-14 00:00:00', 97.78, 99.74, 99.89, 97.38, 'ETF028'),
                                                                                                     (555, 28, '2025-04-15 00:00:00', 91.56, 92.78, 92.86, 90.57, 'ETF028'),
                                                                                                     (556, 28, '2025-04-16 00:00:00', 96.89, 96.99, 97.76, 96.31, 'ETF028'),
                                                                                                     (557, 28, '2025-04-17 00:00:00', 103.66, 105.02, 105.63, 102.95, 'ETF028'),
                                                                                                     (558, 28, '2025-04-18 00:00:00', 97.78, 99.01, 99.86, 96.79, 'ETF028'),
                                                                                                     (559, 28, '2025-04-19 00:00:00', 102.7, 100.99, 103.68, 100.27, 'ETF028'),
                                                                                                     (560, 28, '2025-04-20 00:00:00', 104.4, 105.34, 105.63, 103.81, 'ETF028'),
                                                                                                     (561, 29, '2025-04-01 00:00:00', 93.0, 92.91, 93.11, 92.72, 'ETF029'),
                                                                                                     (562, 29, '2025-04-02 00:00:00', 98.23, 100.13, 100.34, 97.6, 'ETF029'),
                                                                                                     (563, 29, '2025-04-03 00:00:00', 96.72, 97.05, 97.89, 96.48, 'ETF029'),
                                                                                                     (564, 29, '2025-04-04 00:00:00', 106.03, 107.14, 107.91, 105.47, 'ETF029'),
                                                                                                     (565, 29, '2025-04-05 00:00:00', 101.33, 100.48, 101.43, 99.78, 'ETF029'),
                                                                                                     (566, 29, '2025-04-06 00:00:00', 90.63, 91.08, 91.72, 89.88, 'ETF029'),
                                                                                                     (567, 29, '2025-04-07 00:00:00', 97.46, 95.76, 97.58, 95.66, 'ETF029'),
                                                                                                     (568, 29, '2025-04-08 00:00:00', 103.19, 101.46, 103.25, 100.63, 'ETF029'),
                                                                                                     (569, 29, '2025-04-09 00:00:00', 109.15, 109.11, 109.55, 108.88, 'ETF029'),
                                                                                                     (570, 29, '2025-04-10 00:00:00', 104.98, 105.9, 106.05, 104.18, 'ETF029'),
                                                                                                     (571, 29, '2025-04-11 00:00:00', 99.76, 97.93, 100.61, 97.24, 'ETF029'),
                                                                                                     (572, 29, '2025-04-12 00:00:00', 107.79, 108.55, 108.7, 107.17, 'ETF029'),
                                                                                                     (573, 29, '2025-04-13 00:00:00', 99.34, 99.22, 99.71, 98.53, 'ETF029'),
                                                                                                     (574, 29, '2025-04-14 00:00:00', 104.25, 103.37, 104.97, 102.68, 'ETF029'),
                                                                                                     (575, 29, '2025-04-15 00:00:00', 109.34, 109.14, 110.2, 108.67, 'ETF029'),
                                                                                                     (576, 29, '2025-04-16 00:00:00', 106.26, 104.76, 106.87, 104.11, 'ETF029'),
                                                                                                     (577, 29, '2025-04-17 00:00:00', 108.31, 110.07, 110.73, 107.45, 'ETF029'),
                                                                                                     (578, 29, '2025-04-18 00:00:00', 103.35, 101.48, 103.35, 101.43, 'ETF029'),
                                                                                                     (579, 29, '2025-04-19 00:00:00', 92.46, 90.54, 92.98, 90.47, 'ETF029'),
                                                                                                     (580, 29, '2025-04-20 00:00:00', 108.05, 109.69, 110.54, 107.48, 'ETF029'),
                                                                                                     (581, 30, '2025-04-01 00:00:00', 90.97, 91.13, 91.67, 90.13, 'ETF030'),
                                                                                                     (582, 30, '2025-04-02 00:00:00', 103.95, 104.05, 104.7, 103.59, 'ETF030'),
                                                                                                     (583, 30, '2025-04-03 00:00:00', 92.7, 93.9, 94.78, 91.87, 'ETF030'),
                                                                                                     (584, 30, '2025-04-04 00:00:00', 108.4, 107.18, 109.02, 106.55, 'ETF030'),
                                                                                                     (585, 30, '2025-04-05 00:00:00', 95.49, 97.12, 98.04, 94.67, 'ETF030'),
                                                                                                     (586, 30, '2025-04-06 00:00:00', 94.94, 93.14, 95.65, 92.59, 'ETF030'),
                                                                                                     (587, 30, '2025-04-07 00:00:00', 90.8, 92.72, 92.97, 90.28, 'ETF030'),
                                                                                                     (588, 30, '2025-04-08 00:00:00', 99.9, 99.04, 100.04, 98.88, 'ETF030'),
                                                                                                     (589, 30, '2025-04-09 00:00:00', 98.11, 97.72, 99.1, 96.97, 'ETF030'),
                                                                                                     (590, 30, '2025-04-10 00:00:00', 99.49, 100.61, 101.39, 98.69, 'ETF030'),
                                                                                                     (591, 30, '2025-04-11 00:00:00', 91.85, 90.65, 92.1, 89.73, 'ETF030'),
                                                                                                     (592, 30, '2025-04-12 00:00:00', 92.97, 93.01, 93.11, 91.98, 'ETF030'),
                                                                                                     (593, 30, '2025-04-13 00:00:00', 108.95, 108.05, 109.49, 107.59, 'ETF030'),
                                                                                                     (594, 30, '2025-04-14 00:00:00', 109.41, 108.38, 109.84, 107.66, 'ETF030'),
                                                                                                     (595, 30, '2025-04-15 00:00:00', 98.86, 96.94, 99.76, 96.12, 'ETF030'),
                                                                                                     (596, 30, '2025-04-16 00:00:00', 107.91, 106.59, 108.6, 106.13, 'ETF030'),
                                                                                                     (597, 30, '2025-04-17 00:00:00', 91.41, 90.29, 92.0, 90.22, 'ETF030'),
                                                                                                     (598, 30, '2025-04-18 00:00:00', 99.85, 98.4, 100.28, 98.37, 'ETF030'),
                                                                                                     (599, 30, '2025-04-19 00:00:00', 109.51, 108.22, 109.66, 107.71, 'ETF030'),
                                                                                                     (600, 30, '2025-04-20 00:00:00', 95.94, 96.41, 97.33, 95.16, 'ETF030');
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (601, 31, '2025-04-01 00:00:00', 99.02, 99.2, 99.68, 98.57);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (602, 31, '2025-04-02 00:00:00', 100.86, 100.46, 101.34, 100.45);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (603, 31, '2025-04-03 00:00:00', 100.78, 99.09, 100.96, 98.74);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (604, 31, '2025-04-04 00:00:00', 100.27, 100.86, 101.01, 99.91);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (605, 31, '2025-04-05 00:00:00', 98.6, 99.31, 99.64, 98.37);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (606, 31, '2025-04-06 00:00:00', 100.84, 100.53, 100.86, 100.52);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (607, 31, '2025-04-07 00:00:00', 99.01, 97.78, 99.28, 97.59);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (608, 31, '2025-04-08 00:00:00', 100.23, 98.39, 100.37, 97.9);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (609, 31, '2025-04-09 00:00:00', 99.71, 100.42, 100.81, 99.52);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (610, 31, '2025-04-10 00:00:00', 100.86, 100.16, 101.32, 99.76);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (611, 31, '2025-04-11 00:00:00', 99.48, 98.83, 99.55, 98.66);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (612, 31, '2025-04-12 00:00:00', 99.78, 100.05, 100.17, 99.63);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (613, 31, '2025-04-13 00:00:00', 99.84, 100.11, 100.32, 99.75);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (614, 31, '2025-04-14 00:00:00', 98.54, 98.5, 98.6, 98.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (615, 31, '2025-04-15 00:00:00', 100.94, 100.43, 100.96, 100.28);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (616, 31, '2025-04-16 00:00:00', 99.01, 99.8, 100.03, 98.96);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (617, 31, '2025-04-17 00:00:00', 100.97, 100.26, 101.09, 100.23);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (618, 31, '2025-04-18 00:00:00', 99.46, 99.82, 100.05, 99.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (619, 31, '2025-04-19 00:00:00', 100.87, 100.61, 101.23, 100.53);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (620, 31, '2025-04-20 00:00:00', 99.49, 99.42, 99.76, 98.98);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (621, 32, '2025-04-01 00:00:00', 100.05, 99.27, 100.24, 99.14);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (622, 32, '2025-04-02 00:00:00', 99.56, 100.98, 101.13, 99.11);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (623, 32, '2025-04-03 00:00:00', 99.57, 99.87, 100.03, 99.16);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (624, 32, '2025-04-04 00:00:00', 100.83, 99.37, 101.33, 99.22);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (625, 32, '2025-04-05 00:00:00', 100.88, 102.04, 102.14, 100.49);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (626, 32, '2025-04-06 00:00:00', 99.12, 98.96, 99.26, 98.53);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (627, 32, '2025-04-07 00:00:00', 100.94, 99.96, 101.2, 99.61);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (628, 32, '2025-04-08 00:00:00', 101.68, 102.15, 102.35, 101.64);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (629, 32, '2025-04-09 00:00:00', 100.62, 101.74, 102.03, 100.42);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (630, 32, '2025-04-10 00:00:00', 98.58, 98.0, 98.9, 97.6);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (631, 32, '2025-04-11 00:00:00', 100.71, 100.88, 101.14, 100.64);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (632, 32, '2025-04-12 00:00:00', 101.14, 100.47, 101.58, 100.29);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (633, 32, '2025-04-13 00:00:00', 100.47, 101.11, 101.38, 99.97);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (634, 32, '2025-04-14 00:00:00', 101.82, 101.76, 102.25, 101.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (635, 32, '2025-04-15 00:00:00', 99.32, 99.42, 99.46, 99.13);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (636, 32, '2025-04-16 00:00:00', 100.59, 101.72, 101.85, 100.44);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (637, 32, '2025-04-17 00:00:00', 99.3, 100.06, 100.25, 98.86);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (638, 32, '2025-04-18 00:00:00', 101.1, 100.18, 101.55, 99.76);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (639, 32, '2025-04-19 00:00:00', 101.34, 101.82, 101.93, 101.17);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (640, 32, '2025-04-20 00:00:00', 101.09, 100.62, 101.37, 100.26);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (641, 33, '2025-04-01 00:00:00', 99.62, 99.96, 100.11, 99.26);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (642, 33, '2025-04-02 00:00:00', 99.15, 99.06, 99.32, 98.98);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (643, 33, '2025-04-03 00:00:00', 100.32, 100.14, 100.73, 99.67);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (644, 33, '2025-04-04 00:00:00', 100.35, 99.9, 100.83, 99.77);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (645, 33, '2025-04-05 00:00:00', 100.89, 100.24, 101.15, 99.94);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (646, 33, '2025-04-06 00:00:00', 100.01, 101.54, 101.61, 99.63);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (647, 33, '2025-04-07 00:00:00', 100.7, 100.68, 101.19, 100.37);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (648, 33, '2025-04-08 00:00:00', 100.0, 99.47, 100.15, 99.16);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (649, 33, '2025-04-09 00:00:00', 99.18, 97.94, 99.23, 97.75);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (650, 33, '2025-04-10 00:00:00', 100.52, 101.37, 101.49, 100.27);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (651, 33, '2025-04-11 00:00:00', 98.54, 99.52, 99.66, 98.12);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (652, 33, '2025-04-12 00:00:00', 100.1, 100.17, 100.37, 99.7);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (653, 33, '2025-04-13 00:00:00', 100.85, 101.63, 101.98, 100.48);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (654, 33, '2025-04-14 00:00:00', 101.17, 100.8, 101.45, 100.43);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (655, 33, '2025-04-15 00:00:00', 99.06, 98.62, 99.29, 98.13);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (656, 33, '2025-04-16 00:00:00', 100.51, 100.56, 100.87, 100.15);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (657, 33, '2025-04-17 00:00:00', 98.77, 98.39, 98.79, 97.92);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (658, 33, '2025-04-18 00:00:00', 101.96, 100.86, 101.99, 100.41);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (659, 33, '2025-04-19 00:00:00', 99.93, 100.24, 100.5, 99.64);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (660, 33, '2025-04-20 00:00:00', 99.73, 98.56, 100.05, 98.17);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (661, 34, '2025-04-01 00:00:00', 98.33, 99.25, 99.52, 98.18);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (662, 34, '2025-04-02 00:00:00', 98.87, 98.26, 98.96, 98.02);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (663, 34, '2025-04-03 00:00:00', 99.35, 99.4, 99.66, 98.87);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (664, 34, '2025-04-04 00:00:00', 99.32, 99.67, 99.82, 98.9);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (665, 34, '2025-04-05 00:00:00', 99.71, 98.87, 99.77, 98.66);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (666, 34, '2025-04-06 00:00:00', 101.6, 101.53, 101.96, 101.31);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (667, 34, '2025-04-07 00:00:00', 99.32, 99.73, 100.02, 99.11);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (668, 34, '2025-04-08 00:00:00', 100.29, 101.69, 101.78, 100.09);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (669, 34, '2025-04-09 00:00:00', 99.95, 100.29, 100.45, 99.79);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (670, 34, '2025-04-10 00:00:00', 99.69, 99.36, 99.74, 99.06);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (671, 34, '2025-04-11 00:00:00', 100.66, 100.11, 100.82, 99.81);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (672, 34, '2025-04-12 00:00:00', 97.86, 99.32, 99.56, 97.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (673, 34, '2025-04-13 00:00:00', 99.28, 99.06, 99.33, 98.61);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (674, 34, '2025-04-14 00:00:00', 99.17, 100.29, 100.38, 98.88);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (675, 34, '2025-04-15 00:00:00', 99.38, 99.36, 99.72, 98.92);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (676, 34, '2025-04-16 00:00:00', 101.82, 101.15, 102.27, 100.97);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (677, 34, '2025-04-17 00:00:00', 101.09, 101.11, 101.42, 100.64);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (678, 34, '2025-04-18 00:00:00', 100.78, 100.75, 101.04, 100.66);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (679, 34, '2025-04-19 00:00:00', 98.51, 98.38, 98.75, 98.34);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (680, 34, '2025-04-20 00:00:00', 100.72, 100.26, 101.1, 100.2);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (681, 35, '2025-04-01 00:00:00', 98.75, 98.26, 98.97, 97.78);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (682, 35, '2025-04-02 00:00:00', 99.49, 99.49, 99.67, 99.45);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (683, 35, '2025-04-03 00:00:00', 99.54, 98.48, 99.83, 97.98);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (684, 35, '2025-04-04 00:00:00', 99.03, 99.61, 99.62, 98.72);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (685, 35, '2025-04-05 00:00:00', 100.42, 100.78, 100.91, 100.09);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (686, 35, '2025-04-06 00:00:00', 99.16, 98.18, 99.24, 98.03);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (687, 35, '2025-04-07 00:00:00', 100.12, 100.09, 100.15, 99.9);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (688, 35, '2025-04-08 00:00:00', 100.88, 100.5, 101.3, 100.16);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (689, 35, '2025-04-09 00:00:00', 100.1, 100.59, 100.71, 99.78);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (690, 35, '2025-04-10 00:00:00', 101.07, 99.69, 101.17, 99.35);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (691, 35, '2025-04-11 00:00:00', 101.23, 100.06, 101.3, 99.99);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (692, 35, '2025-04-12 00:00:00', 99.38, 99.58, 100.02, 99.1);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (693, 35, '2025-04-13 00:00:00', 99.53, 99.61, 100.02, 99.2);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (694, 35, '2025-04-14 00:00:00', 99.91, 100.96, 101.16, 99.77);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (695, 35, '2025-04-15 00:00:00', 101.35, 100.89, 101.44, 100.64);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (696, 35, '2025-04-16 00:00:00', 100.74, 100.33, 100.82, 100.31);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (697, 35, '2025-04-17 00:00:00', 100.26, 101.29, 101.37, 99.85);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (698, 35, '2025-04-18 00:00:00', 100.98, 100.85, 101.13, 100.37);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (699, 35, '2025-04-19 00:00:00', 98.73, 99.83, 100.18, 98.34);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (700, 35, '2025-04-20 00:00:00', 99.58, 98.04, 99.6, 98.01);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (701, 36, '2025-04-01 00:00:00', 99.8, 98.72, 99.86, 98.65);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (702, 36, '2025-04-02 00:00:00', 98.35, 99.21, 99.25, 98.24);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (703, 36, '2025-04-03 00:00:00', 101.02, 100.64, 101.25, 100.24);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (704, 36, '2025-04-04 00:00:00', 98.23, 98.4, 98.72, 97.78);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (705, 36, '2025-04-05 00:00:00', 99.36, 99.4, 99.49, 99.33);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (706, 36, '2025-04-06 00:00:00', 98.43, 98.0, 98.66, 97.77);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (707, 36, '2025-04-07 00:00:00', 100.88, 101.28, 101.43, 100.77);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (708, 36, '2025-04-08 00:00:00', 99.4, 98.87, 99.6, 98.79);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (709, 36, '2025-04-09 00:00:00', 102.15, 102.14, 102.56, 102.12);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (710, 36, '2025-04-10 00:00:00', 99.71, 100.48, 100.84, 99.32);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (711, 36, '2025-04-11 00:00:00', 99.94, 100.32, 100.59, 99.71);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (712, 36, '2025-04-12 00:00:00', 101.0, 99.83, 101.06, 99.67);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (713, 36, '2025-04-13 00:00:00', 101.11, 100.98, 101.57, 100.57);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (714, 36, '2025-04-14 00:00:00', 100.77, 100.14, 101.21, 100.04);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (715, 36, '2025-04-15 00:00:00', 100.68, 100.56, 100.83, 100.33);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (716, 36, '2025-04-16 00:00:00', 100.64, 101.23, 101.48, 100.36);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (717, 36, '2025-04-17 00:00:00', 98.65, 98.47, 99.04, 98.01);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (718, 36, '2025-04-18 00:00:00', 99.98, 100.6, 100.73, 99.78);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (719, 36, '2025-04-19 00:00:00', 100.86, 101.99, 102.05, 100.75);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (720, 36, '2025-04-20 00:00:00', 99.73, 98.68, 99.73, 98.38);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (721, 37, '2025-04-01 00:00:00', 100.05, 100.17, 100.4, 99.72);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (722, 37, '2025-04-02 00:00:00', 99.91, 99.66, 100.1, 99.29);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (723, 37, '2025-04-03 00:00:00', 98.52, 98.77, 98.86, 98.44);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (724, 37, '2025-04-04 00:00:00', 98.71, 99.03, 99.07, 98.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (725, 37, '2025-04-05 00:00:00', 99.59, 99.47, 99.61, 99.02);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (726, 37, '2025-04-06 00:00:00', 99.84, 100.08, 100.26, 99.61);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (727, 37, '2025-04-07 00:00:00', 99.6, 99.43, 99.7, 99.22);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (728, 37, '2025-04-08 00:00:00', 99.15, 98.31, 99.45, 98.29);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (729, 37, '2025-04-09 00:00:00', 101.41, 100.99, 101.49, 100.73);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (730, 37, '2025-04-10 00:00:00', 99.39, 99.54, 99.86, 99.38);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (731, 37, '2025-04-11 00:00:00', 101.33, 99.41, 101.71, 99.27);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (732, 37, '2025-04-12 00:00:00', 99.59, 99.01, 99.62, 98.97);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (733, 37, '2025-04-13 00:00:00', 101.94, 101.57, 102.08, 101.13);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (734, 37, '2025-04-14 00:00:00', 99.6, 98.54, 99.61, 98.48);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (735, 37, '2025-04-15 00:00:00', 98.76, 98.93, 99.12, 98.28);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (736, 37, '2025-04-16 00:00:00', 100.85, 100.4, 100.87, 100.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (737, 37, '2025-04-17 00:00:00', 98.36, 99.04, 99.18, 98.04);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (738, 37, '2025-04-18 00:00:00', 99.96, 101.37, 101.75, 99.47);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (739, 37, '2025-04-19 00:00:00', 98.74, 98.32, 99.14, 98.0);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (740, 37, '2025-04-20 00:00:00', 100.08, 98.97, 100.19, 98.89);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (741, 38, '2025-04-01 00:00:00', 101.5, 102.25, 102.51, 101.13);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (742, 38, '2025-04-02 00:00:00', 99.2, 98.12, 99.37, 98.1);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (743, 38, '2025-04-03 00:00:00', 100.79, 101.62, 101.68, 100.33);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (744, 38, '2025-04-04 00:00:00', 100.17, 98.87, 100.38, 98.8);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (745, 38, '2025-04-05 00:00:00', 98.79, 98.36, 99.13, 98.03);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (746, 38, '2025-04-06 00:00:00', 100.61, 101.43, 101.48, 100.31);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (747, 38, '2025-04-07 00:00:00', 100.68, 100.29, 101.18, 100.2);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (748, 38, '2025-04-08 00:00:00', 98.3, 99.48, 99.66, 98.2);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (749, 38, '2025-04-09 00:00:00', 99.77, 99.26, 100.11, 98.94);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (750, 38, '2025-04-10 00:00:00', 99.62, 99.49, 99.86, 99.46);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (751, 38, '2025-04-11 00:00:00', 99.87, 100.22, 100.58, 99.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (752, 38, '2025-04-12 00:00:00', 100.52, 100.68, 100.76, 100.51);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (753, 38, '2025-04-13 00:00:00', 99.37, 99.7, 100.05, 98.87);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (754, 38, '2025-04-14 00:00:00', 99.71, 98.87, 100.19, 98.41);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (755, 38, '2025-04-15 00:00:00', 101.9, 101.28, 102.11, 101.08);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (756, 38, '2025-04-16 00:00:00', 100.97, 100.46, 101.38, 100.36);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (757, 38, '2025-04-17 00:00:00', 100.16, 99.78, 100.2, 99.68);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (758, 38, '2025-04-18 00:00:00', 98.73, 99.32, 99.5, 98.43);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (759, 38, '2025-04-19 00:00:00', 97.91, 99.41, 99.56, 97.52);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (760, 38, '2025-04-20 00:00:00', 99.54, 100.13, 100.62, 99.38);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (761, 39, '2025-04-01 00:00:00', 100.46, 100.69, 100.7, 100.03);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (762, 39, '2025-04-02 00:00:00', 99.23, 98.94, 99.48, 98.82);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (763, 39, '2025-04-03 00:00:00', 99.84, 98.56, 100.32, 98.51);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (764, 39, '2025-04-04 00:00:00', 101.36, 100.58, 101.62, 100.43);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (765, 39, '2025-04-05 00:00:00', 99.16, 100.11, 100.24, 99.08);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (766, 39, '2025-04-06 00:00:00', 98.6, 99.7, 100.17, 98.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (767, 39, '2025-04-07 00:00:00', 101.79, 101.67, 102.27, 101.26);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (768, 39, '2025-04-08 00:00:00', 98.71, 98.17, 98.81, 97.77);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (769, 39, '2025-04-09 00:00:00', 99.22, 99.09, 99.55, 98.76);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (770, 39, '2025-04-10 00:00:00', 100.82, 101.15, 101.21, 100.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (771, 39, '2025-04-11 00:00:00', 98.44, 98.71, 98.94, 98.0);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (772, 39, '2025-04-12 00:00:00', 99.5, 100.11, 100.54, 99.22);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (773, 39, '2025-04-13 00:00:00', 99.93, 99.95, 100.19, 99.48);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (774, 39, '2025-04-14 00:00:00', 101.89, 100.2, 102.38, 100.0);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (775, 39, '2025-04-15 00:00:00', 101.81, 100.1, 101.97, 100.09);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (776, 39, '2025-04-16 00:00:00', 99.82, 101.45, 101.92, 99.66);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (777, 39, '2025-04-17 00:00:00', 100.59, 101.22, 101.52, 100.48);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (778, 39, '2025-04-18 00:00:00', 99.11, 99.45, 99.91, 99.08);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (779, 39, '2025-04-19 00:00:00', 100.58, 100.28, 100.77, 99.87);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (780, 39, '2025-04-20 00:00:00', 100.17, 101.68, 101.8, 99.95);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (781, 40, '2025-04-01 00:00:00', 99.12, 100.32, 100.56, 98.75);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (782, 40, '2025-04-02 00:00:00', 99.52, 98.27, 99.63, 97.82);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (783, 40, '2025-04-03 00:00:00', 99.21, 98.06, 99.23, 97.8);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (784, 40, '2025-04-04 00:00:00', 101.37, 100.52, 101.7, 100.14);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (785, 40, '2025-04-05 00:00:00', 100.08, 100.86, 101.02, 99.82);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (786, 40, '2025-04-06 00:00:00', 100.84, 100.91, 101.35, 100.82);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (787, 40, '2025-04-07 00:00:00', 99.84, 99.59, 100.11, 99.34);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (788, 40, '2025-04-08 00:00:00', 100.16, 101.19, 101.51, 100.07);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (789, 40, '2025-04-09 00:00:00', 102.2, 101.15, 102.58, 101.04);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (790, 40, '2025-04-10 00:00:00', 100.74, 100.56, 100.75, 100.23);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (791, 40, '2025-04-11 00:00:00', 100.13, 99.9, 100.32, 99.59);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (792, 40, '2025-04-12 00:00:00', 100.47, 100.47, 100.61, 100.0);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (793, 40, '2025-04-13 00:00:00', 99.68, 99.45, 99.71, 99.28);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (794, 40, '2025-04-14 00:00:00', 99.42, 99.73, 99.82, 99.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (795, 40, '2025-04-15 00:00:00', 101.63, 101.53, 101.9, 101.08);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (796, 40, '2025-04-16 00:00:00', 100.02, 99.03, 100.19, 98.82);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (797, 40, '2025-04-17 00:00:00', 100.13, 100.08, 100.53, 99.85);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (798, 40, '2025-04-18 00:00:00', 98.93, 99.62, 100.04, 98.61);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (799, 40, '2025-04-19 00:00:00', 100.88, 101.69, 101.85, 100.68);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (800, 40, '2025-04-20 00:00:00', 101.35, 100.4, 101.56, 100.03);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (801, 41, '2025-04-01 00:00:00', 98.99, 98.07, 99.13, 97.58);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (802, 41, '2025-04-02 00:00:00', 99.45, 101.16, 101.56, 99.39);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (803, 41, '2025-04-03 00:00:00', 99.3, 97.74, 99.41, 97.44);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (804, 41, '2025-04-04 00:00:00', 101.14, 101.03, 101.49, 100.79);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (805, 41, '2025-04-05 00:00:00', 99.58, 99.98, 100.38, 99.55);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (806, 41, '2025-04-06 00:00:00', 100.74, 100.01, 100.86, 99.64);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (807, 41, '2025-04-07 00:00:00', 99.95, 100.13, 100.29, 99.69);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (808, 41, '2025-04-08 00:00:00', 101.02, 102.33, 102.65, 100.73);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (809, 41, '2025-04-09 00:00:00', 100.06, 99.67, 100.22, 99.65);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (810, 41, '2025-04-10 00:00:00', 100.25, 98.98, 100.51, 98.87);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (811, 41, '2025-04-11 00:00:00', 99.37, 98.91, 99.51, 98.73);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (812, 41, '2025-04-12 00:00:00', 102.03, 101.94, 102.45, 101.76);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (813, 41, '2025-04-13 00:00:00', 98.74, 98.62, 99.13, 98.2);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (814, 41, '2025-04-14 00:00:00', 98.77, 98.34, 98.86, 98.03);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (815, 41, '2025-04-15 00:00:00', 98.89, 97.88, 99.37, 97.48);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (816, 41, '2025-04-16 00:00:00', 98.65, 99.83, 99.93, 98.62);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (817, 41, '2025-04-17 00:00:00', 99.54, 101.01, 101.42, 99.45);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (818, 41, '2025-04-18 00:00:00', 100.64, 98.93, 100.76, 98.6);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (819, 41, '2025-04-19 00:00:00', 100.78, 101.17, 101.4, 100.74);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (820, 41, '2025-04-20 00:00:00', 98.61, 99.13, 99.35, 98.32);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (821, 42, '2025-04-01 00:00:00', 100.47, 102.23, 102.3, 100.21);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (822, 42, '2025-04-02 00:00:00', 102.27, 100.32, 102.74, 100.06);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (823, 42, '2025-04-03 00:00:00', 100.64, 100.53, 100.82, 100.46);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (824, 42, '2025-04-04 00:00:00', 101.39, 100.73, 101.86, 100.26);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (825, 42, '2025-04-05 00:00:00', 101.86, 101.66, 102.07, 101.55);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (826, 42, '2025-04-06 00:00:00', 100.33, 100.49, 100.89, 100.28);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (827, 42, '2025-04-07 00:00:00', 99.59, 101.34, 101.45, 99.58);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (828, 42, '2025-04-08 00:00:00', 101.03, 100.25, 101.32, 99.84);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (829, 42, '2025-04-09 00:00:00', 99.45, 99.43, 99.77, 98.97);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (830, 42, '2025-04-10 00:00:00', 99.42, 99.37, 99.56, 99.3);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (831, 42, '2025-04-11 00:00:00', 100.44, 101.66, 102.12, 100.27);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (832, 42, '2025-04-12 00:00:00', 100.59, 99.54, 100.85, 99.39);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (833, 42, '2025-04-13 00:00:00', 99.8, 101.51, 101.99, 99.34);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (834, 42, '2025-04-14 00:00:00', 99.11, 99.06, 99.5, 98.92);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (835, 42, '2025-04-15 00:00:00', 99.36, 101.04, 101.11, 99.3);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (836, 42, '2025-04-16 00:00:00', 98.21, 98.24, 98.49, 97.74);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (837, 42, '2025-04-17 00:00:00', 101.91, 100.6, 102.19, 100.49);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (838, 42, '2025-04-18 00:00:00', 101.39, 101.78, 102.27, 101.25);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (839, 42, '2025-04-19 00:00:00', 98.4, 99.5, 99.79, 98.03);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (840, 42, '2025-04-20 00:00:00', 100.07, 100.73, 100.89, 99.74);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (841, 43, '2025-04-01 00:00:00', 98.75, 98.58, 99.01, 98.57);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (842, 43, '2025-04-02 00:00:00', 100.86, 100.16, 101.22, 100.08);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (843, 43, '2025-04-03 00:00:00', 98.41, 99.07, 99.54, 98.31);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (844, 43, '2025-04-04 00:00:00', 99.97, 99.83, 100.43, 99.55);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (845, 43, '2025-04-05 00:00:00', 100.07, 99.2, 100.54, 98.98);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (846, 43, '2025-04-06 00:00:00', 100.41, 99.17, 100.79, 98.98);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (847, 43, '2025-04-07 00:00:00', 99.36, 100.06, 100.46, 98.95);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (848, 43, '2025-04-08 00:00:00', 100.76, 100.98, 101.15, 100.61);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (849, 43, '2025-04-09 00:00:00', 98.8, 100.09, 100.34, 98.43);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (850, 43, '2025-04-10 00:00:00', 100.36, 101.79, 102.24, 100.17);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (851, 43, '2025-04-11 00:00:00', 100.13, 99.69, 100.28, 99.48);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (852, 43, '2025-04-12 00:00:00', 98.13, 98.44, 98.66, 97.69);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (853, 43, '2025-04-13 00:00:00', 100.31, 99.84, 100.38, 99.66);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (854, 43, '2025-04-14 00:00:00', 98.89, 99.48, 99.77, 98.64);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (855, 43, '2025-04-15 00:00:00', 100.83, 100.6, 101.0, 100.37);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (856, 43, '2025-04-16 00:00:00', 99.45, 98.59, 99.72, 98.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (857, 43, '2025-04-17 00:00:00', 102.3, 101.03, 102.36, 100.72);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (858, 43, '2025-04-18 00:00:00', 99.4, 100.01, 100.28, 98.96);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (859, 43, '2025-04-19 00:00:00', 97.88, 99.1, 99.29, 97.6);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (860, 43, '2025-04-20 00:00:00', 101.24, 101.52, 101.58, 101.13);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (861, 44, '2025-04-01 00:00:00', 100.66, 101.7, 101.89, 100.34);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (862, 44, '2025-04-02 00:00:00', 99.82, 98.76, 100.08, 98.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (863, 44, '2025-04-03 00:00:00', 98.83, 100.4, 100.78, 98.6);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (864, 44, '2025-04-04 00:00:00', 97.97, 99.79, 99.82, 97.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (865, 44, '2025-04-05 00:00:00', 99.87, 100.01, 100.45, 99.45);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (866, 44, '2025-04-06 00:00:00', 101.94, 101.57, 102.3, 101.21);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (867, 44, '2025-04-07 00:00:00', 100.33, 100.42, 100.63, 100.06);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (868, 44, '2025-04-08 00:00:00', 100.29, 99.82, 100.76, 99.43);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (869, 44, '2025-04-09 00:00:00', 97.58, 99.43, 99.74, 97.28);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (870, 44, '2025-04-10 00:00:00', 101.05, 100.54, 101.17, 100.23);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (871, 44, '2025-04-11 00:00:00', 100.7, 100.26, 101.0, 99.78);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (872, 44, '2025-04-12 00:00:00', 100.2, 100.66, 100.93, 100.1);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (873, 44, '2025-04-13 00:00:00', 101.76, 102.05, 102.28, 101.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (874, 44, '2025-04-14 00:00:00', 99.66, 99.04, 99.74, 98.68);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (875, 44, '2025-04-15 00:00:00', 101.15, 100.54, 101.58, 100.17);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (876, 44, '2025-04-16 00:00:00', 100.17, 100.98, 101.41, 100.17);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (877, 44, '2025-04-17 00:00:00', 100.61, 100.24, 100.65, 99.83);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (878, 44, '2025-04-18 00:00:00', 98.96, 98.81, 99.42, 98.71);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (879, 44, '2025-04-19 00:00:00', 101.98, 101.54, 101.99, 101.31);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (880, 44, '2025-04-20 00:00:00', 98.59, 98.52, 98.9, 98.21);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (881, 45, '2025-04-01 00:00:00', 99.22, 98.62, 99.56, 98.3);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (882, 45, '2025-04-02 00:00:00', 100.91, 101.36, 101.77, 100.69);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (883, 45, '2025-04-03 00:00:00', 99.17, 98.62, 99.23, 98.39);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (884, 45, '2025-04-04 00:00:00', 98.58, 98.78, 99.08, 98.33);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (885, 45, '2025-04-05 00:00:00', 99.92, 98.98, 100.17, 98.59);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (886, 45, '2025-04-06 00:00:00', 100.22, 100.93, 101.19, 99.74);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (887, 45, '2025-04-07 00:00:00', 99.13, 99.8, 99.96, 99.09);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (888, 45, '2025-04-08 00:00:00', 100.56, 100.55, 100.68, 100.25);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (889, 45, '2025-04-09 00:00:00', 99.35, 100.42, 100.67, 99.05);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (890, 45, '2025-04-10 00:00:00', 102.16, 102.08, 102.57, 101.83);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (891, 45, '2025-04-11 00:00:00', 99.22, 100.09, 100.27, 99.01);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (892, 45, '2025-04-12 00:00:00', 98.41, 98.03, 98.91, 97.92);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (893, 45, '2025-04-13 00:00:00', 100.22, 100.6, 100.8, 100.1);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (894, 45, '2025-04-14 00:00:00', 99.11, 98.67, 99.29, 98.3);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (895, 45, '2025-04-15 00:00:00', 99.32, 100.63, 100.71, 99.11);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (896, 45, '2025-04-16 00:00:00', 99.51, 100.21, 100.5, 99.42);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (897, 45, '2025-04-17 00:00:00', 100.35, 101.49, 101.89, 99.92);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (898, 45, '2025-04-18 00:00:00', 100.81, 100.74, 101.28, 100.71);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (899, 45, '2025-04-19 00:00:00', 101.58, 101.94, 102.03, 101.45);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (900, 45, '2025-04-20 00:00:00', 98.88, 99.25, 99.65, 98.52);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (901, 46, '2025-04-01 00:00:00', 99.57, 98.38, 99.66, 98.18);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (902, 46, '2025-04-02 00:00:00', 99.43, 100.43, 100.55, 99.37);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (903, 46, '2025-04-03 00:00:00', 98.71, 98.78, 98.89, 98.58);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (904, 46, '2025-04-04 00:00:00', 100.8, 101.42, 101.83, 100.71);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (905, 46, '2025-04-05 00:00:00', 100.58, 100.09, 100.87, 99.96);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (906, 46, '2025-04-06 00:00:00', 102.17, 101.76, 102.29, 101.54);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (907, 46, '2025-04-07 00:00:00', 100.57, 99.79, 100.58, 99.3);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (908, 46, '2025-04-08 00:00:00', 97.68, 99.19, 99.61, 97.54);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (909, 46, '2025-04-09 00:00:00', 98.95, 100.49, 100.67, 98.78);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (910, 46, '2025-04-10 00:00:00', 97.9, 99.54, 100.04, 97.88);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (911, 46, '2025-04-11 00:00:00', 98.79, 99.22, 99.23, 98.59);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (912, 46, '2025-04-12 00:00:00', 98.35, 98.49, 98.92, 98.25);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (913, 46, '2025-04-13 00:00:00', 99.64, 99.22, 100.03, 99.0);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (914, 46, '2025-04-14 00:00:00', 101.48, 100.84, 101.86, 100.52);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (915, 46, '2025-04-15 00:00:00', 100.73, 101.83, 102.02, 100.63);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (916, 46, '2025-04-16 00:00:00', 101.17, 99.5, 101.19, 99.46);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (917, 46, '2025-04-17 00:00:00', 99.43, 100.02, 100.49, 99.05);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (918, 46, '2025-04-18 00:00:00', 102.43, 100.95, 102.55, 100.46);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (919, 46, '2025-04-19 00:00:00', 99.44, 98.52, 99.71, 98.21);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (920, 46, '2025-04-20 00:00:00', 99.38, 98.89, 99.57, 98.41);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (921, 47, '2025-04-01 00:00:00', 99.91, 100.2, 100.47, 99.47);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (922, 47, '2025-04-02 00:00:00', 98.47, 97.75, 98.88, 97.45);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (923, 47, '2025-04-03 00:00:00', 101.7, 100.71, 101.79, 100.36);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (924, 47, '2025-04-04 00:00:00', 100.6, 102.09, 102.24, 100.21);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (925, 47, '2025-04-05 00:00:00', 100.39, 100.28, 100.42, 99.91);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (926, 47, '2025-04-06 00:00:00', 99.37, 97.95, 99.5, 97.69);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (927, 47, '2025-04-07 00:00:00', 100.8, 99.0, 100.85, 98.55);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (928, 47, '2025-04-08 00:00:00', 100.43, 99.68, 100.56, 99.35);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (929, 47, '2025-04-09 00:00:00', 99.02, 98.12, 99.16, 98.01);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (930, 47, '2025-04-10 00:00:00', 99.96, 100.11, 100.18, 99.46);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (931, 47, '2025-04-11 00:00:00', 100.6, 100.15, 101.02, 99.89);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (932, 47, '2025-04-12 00:00:00', 101.28, 101.04, 101.68, 100.7);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (933, 47, '2025-04-13 00:00:00', 99.58, 100.88, 100.89, 99.53);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (934, 47, '2025-04-14 00:00:00', 100.7, 101.3, 101.38, 100.27);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (935, 47, '2025-04-15 00:00:00', 99.1, 100.26, 100.75, 98.83);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (936, 47, '2025-04-16 00:00:00', 100.87, 100.81, 101.24, 100.74);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (937, 47, '2025-04-17 00:00:00', 100.89, 101.01, 101.5, 100.51);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (938, 47, '2025-04-18 00:00:00', 100.44, 99.75, 100.53, 99.59);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (939, 47, '2025-04-19 00:00:00', 101.06, 101.43, 101.59, 100.64);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (940, 47, '2025-04-20 00:00:00', 100.88, 101.38, 101.4, 100.58);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (941, 48, '2025-04-01 00:00:00', 99.61, 99.31, 99.69, 99.02);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (942, 48, '2025-04-02 00:00:00', 99.66, 101.14, 101.55, 99.16);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (943, 48, '2025-04-03 00:00:00', 99.38, 98.78, 99.48, 98.47);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (944, 48, '2025-04-04 00:00:00', 100.75, 100.68, 100.88, 100.48);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (945, 48, '2025-04-05 00:00:00', 98.38, 98.93, 99.18, 98.0);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (946, 48, '2025-04-06 00:00:00', 98.28, 98.46, 98.6, 98.04);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (947, 48, '2025-04-07 00:00:00', 101.33, 100.13, 101.73, 99.7);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (948, 48, '2025-04-08 00:00:00', 100.96, 101.43, 101.86, 100.75);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (949, 48, '2025-04-09 00:00:00', 100.06, 99.54, 100.34, 99.33);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (950, 48, '2025-04-10 00:00:00', 100.81, 99.35, 101.2, 99.08);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (951, 48, '2025-04-11 00:00:00', 99.36, 98.81, 99.86, 98.34);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (952, 48, '2025-04-12 00:00:00', 100.49, 100.68, 101.11, 100.15);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (953, 48, '2025-04-13 00:00:00', 98.57, 99.74, 99.96, 98.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (954, 48, '2025-04-14 00:00:00', 99.3, 100.2, 100.66, 99.06);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (955, 48, '2025-04-15 00:00:00', 100.76, 99.53, 100.92, 99.35);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (956, 48, '2025-04-16 00:00:00', 100.34, 101.12, 101.2, 100.03);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (957, 48, '2025-04-17 00:00:00', 99.84, 99.63, 100.33, 99.38);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (958, 48, '2025-04-18 00:00:00', 101.05, 100.0, 101.24, 99.76);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (959, 48, '2025-04-19 00:00:00', 99.93, 99.92, 100.14, 99.8);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (960, 48, '2025-04-20 00:00:00', 99.0, 100.1, 100.32, 98.87);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (961, 49, '2025-04-01 00:00:00', 99.65, 98.2, 100.14, 97.77);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (962, 49, '2025-04-02 00:00:00', 99.38, 100.35, 100.4, 99.2);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (963, 49, '2025-04-03 00:00:00', 98.88, 100.46, 100.93, 98.81);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (964, 49, '2025-04-04 00:00:00', 100.76, 99.04, 100.89, 98.59);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (965, 49, '2025-04-05 00:00:00', 100.46, 99.76, 100.5, 99.34);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (966, 49, '2025-04-06 00:00:00', 98.97, 98.77, 99.04, 98.31);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (967, 49, '2025-04-07 00:00:00', 99.16, 99.24, 99.62, 98.96);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (968, 49, '2025-04-08 00:00:00', 99.71, 101.2, 101.24, 99.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (969, 49, '2025-04-09 00:00:00', 101.5, 101.02, 101.91, 100.97);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (970, 49, '2025-04-10 00:00:00', 99.43, 99.46, 99.88, 99.24);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (971, 49, '2025-04-11 00:00:00', 100.8, 99.95, 101.09, 99.8);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (972, 49, '2025-04-12 00:00:00', 99.74, 100.35, 100.81, 99.72);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (973, 49, '2025-04-13 00:00:00', 100.58, 99.69, 100.75, 99.25);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (974, 49, '2025-04-14 00:00:00', 99.84, 100.79, 100.92, 99.53);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (975, 49, '2025-04-15 00:00:00', 101.48, 101.95, 102.1, 101.39);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (976, 49, '2025-04-16 00:00:00', 98.77, 98.45, 99.23, 98.34);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (977, 49, '2025-04-17 00:00:00', 99.96, 100.47, 100.64, 99.68);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (978, 49, '2025-04-18 00:00:00', 98.99, 99.28, 99.41, 98.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (979, 49, '2025-04-19 00:00:00', 100.02, 100.2, 100.53, 99.55);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (980, 49, '2025-04-20 00:00:00', 98.21, 99.05, 99.35, 98.09);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (981, 50, '2025-04-01 00:00:00', 100.87, 99.92, 101.18, 99.91);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (982, 50, '2025-04-02 00:00:00', 99.29, 99.44, 99.49, 99.07);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (983, 50, '2025-04-03 00:00:00', 99.99, 100.29, 100.79, 99.75);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (984, 50, '2025-04-04 00:00:00', 100.92, 101.29, 101.49, 100.44);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (985, 50, '2025-04-05 00:00:00', 97.81, 99.04, 99.46, 97.54);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (986, 50, '2025-04-06 00:00:00', 101.32, 99.71, 101.33, 99.32);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (987, 50, '2025-04-07 00:00:00', 100.95, 101.25, 101.3, 100.51);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (988, 50, '2025-04-08 00:00:00', 101.44, 100.3, 101.54, 100.15);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (989, 50, '2025-04-09 00:00:00', 100.51, 100.52, 100.53, 100.32);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (990, 50, '2025-04-10 00:00:00', 99.06, 100.26, 100.46, 98.65);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (991, 50, '2025-04-11 00:00:00', 98.79, 99.77, 100.04, 98.62);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (992, 50, '2025-04-12 00:00:00', 98.6, 98.72, 98.8, 98.52);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (993, 50, '2025-04-13 00:00:00', 99.3, 99.53, 100.0, 99.14);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (994, 50, '2025-04-14 00:00:00', 99.46, 99.22, 99.66, 99.08);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (995, 50, '2025-04-15 00:00:00', 101.03, 99.73, 101.52, 99.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (996, 50, '2025-04-16 00:00:00', 99.19, 100.32, 100.55, 98.85);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (997, 50, '2025-04-17 00:00:00', 101.07, 100.49, 101.47, 100.01);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (998, 50, '2025-04-18 00:00:00', 98.64, 99.17, 99.5, 98.17);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (999, 50, '2025-04-19 00:00:00', 99.4, 99.67, 99.74, 98.96);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1000, 50, '2025-04-20 00:00:00', 99.55, 98.6, 99.83, 98.55);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1001, 51, '2025-04-01 00:00:00', 100.87, 100.16, 100.95, 99.82);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1002, 51, '2025-04-02 00:00:00', 100.72, 99.76, 100.82, 99.43);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1003, 51, '2025-04-03 00:00:00', 98.73, 98.9, 99.07, 98.36);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1004, 51, '2025-04-04 00:00:00', 98.64, 99.18, 99.44, 98.45);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1005, 51, '2025-04-05 00:00:00', 100.6, 101.1, 101.17, 100.57);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1006, 51, '2025-04-06 00:00:00', 100.49, 100.18, 100.62, 100.09);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1007, 51, '2025-04-07 00:00:00', 101.13, 101.43, 101.63, 100.64);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1008, 51, '2025-04-08 00:00:00', 98.94, 98.4, 99.28, 98.06);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1009, 51, '2025-04-09 00:00:00', 102.26, 102.1, 102.67, 101.93);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1010, 51, '2025-04-10 00:00:00', 99.96, 99.17, 100.17, 98.96);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1011, 51, '2025-04-11 00:00:00', 99.99, 101.4, 101.4, 99.73);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1012, 51, '2025-04-12 00:00:00', 100.12, 101.17, 101.18, 99.63);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1013, 51, '2025-04-13 00:00:00', 100.07, 99.39, 100.12, 99.36);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1014, 51, '2025-04-14 00:00:00', 98.69, 99.47, 99.51, 98.27);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1015, 51, '2025-04-15 00:00:00', 99.56, 99.46, 99.84, 99.25);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1016, 51, '2025-04-16 00:00:00', 100.27, 100.02, 100.75, 100.02);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1017, 51, '2025-04-17 00:00:00', 99.39, 98.61, 99.72, 98.43);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1018, 51, '2025-04-18 00:00:00', 98.38, 99.02, 99.33, 98.18);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1019, 51, '2025-04-19 00:00:00', 101.74, 101.02, 101.85, 100.96);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1020, 51, '2025-04-20 00:00:00', 101.41, 101.14, 101.61, 100.91);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1021, 52, '2025-04-01 00:00:00', 99.81, 100.35, 100.47, 99.67);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1022, 52, '2025-04-02 00:00:00', 100.2, 100.67, 100.91, 100.01);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1023, 52, '2025-04-03 00:00:00', 101.43, 101.61, 101.96, 101.34);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1024, 52, '2025-04-04 00:00:00', 100.9, 100.63, 101.31, 100.58);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1025, 52, '2025-04-05 00:00:00', 101.0, 100.23, 101.19, 99.82);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1026, 52, '2025-04-06 00:00:00', 101.04, 101.37, 101.39, 100.63);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1027, 52, '2025-04-07 00:00:00', 98.12, 99.15, 99.41, 97.83);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1028, 52, '2025-04-08 00:00:00', 98.34, 99.75, 99.93, 97.98);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1029, 52, '2025-04-09 00:00:00', 100.02, 99.82, 100.09, 99.58);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1030, 52, '2025-04-10 00:00:00', 99.65, 100.78, 100.81, 99.39);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1031, 52, '2025-04-11 00:00:00', 100.45, 101.61, 101.83, 100.15);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1032, 52, '2025-04-12 00:00:00', 100.27, 100.06, 100.36, 99.88);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1033, 52, '2025-04-13 00:00:00', 102.03, 101.99, 102.16, 101.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1034, 52, '2025-04-14 00:00:00', 100.24, 100.72, 100.92, 99.94);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1035, 52, '2025-04-15 00:00:00', 100.34, 100.65, 101.04, 100.1);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1036, 52, '2025-04-16 00:00:00', 99.87, 99.74, 99.88, 99.47);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1037, 52, '2025-04-17 00:00:00', 98.01, 98.42, 98.47, 97.98);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1038, 52, '2025-04-18 00:00:00', 102.15, 100.84, 102.22, 100.42);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1039, 52, '2025-04-19 00:00:00', 101.5, 100.28, 101.93, 99.82);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1040, 52, '2025-04-20 00:00:00', 99.91, 100.54, 100.84, 99.47);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1041, 53, '2025-04-01 00:00:00', 100.25, 101.44, 101.76, 100.07);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1042, 53, '2025-04-02 00:00:00', 99.55, 98.7, 99.75, 98.25);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1043, 53, '2025-04-03 00:00:00', 100.07, 101.83, 102.3, 99.69);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1044, 53, '2025-04-04 00:00:00', 99.35, 98.62, 99.46, 98.58);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1045, 53, '2025-04-05 00:00:00', 100.76, 100.84, 100.94, 100.75);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1046, 53, '2025-04-06 00:00:00', 98.97, 99.13, 99.58, 98.78);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1047, 53, '2025-04-07 00:00:00', 100.92, 101.83, 101.93, 100.69);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1048, 53, '2025-04-08 00:00:00', 99.98, 100.09, 100.48, 99.77);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1049, 53, '2025-04-09 00:00:00', 101.69, 100.93, 101.85, 100.85);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1050, 53, '2025-04-10 00:00:00', 100.89, 100.58, 101.32, 100.39);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1051, 53, '2025-04-11 00:00:00', 100.13, 101.13, 101.63, 99.94);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1052, 53, '2025-04-12 00:00:00', 100.95, 100.88, 101.22, 100.81);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1053, 53, '2025-04-13 00:00:00', 100.87, 100.8, 101.04, 100.49);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1054, 53, '2025-04-14 00:00:00', 100.16, 100.73, 100.89, 99.87);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1055, 53, '2025-04-15 00:00:00', 99.42, 98.73, 99.52, 98.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1056, 53, '2025-04-16 00:00:00', 100.7, 100.64, 100.72, 100.57);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1057, 53, '2025-04-17 00:00:00', 98.17, 98.36, 98.78, 97.72);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1058, 53, '2025-04-18 00:00:00', 100.87, 101.68, 101.98, 100.58);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1059, 53, '2025-04-19 00:00:00', 101.4, 101.78, 102.1, 100.93);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1060, 53, '2025-04-20 00:00:00', 99.2, 98.57, 99.41, 98.13);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1061, 54, '2025-04-01 00:00:00', 102.07, 102.14, 102.56, 101.95);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1062, 54, '2025-04-02 00:00:00', 100.18, 100.0, 100.21, 99.69);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1063, 54, '2025-04-03 00:00:00', 101.03, 101.3, 101.51, 100.83);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1064, 54, '2025-04-04 00:00:00', 100.1, 99.63, 100.58, 99.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1065, 54, '2025-04-05 00:00:00', 97.81, 98.48, 98.98, 97.77);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1066, 54, '2025-04-06 00:00:00', 102.01, 100.91, 102.47, 100.59);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1067, 54, '2025-04-07 00:00:00', 100.95, 99.81, 101.3, 99.36);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1068, 54, '2025-04-08 00:00:00', 100.12, 100.19, 100.37, 99.7);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1069, 54, '2025-04-09 00:00:00', 100.15, 99.23, 100.16, 99.13);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1070, 54, '2025-04-10 00:00:00', 101.14, 100.8, 101.18, 100.63);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1071, 54, '2025-04-11 00:00:00', 100.63, 101.42, 101.7, 100.24);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1072, 54, '2025-04-12 00:00:00', 100.11, 100.75, 100.93, 99.7);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1073, 54, '2025-04-13 00:00:00', 98.96, 99.66, 99.96, 98.77);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1074, 54, '2025-04-14 00:00:00', 100.66, 101.3, 101.59, 100.63);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1075, 54, '2025-04-15 00:00:00', 100.53, 100.29, 100.71, 100.2);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1076, 54, '2025-04-16 00:00:00', 98.43, 99.16, 99.17, 98.38);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1077, 54, '2025-04-17 00:00:00', 99.67, 99.93, 100.16, 99.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1078, 54, '2025-04-18 00:00:00', 100.68, 99.69, 100.78, 99.47);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1079, 54, '2025-04-19 00:00:00', 100.69, 99.33, 100.91, 99.24);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1080, 54, '2025-04-20 00:00:00', 100.43, 99.86, 100.61, 99.41);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1081, 55, '2025-04-01 00:00:00', 99.18, 100.3, 100.62, 99.12);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1082, 55, '2025-04-02 00:00:00', 100.9, 100.37, 101.01, 100.27);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1083, 55, '2025-04-03 00:00:00', 100.32, 100.9, 101.35, 100.31);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1084, 55, '2025-04-04 00:00:00', 99.97, 100.29, 100.53, 99.69);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1085, 55, '2025-04-05 00:00:00', 101.04, 101.28, 101.68, 100.88);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1086, 55, '2025-04-06 00:00:00', 99.53, 99.55, 99.73, 99.08);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1087, 55, '2025-04-07 00:00:00', 99.55, 99.07, 99.77, 98.74);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1088, 55, '2025-04-08 00:00:00', 97.77, 97.82, 98.21, 97.77);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1089, 55, '2025-04-09 00:00:00', 99.87, 99.18, 100.2, 98.69);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1090, 55, '2025-04-10 00:00:00', 99.31, 98.98, 99.67, 98.9);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1091, 55, '2025-04-11 00:00:00', 100.95, 100.52, 101.04, 100.47);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1092, 55, '2025-04-12 00:00:00', 102.1, 102.42, 102.8, 102.08);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1093, 55, '2025-04-13 00:00:00', 101.16, 101.25, 101.38, 100.75);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1094, 55, '2025-04-14 00:00:00', 101.22, 101.79, 102.21, 100.99);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1095, 55, '2025-04-15 00:00:00', 99.82, 99.76, 99.97, 99.63);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1096, 55, '2025-04-16 00:00:00', 99.43, 100.69, 100.98, 99.05);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1097, 55, '2025-04-17 00:00:00', 100.83, 99.86, 100.87, 99.37);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1098, 55, '2025-04-18 00:00:00', 100.62, 99.35, 100.88, 99.14);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1099, 55, '2025-04-19 00:00:00', 100.78, 101.59, 101.75, 100.53);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1100, 55, '2025-04-20 00:00:00', 99.51, 97.63, 99.74, 97.39);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1101, 56, '2025-04-01 00:00:00', 98.59, 99.86, 100.05, 98.59);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1102, 56, '2025-04-02 00:00:00', 98.3, 99.02, 99.26, 98.13);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1103, 56, '2025-04-03 00:00:00', 99.02, 99.52, 99.8, 98.93);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1104, 56, '2025-04-04 00:00:00', 101.53, 101.75, 101.9, 101.29);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1105, 56, '2025-04-05 00:00:00', 101.41, 100.75, 101.76, 100.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1106, 56, '2025-04-06 00:00:00', 100.16, 99.99, 100.64, 99.92);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1107, 56, '2025-04-07 00:00:00', 99.18, 99.94, 100.19, 98.93);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1108, 56, '2025-04-08 00:00:00', 101.51, 101.29, 101.57, 101.08);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1109, 56, '2025-04-09 00:00:00', 100.19, 100.74, 101.0, 99.9);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1110, 56, '2025-04-10 00:00:00', 100.4, 101.89, 102.28, 100.39);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1111, 56, '2025-04-11 00:00:00', 99.15, 99.93, 100.13, 98.82);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1112, 56, '2025-04-12 00:00:00', 101.35, 101.54, 101.64, 101.13);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1113, 56, '2025-04-13 00:00:00', 99.52, 98.94, 99.89, 98.83);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1114, 56, '2025-04-14 00:00:00', 101.44, 101.9, 102.37, 101.21);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1115, 56, '2025-04-15 00:00:00', 99.74, 99.34, 100.09, 99.3);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1116, 56, '2025-04-16 00:00:00', 99.64, 98.86, 99.8, 98.74);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1117, 56, '2025-04-17 00:00:00', 99.95, 101.16, 101.27, 99.74);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1118, 56, '2025-04-18 00:00:00', 100.49, 100.86, 101.14, 100.44);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1119, 56, '2025-04-19 00:00:00', 99.94, 98.48, 100.06, 98.35);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1120, 56, '2025-04-20 00:00:00', 98.23, 99.03, 99.12, 97.81);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1121, 57, '2025-04-01 00:00:00', 100.51, 101.54, 101.72, 100.46);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1122, 57, '2025-04-02 00:00:00', 99.46, 100.14, 100.63, 99.15);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1123, 57, '2025-04-03 00:00:00', 100.43, 99.68, 100.81, 99.55);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1124, 57, '2025-04-04 00:00:00', 99.34, 99.24, 99.5, 99.2);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1125, 57, '2025-04-05 00:00:00', 100.17, 99.73, 100.44, 99.29);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1126, 57, '2025-04-06 00:00:00', 100.7, 101.22, 101.36, 100.35);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1127, 57, '2025-04-07 00:00:00', 100.3, 99.51, 100.52, 99.17);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1128, 57, '2025-04-08 00:00:00', 100.46, 99.28, 100.94, 99.17);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1129, 57, '2025-04-09 00:00:00', 100.64, 100.98, 101.11, 100.54);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1130, 57, '2025-04-10 00:00:00', 101.27, 99.56, 101.6, 99.07);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1131, 57, '2025-04-11 00:00:00', 100.33, 100.47, 100.54, 100.2);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1132, 57, '2025-04-12 00:00:00', 100.09, 100.11, 100.18, 99.66);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1133, 57, '2025-04-13 00:00:00', 100.66, 100.17, 101.08, 100.16);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1134, 57, '2025-04-14 00:00:00', 99.35, 100.96, 101.2, 98.89);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1135, 57, '2025-04-15 00:00:00', 99.07, 98.3, 99.1, 98.28);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1136, 57, '2025-04-16 00:00:00', 100.16, 99.26, 100.16, 99.19);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1137, 57, '2025-04-17 00:00:00', 101.49, 101.41, 101.61, 100.96);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1138, 57, '2025-04-18 00:00:00', 101.29, 100.85, 101.62, 100.67);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1139, 57, '2025-04-19 00:00:00', 101.05, 100.91, 101.49, 100.57);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1140, 57, '2025-04-20 00:00:00', 100.95, 100.49, 100.96, 100.37);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1141, 58, '2025-04-01 00:00:00', 101.1, 101.77, 101.84, 100.71);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1142, 58, '2025-04-02 00:00:00', 100.58, 100.4, 100.85, 100.3);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1143, 58, '2025-04-03 00:00:00', 99.98, 100.28, 100.65, 99.55);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1144, 58, '2025-04-04 00:00:00', 99.77, 99.75, 100.06, 99.36);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1145, 58, '2025-04-05 00:00:00', 98.81, 99.57, 99.88, 98.44);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1146, 58, '2025-04-06 00:00:00', 98.8, 98.02, 99.24, 97.74);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1147, 58, '2025-04-07 00:00:00', 101.06, 102.32, 102.41, 101.0);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1148, 58, '2025-04-08 00:00:00', 101.4, 100.12, 101.43, 99.81);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1149, 58, '2025-04-09 00:00:00', 100.0, 98.74, 100.48, 98.67);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1150, 58, '2025-04-10 00:00:00', 99.73, 101.27, 101.71, 99.65);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1151, 58, '2025-04-11 00:00:00', 99.53, 101.05, 101.32, 99.26);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1152, 58, '2025-04-12 00:00:00', 98.56, 98.4, 98.75, 98.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1153, 58, '2025-04-13 00:00:00', 98.62, 100.17, 100.38, 98.53);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1154, 58, '2025-04-14 00:00:00', 100.76, 99.51, 100.9, 99.04);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1155, 58, '2025-04-15 00:00:00', 101.42, 101.02, 101.85, 101.0);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1156, 58, '2025-04-16 00:00:00', 98.67, 97.9, 98.98, 97.8);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1157, 58, '2025-04-17 00:00:00', 100.03, 99.11, 100.23, 99.06);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1158, 58, '2025-04-18 00:00:00', 99.16, 99.45, 99.85, 98.83);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1159, 58, '2025-04-19 00:00:00', 98.33, 98.59, 99.08, 97.88);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1160, 58, '2025-04-20 00:00:00', 99.37, 99.11, 99.77, 98.73);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1161, 59, '2025-04-01 00:00:00', 99.71, 100.66, 101.13, 99.38);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1162, 59, '2025-04-02 00:00:00', 99.67, 98.4, 99.84, 98.18);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1163, 59, '2025-04-03 00:00:00', 100.61, 100.91, 101.3, 100.14);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1164, 59, '2025-04-04 00:00:00', 99.51, 98.87, 99.69, 98.57);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1165, 59, '2025-04-05 00:00:00', 100.33, 100.35, 100.61, 100.08);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1166, 59, '2025-04-06 00:00:00', 100.71, 100.99, 101.04, 100.51);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1167, 59, '2025-04-07 00:00:00', 98.46, 99.94, 99.97, 98.4);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1168, 59, '2025-04-08 00:00:00', 99.4, 99.61, 99.9, 99.37);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1169, 59, '2025-04-09 00:00:00', 101.08, 101.55, 102.05, 100.96);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1170, 59, '2025-04-10 00:00:00', 97.93, 97.98, 98.13, 97.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1171, 59, '2025-04-11 00:00:00', 99.49, 98.78, 99.99, 98.43);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1172, 59, '2025-04-12 00:00:00', 99.94, 100.21, 100.59, 99.58);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1173, 59, '2025-04-13 00:00:00', 99.23, 100.38, 100.82, 98.75);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1174, 59, '2025-04-14 00:00:00', 99.36, 98.93, 99.45, 98.47);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1175, 59, '2025-04-15 00:00:00', 100.65, 101.05, 101.43, 100.37);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1176, 59, '2025-04-16 00:00:00', 99.52, 98.8, 99.58, 98.48);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1177, 59, '2025-04-17 00:00:00', 101.01, 100.4, 101.1, 99.91);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1178, 59, '2025-04-18 00:00:00', 99.85, 100.31, 100.59, 99.85);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1179, 59, '2025-04-19 00:00:00', 101.34, 100.9, 101.4, 100.5);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1180, 59, '2025-04-20 00:00:00', 101.0, 100.46, 101.15, 100.41);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1181, 60, '2025-04-01 00:00:00', 99.88, 99.95, 100.33, 99.67);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1182, 60, '2025-04-02 00:00:00', 98.07, 98.7, 99.19, 97.67);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1183, 60, '2025-04-03 00:00:00', 98.86, 99.86, 99.86, 98.36);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1184, 60, '2025-04-04 00:00:00', 100.4, 99.85, 100.9, 99.53);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1185, 60, '2025-04-05 00:00:00', 97.76, 98.13, 98.41, 97.52);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1186, 60, '2025-04-06 00:00:00', 100.81, 100.48, 101.11, 100.34);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1187, 60, '2025-04-07 00:00:00', 98.5, 99.38, 99.58, 98.12);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1188, 60, '2025-04-08 00:00:00', 99.33, 100.33, 100.49, 99.02);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1189, 60, '2025-04-09 00:00:00', 100.59, 100.77, 100.83, 100.43);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1190, 60, '2025-04-10 00:00:00', 99.79, 99.39, 99.88, 99.32);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1191, 60, '2025-04-11 00:00:00', 99.99, 99.78, 100.3, 99.28);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1192, 60, '2025-04-12 00:00:00', 99.86, 99.32, 100.29, 98.93);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1193, 60, '2025-04-13 00:00:00', 99.78, 100.08, 100.1, 99.6);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1194, 60, '2025-04-14 00:00:00', 99.46, 100.43, 100.92, 99.13);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1195, 60, '2025-04-15 00:00:00', 101.81, 101.01, 101.92, 100.85);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1196, 60, '2025-04-16 00:00:00', 100.26, 99.44, 100.29, 99.1);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1197, 60, '2025-04-17 00:00:00', 100.0, 99.73, 100.02, 99.35);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1198, 60, '2025-04-18 00:00:00', 100.92, 100.19, 101.3, 100.05);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1199, 60, '2025-04-19 00:00:00', 100.12, 98.98, 100.34, 98.68);
INSERT INTO kurs (kurs_id, wertpapier_id, datum, eröffnungskurs, schlusskurs, high, low) VALUES (1200, 60, '2025-04-20 00:00:00', 99.59, 99.25, 99.73, 98.83);
