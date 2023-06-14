package br.com.erickdourado.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erickdourado.data.vo.v1.BookVO;
import br.com.erickdourado.exceptions.RequiredObjectIsNullException;
import br.com.erickdourado.mapper.DozerMapper;
import br.com.erickdourado.model.Book;
import br.com.erickdourado.repositories.BookRepository;
import br.com.erickdourado.services.BookService;
import br.com.erickdourado.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	
	private MockBook mockBook;
	
	@InjectMocks
	private BookService bookService;
	
	@Mock
	private BookRepository bookRepository;

	@BeforeEach
	void setUpMocks() throws Exception {
		mockBook = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreate() {
		BookVO bookVo = mockBook.mockVO(1);
		
		Book book = DozerMapper.parseObject(bookVo, Book.class);
		
		when(bookRepository.save(book)).thenReturn(book);
		
		bookVo = bookService.create(bookVo);
		
		assertNotNull(bookVo);
		assertNotNull(bookVo.getKey());
		assertNotNull(bookVo.getLinks());
		
		assertTrue(bookVo.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		
		assertEquals("Author Test1", bookVo.getAuthor());
		assertEquals(1, bookVo.getLaunchDate().getTime());
		assertEquals(1.0, bookVo.getPrice());
		assertEquals("Title Test1", bookVo.getTitle());
	}

	@Test
	void testCreateWithNullBook() {
		
		//O método assertThrows() espera que, quando o método bookService.create() for chamado com o parâmetro null, ele retorne a excessão RequiredObjectIsNullException.
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			bookService.create(null);
		});
		
		String actualMessage = exception.getMessage();
		String expectedMessage = "It is not allowed to persist a null object!";
		
		assertEquals(actualMessage, expectedMessage);
	}
	
	@Test
	void testFindById() {
		Book book = mockBook.mockEntity(1);
		
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		
		BookVO bookVo = bookService.findById(1L);
		
		assertNotNull(bookVo);
		assertNotNull(bookVo.getKey());
		assertNotNull(bookVo.getLinks());
		
		assertTrue(bookVo.toString().contains("links: [</api/book/v1>;rel=\"Book List\"]"));
		
		assertEquals("Author Test1", bookVo.getAuthor());
		assertEquals(1, bookVo.getLaunchDate().getTime());
		assertEquals(1.0, bookVo.getPrice());
		assertEquals("Title Test1", bookVo.getTitle());
	}

	@Test
	void testFindAll() {
		List<Book> bookList = mockBook.mockEntityList();
		
		when(bookRepository.findAll()).thenReturn(bookList);
		
		List<BookVO> bookVoList = bookService.findAll();
		
		assertNotNull(bookVoList);
		assertEquals(14, bookVoList.size());
		
		for (int i = 0; i < bookVoList.size(); i++) {
			BookVO bVo = bookVoList.get(i);
			
			assertNotNull(bVo);
			assertNotNull(bVo.getKey());
			assertNotNull(bVo.getLinks());
			
			assertTrue(bVo.toString().contains("links: [</api/book/v1/"+i+">;rel=\"self\"]"));
			
			assertEquals("Author Test"+i, bVo.getAuthor());
			assertEquals(i, bVo.getLaunchDate().getTime());
			assertEquals(i, bVo.getPrice());
			assertEquals("Title Test"+i, bVo.getTitle());
		}
	}

	@Test
	void testUpdate() {
		BookVO bookVo = mockBook.mockVO(1);
		
		Book book = DozerMapper.parseObject(bookVo, Book.class);
		
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		when(bookRepository.save(book)).thenReturn(book);
		
		bookVo = bookService.update(bookVo, 1L);
		
		assertNotNull(bookVo);
		assertNotNull(bookVo.getKey());
		assertNotNull(bookVo.getLinks());
		
		assertTrue(bookVo.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		
		assertEquals("Author Test1", bookVo.getAuthor());
		assertEquals(1, bookVo.getLaunchDate().getTime());
		assertEquals(1.0, bookVo.getPrice());
		assertEquals("Title Test1", bookVo.getTitle());
	}
	
	@Test
	void testUpdateWithNullBook() {
		
		//O método assertThrows() espera que, quando o método bookService.update() for chamado com o parâmetro (Book) null, ele retorne a excessão RequiredObjectIsNullException.
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			bookService.update(null, 1L);
		});
		
		String actualMessage = exception.getMessage();
		String expectedMessage = "It is not allowed to persist a null object!";
		
		assertEquals(actualMessage, expectedMessage);
	}

	@Test
	void testDelete() {
		Book book = mockBook.mockEntity(1);
		
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		
		BookVO bookVo = bookService.findById(1L);
		
		assertNotNull(bookVo);
		assertNotNull(bookVo.getKey());
		assertNotNull(bookVo.getLinks());
		
		assertTrue(bookVo.toString().contains("links: [</api/book/v1>;rel=\"Book List\"]"));
		
		assertEquals("Author Test1", bookVo.getAuthor());
		assertEquals(1, bookVo.getLaunchDate().getTime());
		assertEquals(1.0, bookVo.getPrice());
		assertEquals("Title Test1", bookVo.getTitle());

		//Após o findById não tem mais o que testar, pq só é chamado o método bookRepository.delete(book), e ele não tem retorno.
	}
}
