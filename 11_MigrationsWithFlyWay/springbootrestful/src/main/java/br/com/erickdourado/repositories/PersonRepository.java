package br.com.erickdourado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erickdourado.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {}
