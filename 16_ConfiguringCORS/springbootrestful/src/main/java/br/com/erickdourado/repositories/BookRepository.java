package br.com.erickdourado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erickdourado.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}
