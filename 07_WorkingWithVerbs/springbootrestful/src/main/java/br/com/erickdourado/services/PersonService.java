package br.com.erickdourado.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.erickdourado.model.Person;

@Service
public class PersonService {

	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	public Person create(Person person) {
		logger.info("Creating one person!");
		
		person.setId(counter.incrementAndGet());
		
		return person;
	}
	
	public Person findById(String id) {
		logger.info("Finding one person!");
		
		Person person = new Person();
		
		person.setId(counter.incrementAndGet());
		person.setFirstName("Erick");
		person.setLastName("Dourado");
		person.setAddress("SP - São Paulo - Brasil");
		person.setGender("Male");
		
		return person;
	}

	public List<Person> findAll() {
		logger.info("Finding all people!");
		
		List<Person> people = new ArrayList<>();
				
		for (int i = 0; i < 10; i++) {
			people.add(new Person(counter.incrementAndGet(), "Erick", "Dourado", "SP - São Paulo - Brasil", "Male"));
		}
		
		return people;
	}

	public Person update(Person person, String id) {
		logger.info("Updating one person!");
		
		person.setId(Long.parseLong(id));
		
		return person;
	}
	
	public void delete(String id) {
		logger.info("Deleting one person!");
	}
}
