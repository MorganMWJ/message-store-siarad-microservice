/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Morgan
 */
@Entity
@Table(name = "system_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SystemUser.findAll", query = "SELECT s FROM SystemUser s")
    , @NamedQuery(name = "SystemUser.findByUid", query = "SELECT s FROM SystemUser s WHERE s.uid = :uid")})
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "uid")
    private String uid;
    @OneToMany(mappedBy = "userUid")
    private Collection<Message> messageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userUid")
    private Collection<MessageToUser> messageToUserCollection;

    public SystemUser() {
    }

    public SystemUser(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
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
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemUser)) {
            return false;
        }
        SystemUser other = (SystemUser) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SystemUser[ uid=" + uid + " ]";
    }
    
}
