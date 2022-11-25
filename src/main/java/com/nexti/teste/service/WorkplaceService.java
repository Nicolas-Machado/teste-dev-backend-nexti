package com.nexti.teste.service;

import com.nexti.teste.domain.Person;
import com.nexti.teste.domain.Workplace;
import com.nexti.teste.repository.PersonRepository;
import com.nexti.teste.repository.WorkplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkplaceService {

    @Autowired
    WorkplaceRepository workplaceRepository;

    @Autowired
    PersonRepository personRepository;

    public List<Workplace> getAll() {return workplaceRepository.findAll();}

    public Workplace createWorkplace(Workplace obj){
        return workplaceRepository.save(obj);
    }

    public Workplace findById(Long id) {
        Optional<Workplace> obj = workplaceRepository.findById(id);
        return obj.get();
    }

    public void delete(Long id){
        workplaceRepository.deleteById(id);
    }

    public Workplace updateWorkplace(Long id, Workplace obj){
        Workplace workplace = workplaceRepository.getReferenceById(id);
        updateData(workplace, obj);
        return workplaceRepository.save(workplace);
    }


    public void updateData(Workplace workplace, Workplace obj){
        workplace.setName(obj.getName());
        workplace.setServiceType(obj.getServiceType());
        workplace.setStartDate(obj.getStartDate());
        workplace.setFinishDate(obj.getFinishDate());
    }

    public List<Person> getAllPersonsWithWorkplace(Long id) {return personRepository.findPersonsWithWorkplaceId(id);}
}
