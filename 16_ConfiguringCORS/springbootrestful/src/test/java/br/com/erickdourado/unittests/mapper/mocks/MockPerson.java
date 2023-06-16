package br.com.erickdourado.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.erickdourado.data.vo.v1.PersonVO;
import br.com.erickdourado.model.Person;

public class MockPerson {

    public Person mockEntity() {
        return mockEntity(0);
    }
    
    public PersonVO mockVO() {
        return mockVO(0);
    }
    
    public List<Person> mockEntityList() {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<PersonVO> mockVOList() {
        List<PersonVO> personsVo = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            personsVo.add(mockVO(i));
        }
        return personsVo;
    }
    
    public Person mockEntity(Integer number) {
        Person person = new Person();
        person.setId(number.longValue());
        person.setFirstName("First Name Test" + number);
        person.setLastName("Last Name Test" + number);
        person.setAddress("Addres Test" + number);
        person.setGender((number % 2 == 0) ? "Male" : "Female");
        return person;
    }

    public PersonVO mockVO(Integer number) {
        PersonVO personVo = new PersonVO();
        personVo.setKey(number.longValue());
        personVo.setFirstName("First Name Test" + number);
        personVo.setLastName("Last Name Test" + number);
        personVo.setAddress("Addres Test" + number);
        personVo.setGender(((number % 2)==0) ? "Male" : "Female");
        return personVo;
    }
}