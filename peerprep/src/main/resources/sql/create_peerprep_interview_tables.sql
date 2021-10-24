CREATE TABLE IF NOT EXISTS `interview_questions` (
  `id` int unsigned PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(255),
  `content` text,
  `difficulty` int,

  INDEX (difficulty)
);

CREATE TABLE IF NOT EXISTS `interview_solutions` (
  `id` int unsigned PRIMARY KEY AUTO_INCREMENT,
  `question_id` int unsigned,
  `content` text,

  INDEX (question_id)
);

CREATE TABLE IF NOT EXISTS `users_questions_history` (
  `id` int unsigned PRIMARY KEY AUTO_INCREMENT,
  `user_id` int unsigned,
  `question_id` int unsigned,
  `user_answer` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` timestamp,

  INDEX (user_id),
  INDEX (question_id)
);

ALTER TABLE `interview_solutions` ADD FOREIGN KEY (`question_id`) REFERENCES `interview_questions` (`id`);

ALTER TABLE `users_questions_history` ADD FOREIGN KEY (`question_id`) REFERENCES `interview_questions` (`id`);
