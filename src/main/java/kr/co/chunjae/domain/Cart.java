package kr.co.chunjae.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Cart{
    private String cartId;  //장바구니 ID
    private Map<String,CartItem> cartItems; //장바구니 항목
    private BigDecimal grandTotal; // 총액

    public Cart() {
        cartItems = new HashMap<String, CartItem>();
        grandTotal = new BigDecimal("0");
    }

    public Cart(String cartId) {
        this();
        this.cartId = cartId;
    }

    public void updateGrandTotal() {
        grandTotal= new BigDecimal("0");;
        for(CartItem item : cartItems.values()){
            grandTotal = grandTotal.add(item.getTotalPrice());
        }
    }

    public void addCartItem(CartItem item) { //도서목록중 선택한 도서를 장바구니에 등록
        String bookId = item.getBook().getBookId();

        //도서아이디가 cartItems 객체에 등록되어있는지 여부 확인
        if(cartItems.containsKey(bookId)) {
            CartItem cartItem = cartItems.get(bookId);
            //등록된 도서ID의 개수 추가 저장
            cartItem.setQuantity(cartItem.getQuantity()+item.getQuantity());
            cartItems.put(bookId, cartItem); //등록된 도서ID에 대한 변경 정보(cartItem) 저장
        } else {
            cartItems.put(bookId, item);  //도서ID에 대한 도서정보(item) 저장
        }
        updateGrandTotal(); //총액갱신
    }

    public void removeCartItem(CartItem item) {
        String bookId = item.getBook().getBookId();
        cartItems.remove(bookId);
        updateGrandTotal();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cartId == null) ? 0 : cartId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cart other = (Cart) obj;
        if (cartId == null) {
            if (other.cartId != null)
                return false;
        } else if (!cartId.equals(other.cartId))
            return false;
        return true;
    }
}
