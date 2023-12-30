package kr.co.chunjae.service;


import kr.co.chunjae.domain.Book;
import kr.co.chunjae.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> getAllBookList() {
        return bookRepository.getAllBookList(); // 저장된 도서 목록을 가져옴.

    }
    @Override
    public List<Book> getBookListByCategory(String category) {
        // 저장소 객체에서 일치하는 도서 목록을 가져와서 저장
        List<Book> booksByCategory = bookRepository.getBookListByCategory(category);
        return booksByCategory; // 도서목록이 저장된 도서 목록 객체 반환
    }

    @Override
    public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
        Set<Book> booksByFilter = bookRepository.getBookListByFilter(filter);
        return booksByFilter;
    }

    @Override
    public Book getBookById(String bookId) {
        Book bookById = bookRepository.getBookById(bookId);
        return bookById;
    }

    @Override
    public void setNewBook(Book book) { // 신규 도서 정보를 저장소 객체에 저장하는 메서드
        bookRepository.setNewBook(book); // 저장소 객체의 setNewBook메서드 호출
    }
}
