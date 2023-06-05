package br.com.erickdourado.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.erickdourado.controllers.PersonController;
import br.com.erickdourado.data.vo.v1.PersonVO;
import br.com.erickdourado.exceptions.RequiredObjectIsNullException;
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
		
		if (Objects.isNull(personVo)) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one person!");
		
		Person person = DozerMapper.parseObject(personVo, Person.class);
		
		personRepository.save(person);
		
		personVo = DozerMapper.parseObject(person, PersonVO.class);
		
		personVo.add(linkTo(methodOn(PersonController.class).findById(personVo.getKey())).withSelfRel()); //Aplicando HATEOAS.
		
		logger.info("Person successfully created!");

		return personVo;
	}
	
	@Transactional(readOnly = true)
	public PersonVO findById(Long key) {
		logger.info("Finding one person!");
		
		Person person = personRepository.findById(key)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		PersonVO personVo = DozerMapper.parseObject(person, PersonVO.class);
		
		personVo.add(linkTo(methodOn(PersonController.class).findAll()).withRel("Person List")); //Aplicando HATEOAS.
		
		logger.info("Person successfully found!");
		
		return personVo;
	}
	
	@Transactional(readOnly = true)
	public List<PersonVO> findAll() {
		logger.info("Finding all people!");
		
		List<PersonVO> personVOs = DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
		
		//Aplicando HATEOAS para cada personVo dentro da lista.
		personVOs.forEach(pVo -> pVo.add(linkTo(methodOn(PersonController.class).findById(pVo.getKey())).withSelfRel()));
		
		logger.info("All people successfully found!");

		return personVOs;
	}

	public PersonVO update(PersonVO personVo, Long key) {
		
		if (Objects.isNull(personVo)) throw new RequiredObjectIsNullException();
		
		PersonVO oldPerson = findById(key);
		
		oldPerson.removeLinks(); //Removendo link que o findById() cria, pq o link que ele cria é pra lista, e nesse caso eu quero um link para o próprio objeto.
		
		logger.info("Updating person...");
		
		oldPerson.setFirstName(personVo.getFirstName());
		oldPerson.setLastName(personVo.getLastName());
		oldPerson.setAddress(personVo.getAddress());
		oldPerson.setGender(personVo.getGender());
		
		personRepository.save(DozerMapper.parseObject(oldPerson, Person.class));
		
		oldPerson.add(linkTo(methodOn(PersonController.class).findById(key)).withSelfRel()); //Aplicando HATEOAS.
		
		logger.info("Person successfully updated!");
		
		return oldPerson;
	}
	
	public void delete(Long key) {
		Person person = DozerMapper.parseObject(findById(key), Person.class);
		
		logger.info("Deleting person...");
		
		personRepository.delete(person);
		
		logger.info("Person successfully deleted!");
	}
}
