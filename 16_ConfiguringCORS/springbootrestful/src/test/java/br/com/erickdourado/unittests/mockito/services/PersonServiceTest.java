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

import br.com.erickdourado.data.vo.v1.PersonVO;
import br.com.erickdourado.exceptions.RequiredObjectIsNullException;
import br.com.erickdourado.mapper.DozerMapper;
import br.com.erickdourado.model.Person;
import br.com.erickdourado.repositories.PersonRepository;
import br.com.erickdourado.services.PersonService;
import br.com.erickdourado.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
	
	private MockPerson mockPerson;
	
	@InjectMocks
	private PersonService personService;
	
	@Mock
	private PersonRepository personRepository;

	@BeforeEach
	void setUpMocks() throws Exception {
		mockPerson = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreate() {
		PersonVO personVo = mockPerson.mockVO(1);
		
		Person person = DozerMapper.parseObject(personVo, Person.class);
		
		when(personRepository.save(person)).thenReturn(person);
		
		personVo = personService.create(personVo);
		
		assertNotNull(personVo);
		assertNotNull(personVo.getKey());
		assertNotNull(personVo.getLinks());
		
		assertTrue(personVo.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		
		assertEquals("Addres Test1", personVo.getAddress());
		assertEquals("First Name Test1", personVo.getFirstName());
		assertEquals("Female", personVo.getGender());
		assertEquals(1L, personVo.getKey());
		assertEquals("Last Name Test1", personVo.getLastName());
	}

	@Test
	void testCreateWithNullPerson() {
		
		//O método assertThrows() espera que, quando o método personService.create() for chamado com o parâmetro null, ele retorne a excessão RequiredObjectIsNullException.
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			personService.create(null);
		});
		
		String actualMessage = exception.getMessage();
		String expectedMessage = "It is not allowed to persist a null object!";
		
		assertEquals(actualMessage, expectedMessage);
	}
	
	@Test
	void testFindById() {
		Person person = mockPerson.mockEntity(1);
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		
		PersonVO personVo = personService.findById(1L);
		
		assertNotNull(personVo);
		assertNotNull(personVo.getKey());
		assertNotNull(personVo.getLinks());
		
		assertTrue(personVo.toString().contains("links: [</api/person/v1>;rel=\"Person List\"]"));
		
		assertEquals("Addres Test1", personVo.getAddress());
		assertEquals("First Name Test1", personVo.getFirstName());
		assertEquals("Female", personVo.getGender());
		assertEquals(1L, personVo.getKey());
		assertEquals("Last Name Test1", personVo.getLastName());
	}

	@Test
	void testFindAll() {
		List<Person> personList = mockPerson.mockEntityList();
		
		when(personRepository.findAll()).thenReturn(personList);
		
		List<PersonVO> personVoList = personService.findAll();
		
		assertNotNull(personVoList);
		assertEquals(14, personVoList.size());
		
		for (int i = 0; i < personVoList.size(); i++) {
			PersonVO pVo = personVoList.get(i);
			
			assertNotNull(pVo);
			assertNotNull(pVo.getKey());
			assertNotNull(pVo.getLinks());
			
			assertTrue(pVo.toString().contains("links: [</api/person/v1/"+i+">;rel=\"self\"]"));
			
			assertEquals("Addres Test"+i, pVo.getAddress());
			assertEquals("First Name Test"+i, pVo.getFirstName());
			assertEquals(i % 2 == 0 ? "Male" : "Female", pVo.getGender());
			assertEquals(i, pVo.getKey());
			assertEquals("Last Name Test"+i, pVo.getLastName());
		}
	}

	@Test
	void testUpdate() {
		PersonVO personVo = mockPerson.mockVO(1);
		
		Person person = DozerMapper.parseObject(personVo, Person.class);
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		when(personRepository.save(person)).thenReturn(person);
		
		personVo = personService.update(personVo, 1L);
		
		assertNotNull(personVo);
		assertNotNull(personVo.getKey());
		assertNotNull(personVo.getLinks());
		
		assertTrue(personVo.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		
		assertEquals("Addres Test1", personVo.getAddress());
		assertEquals("First Name Test1", personVo.getFirstName());
		assertEquals("Female", personVo.getGender());
		assertEquals(1L, personVo.getKey());
		assertEquals("Last Name Test1", personVo.getLastName());
	}
	
	@Test
	void testUpdateWithNullPerson() {
		
		//O método assertThrows() espera que, quando o método personService.update() for chamado com o parâmetro (Person) null, ele retorne a excessão RequiredObjectIsNullException.
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			personService.update(null, 1L);
		});
		
		String actualMessage = exception.getMessage();
		String expectedMessage = "It is not allowed to persist a null object!";
		
		assertEquals(actualMessage, expectedMessage);
	}

	@Test
	void testDelete() {
		Person person = mockPerson.mockEntity(1);
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		
		PersonVO personVo = personService.findById(1L);
		
		assertNotNull(personVo);
		assertNotNull(personVo.getKey());
		assertNotNull(personVo.getLinks());
		
		assertTrue(personVo.toString().contains("links: [</api/person/v1>;rel=\"Person List\"]"));
		
		assertEquals("Addres Test1", personVo.getAddress());
		assertEquals("First Name Test1", personVo.getFirstName());
		assertEquals("Female", personVo.getGender());
		assertEquals(1L, personVo.getKey());
		assertEquals("Last Name Test1", personVo.getLastName());

		//Após o findById não tem mais o que testar, pq só é chamado o método personRepository.delete(person), e ele não tem retorno.
	}
}
