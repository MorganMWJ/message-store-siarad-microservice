/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Morgan
 */
@Embeddable
public class MessageToUserPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "message_id")
    private int messageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "user_uid")
    private String userUid;

    public MessageToUserPK() {
    }

    public MessageToUserPK(int messageId, String userUid) {
        this.messageId = messageId;
        this.userUid = userUid;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) messageId;
        hash += (userUid != null ? userUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageToUserPK)) {
            return false;
        }
        MessageToUserPK other = (MessageToUserPK) object;
        if (this.messageId != other.messageId) {
            return false;
        }
        if ((this.userUid == null && other.userUid != null) || (this.userUid != null && !this.userUid.equals(other.userUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.MessageToUserPK[ messageId=" + messageId + ", userUid=" + userUid + " ]";
    }
    
}
