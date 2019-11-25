/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

/**
 *
 * @author Morgan
 */
@Entity
@Table(name = "message_to_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MessageToUser.findAll", query = "SELECT m FROM MessageToUser m")
    , @NamedQuery(name = "MessageToUser.findById", query = "SELECT m FROM MessageToUser m WHERE m.id = :id")
    , @NamedQuery(name = "MessageToUser.findByIsTagged", query = "SELECT m FROM MessageToUser m WHERE m.isTagged = :isTagged")
    , @NamedQuery(name = "MessageToUser.findByHasSeen", query = "SELECT m FROM MessageToUser m WHERE m.hasSeen = :hasSeen")
    , @NamedQuery(name = "MessageToUser.findByHasBeenNotified", query = "SELECT m FROM MessageToUser m WHERE m.hasBeenNotified = :hasBeenNotified")})
public class MessageToUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 7)
    @Column(name = "user_uid")
    private String userUid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_owner")
    private boolean isOwner;
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
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Message messageId;

    public MessageToUser() {
    }

    public MessageToUser(Integer id) {
        this.id = id;
    }

    public MessageToUser(Integer id, boolean isTagged, boolean hasSeen, boolean hasBeenNotified) {
        this.id = id;
        this.isTagged = isTagged;
        this.hasSeen = hasSeen;
        this.hasBeenNotified = hasBeenNotified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
        
    public boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
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

    public Message getMessageId() {
        return messageId;
    }

    public void setMessageId(Message messageId) {
        this.messageId = messageId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageToUser)) {
            return false;
        }
        MessageToUser other = (MessageToUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MessageToUser[ id=" + id + " ]";
    }
    
}
