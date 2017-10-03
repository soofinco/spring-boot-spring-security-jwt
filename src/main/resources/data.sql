insert into users  (id ,date_created ,last_updated,version , email ,enabled ,encrypted_password ,first_name ,last_name  ,username ) values
('1','2017-09-23','2017-09-23','1','sofien@gmail.com', 'TRUE','$2a$10$o0yaB2mbgfhZQ.qIX.aLQuEviCjtQOTTmac29k8RhfgULTFzpdZKG' ,'sofien','mnassri','sof');


insert into roles  (id ,date_created, last_updated,version,role_name) values
('1' , '2017-09-23' ,'2017-09-23','1','ROLE_ADMIN');


insert into user_role  (user_id, role_id) values
('1' ,'1');
