package br.com.erickdourado.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.erickdourado.controllers.BookController;
import br.com.erickdourado.data.vo.v1.BookVO;
import br.com.erickdourado.exceptions.RequiredObjectIsNullException;
import br.com.erickdourado.exceptions.ResourceNotFoundException;
import br.com.erickdourado.mapper.DozerMapper;
import br.com.erickdourado.model.Book;
import br.com.erickdourado.repositories.BookRepository;

@Service
public class BookService {
	
	private Logger logger = Logger.getLogger(BookService.class.getName());
	
	@Autowired
	private BookRepository bookRepository;
	
	public BookVO create(BookVO bookVo) {
		
		if (Objects.isNull(bookVo)) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one book!");
		
		Book book = DozerMapper.parseObject(bookVo, Book.class);
		
		bookRepository.save(book);
		
		bookVo = DozerMapper.parseObject(book, BookVO.class);
		
		bookVo.add(linkTo(methodOn(BookController.class).findById(bookVo.getKey())).withSelfRel()); //Aplicando HATEOAS.
		
		logger.info("Book successfully created!");

		return bookVo;
		
	}
	
	@Transactional(readOnly = true)
	public BookVO findById(Long key) {
		logger.info("Finding one book!");
		
		Book book = bookRepository.findById(key)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		BookVO bookVo = DozerMapper.parseObject(book, BookVO.class);
		
		bookVo.add(linkTo(methodOn(BookController.class).findAll()).withRel("Book List")); //Aplicando HATEOAS.
		
		logger.info("Book successfully found!");
		
		return bookVo;
	}
	
	@Transactional(readOnly = true)
	public List<BookVO> findAll() {
		logger.info("Finding all books!");
		
		List<Book> bookList = bookRepository.findAll();
		
		if (bookList.isEmpty()) {
			throw new ResourceNotFoundException("Empty list!");
		}
		
		List<BookVO> bookVOs = DozerMapper.parseListObjects(bookList, BookVO.class);
		
		//Aplicando HATEOAS para cada personVo dentro da lista.
		bookVOs.forEach(bVo -> bVo.add(linkTo(methodOn(BookController.class).findById(bVo.getKey())).withSelfRel()));
		
		logger.info("All books successfully found!");

		return bookVOs;
	}
	
	public BookVO update(BookVO bookVo, Long key) {
		
		if (Objects.isNull(bookVo)) throw new RequiredObjectIsNullException();
		
		BookVO oldBook = findById(key);
		
		oldBook.removeLinks(); //Removendo link que o findById() cria, pq o link que ele cria é pra lista, e nesse caso eu quero um link para o próprio objeto.
		
		logger.info("Updating book...");
		
		oldBook.setAuthor(bookVo.getAuthor());
		oldBook.setLaunchDate(bookVo.getLaunchDate());
		oldBook.setPrice(bookVo.getPrice());
		oldBook.setTitle(bookVo.getTitle());
		
		bookRepository.save(DozerMapper.parseObject(oldBook, Book.class));
		
		oldBook.add(linkTo(methodOn(BookController.class).findById(key)).withSelfRel()); //Aplicando HATEOAS.
		
		logger.info("Book successfully updated!");
		
		return oldBook;
	}
	
	public void delete(Long key) {
		Book book = DozerMapper.parseObject(findById(key), Book.class);
		
		logger.info("Deleting book...");
		
		bookRepository.delete(book);
		
		logger.info("Book successfully deleted!");
	}
}