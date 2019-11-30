--empty user_to_message table
DELETE FROM message_to_user;

--empty message table
DELETE FROM message;

--restart auto id sequence upon redeployment
ALTER SEQUENCE message_id_seq RESTART;

--insert some messages
INSERT INTO message (body, group_id, is_deleted, time_created, time_edited) VALUES ('First Message Body',1,false,'2019-11-27 21:09:44.490','2019-11-27 21:09:44.490');
INSERT INTO message (body, group_id, is_deleted, time_created, time_edited) VALUES ('Second Message Body Content mentions @mwj7',1,false,current_timestamp,current_timestamp);
INSERT INTO message (body, owner_uid, group_id, is_deleted, time_created, time_edited) VALUES ('Third Message - Parent Message','mwj7',1,false,current_timestamp,current_timestamp);
INSERT INTO message (body, group_id, is_deleted, time_created, time_edited) VALUES ('Message in different group',2,false,current_timestamp,current_timestamp); --try auto current time 

--insert child message
INSERT INTO message (body, group_id, is_deleted, time_created, time_edited, parent_message_id) VALUES ('Child Message Body',1,false,current_timestamp,current_timestamp,3);

--insert some messages owned by a user
INSERT INTO message (body, owner_uid, group_id, is_deleted, time_created, time_edited) VALUES ('mwj7 - first message','mwj7',1,false,current_timestamp,current_timestamp);
INSERT INTO message (body, owner_uid, group_id, is_deleted, time_created, time_edited) VALUES ('mwj7 - second message','mwj7',1,false,current_timestamp,current_timestamp);


--restart auto id sequence upon redeployment
ALTER SEQUENCE message_to_user_id_seq RESTART; 

--insert a new message_to_user
INSERT INTO message_to_user (message_id, user_uid, is_owner, is_tagged, has_seen, has_been_notified) VALUES (6, 'mwj7', true, false, true, false);

--mwj7 is owner of 3rd message
INSERT INTO message_to_user (message_id, user_uid, is_owner, is_tagged, has_seen, has_been_notified) VALUES (3, 'mwj7', true, false, true, true);
--but mwj7 has seen the response to the 3rd message but not been notified about it
INSERT INTO message_to_user (message_id, user_uid, is_owner, is_tagged, has_seen, has_been_notified) VALUES (5, 'mwj7', false, false, true, false)

-- mwj7 has been mentioned in the 2nd message
INSERT INTO message_to_user (message_id, user_uid, is_owner, is_tagged, has_seen, has_been_notified) VALUES (2, 'mwj7', false, true, false, false)
-- mwj7 has been mentioned in the 4th message but has already been notified
INSERT INTO message_to_user (message_id, user_uid, is_owner, is_tagged, has_seen, has_been_notified) VALUES (4, 'mwj7', false, true, false, true)


--to test cascade delete
INSERT INTO message (body, group_id, is_deleted, time_created, time_edited) VALUES ('PArent to delete',2,false,current_timestamp,current_timestamp);
INSERT INTO message (body, group_id, is_deleted, time_created, time_edited, parent_message_id) VALUES ('Child Message to CASCADE delete',1,false,'2019-11-27 21:31:44.490','2019-11-27 21:31:44.490',8);