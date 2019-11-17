package entities;

import entities.Message;
import entities.MessageToUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-17T16:31:17")
@StaticMetamodel(SystemUser.class)
public class SystemUser_ { 

    public static volatile SingularAttribute<SystemUser, String> uid;
    public static volatile CollectionAttribute<SystemUser, Message> messageCollection;
    public static volatile CollectionAttribute<SystemUser, MessageToUser> messageToUserCollection;

}