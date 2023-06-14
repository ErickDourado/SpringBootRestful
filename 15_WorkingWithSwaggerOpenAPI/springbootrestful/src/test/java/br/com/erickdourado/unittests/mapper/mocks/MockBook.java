package br.com.erickdourado.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.erickdourado.data.vo.v1.BookVO;
import br.com.erickdourado.model.Book;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookVO mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> booksVo = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            booksVo.add(mockVO(i));
        }
        return booksVo;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setAuthor("Author Test" + number); 
        book.setLaunchDate(new Date(number.longValue()));
        book.setPrice(Double.parseDouble(number.toString()));
        book.setTitle("Title Test" + number);
        return book;
    }

    public BookVO mockVO(Integer number) {
        BookVO bookVo = new BookVO();
        bookVo.setKey(number.longValue());
        bookVo.setAuthor("Author Test" + number); 
        bookVo.setLaunchDate(new Date(number.longValue()));
        bookVo.setPrice(Double.parseDouble(number.toString()));
        bookVo.setTitle("Title Test" + number);
        return bookVo;
    }
}