
DROP TABLE IF EXISTS message_to_user CASCADE;
DROP TABLE IF EXISTS message CASCADE;
DROP TABLE IF EXISTS system_user CASCADE;

CREATE TABLE message (
   id SERIAL PRIMARY KEY,
   body VARCHAR (1500) NOT NULL,
   owner_uid VARCHAR (7),
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
  id SERIAL PRIMARY KEY,
  message_id INT NOT NULL REFERENCES message(id),
  user_uid VARCHAR (7) NOT NULL,
  is_owner BOOLEAN NOT NULL,
  is_tagged BOOLEAN NOT NULL,
  has_seen BOOLEAN NOT NULL,
  has_been_notified BOOLEAN NOT NULL
);
