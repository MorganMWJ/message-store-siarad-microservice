package entities;

import entities.Message;
import entities.MessageToUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-14T16:03:11")
@StaticMetamodel(SystemUser.class)
public class SystemUser_ { 

    public static volatile SingularAttribute<SystemUser, String> uid;
    public static volatile ListAttribute<SystemUser, Message> messageList;
    public static volatile ListAttribute<SystemUser, MessageToUser> messageToUserList;

}