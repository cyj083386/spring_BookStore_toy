package kr.co.chunjae.controller;


import kr.co.chunjae.domain.Book;
import kr.co.chunjae.exception.BookIdException;
import kr.co.chunjae.exception.CategoryException;
import kr.co.chunjae.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping // 클래스 수준의 매핑 요청(/books)을 처리하는 메서드. 메소드 단위 매핑이 없음.
    public String requestBookList(Model model){
        List<Book> list = bookService.getAllBookList(); // 웹 요청을 처리할 메서드
        model.addAttribute("bookList", list); // 모델 속성 bookList에 저장된 도서 목록을 저장
        return "books"; // book을 반환하므로 book.jsp가 있어야함.

    }

    /*@RequestMapping(value="/all")
    public String requestAllBookList(Model model){
        List<Book> list = bookService.getAllBookList(); // 웹 요청을 처리할 메서드
        model.addAttribute("bookList", list); // 모델 속성 bookList에 저장된 도서 목록을 저장
        return "books"; // book을 반환하므로 book.jsp가 있어야함.

    }*/
    @GetMapping("/all")
    public ModelAndView requestAllBookList( ){
        ModelAndView modelAndView = new ModelAndView();

        List<Book> list = bookService.getAllBookList(); // 웹 요청을 처리할 메서드
        modelAndView.addObject("bookList", list);
        modelAndView.setViewName("books");
        return modelAndView; // book을 반환하므로 book.jsp가 있어야함.
        // url : http://localhost:8092/BookMarket/books/all
    }

    @GetMapping("/{category}")
    // 경로변수 category를 bookCategory로 재정의
    public String requestBooksByCategory(@PathVariable("category") String bookCategory, Model model) {
        //bookCategory와 일치하는 도서 목록을 서비스 객체에서 가져와 booksByCategory에 저장
        List<Book> booksByCategory = bookService.getBookListByCategory(bookCategory);
        if (booksByCategory == null || booksByCategory.isEmpty()) {
            throw new CategoryException();
        }
        model.addAttribute("bookList", booksByCategory); // 값을 모델 속성 booklist에 저장 후 출력
        return "books"; // books.jsp
    }

    @GetMapping("/book")
    public String requestBookById(@RequestParam("id") String bookId, Model model){
        Book bookById = bookService.getBookById(bookId);
        model.addAttribute("book", bookById);
        return "book"; // book.jsp
    }

    @GetMapping("/filter/{bookFilter}")
    public String requestBooksByFilter(@MatrixVariable(pathVar = "bookFilter") Map<String, List<String>> bookFilter, Model model) {

        Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", booksByFilter);
        return "books";
    }

   /* @GetMapping("/add")
    public String requestAddBookForm(Book book) {
        return "addBook";
    } 기존에서 아래로 수정*/
    @GetMapping("/add")
    public String requestAddBookForm(@ModelAttribute("NewBook") Book book) {

        return "addBook";
    }
    @PostMapping("/add")
    public String submitAddNewBook(@ModelAttribute("NewBook") Book book) {
        // 이미지 등록을 위한 수정
        MultipartFile bookImage = book.getBookImage();

        String saveName = bookImage.getOriginalFilename();
        File saveFile = new File("C:\\upload", saveName);

        if (bookImage != null && !bookImage.isEmpty()) {
            try {
                bookImage.transferTo(saveFile); //도서 이미지 파일을 C:\\upload 경로로 업로드
            } catch (Exception e) {
                throw new RuntimeException("도서 이미지 업로드가 실패하였습니다", e);
            }
        }
        bookService.setNewBook(book);
        return "redirect:/books";  // 웹 요청 URL을 강제로 /books로 이동시켜 매핑함.
    }

    @ModelAttribute // 메서드 수준의 @ModelAttribute 선언
    public void addAttribute(Model model) {
        // 모델 속성 addTitle에 신규 도서 등록을 저장함.
        model.addAttribute("addTitle", "신규 도서 등록");
    }

    @InitBinder("NewBook")
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("bookId","name","unitPrice","author", "description",
                "publisher","category","unitsInStock","totalPages", "releaseDate", "condition", "bookImage");
    }

    @ExceptionHandler(value={BookIdException.class})
    public ModelAndView handleError(HttpServletRequest req, BookIdException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("invalidBookId", exception.getBookId());
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
        mav.setViewName("errorBook");
        return mav;
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        return "login";
    }

}

