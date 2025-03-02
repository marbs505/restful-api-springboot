package com.marvinfabricante.storeapi.controllers;

import com.marvinfabricante.storeapi.models.Product;
import com.marvinfabricante.storeapi.models.ProductDto;
import com.marvinfabricante.storeapi.repositories.ProductsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsRepository repo;

    @GetMapping
    public List<Product> getProducts() {
        return repo.findAll();
    }

    // ANG PURPOSE NG METHOD NA ITO IS BASAHIN ANG SPECIFIC NA PRODUCT BY ID
    @GetMapping("{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = repo.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    // ETONG METHOD NA ITO, ETO YUNG NAG SSEND OR NAG CCREATE NG PRODUCT SA DATABSE MOH
    @PostMapping
    public ResponseEntity<Object> createProduct(
            @Valid @RequestBody ProductDto productDto, BindingResult result
    ) {
        double price = 0;
        try {
            price = Double.parseDouble(productDto.getPrice());
        } catch (Exception e) {
            result.addError(new FieldError("productDto", "price", "The price should be a number"));
        }

        if (result.hasErrors()) {
            var errorList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for (int i = 0; i < errorList.size(); i++) {
                var error = (FieldError) errorList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errorsMap);
        }

        Product product = new Product();

        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(price);
        product.setDescription(productDto.getDescription());
        product.setCreatedAt(new Date());

        // etoh yung mag ppasok ng data galing api
        repo.save(product);

        return ResponseEntity.ok(product);
    }

    // UPDATE LANG NG SHITS GAMIT YUNG ID
    @PutMapping("{id}")
    public ResponseEntity<Object> updateProduct(
            @PathVariable int id,
            @Valid @RequestBody ProductDto productDto,
            BindingResult result
    ) {
        Product product = repo.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        double price = 0;

        try {
            price = Double.parseDouble(productDto.getPrice());
        } catch (Exception e) {
            result.addError(new FieldError("productDto", "price", "The price should be a number"));
        }

        if (result.hasErrors()) {
            var errorsList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for (int i = 0; i < errorsList.size(); i++) {
                var error = (FieldError) errorsList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errorsMap);
        }

        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(price);
        product.setDescription(productDto.getDescription());

        repo.save(product);

        return ResponseEntity.ok(product);
    }

    // DELETE USER BY ID, i
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable int id) {
        Product product = repo.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        repo.delete(product);
        return ResponseEntity.ok().build();
    }

}
