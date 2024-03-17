package com.project.controller;

import com.project.model.Zadanie;
import com.project.service.ProjektService;
import com.project.service.ZadanieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api")
public class ZadanieRestController {
    private ZadanieService zadanieService;

    @Autowired
    public ZadanieRestController(ZadanieService zadanieService){
        this.zadanieService = zadanieService;
    }

    @GetMapping("/zadania/{zadanieId}")
    ResponseEntity<Zadanie> getZadanie(@PathVariable Integer zadanieId){
        return ResponseEntity.of(zadanieService.getZadanie(zadanieId));
    }
    @PostMapping(path = "/zadania")
    ResponseEntity<Void> createZadanie(@Valid @RequestBody Zadanie zadanie){
        Zadanie createdZadanie = zadanieService.createZadanie(zadanie);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{zadanieId}").buildAndExpand(createdZadanie.getZadanieId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/zadania/{zadanieId}")
    public ResponseEntity<Void> updateProjekt(@Valid @RequestBody Zadanie zadanie, @PathVariable Integer zadanieId){
        return zadanieService.getZadanie(zadanieId)
                .map(p ->{
                    zadanieService.updateZadanie(zadanie, zadanieId);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/zadania/{zadanieId}")
    public ResponseEntity<Void> deleteZadanie(@PathVariable Integer zadanieId){
        return zadanieService.getZadanie(zadanieId).map(p ->{
            zadanieService.deleteZadanie(zadanieId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/zadania")
    Page<Zadanie> getZadania(Pageable pageable){
        return zadanieService.getZadania(pageable);
    }


}
