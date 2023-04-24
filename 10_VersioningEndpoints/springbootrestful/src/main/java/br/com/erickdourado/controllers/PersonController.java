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
import br.com.erickdourado.data.vo.v2.PersonVOV2;
import br.com.erickdourado.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping
	public ResponseEntity<PersonVO> create(@RequestBody PersonVO person) {
		person = personService.create(person);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(person.getId()).toUri();
		
		return ResponseEntity.created(uri).body(person);
	}
	
	@PostMapping("/v2")
	public ResponseEntity<PersonVOV2> createV2(@RequestBody PersonVOV2 person) {
		person = personService.createV2(person);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(person.getId()).toUri();
		
		return ResponseEntity.created(uri).body(person);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonVO> findById(@PathVariable(value = "id") Long id) {
		
		PersonVO person = personService.findById(id);
		
		return ResponseEntity.ok(person);
	}
	
	@GetMapping
	public ResponseEntity<List<PersonVO>> findAll() {
		
		List<PersonVO> people = personService.findAll();
		
		return ResponseEntity.ok(people);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PersonVO> update(@RequestBody PersonVO person, @PathVariable Long id) {
		
		person = personService.update(person, id);
		
		return ResponseEntity.ok(person);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		
		personService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
