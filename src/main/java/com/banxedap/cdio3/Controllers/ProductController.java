package com.banxedap.cdio3.Controllers;

import com.banxedap.cdio3.Entities.Product;
import com.banxedap.cdio3.Request.CreateProductRequest;
import com.banxedap.cdio3.Services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {
    private ProductService productService;
    @GetMapping("/api/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK) ;
    }
    @PostMapping("/api/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductRequest productInformation){
        return new ResponseEntity<>(productService.saveProduct(productInformation), HttpStatus.OK);
    }
    @PutMapping("/api/products/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid CreateProductRequest productInformation, @PathVariable("id") int product_id){
        return new ResponseEntity<>(productService.updateProduct(productInformation, product_id), HttpStatus.OK);
    }
    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int product_id){
        if(productService.deleteProduct(product_id) )
            return new ResponseEntity<>( HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
