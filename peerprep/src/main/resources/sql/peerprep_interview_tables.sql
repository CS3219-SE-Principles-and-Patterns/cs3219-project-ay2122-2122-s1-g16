CREATE TABLE IF NOT EXISTS `interview_questions` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(255),
  `content` text,
  `difficulty` int
);

CREATE TABLE IF NOT EXISTS `interview_solutions` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `question_id` bigint,
  `content` text
);

CREATE TABLE IF NOT EXISTS `users_questions_history` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `user_id` bigint,
  `question_id` bigint,
  `user_answer` text,
  `created_at` timestamp,
  `deleted_at` timestamp
);

CREATE TABLE IF NOT EXISTS `users_roles_history` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `user_id` bigint,
  `role` int,
  `created_at` timestamp,
  `deleted_at` timestamp
);

ALTER TABLE `interview_solutions` ADD FOREIGN KEY (`question_id`) REFERENCES `interview_questions` (`id`);

ALTER TABLE `users_questions_history` ADD FOREIGN KEY (`question_id`) REFERENCES `interview_questions` (`id`);
