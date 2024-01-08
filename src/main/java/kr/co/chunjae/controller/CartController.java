package kr.co.chunjae.controller;

import kr.co.chunjae.domain.Book;
import kr.co.chunjae.domain.Cart;
import kr.co.chunjae.domain.CartItem;
import kr.co.chunjae.exception.BookIdException;
import kr.co.chunjae.service.BookService;
import kr.co.chunjae.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private BookService bookService;

    @GetMapping // "/cart"로 uri 요청시 세션ID를 가져와서 리다이렉트
    public String requestCartId(HttpServletRequest request) {
        String sessionid = request.getSession(true).getId();
        return "redirect:/cart/"+ sessionid;
    }

    @PostMapping //post 방식 요청시 Cart 클래스 정보를 요청 body로 전달받아 장바구니를 새로 생성, 응답 body로 전달
    public @ResponseBody Cart create(@RequestBody Cart cart ) {
        return cartService.create(cart);
    }

    @GetMapping( "/{cartId}") // /cart/cartId  uri 요청시
    public String requestCartList(@PathVariable(value = "cartId") String cartId, Model model) {
        Cart cart = cartService.read(cartId); // 경로변수 cartId에 대해 장바구니 등록 모든 정보를 읽어 커맨드 객체 cart 속성에 등록
        model.addAttribute("cart",cart);
        return "cart";
    }

    @PutMapping("/{cartId}") // /cart/cartId uri요청, http 메서드가 put 방식이면 요청처리
    public @ResponseBody Cart read(@PathVariable(value = "cartId") String cartId) {
        return cartService.read(cartId); //경로변수 cartId에 대해 장바구니에 등록된 모든 정보를 가져옴
    }

    @PutMapping("/add/{bookId}") // cart/add/{bookId} uri요청, http 메서드가 put 방식이면 요청처리
    @ResponseStatus(value = HttpStatus.NO_CONTENT) //오류 응답상태 코드설정. 응답 바디 무시, 204
    public void addCartByNewItem(@PathVariable String bookId, HttpServletRequest request) {

        String sessionId = request.getSession(true).getId(); //장바구니 아이디인 세션아이디 가져오기
        Cart cart = cartService.read(sessionId); //장바구니에 등록된 모든 정보 얻어오기
        if(cart== null)
            cart = cartService.create(new Cart(sessionId)); //장바구니에 등록된 정보 없는경우 sessionId를 cartId로 하여 장바구니 신규 생성

        Book book = bookService.getBookById(bookId); //경로변수 bookId에 대한 정보 얻어오기
        if(book == null)
            throw new IllegalArgumentException(new BookIdException(bookId));

        cart.addCartItem(new CartItem(book)); // bookId에 대한 도서정보를 장바구니에 등록하기
        cartService.update(sessionId, cart); //세션Id(cartId)에 대한 장바구니 갱신
    }

    @PutMapping("/remove/{bookId}") //변수 bookId에 대해 해당 도서를 장바구니에서 삭제하고 장바구니를 갱신
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeCartByItem(@PathVariable String bookId, HttpServletRequest request) {
        String sessionId = request.getSession(true).getId();
        System.out.println(sessionId);
        Cart cart = cartService.read(sessionId);
        if(cart== null)
            cart = cartService.create(new Cart(sessionId));

        Book book = bookService.getBookById(bookId);
        if(book == null)
            throw new IllegalArgumentException(new BookIdException(bookId));

        cart.removeCartItem(new CartItem(book));//bookId에 대한 도서정보를 장바구니에서 삭제하기
        cartService.update(sessionId, cart);
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCartList(@PathVariable(value = "cartId") String cartId) {
        cartService.delete(cartId);
    }
}
