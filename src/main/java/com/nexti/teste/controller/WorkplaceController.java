package com.nexti.teste.controller;

import com.nexti.teste.domain.Person;
import com.nexti.teste.domain.Workplace;
import com.nexti.teste.dto.WorkplaceDTO;
import com.nexti.teste.response.ResponseHandler;
import com.nexti.teste.service.WorkplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/workplace")
public class WorkplaceController {


    @Autowired
    WorkplaceService workplaceService;

    @GetMapping("/all")
    public ResponseEntity<List<Workplace>> getAllWorkplaces() {
        return ResponseEntity.ok().body(workplaceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPersonsWithWorkplace(@PathVariable("id") Long id){
    try {

        Workplace workplace = workplaceService.findById(id);
        List<Person> persons;
        WorkplaceDTO workplaceDTO;
        Date today = new Date();

        if (workplace.getStartDate() != null && workplace.getStartDate().before(today)) {
            persons = workplaceService.getAllPersonsWithWorkplace(id);
            workplaceDTO = new WorkplaceDTO(workplace, persons);
            return ResponseEntity.ok().body(workplaceDTO);
        }
        if (workplace.getFinishDate() != null && workplace.getFinishDate().after(today)) {
            persons = workplaceService.getAllPersonsWithWorkplace(id);
            workplaceDTO = new WorkplaceDTO(workplace, persons);
            return ResponseEntity.ok().body(workplaceDTO);
        } else {
            return ResponseEntity.ok().body(workplace);
        }
    }catch (NoSuchElementException e){
        return ResponseHandler.generateResponse("Posto não encontrado", HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createPerson(@RequestBody Workplace obj) {
        if (obj.getStartDate().after(obj.getFinishDate())){
            return ResponseHandler.generateResponse("Data Final maior que a Inicial", HttpStatus.BAD_REQUEST);
        }
        try {
            obj = workplaceService.createWorkplace(obj);
        }catch (Exception e){
            throw new RuntimeException();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteWorkplace(@PathVariable Long id){
        try{
        workplaceService.delete(id);
        }catch (Exception e){
            return ResponseHandler.generateResponse("Posto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.generateResponse("Posto deletado com sucesso!", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateWorkplace(@PathVariable Long id, @RequestBody Workplace obj){
        if (obj.getStartDate().after(obj.getFinishDate())){
            return ResponseHandler.generateResponse("Data Final maior que a Inicial", HttpStatus.BAD_REQUEST);
        }
        try {
            obj = workplaceService.updateWorkplace(id, obj);
        }catch (EntityNotFoundException e){
            return ResponseHandler.generateResponse("Posto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(obj);
    }


}
