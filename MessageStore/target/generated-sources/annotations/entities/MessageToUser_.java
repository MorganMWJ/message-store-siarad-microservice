package entities;

import entities.Message;
import entities.SystemUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-16T17:07:56")
@StaticMetamodel(MessageToUser.class)
public class MessageToUser_ { 

    public static volatile SingularAttribute<MessageToUser, Boolean> isTagged;
    public static volatile SingularAttribute<MessageToUser, Boolean> hasBeenNotified;
    public static volatile SingularAttribute<MessageToUser, Integer> messageToUserId;
    public static volatile SingularAttribute<MessageToUser, Message> messageId;
    public static volatile SingularAttribute<MessageToUser, Boolean> hasSeen;
    public static volatile SingularAttribute<MessageToUser, SystemUser> userUid;

}