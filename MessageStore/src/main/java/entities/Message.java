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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Morgan
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
    , @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id")
    , @NamedQuery(name = "Message.findByBody", query = "SELECT m FROM Message m WHERE m.body = :body")
    , @NamedQuery(name = "Message.findByIsDeleted", query = "SELECT m FROM Message m WHERE m.isDeleted = :isDeleted")
    , @NamedQuery(name = "Message.findByHasReplies", query = "SELECT m FROM Message m WHERE m.hasReplies = :hasReplies")
    , @NamedQuery(name = "Message.findByTimeCreated", query = "SELECT m FROM Message m WHERE m.timeCreated = :timeCreated")
    , @NamedQuery(name = "Message.findByTimeEdited", query = "SELECT m FROM Message m WHERE m.timeEdited = :timeEdited")
    , @NamedQuery(name = "Message.findByGroupId", query = "SELECT m FROM Message m WHERE m.groupId = :groupId")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="message_id_seq")
    @SequenceGenerator(name="message_id_seq", sequenceName="message_id_seq", allocationSize=1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    @Column(name = "body")
    private String body;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "has_replies")
    private boolean hasReplies;
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
    private Message parentMessageId;
    @JoinColumn(name = "user_uid", referencedColumnName = "uid") //user_uid
    @ManyToOne(cascade=CascadeType.PERSIST) //MERGE not working
    private SystemUser userUid;
    @JoinColumn(name = "message_to_user_id")
    @OneToMany(mappedBy = "messageId", cascade = CascadeType.ALL) //orpahnremoval??
    private Collection<MessageToUser> messageToUserCollection;

    public Message() {
    }

    public Message(Integer id) {
        this.id = id;
    }

    public Message(Integer id, String body, boolean isDeleted, boolean hasReplies, Date timeCreated, Date timeEdited, int groupId) {
        this.id = id;
        this.body = body;
        this.isDeleted = isDeleted;
        this.hasReplies = hasReplies;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getHasReplies() {
        return hasReplies;
    }

    public void setHasReplies(boolean hasReplies) {
        this.hasReplies = hasReplies;
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

    public Message getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(Message parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    public SystemUser getUserUid() {
        return userUid;
    }

    public void setUserUid(SystemUser userUid) {
        this.userUid = userUid;
    }

    @XmlTransient
    public Collection<MessageToUser> getMessageToUserCollection() {
        return messageToUserCollection;
    }

    public void setMessageToUserCollection(Collection<MessageToUser> messageToUserCollection) {
        this.messageToUserCollection = messageToUserCollection;
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
        return "entities.Message[ id=" + id + ", body=" + body + " ]";
    }
    
}
