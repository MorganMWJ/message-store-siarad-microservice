/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restjpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nwh
 */
@Entity
@Table(name = "staff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s"),
    @NamedQuery(name = "Staff.findByNi", query = "SELECT s FROM Staff s WHERE s.ni = :ni"),
    @NamedQuery(name = "Staff.findByName", query = "SELECT s FROM Staff s WHERE s.name = :name"),
    @NamedQuery(name = "Staff.findByAge", query = "SELECT s FROM Staff s WHERE s.age = :age"),
    @NamedQuery(name = "Staff.findByGrade", query = "SELECT s FROM Staff s WHERE s.grade = :grade")})
public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ni")
    private String ni;
    @Size(max = 20)
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "grade")
    private Integer grade;
    @JoinColumn(name = "dept", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private Dept dept;

    public Staff() {
    }

    public Staff(String ni) {
        this.ni = ni;
    }

    public String getNi() {
        return ni;
    }

    public void setNi(String ni) {
        this.ni = ni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ni != null ? ni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.ni == null && other.ni != null) || (this.ni != null && !this.ni.equals(other.ni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.restjpa.Staff[ ni=" + ni + " ]";
    }
    
}
