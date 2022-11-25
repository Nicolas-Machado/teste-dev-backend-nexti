package com.nexti.teste.service;

import com.nexti.teste.domain.Person;
import com.nexti.teste.domain.Workplace;
import com.nexti.teste.repository.PersonRepository;
import com.nexti.teste.response.ResponseHandler;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAll(){
       return personRepository.findAll();
    }

    public Person findById(Long id){
        Optional<Person> obj = personRepository.findById(id);
        return obj.get();
    }

    public List<Person> findAllActives(){ return personRepository.findAllActives(); }

    public List<Person> findAllInactives(){ return personRepository.findAllInactives(); }

    public Person create(Person obj) {
        return personRepository.save(obj);
    }

    public void delete(Long id){
        personRepository.deleteById(id);
    }

    public Person updatePerson(Long id, Person obj){
        Person person = personRepository.getReferenceById(id);
        updateData(person, obj);
        return personRepository.save(person);
    }

    private void updateData(Person person, Person obj) {
        person.setName(obj.getName());
        person.setWorkplace(obj.getWorkplace());
        person.setAdmissionDate(obj.getAdmissionDate());
        person.setDemissionDate(obj.getDemissionDate());
    }

    @Transactional
    public void workplaceTransfer(Long workplaceId, Long id) {
            personRepository.workplaceTransfer(workplaceId, id);
    }
}