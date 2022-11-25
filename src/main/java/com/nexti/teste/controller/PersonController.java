package com.nexti.teste.controller;

import com.nexti.teste.domain.Person;
import com.nexti.teste.domain.Workplace;
import com.nexti.teste.response.ResponseHandler;
import com.nexti.teste.service.PersonService;
import com.nexti.teste.service.WorkplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.*;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    WorkplaceService workplaceService;

    @GetMapping("/all")
    ResponseEntity<List<Person>> getAllPersons(){
        return ResponseEntity.ok(personService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        Person obj;
        try{
         obj = personService.findById(id);
        }catch (NoSuchElementException e){
            return ResponseHandler.generateResponse("Colaborador não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/actives")
    ResponseEntity<List<Person>> findAllActives(){
        return ResponseEntity.ok(personService.findAllActives());
    }

    @GetMapping("/inactives")
    ResponseEntity<List<Person>> findAllInactives(){
        return ResponseEntity.ok(personService.findAllInactives());
    }

    @PostMapping("/create")
    public ResponseEntity<Person> createPerson(@RequestBody Person obj) {
        try {
            obj = personService.create(obj);
        }catch (Exception e){
            throw new RuntimeException();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable Long id){
        try{
            personService.delete(id);
        }catch (EmptyResultDataAccessException e){
            return ResponseHandler.generateResponse("Colaborador não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.generateResponse("Colaborador deletado com sucesso!", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePerson(@PathVariable Long id, @RequestBody Person obj){
        if (obj.getAdmissionDate().after(obj.getDemissionDate())){
            return ResponseHandler.generateResponse("Data Final maior que a Inicial", HttpStatus.BAD_REQUEST);
        }

        try {
            obj = personService.updatePerson(id, obj);
        }catch (EntityNotFoundException e){
            return ResponseHandler.generateResponse("Colaborador não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/{id}/workplacetransfer/{workplaceId}")
    public ResponseEntity<Object> workplaceTransfer(@PathVariable Long id, @PathVariable Long workplaceId){
        Person person = personService.findById(id);
        Workplace workplace = workplaceService.findById(workplaceId);
        Date today = new Date();

        if (person.getAdmissionDate() != null && !person.getAdmissionDate().before(today)){
            return ResponseHandler.generateResponse("Colaborador ainda não foi admitido", HttpStatus.BAD_REQUEST);
        }
        if (person.getDemissionDate() != null && !person.getDemissionDate().after(today)){
            return ResponseHandler.generateResponse("Colaborador já está demitido", HttpStatus.BAD_REQUEST);
        }
        if(workplace.getStartDate() != null && !workplace.getStartDate().before(today)){
            return ResponseHandler.generateResponse("Posto ainda não foi ativado", HttpStatus.BAD_REQUEST);
        }
        if (workplace.getFinishDate() != null && !workplace.getFinishDate().after(today)){
            return ResponseHandler.generateResponse("Posto desativado", HttpStatus.BAD_REQUEST);
        }
        if(workplace.getServiceType().getId() == 3){
            return ResponseHandler.generateResponse("Erro ao tentar vincular: Posto com o tipo de serviço C", HttpStatus.BAD_REQUEST);
        }
        personService.workplaceTransfer(workplaceId, id);
        person.setWorkplace(workplace);
        return ResponseEntity.ok().body(person);
    }

}
