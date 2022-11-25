package com.nexti.teste.dto;

import com.nexti.teste.domain.Person;
import com.nexti.teste.domain.ServiceType;
import com.nexti.teste.domain.Workplace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkplaceDTO implements Serializable {

    private Long id;

    private String name;

    private Date startDate;

    private Date finishDate;

    private ServiceType serviceType;

    private List<Person> persons = new ArrayList<>();

    public WorkplaceDTO(Workplace obj, List<Person> personsList){
        id = obj.getId();
        name = obj.getName();
        startDate = obj.getStartDate();
        finishDate = obj.getFinishDate();
        serviceType = obj.getServiceType();
        persons = personsList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public List<Person> getPersons() {
        return persons;
    }
}
