package com.banxedap.cdio3.Controllers;

import com.banxedap.cdio3.DTO.CartDTO;
import com.banxedap.cdio3.Entities.Carts;
import com.banxedap.cdio3.Request.AddToCartRequest;
import com.banxedap.cdio3.Services.CartService;
import com.banxedap.cdio3.Services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @PostMapping("/api/carts")
    public ResponseEntity<Carts> addToCart(@RequestBody @Valid AddToCartRequest request){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Carts carts = cartService.addToCartRequest(request, principal);
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }
    @GetMapping("/api/carts")
    public ResponseEntity<Object> getAllCartsOfUser(){
        try{
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<CartDTO> carts = cartService.getAllCartsOfUser(principal);
            return new ResponseEntity<>(carts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/api/carts/{id}")
    public ResponseEntity<Object> deleteCartById(@PathVariable("id") int id){
        try{
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            com.banxedap.cdio3.Entities.User user = userService.getUserByUserName(principal.getUsername());
            boolean isSuccess = cartService.deleteCartByProductId(id, user);
            return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
