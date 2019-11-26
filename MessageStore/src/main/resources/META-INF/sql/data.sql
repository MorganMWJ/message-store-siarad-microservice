--empty user_to_message table
DELETE FROM message_to_user;

--empty message table
DELETE FROM message;

--restart auto id sequence upon redeployment
ALTER SEQUENCE message_id_seq RESTART;

--insert some messages
INSERT INTO message (body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('First Message Body',1,false,false,'2019-11-15 21:09:44.490','2019-11-15 21:09:44.490');
INSERT INTO message (body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('Second Message Body Content',1,false,false,'2019-11-15 21:31:44.490','2019-11-15 21:31:44.490');
INSERT INTO message (body, owner_uid, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('Third Message - Parent Message','mwj7',1,true,false,'2019-11-15 21:31:44.490','2019-11-15 21:31:44.490');
INSERT INTO message (body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('Message in different group',2,false,false,'2019-11-15 21:31:44.490','2019-11-15 21:31:44.490');

--insert child message
INSERT INTO message (body, group_id, has_replies, is_deleted, time_created, time_edited, parent_message_id) VALUES ('Child Message Body',1,false,false,'2019-11-15 21:31:44.490','2019-11-15 21:31:44.490',3);

--insert some messages owned by a user
INSERT INTO message (body, owner_uid, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('mwj7 - first message','mwj7',1,false,false,'2019-11-16 15:47:44.490','2019-11-16 16:47:44.490');
INSERT INTO message (body, owner_uid, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('mwj7 - second message','mwj7',1,false,false,'2019-11-16 15:47:44.490','2019-11-16 16:47:44.490');


--restart auto id sequence upon redeployment
ALTER SEQUENCE message_to_user_id_seq RESTART; 

--insert a new message_to_user
INSERT INTO message_to_user (message_id, user_uid, is_owner, is_tagged, has_seen, has_been_notified) VALUES (5, 'mwj7', true, false, true, false);
INSERT INTO message_to_user (message_id, user_uid, is_owner, is_tagged, has_seen, has_been_notified) VALUES (6, 'mwj7', true, false, true, false);

--mwj7 is owner of 3rd message
INSERT INTO message_to_user (message_id, user_uid, is_owner, is_tagged, has_seen, has_been_notified) VALUES (3, 'mwj7', true, false, true, true);
INSERT INTO message_to_user (message_id, user_uid, is_owner, is_tagged, has_seen, has_been_notified) VALUES (5, 'mwj7', false, false, true, false);