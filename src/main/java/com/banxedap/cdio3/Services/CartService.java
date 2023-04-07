package com.banxedap.cdio3.Services;

import com.banxedap.cdio3.AdviceHandle.BadRequestException;
import com.banxedap.cdio3.AdviceHandle.NotFoundException;
import com.banxedap.cdio3.DTO.CartDTO;
import com.banxedap.cdio3.Entities.Carts;
import com.banxedap.cdio3.Entities.CustomPrimaryKey.CartId;
import com.banxedap.cdio3.Entities.Product;
import com.banxedap.cdio3.Repository.CartRepository;
import com.banxedap.cdio3.Request.AddToCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    public Carts addToCartRequest(AddToCartRequest request, User principal) {
        int userId = userService.getUserByUserName(principal.getUsername()).getId();
        Product found = productService.findProductById(request.getProductId());
        if(found== null)
            throw new NotFoundException("Not found Product");
        if( found.getQuantity() - request.getQuantity() < 0 )
            throw new BadRequestException("Quantity is not enough");
        Optional<Carts> checkExist = cartRepository.findById(new CartId(userId, found.getId()));
        if(checkExist.isEmpty()){
            Carts carts = new Carts();
            carts.setAvatar(found.getAvatar());
            carts.setCartId(new CartId(userId, found.getId()));
            carts.setPrice(found.getPrice());
            carts.setIsPossibleToOrder(1);
            carts.setTotal((int)request.getQuantity()* found.getPrice());
            carts.setQuantity(request.getQuantity());
            return cartRepository.save(carts);
        }else{
            Carts tmp = checkExist.get();
            tmp.setQuantity(request.getQuantity());
            tmp.setPrice(found.getPrice());
            tmp.setTotal((int) request.getQuantity()* found.getPrice());
            return cartRepository.save(tmp);
        }


    }

    public List<CartDTO> getAllCartsOfUser(User principal) {
        int id = userService.getUserByUserName(principal.getUsername()).getId();
        List<CartDTO> list = new ArrayList<>();
        List<Carts> carts =  cartRepository.findCartsByUserId(id);
        for (Carts cart:
             carts) {
            Product found = productService.findProductById(cart.getCartId().getProductId());
            list.add(new CartDTO(cart.getCartId(), found, cart.getQuantity(), (int) cart.getTotal()));
        }
        return list;
    }
    public Carts findByUserIdAndProductId(int userId, int productId){
        return cartRepository.findByUserIdAndProductId(userId, productId);
    }
    public boolean deleteCartByProductId(int productId, com.banxedap.cdio3.Entities.User principal) {
        int userId = principal.getId();
        if(productService.findProductById(productId) == null)
            throw new NotFoundException("");
        Carts found =  findByUserIdAndProductId(userId, productId) ;
        if(found == null)
            throw new NotFoundException("Not found carts");
        cartRepository.delete(found);
        if(findByUserIdAndProductId(userId, productId) == null)
            return true;
        return false;
    }
}
