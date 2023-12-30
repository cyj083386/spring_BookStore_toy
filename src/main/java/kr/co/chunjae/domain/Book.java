package kr.co.chunjae.domain;

import kr.co.chunjae.validator.BookId;
import lombok.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
//@AllArgsConstructor // 파라미터가 들어가는 생성자
@NoArgsConstructor  // 일반 생성자

public class Book {
    @BookId
    @Pattern(regexp="ISBN[1-9]+", message="{Pattern.NewBook.bookId}")
    private String bookId;            // 도서Id

    @Size(min=4, max=50, message="{Size.NewBook.name}")
    private String name;              // 도서명

    @Min(value=0, message="{Min.NewBook.unitPrice}")
    @Digits(integer=8, fraction=2, message="{Digits.NewBook.unitPrice}") // 이 숫자에 허용되는 최대 정수 자릿수
    @NotNull(message= "{NotNull.NewBook.unitPrice}")
    private BigDecimal unitPrice;            // 도서 가격

    private String author;            // 저자
    private String description;       // 설명
    private String publisher;         // 출판사
    private String category;          //분류
    private long unitsInStock;        // 재고
    private String releaseDate;       // 출판일(월/년)
    private String condition;         // 신규 도서 또는 중고 도서 또는 전자책
    private MultipartFile bookImage;  // 도서 이미지


    public Book(String bookId, String name, BigDecimal unitPrice) {
        super();
        this.bookId = bookId;
        this.name = name;
        this.unitPrice = unitPrice;
    }

}
