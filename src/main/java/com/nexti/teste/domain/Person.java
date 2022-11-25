package com.nexti.teste.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "person")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name="workplace_id", nullable=false)
    private Workplace workplace;

    @Temporal(TemporalType.TIMESTAMP)
    private Date admissionDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date demissionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Date getDemissionDate() {
        return demissionDate;
    }

    public void setDemissionDate(Date demissionDate) {
        this.demissionDate = demissionDate;
    }

}
