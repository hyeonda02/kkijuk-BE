insert into `member` (`member_id`, `email`, `name`, `phone_number`, `birth_date`, `password`, `marketing_agree`, `user_state`, `field`, `recruit_tags`)
values (1, 'test@test.com', 'tester', '010-0000-0000', '2024-08-06', '$2a$10$pRaYeoVdud3/Fq6QzwfQ6eMrR2BFG5c4wnj2zmygWOMmwZWK7Nh7S', 'BOTH', 'ACTIVATE', '["game", "computer"]', '[]');

insert into `recruit` (`id`, `member_id`, `title`, `status`, `start_time`, `end_time`, `apply_date`, `active`, `link`, `tags`)
values (1, 1, 'testTitle', 'PLANNED', '2022-09-18 19:11:00.000000', '2024-08-21 01:26:00.000000', '2024-08-05', 1, 'testLink.com', '[]');
