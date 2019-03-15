INSERT INTO `freight_management`.`place`
VALUES (1, 'Stockholm', 'STKHM', NULL, 18.069915, 59.347553, 'ADMIN', NULL, '2017-10-10 16:46:24', NULL),
  (2, 'Gothenburg', 'GTB', NULL, 11.974831, 57.72929, 'ADMIN', NULL, '2017-10-10 16:47:31', NULL),
  (3, 'Ft. Lauderdale', 'FLD', NULL, -80.134927, 26.127534, 'ADMIN', NULL, '2017-10-10 16:49:18', NULL),
  (4, 'Orlando', 'ORLD', NULL, -81.384556, 28.537212, 'ADMIN', NULL, '2017-10-10 16:50:58', NULL),
  (5, 'Savannah', 'SVNNH', NULL, -81.1021, 32.081829, 'ADMIN', NULL, '2017-10-10 16:52:00', NULL),
  (6, 'Rotterdam', 'RTTD', NULL, 4.484904, 51.922384, 'ADMIN', NULL, '2017-10-10 16:53:41', NULL),
  (7, 'Cox\'s Bazar', 'COX', NULL, 92.005302, 21.440019, 'ADMIN', NULL, '2017-10-10 15:10:17', NULL),
  (8, 'Chittagong', 'CTG', NULL, 91.819854, 22.353184, 'ADMIN', NULL, '2017-10-10 17:11:53', NULL),
  (9, 'Mirpur', 'MRP', NULL, 90.366228, 23.823343, 'ADMIN', NULL, '2017-10-10 17:13:02', NULL),
  (10, 'Mohakhali', 'MKH', NULL, 90.404933, 23.777658, 'ADMIN', NULL, '2017-10-10 17:13:59', NULL),
  (11, 'Comilla', 'CML', NULL, 91.188435, 23.46232, 'ADMIN', NULL, '2017-10-10 17:14:48', NULL);


INSERT INTO `freight_management`.`path` VALUES (1, 'STKHM', 'GTB', 20, 420, 'Road', 1, 'ADMIN', NULL, '2017-10-10 17:29:26', NULL),
  (2, 'STKHM', 'GTB', 40, 430, 'Road', 1, 'ADMIN', NULL, '2017-10-10 17:30:16', NULL),
  (23, 'GTB', 'FLD', 20, 1623, 'Ocean', 22, 'ADMIN', NULL, '2017-10-10 18:45:47', NULL),
  (24, 'GTB', 'FLD', 40, 2500, 'Ocean', 22, 'ADMIN', NULL, '2017-10-10 18:46:28', NULL),
  (25, 'FLD', 'ORLD', 20, 600, 'Road', 1, 'ADMIN', NULL, '2017-10-10 18:47:46', NULL),
  (26, 'FLD', 'ORLD', 40, 900, 'Road', 1, 'ADMIN', NULL, '2017-10-10 18:48:07', NULL),
  (27, 'GTB', 'SVNNH', 20, 1765, 'Ocean', 23, 'ADMIN', NULL, '2017-10-10 18:49:06', NULL),
  (28, 'GTB', 'SVNNH', 40, 2600, 'Ocean', 23, 'ADMIN', NULL, '2017-10-10 18:50:35', NULL),
  (29, 'SVNNH', 'ORLD', 20, 600, 'Road', 1, 'ADMIN', NULL, '2017-10-10 18:51:30', NULL),
  (30, 'SVNNH', 'ORLD', 40, 900, 'Road', 1, 'ADMIN', NULL, '2017-10-10 18:51:49', NULL),
  (31, 'STKHM', 'RTTD', 20, 1430, 'Road', 3, 'ADMIN', NULL, '2017-10-10 18:52:59', NULL),
  (32, 'STKHM', 'RTTD', 40, 2600, 'Road', 3, 'ADMIN', NULL, '2017-10-10 18:53:16', NULL),
  (33, 'RTTD', 'FLD', 20, 1623, 'Ocean', 18, 'ADMIN', NULL, '2017-10-10 18:54:45', NULL),
  (35, 'RTTD', 'FLD', 40, 2600, 'Ocean', 18, 'ADMIN', NULL, '2017-10-10 18:55:12', NULL),
  (36, 'COX', 'CTG', 20, 50, 'Road', 1, 'ADMIN', NULL, '2017-10-10 18:56:35', NULL),
  (37, 'CTG', 'MRP', 20, 62, 'Road', 1, 'ADMIN', NULL, '2017-10-10 18:57:24', NULL),
  (38, 'CTG', 'MKH', 20, 65, 'Road', 1, 'ADMIN', NULL, '2017-10-10 18:58:03', NULL),
  (39, 'COX', 'CML', 20, 85, 'Road', 1, 'ADMIN', NULL, '2017-10-10 18:59:05', NULL),
  (40, 'CML', 'MRP', 20, 32, 'Road', 1, 'ADMIN', NULL, '2017-10-10 18:59:45', NULL),
  (41, 'CML', 'MKH', 20, 35, 'Road', 1, 'ADMIN', NULL, '2017-10-10 19:00:12', NULL),
  (42, 'COX', 'CTG', 40, 80, 'Road', 1, 'ADMIN', NULL, '2017-10-10 19:01:15', NULL),
  (43, 'CTG', 'MRP', 40, 92, 'Road', 1, 'ADMIN', NULL, '2017-10-10 19:01:46', NULL),
  (44, 'CTG', 'MKH', 40, 92, 'Road', 1, 'ADMIN', NULL, '2017-10-10 19:02:03', NULL),
  (45, 'COX', 'CML', 40, 115, 'Road', 1, 'ADMIN', NULL, '2017-10-10 19:02:56', NULL),
  (46, 'CML', 'MRP', 40, 62, 'Road', 1, 'ADMIN', NULL, '2017-10-10 19:03:28', NULL),
  (47, 'CML', 'MKH', 40, 65, 'Road', 1, 'ADMIN', NULL, '2017-10-10 19:03:57', NULL);




