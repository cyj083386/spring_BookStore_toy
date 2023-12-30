package kr.co.chunjae.repository;


import kr.co.chunjae.domain.Book;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface BookRepository {
    List<Book> getAllBookList();

    List<Book> getBookListByCategory(String category);

    Set<Book> getBookListByFilter(Map<String, List<String>> filter);

    Book getBookById(String bookId);

    // NewBook을 넣겠다?
    // 신규 도서 정보 저장
    void setNewBook(Book book); // 값을 주므로 set , 가져오면 get

}
