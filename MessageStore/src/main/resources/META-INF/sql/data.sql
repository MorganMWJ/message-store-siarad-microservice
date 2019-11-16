--empty message table
DELETE FROM message;
--restart auto id sequence upon redeployment
ALTER SEQUENCE message_id_seq RESTART;

--insert some messages
INSERT INTO message (body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('First Message Body',1,false,false,'2019-11-15 21:09:44.490','2019-11-15 21:09:44.490');
INSERT INTO message (body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('Second Message Body Content',1,false,false,'2019-11-15 21:31:44.490','2019-11-15 21:31:44.490');
INSERT INTO message (body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('Third Message Of Christmas',1,false,false,'2019-11-15 21:31:44.490','2019-11-15 21:31:44.490');
INSERT INTO message (body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('Message in different group',2,false,false,'2019-11-15 21:31:44.490','2019-11-15 21:31:44.490');

--empty user table
DELETE FROM system_user;
--insert a new user
INSERT INTO system_user (uid) VALUES ('mwj7');

--insert some messages owned by that user
INSERT INTO message (user_uid, body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('mwj7', 'mwj7 - first message',1,false,false,'2019-11-16 15:47:44.490','2019-11-16 16:47:44.490');
INSERT INTO message (user_uid, body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('mwj7', 'mwj7 - second message',1,false,false,'2019-11-16 15:47:44.490','2019-11-16 16:47:44.490');