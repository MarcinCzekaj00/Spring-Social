insert into user_details (date_of_birth,phone_number) values ("1995-02-01",847253841);
insert into user (email,first_name,last_name,password,role,user_details_id) values ("test1@wp.pl","Wojciech","Zabrzewski","{bcrypt}$2a$12$zM3fGNvTz1y83UMJiqD8Eek2nL7HsbTvfkUvxhsXYmadsG9Zsk5oi",0,1);

insert into user_details (date_of_birth,phone_number) values ("1992-06-05",521452321);
insert into user (email,first_name,last_name,password,role,user_details_id) values ("test2@wp.pl","Maria","Zabrzewska","{bcrypt}$2a$12$ANiRdjJgUfM79crWIid8NelcQOStVx8IoEBYH.4s0l/xfYRjq6GzW",0,2);

insert into user_details (date_of_birth,phone_number) values ("2002-12-12",524321643);
insert into user (email,first_name,last_name,password,role,user_details_id) values ("test3@wp.pl","Pawel","Lichocki","{bcrypt}$2a$12$oQoT.CFs4IsZv0JQp605h.CnZ76.zeWrVvSgX7JdP15YM.uR4ZZJK",0,3);

insert into user_details (date_of_birth,phone_number) values ("1990-01-02",745291745);
insert into user (email,first_name,last_name,password,role,user_details_id) values ("test4@wp.pl","Olgier","Cichy","{bcrypt}$2a$12$jC93b7s6hDCvP6yYwjKMiOUNgiYuGkmMB76OMrgA9fOepqdqcpsm.",0,4);

insert into relationship (from_user_id,to_user_id,friends) values (1,2,1);
insert into relationship (from_user_id,to_user_id,friends) values (2,1,2);
insert into relationship (from_user_id,to_user_id,friends) values (1,3,1);
insert into relationship (from_user_id,to_user_id,friends) values (3,1,3);
insert into relationship (from_user_id,to_user_id,friends) values (2,3,2);
insert into relationship (from_user_id,to_user_id,friends) values (3,2,3);
insert into relationship (from_user_id,to_user_id,friends) values (1,4,1);
insert into relationship (from_user_id,to_user_id,friends) values (4,1,4);
insert into relationship (from_user_id,to_user_id,friends) values (2,4,2);
insert into relationship (from_user_id,to_user_id,friends) values (4,2,4);

insert into post (content,time_created,post_user_id) values ("Hello its my first post","2020-02-01",1);
insert into comment (content,post_id,time_created) values ("Hi nice to meet you",1,"2020-02-02");
insert into comment (content,post_id,time_created) values ("Nice to see you here",1,"2020-05-16");
insert into comment (content,post_id,time_created) values ("Hi boyyyzz haha",1,"2021-02-16");

insert into post (content,time_created,post_user_id) values ("Hello how you doin?","2016-12-12",2);
insert into comment (content,post_id,time_created) values ("Fine!",2,"2016-12-13");
insert into comment (content,post_id,time_created) values ("Not good today...",2,"2017-02-12");
insert into comment (content,post_id,time_created) values ("No comment.....",2,"2018-06-21");

insert into post (content,time_created,post_user_id) values ("Someone play?","2021-03-21",3);
insert into comment (content,post_id,time_created) values ("Yeees add me as friend",3,"2021-03-22");
insert into comment (content,post_id,time_created) values ("Not today...",3,"2021-03-23");
insert into comment (content,post_id,time_created) values ("Why not!",3,"2021-04-24");