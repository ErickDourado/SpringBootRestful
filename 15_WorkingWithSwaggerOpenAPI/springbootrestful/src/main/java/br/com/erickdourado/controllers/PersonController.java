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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People") //Anotação do Swagger para customizar a página de documentação
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping
	@Operation(summary = "Adds a new Person", description = "Adds a new Person by passing in a JSON, XML or YML representation of the person!", tags = {"People"},
    responses = {
	    @ApiResponse(description = "Created", responseCode = "201", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonVO.class))}),
	    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
	    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<PersonVO> create(@RequestBody PersonVO person) {
		person = personService.create(person);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{key}").buildAndExpand(person.getKey()).toUri();
		
		return ResponseEntity.created(uri).body(person);
	}
	
	@GetMapping("/{key}")
	@Operation(summary = "Finds a Person", description = "Finds a Person", tags = {"People"},
    responses = {
	    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonVO.class))}),
	    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<PersonVO> findById(@PathVariable(value = "key") Long key) {
		
		PersonVO person = personService.findById(key);
		
		return ResponseEntity.ok(person);
	}
	
	@GetMapping
	@Operation(summary = "Finds all People", description = "Finds all People", tags = {"People"},
	    responses = {
		    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))}),
		    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
		    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<List<PersonVO>> findAll() {
		
		List<PersonVO> people = personService.findAll();
		
		return ResponseEntity.ok(people);
	}
	
	@PutMapping("/{key}")
	@Operation(summary = "Updates a Person", description = "Updates a Person by passing in a JSON, XML or YML representation of the person!", tags = {"People"},
    responses = {
	    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonVO.class))}),
	    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
	    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<PersonVO> update(@RequestBody PersonVO person, @PathVariable Long key) {
		
		person = personService.update(person, key);
		
		return ResponseEntity.ok(person);
	}
	
	@DeleteMapping("/{key}")
	@Operation(summary = "Deletes a Person", description = "Deletes a Person by passing in a JSON, XML or YML representation of the person!", tags = {"People"},
    responses = {
	    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
	    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
	    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<Void> delete(@PathVariable Long key) {
		
		personService.delete(key);
		
		return ResponseEntity.noContent().build();
	}
}
