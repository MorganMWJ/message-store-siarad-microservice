
DROP TABLE IF EXISTS test_message_to_user CASCADE;
DROP TABLE IF EXISTS test_message CASCADE;
DROP TABLE IF EXISTS test_system_user CASCADE;

CREATE TABLE test_system_user (
  uid VARCHAR (7) PRIMARY KEY
);

CREATE TABLE test_message (
   id SERIAL PRIMARY KEY,
   user_uid VARCHAR (7) REFERENCES test_system_user(uid),
   body VARCHAR (1500) NOT NULL,
   is_deleted BOOLEAN NOT NULL,
   has_replies BOOLEAN NOT NULL,
   time_created TIMESTAMP NOT NULL,
   time_edited TIMESTAMP NOT NULL,
   group_id INT NOT NULL,
   parent_message_id INT,
   FOREIGN KEY (parent_message_id)
   REFERENCES test_message (id)
   ON DELETE CASCADE
);

CREATE TABLE test_message_to_user (
  message_to_user_id INT PRIMARY KEY,
  message_id INT NOT NULL REFERENCES test_message(id),
  user_uid VARCHAR (7) NOT NULL REFERENCES test_system_user(uid),
  is_tagged BOOLEAN NOT NULL,
  has_seen BOOLEAN NOT NULL,
  has_been_notified BOOLEAN NOT NULL
);


----------------

--empty message table
DELETE FROM test_message;
--restart auto id sequence upon redeployment
ALTER SEQUENCE test_message_id_seq RESTART;

--insert some messages
INSERT INTO test_message (body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('First Message Body',1,false,false,'2019-11-15 21:09:44.490','2019-11-15 21:09:44.490');
INSERT INTO test_message (body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('Second Message Body Content',1,false,false,'2019-11-15 21:31:44.490','2019-11-15 21:31:44.490');
INSERT INTO test_message (body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('Third Message Of Christmas',1,false,false,'2019-11-15 21:31:44.490','2019-11-15 21:31:44.490');
INSERT INTO test_message (body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('Message in different group',2,false,false,'2019-11-15 21:31:44.490','2019-11-15 21:31:44.490');

--empty user table
DELETE FROM test_system_user;
--insert a new user
INSERT INTO test_system_user (uid) VALUES ('mwj7');

--insert some messages owned by that user
INSERT INTO test_message (user_uid, body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('mwj7', 'mwj7 - first message',1,false,false,'2019-11-16 15:47:44.490','2019-11-16 16:47:44.490');
INSERT INTO test_message (user_uid, body, group_id, has_replies, is_deleted, time_created, time_edited) VALUES ('mwj7', 'mwj7 - second message',1,false,false,'2019-11-16 15:47:44.490','2019-11-16 16:47:44.490');