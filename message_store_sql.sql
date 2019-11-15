
DROP TABLE IF EXISTS message_to_user CASCADE;
DROP TABLE IF EXISTS message CASCADE;
DROP TABLE IF EXISTS system_user CASCADE;

CREATE TABLE system_user (
  uid VARCHAR (7) PRIMARY KEY
);

CREATE TABLE message (
   id INT PRIMARY KEY,
   user_uid VARCHAR (7) REFERENCES system_user(uid),
   body VARCHAR (1500) NOT NULL,
   is_deleted BOOLEAN NOT NULL,
   has_replies BOOLEAN NOT NULL,
   time_created TIMESTAMP NOT NULL,
   time_edited TIMESTAMP NOT NULL,
   group_id INT NOT NULL,
   parent_message_id INT,
   FOREIGN KEY (parent_message_id)
   REFERENCES message (id)
   ON DELETE CASCADE
);

CREATE TABLE message_to_user (
  message_to_user_id INT PRIMARY KEY,
  message_id INT NOT NULL REFERENCES message(id),
  user_uid VARCHAR (7) NOT NULL REFERENCES system_user(uid),
  is_tagged BOOLEAN NOT NULL,
  has_seen BOOLEAN NOT NULL,
  has_been_notified BOOLEAN NOT NULL
);
