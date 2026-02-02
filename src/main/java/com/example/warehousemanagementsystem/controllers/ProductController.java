package com.example.warehousemanagementsystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Local Imports
import com.example.warehousemanagementsystem.entities.ProductEntity;
import com.example.warehousemanagementsystem.services.ProductService;
import com.example.warehousemanagementsystem.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity productEntity) {
        ProductEntity savedProductEntity = productService.saveProduct(productEntity);

        return new ResponseEntity<>(savedProductEntity, HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PutMapping(path = "{id}/stock")
    public ResponseEntity<ProductEntity> updateProductStock(@PathVariable Long id, @RequestBody ProductEntity productEntity) throws ResourceNotFoundException {
        return new ResponseEntity<>(productService.updateProduct(id, productEntity), HttpStatus.OK);
    }

}
