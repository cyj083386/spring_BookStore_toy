package kr.co.chunjae.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
@AllArgsConstructor
public class BookIdException extends RuntimeException{

    private String bookId;

    //getter를 활용, BookIdException 발생시 검색한 bookId를 넘김

}
