package kr.co.chunjae.validator;

import kr.co.chunjae.domain.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
public class UnitsInStockValidator implements Validator {
    public boolean supports(Class<?> clazz) {  // Book 클래스의 유효성 검사 여부를 위한 메서드
        return Book.class.isAssignableFrom(clazz); //isAssignableFrom()은 Class 객체가 나타내는 클래스 또는 인터페이스가 지정된
        // Class 매개변수로 표시된 클래스 또는 인터페이스와 동일하거나, 상위 클래스 또는 상위 인터페이스 인지 체크.
    }

    public void validate(Object target, Errors errors) {  // Book 클래스의 유효성 검사 메서드
        Book book = (Book) target;
        if(book.getUnitPrice().compareTo(new BigDecimal("10000")) >= 0
                && book.getUnitsInStock()>99) {//도서가격이 1만원 이상이고, 재고가 99권을 초과하면 유효성 검사를 할때 오류가 발생
            errors.rejectValue("unitsInStock", "UnitsInStockValidator.message");  // 오류 객체의 속성과 메시지 저장
        }
    }
}
