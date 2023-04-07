package com.banxedap.cdio3.Services;

import com.banxedap.cdio3.AdviceHandle.NotFoundException;
import com.banxedap.cdio3.AdviceHandle.SaveEntityFailed;
import com.banxedap.cdio3.Entities.Carts;
import com.banxedap.cdio3.Entities.Product;
import com.banxedap.cdio3.Repository.ProductRepository;
import com.banxedap.cdio3.Request.CreateProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Product findProductById(int id){
        if(!productRepository.findById(id).isPresent())
            throw new NotFoundException("Not found Product by id = "+id);
        return productRepository.findById(id).get();
    }
    public Product saveProduct(CreateProductRequest productInformation){
        Product toSave = new Product();
        toSave.setProductName(productInformation.getProductName());
        toSave.setAvatar(productInformation.getAvatar());
        toSave.setCategory(productInformation.getCategory());
        toSave.setQuantity(productInformation.getQuantity());
        toSave.setManufacturer(productInformation.getManufacturer());
        toSave.setPrice(  ( productInformation.getCost() - (int)(productInformation.getCost()* productInformation.getPercent() / 100)  ));
        toSave.setCost(productInformation.getCost());
        toSave.setPercent(productInformation.getPercent());
        toSave.setSize(productInformation.getSize());
        toSave.setWeight(productInformation.getWeight());
        toSave.setTire(productInformation.getTire());
        toSave.setDescription(productInformation.getDescription());

        try{
            return productRepository.save(toSave);
        }catch (Exception exception){

            throw new SaveEntityFailed("Save product failed");
        }
    }

    public Product updateProduct(CreateProductRequest productInformation, int productId) {
        Product toSave = findProductById(productId);
        toSave.setProductName(productInformation.getProductName());
        toSave.setAvatar(productInformation.getAvatar());
        toSave.setQuantity(productInformation.getQuantity());
        toSave.setCategory(productInformation.getCategory());
        toSave.setManufacturer(productInformation.getManufacturer());
        toSave.setPrice(  ( productInformation.getCost() - (int)(productInformation.getCost()* productInformation.getPercent() / 100)  ));
        toSave.setCost(productInformation.getCost());
        toSave.setPercent(productInformation.getPercent());
        toSave.setSize(productInformation.getSize());
        toSave.setWeight(productInformation.getWeight());
        toSave.setTire(productInformation.getTire());
        toSave.setDescription(productInformation.getDescription());
        try{
            return productRepository.save(toSave);
        }catch (Exception exception){

            throw new SaveEntityFailed("Save product failed");
        }
    }

    public boolean deleteProduct(int productId) {
        Product found = findProductById(productId);
        try{
            productRepository.delete(found);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Object>  getAllManufacturers() {
        return productRepository.getAllManufacturers();
    }

    public List<Product> searchProduct(Map<String, String> allParams) {
        if(allParams.containsKey("manufacturer")){
            List<Product> list = productRepository.searchProduct(allParams.get("manufacturer"));
            return list;
        }
        return null;
    }
}
