package com.banxedap.cdio3.Controllers;

import com.banxedap.cdio3.Entities.Carts;
import com.banxedap.cdio3.Entities.Product;
import com.banxedap.cdio3.Request.CreateProductRequest;
import com.banxedap.cdio3.Services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin
public class ProductController {
    private ProductService productService;
    @GetMapping("/api/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK) ;
    }
    @GetMapping("/api/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int productId){
        return new ResponseEntity<>(productService.findProductById(productId), HttpStatus.OK) ;
    }
    @GetMapping("/api/manufacturers")
    public ResponseEntity< List<Object>> getAllManufacturers(){
        return new ResponseEntity<>(productService.getAllManufacturers(), HttpStatus.OK) ;
    }
    @GetMapping("/api/search")
    public ResponseEntity< List<Product>> getAllManufacturers(@RequestParam Map<String, String> allParams){
        List<Product> list = productService.searchProduct(allParams);
        return new ResponseEntity<>(list, HttpStatus.OK) ;
    }
    @PostMapping("/api/products")
    public ResponseEntity<Object> createProduct(@RequestBody @Valid CreateProductRequest productInformation){
        try {
            return new ResponseEntity<>(productService.saveProduct(productInformation), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
