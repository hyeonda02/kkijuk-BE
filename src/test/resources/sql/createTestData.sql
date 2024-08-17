insert into `member` (`member_id`, `email`, `name`, `phone_number`, `birth_date`, `password`, `marketing_agree`, `user_state`, `field`)
values (1, 'test@test.com', 'tester', '010-0000-0000', '2024-08-06', 'testPassword', 'BOTH', 'ACTIVATE', '[]');

insert into `recruit` (`id`, `member_id`, `title`, `status`, `start_time`, `end_time`, `apply_date`, `active`, `link`, `tags`)
values (1, 1, 'testTitle', 'PLANNED', '2022-09-18 19:11:00.000000', '2024-08-21 01:26:00.000000', '2024-08-05', 1, 'testLink.com', '[]');

insert into `category` (`category_id`, `category_name`)
values (1, '동아리');

insert into `career` (`career_id`, `member_id`,`category_id`, `career_name`, `career_alias`, `career_summary`, `career_startdate`, `career_enddate`, `career_unknown`, `career_year`)
values (1, 1, 1,'IT 서비스 개발 동아리', 'UMC', '웹서비스를 개발함', '2024-03-01', '2024-08-23', FALSE, '2024');

insert into `tag` (`tag_id`, `member_id`, `tag_name`)
values (1, 1, '커뮤니케이션 능력');

insert into `tag` (`tag_id`, `member_id`, `tag_name`)
values (2, 1, '피그마 활용 능력')
