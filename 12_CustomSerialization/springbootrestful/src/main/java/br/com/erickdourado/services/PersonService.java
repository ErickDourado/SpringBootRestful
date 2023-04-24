package br.com.erickdourado.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erickdourado.data.vo.v1.PersonVO;
import br.com.erickdourado.exceptions.ResourceNotFoundException;
import br.com.erickdourado.mapper.DozerMapper;
import br.com.erickdourado.model.Person;
import br.com.erickdourado.repositories.PersonRepository;

@Service
public class PersonService {

	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	private PersonRepository personRepository;
	
	public PersonVO create(PersonVO personVo) {
		logger.info("Creating one person!");
		
		Person person = DozerMapper.parseObject(personVo, Person.class);
		
		personRepository.save(person);
		
		personVo = DozerMapper.parseObject(person, PersonVO.class);
		
		return personVo;
	}
	
	public PersonVO findById(Long id) {
		logger.info("Finding one person!");
		
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		return DozerMapper.parseObject(person, PersonVO.class);
	}

	public List<PersonVO> findAll() {
		logger.info("Finding all people!");
		
		return DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
	}

	public PersonVO update(PersonVO person, Long id) {
		logger.info("Updating one person!");
		
		PersonVO oldPerson = findById(id);
		
		oldPerson.setFirstName(person.getFirstName());
		oldPerson.setLastName(person.getLastName());
		oldPerson.setAddress(person.getAddress());
		oldPerson.setGender(person.getGender());
		
		Person p = personRepository.save(DozerMapper.parseObject(oldPerson, Person.class));
		
		return DozerMapper.parseObject(p, PersonVO.class);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		
		Person person = DozerMapper.parseObject(findById(id), Person.class);
		
		personRepository.delete(person);
	}
}
