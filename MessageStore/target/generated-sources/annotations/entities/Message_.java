package entities;

import entities.Message;
import entities.MessageToUser;
import entities.SystemUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-14T16:03:11")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, Boolean> isDeleted;
    public static volatile ListAttribute<Message, Message> messageList;
    public static volatile SingularAttribute<Message, Integer> groupId;
    public static volatile SingularAttribute<Message, Message> parentMessageId;
    public static volatile SingularAttribute<Message, Date> timeEdited;
    public static volatile SingularAttribute<Message, Date> timeCreated;
    public static volatile SingularAttribute<Message, Integer> id;
    public static volatile SingularAttribute<Message, String> body;
    public static volatile SingularAttribute<Message, Boolean> hasReplies;
    public static volatile ListAttribute<Message, MessageToUser> messageToUserList;
    public static volatile SingularAttribute<Message, SystemUser> userUid;

}