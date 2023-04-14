package br.com.erickdourado.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erickdourado.exceptions.ResourceNotFoundException;
import br.com.erickdourado.model.Person;
import br.com.erickdourado.repositories.PersonRepository;

@Service
public class PersonService {

	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	private PersonRepository personRepository;
	
	public Person create(Person person) {
		logger.info("Creating one person!");
		
		return personRepository.save(person);
	}
	
	public Person findById(Long id) {
		logger.info("Finding one person!");
		
		return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
	}

	public List<Person> findAll() {
		logger.info("Finding all people!");
		
		return personRepository.findAll();
	}

	public Person update(Person person, Long id) {
		logger.info("Updating one person!");
		
		Person oldPerson = findById(id);
		
		oldPerson.setFirstName(person.getFirstName());
		oldPerson.setLastName(person.getLastName());
		oldPerson.setAddress(person.getAddress());
		oldPerson.setGender(person.getGender());
		
		return personRepository.save(oldPerson);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		
		Person person = findById(id);
		
		personRepository.delete(person);
	}
}
