insert into `member` (`member_id`, `email`, `name`, `phone_number`, `birth_date`, `password`, `marketing_agree`, `user_state`, `field`, `recruit_tags`)
values (1, 'test@test.com', 'tester', '010-0000-0000', '2024-08-06', 'testPassword', 'BOTH', 'ACTIVATE', '[]', '[]');

insert into `category` (`category_id`, `category_name`)
values (1, '동아리');

insert into `category` (`category_id`, `category_name`)
values (2, '공모전');

insert into `career` (`career_id`, `member_id`,`category_id`, `career_name`, `career_alias`, `career_summary`, `career_startdate`, `career_enddate`, `career_unknown`, `career_year`)
values (1, 1, 1,'IT 서비스 개발 동아리', 'UMC', '웹서비스를 개발함', '2024-03-01', '2024-08-23', FALSE, '2024');

insert into `career` (`career_id`, `member_id`,`category_id`, `career_name`, `career_alias`, `career_summary`, `career_startdate`, `career_enddate`, `career_unknown`, `career_year`)
values (2, 1, 1,'앱 개발 동아리', '멋쟁이 사자처럼', '안드로이드 앱서비스를 개발함', '2023-03-01', '2023-08-23', FALSE, '2023');

insert into `tag` (`tag_id`, `member_id`, `tag_name`)
values (1, 1, '커뮤니케이션 능력');

insert into `tag` (`tag_id`, `member_id`, `tag_name`)
values (2, 1, '피그마 활용 능력');

insert into `career_detail` (`career_id`, `member_id`,`career_detail_id`, `career_detail_title`, `career_detail_content`, `career_detail_startdate`, `career_detail_enddate`)
values (1, 1, 1,'아이디어톤', '아이디어를 발표함. 투표결과 우수상', '2024-03-01', '2024-08-23');
