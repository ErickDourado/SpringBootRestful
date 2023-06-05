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

import br.com.erickdourado.data.vo.v1.PersonVO;
import br.com.erickdourado.services.PersonService;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping
	public ResponseEntity<PersonVO> create(@RequestBody PersonVO person) {
		person = personService.create(person);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{key}").buildAndExpand(person.getKey()).toUri();
		
		return ResponseEntity.created(uri).body(person);
	}
	
	@GetMapping("/{key}")
	public ResponseEntity<PersonVO> findById(@PathVariable(value = "key") Long key) {
		
		PersonVO person = personService.findById(key);
		
		return ResponseEntity.ok(person);
	}
	
	@GetMapping
	public ResponseEntity<List<PersonVO>> findAll() {
		
		List<PersonVO> people = personService.findAll();
		
		return ResponseEntity.ok(people);
	}
	
	@PutMapping("/{key}")
	public ResponseEntity<PersonVO> update(@RequestBody PersonVO person, @PathVariable Long key) {
		
		person = personService.update(person, key);
		
		return ResponseEntity.ok(person);
	}
	
	@DeleteMapping("/{key}")
	public ResponseEntity<Void> delete(@PathVariable Long key) {
		
		personService.delete(key);
		
		return ResponseEntity.noContent().build();
	}
}
