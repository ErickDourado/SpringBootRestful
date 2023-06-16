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

import br.com.erickdourado.data.vo.v1.BookVO;
import br.com.erickdourado.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Books", description = "Endpoints for Managing Books") //Anotação do Swagger para customizar a página de documentação
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@PostMapping
	@Operation(summary = "Adds a new Book", description = "Adds a new Book by passing in a JSON, XML or YML representation of the person!", tags = {"Books"},
    responses = {
	    @ApiResponse(description = "Created", responseCode = "201", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BookVO.class))}),
	    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
	    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<BookVO> create(@RequestBody BookVO book) {
		book = bookService.create(book);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{key}").buildAndExpand(book.getKey()).toUri();
		
		return ResponseEntity.created(uri).body(book);
	}
	
	@GetMapping("/{key}")
	@Operation(summary = "Finds a Book", description = "Finds a Book", tags = {"Books"},
    responses = {
	    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BookVO.class))}),
	    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<BookVO> findById(@PathVariable(value = "key") Long key) {
		
		BookVO book = bookService.findById(key);
		
		return ResponseEntity.ok(book);
	}
	
	@GetMapping
	@Operation(summary = "Finds all Books", description = "Finds all Books", tags = {"Books"},
	    responses = {
		    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookVO.class)))}),
		    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
		    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<List<BookVO>> findAll() {
		
		List<BookVO> books = bookService.findAll();
		
		return ResponseEntity.ok(books);
	}
	
	@PutMapping("/{key}")
	@Operation(summary = "Updates a Book", description = "Updates a Book by passing in a JSON, XML or YML representation of the person!", tags = {"Books"},
    responses = {
	    @ApiResponse(description = "Success", responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BookVO.class))}),
	    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
	    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<BookVO> update(@RequestBody BookVO book, @PathVariable Long key) {
		
		book = bookService.update(book, key);
		
		return ResponseEntity.ok(book);
	}
	
	@DeleteMapping("/{key}")
	@Operation(summary = "Deletes a Book", description = "Deletes a Book by passing in a JSON, XML or YML representation of the person!", tags = {"Books"},
    responses = {
	    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
	    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
	    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
	    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<Void> delete(@PathVariable Long key) {
		
		bookService.delete(key);
		
		return ResponseEntity.noContent().build();
	}
}
