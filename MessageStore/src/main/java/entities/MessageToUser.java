/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Morgan
 */
@Entity
@Table(name = "message_to_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MessageToUser.findAll", query = "SELECT m FROM MessageToUser m"),
    @NamedQuery(name = "MessageToUser.findByMessageId", query = "SELECT m FROM MessageToUser m WHERE m.messageToUserPK.messageId = :messageId"),
    @NamedQuery(name = "MessageToUser.findByUserUid", query = "SELECT m FROM MessageToUser m WHERE m.messageToUserPK.userUid = :userUid"),
    @NamedQuery(name = "MessageToUser.findByIsTagged", query = "SELECT m FROM MessageToUser m WHERE m.isTagged = :isTagged"),
    @NamedQuery(name = "MessageToUser.findByHasSeen", query = "SELECT m FROM MessageToUser m WHERE m.hasSeen = :hasSeen"),
    @NamedQuery(name = "MessageToUser.findByHasBeenNotified", query = "SELECT m FROM MessageToUser m WHERE m.hasBeenNotified = :hasBeenNotified")})
public class MessageToUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MessageToUserPK messageToUserPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_tagged")
    private boolean isTagged;
    @Basic(optional = false)
    @NotNull
    @Column(name = "has_seen")
    private boolean hasSeen;
    @Basic(optional = false)
    @NotNull
    @Column(name = "has_been_notified")
    private boolean hasBeenNotified;
    @JoinColumn(name = "message_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Message message;
    @JoinColumn(name = "user_uid", referencedColumnName = "uid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SystemUser systemUser;

    public MessageToUser() {
    }

    public MessageToUser(MessageToUserPK messageToUserPK) {
        this.messageToUserPK = messageToUserPK;
    }

    public MessageToUser(MessageToUserPK messageToUserPK, boolean isTagged, boolean hasSeen, boolean hasBeenNotified) {
        this.messageToUserPK = messageToUserPK;
        this.isTagged = isTagged;
        this.hasSeen = hasSeen;
        this.hasBeenNotified = hasBeenNotified;
    }

    public MessageToUser(int messageId, String userUid) {
        this.messageToUserPK = new MessageToUserPK(messageId, userUid);
    }

    public MessageToUserPK getMessageToUserPK() {
        return messageToUserPK;
    }

    public void setMessageToUserPK(MessageToUserPK messageToUserPK) {
        this.messageToUserPK = messageToUserPK;
    }

    public boolean getIsTagged() {
        return isTagged;
    }

    public void setIsTagged(boolean isTagged) {
        this.isTagged = isTagged;
    }

    public boolean getHasSeen() {
        return hasSeen;
    }

    public void setHasSeen(boolean hasSeen) {
        this.hasSeen = hasSeen;
    }

    public boolean getHasBeenNotified() {
        return hasBeenNotified;
    }

    public void setHasBeenNotified(boolean hasBeenNotified) {
        this.hasBeenNotified = hasBeenNotified;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageToUserPK != null ? messageToUserPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageToUser)) {
            return false;
        }
        MessageToUser other = (MessageToUser) object;
        if ((this.messageToUserPK == null && other.messageToUserPK != null) || (this.messageToUserPK != null && !this.messageToUserPK.equals(other.messageToUserPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.MessageToUser[ messageToUserPK=" + messageToUserPK + " ]";
    }
    
}
