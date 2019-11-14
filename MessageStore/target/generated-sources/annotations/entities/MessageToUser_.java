package entities;

import entities.Message;
import entities.MessageToUserPK;
import entities.SystemUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-14T16:03:11")
@StaticMetamodel(MessageToUser.class)
public class MessageToUser_ { 

    public static volatile SingularAttribute<MessageToUser, Boolean> isTagged;
    public static volatile SingularAttribute<MessageToUser, Boolean> hasBeenNotified;
    public static volatile SingularAttribute<MessageToUser, MessageToUserPK> messageToUserPK;
    public static volatile SingularAttribute<MessageToUser, SystemUser> systemUser;
    public static volatile SingularAttribute<MessageToUser, Boolean> hasSeen;
    public static volatile SingularAttribute<MessageToUser, Message> message;

}