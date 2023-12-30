package kr.co.chunjae.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kr.co.chunjae.domain.Book;
import kr.co.chunjae.exception.BookIdException;
import kr.co.chunjae.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

//ConstraintValidator<사용자정의 애너테이션, 도메인 클래스 Book에서 사용자 정의 애너테이션 @BookId을 붙인 멤버변수 bookId의 타입>
public class BookIdValidator implements ConstraintValidator<BookId, String>{

    @Autowired
    private BookService bookService;

    public void initialize(BookId constraintAnnotation) {  // @BookId 정보 초기화 메서드
    }
    public boolean isValid(String value, ConstraintValidatorContext context) {  // 유효성 검사 메서드 , value는 도메인 클래스 Book의 멤버변수 bookId의 값
        Book book;
        try {
            book = bookService.getBookById(value);
        } catch (BookIdException e) {
            return true;
        }
        if(book != null) {
            return false;
        }
        return true;
    }
}