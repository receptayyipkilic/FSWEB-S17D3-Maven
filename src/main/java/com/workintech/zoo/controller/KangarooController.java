package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init(){
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> findAllKangaroo(){
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo findByIdKangaroo(@PathVariable int id){
        if (id <= 0){
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)){
            throw new ZooException("Kangaroo with given id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo saveKangaroo(@RequestBody Kangaroo kangaroo){
        if (kangaroo.getId() <= 0 || kangaroo.getName() == null || kangaroo.getName().isBlank()) {
            throw new ZooException("Invalid input data", HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo updateKangaroo(@PathVariable int id, @RequestBody Kangaroo kangaroo){
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Cannot update. Kangaroo with id not found: " + id, HttpStatus.NOT_FOUND);
        }
        Kangaroo updated = new Kangaroo(id, kangaroo.getName(), kangaroo.getWeight(), kangaroo.getHeight(), kangaroo.getGender(), kangaroo.isAggressive());
        kangaroos.put(id, updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    public Kangaroo deleteKangaroo(@PathVariable int id){
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Cannot delete. Kangaroo with id not found: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.remove(id);
    }
}
