package com.nexti.teste.domain;

import com.sun.istack.NotNull;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "workplace")
public class Workplace implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column
    private String name;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @ManyToOne
    @JoinColumn(name="service_type_id", nullable=false)
    private ServiceType serviceType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date finishDate;

    public Workplace() {
    }

    public Workplace(Long id) {
        this.id = id;
    }

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

}
