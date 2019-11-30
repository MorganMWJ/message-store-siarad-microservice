/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

/**
 *
 * @author Morgan
 */
@Entity
@Table(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
    , @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id")
    , @NamedQuery(name = "Message.findByBody", query = "SELECT m FROM Message m WHERE m.body = :body")
    , @NamedQuery(name = "Message.findByIsDeleted", query = "SELECT m FROM Message m WHERE m.isDeleted = :isDeleted")
    , @NamedQuery(name = "Message.findByTimeCreated", query = "SELECT m FROM Message m WHERE m.timeCreated = :timeCreated")
    , @NamedQuery(name = "Message.findByTimeEdited", query = "SELECT m FROM Message m WHERE m.timeEdited = :timeEdited")
    , @NamedQuery(name = "Message.findByGroupId", query = "SELECT m FROM Message m WHERE m.groupId = :groupId")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    @Column(name = "body")
    private String body;
    @Size(max = 7)
    @Column(name = "owner_uid")
    private String ownerUid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time_edited")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeEdited;
    @Basic(optional = false)
    @NotNull
    @Column(name = "group_id")
    private int groupId;
    @OneToMany(mappedBy = "parentMessageId")
    private Collection<Message> messageCollection;
    @JoinColumn(name = "parent_message_id", referencedColumnName = "id")
    @ManyToOne
    @XmlInverseReference(mappedBy="messageCollection")
    private Message parentMessageId;
    @OneToMany(mappedBy = "messageId")
    @XmlTransient
    private Collection<MessageToUser> messageToUserCollection;
    public Message() {
    }

    public Message(Integer id) {
        this.id = id;
    }

    public Message(Integer id, String body, String ownerUid, boolean isDeleted, Date timeCreated, Date timeEdited, int groupId) {
        this.id = id;
        this.body = body;
        this.ownerUid = ownerUid;
        this.isDeleted = isDeleted;
        this.timeCreated = timeCreated;
        this.timeEdited = timeEdited;
        this.groupId = groupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    public String getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(String ownerUid) {
        this.ownerUid = ownerUid;
    }
    
    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getHasReplies() {
        return messageCollection.isEmpty();
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeEdited() {
        return timeEdited;
    }

    public void setTimeEdited(Date timeEdited) {
        this.timeEdited = timeEdited;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    @XmlTransient
    public Message getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(Message parentMessageId) {
        this.parentMessageId = parentMessageId;
    }
    
    @XmlTransient
    public Collection<MessageToUser> getMessageToUserCollection(){
        return messageToUserCollection;
    }
    
    public void setMessageToUserCollection(Collection<MessageToUser> messageToUserCollection){
        this.messageToUserCollection = messageToUserCollection;
    }
    
    public boolean isCreatedToday(){
        final long DAY = 24 * 60 * 60 * 1000;
        return this.timeCreated.getTime() > System.currentTimeMillis() - DAY;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Message[ id=" + id + " ]";
    }
    
}
