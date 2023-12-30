package kr.co.chunjae.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="요청한 도서 분야를 찾을 수 없습니다.")
public class CategoryException extends RuntimeException{
    // 예외가 발생하면 웹 브라우저에 상태 코드 404와 오류 메시지를 출력함.


}
