package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> findAllKoala() {
        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala findByIdKoala(@PathVariable int id) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PostMapping
    public Koala saveKoala(@RequestBody Koala koala) {
        if (koalas.containsKey(koala.getId())) {
            throw new ZooException("Koala with id already exists: " + koala.getId(), HttpStatus.BAD_REQUEST);
        }
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala updateKoala(@PathVariable int id, @RequestBody Koala koala) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Cannot update. Koala with id not found: " + id, HttpStatus.NOT_FOUND);
        }
        Koala updated = new Koala(id, koala.getName(), koala.getWeight(), koala.getSleepHour(), koala.getGender());
        koalas.put(id, updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    public Koala deleteKoala(@PathVariable int id) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Cannot delete. Koala with id not found: " + id, HttpStatus.NOT_FOUND);
        }
        return koalas.remove(id);
    }
}


