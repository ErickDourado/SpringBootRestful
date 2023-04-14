package br.com.erickdourado.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.erickdourado.model.Person;
import br.com.erickdourado.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping
	public ResponseEntity<Person> create(@RequestBody Person person) {
		person = personService.create(person);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(person.getId()).toUri();
		
		return ResponseEntity.created(uri).body(person);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Person> findById(@PathVariable(value = "id") String id) {
		
		Person person = personService.findById(id);
		
		return ResponseEntity.ok(person);
	}
	
	@GetMapping
	public ResponseEntity<List<Person>> findAll() {
		
		List<Person> people = personService.findAll();
		
		return ResponseEntity.ok(people);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Person> update(@RequestBody Person person, @PathVariable String id) {
		
		person = personService.update(person, id);
		
		return ResponseEntity.ok(person);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		
		personService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
