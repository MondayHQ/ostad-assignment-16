package com.example.warehousemanagementsystem.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

// Local Imports
import com.example.warehousemanagementsystem.entities.ProductEntity;
import com.example.warehousemanagementsystem.repositories.ProductRepository;
import com.example.warehousemanagementsystem.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity saveProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public ProductEntity getProductById(Long id) throws ResourceNotFoundException {
        Optional<ProductEntity> existingProduct = productRepository.findById(id);

        return existingProduct
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    public ProductEntity updateProduct(Long id, ProductEntity productEntity) throws ResourceNotFoundException {
        Optional<ProductEntity> existingProduct = productRepository.findById(id);

        return existingProduct
                .map(
                        product -> {
                            product.setQuantity(productEntity.getQuantity());
                            return productRepository.save(productEntity);
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

}
