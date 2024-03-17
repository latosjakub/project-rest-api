package com.project.controller;

import com.project.model.Student;
import com.project.service.StudentService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    private StudentService studentService;

    @Autowired
    public  StudentRestController(StudentService studentService){
        this.studentService = studentService;
    }
    // PRZED KAŻDĄ Z PONIŻSZYCH METOD JEST UMIESZCZONA ADNOTACJA (@GetMapping, PostMapping, ... ), KTÓRA OKREŚLA
    // RODZAJ METODY HTTP, A TAKŻE ADRES I PARAMETRY ŻĄDANIA


    //Przykład żądania wywołującego metodę: GET http://localhost:8080/api/projekty/1
    @GetMapping("student/{studentId}")
    ResponseEntity<Student> getStudent(@PathVariable Integer studentId){
        return ResponseEntity.of(studentService.getStudent(studentId));
    }
    // @ Valid włącza automatyczną walidację na podstawie adnotacji zawartych w modelu np. NotNull
    // @RequestBody oznacza, że dane studenta (w formacie JSON) są  pzewkazywane w ciele żądania
    @PostMapping(path = "/student")
    ResponseEntity<Void> createStudent(@Valid @RequestBody Student student){
        Student createdStudent = studentService.createStudent(student);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{studentId}").buildAndExpand(createdStudent.getStudentId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/student/{studentId}")
    public ResponseEntity<Void> updateStudent(@Valid @RequestBody Student student, @PathVariable Integer studentId){
        return  studentService.getStudent(studentId)
                .map(p ->{
                    studentService.updateStudent(student, studentId);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/student/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer studentId){
        return studentService.getStudent(studentId).map(p ->{
            studentService.deleteStudent(studentId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() ->ResponseEntity.notFound().build());
    }

    //Przykład żądania wywołującego metodę: http://localhost:8080/api/projekty?page=0&size=10&sort=nazwa,desc
    @GetMapping(value = "/studenci")
    Page<Student> getStudenci(Pageable pageable) { // @RequestHeader HttpHeaders headers – jeżeli potrzebny
        return studentService.getStudenci(pageable); // byłby nagłówek, wystarczy dodać drugą zmienną z adnotacją
    }
    @GetMapping(value = "/studenci", params="nazwisko")
    Page<Student> getStudentByNazwa(@RequestParam String nazwisko, Pageable pageable) {
        return studentService.searchByNazwa(nazwisko, pageable);
    }

}
