package com.example.warehousemanagementsystem.services;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

// Local Imports
import com.example.warehousemanagementsystem.entities.ProductEntity;
import com.example.warehousemanagementsystem.repositories.ProductRepository;
import com.example.warehousemanagementsystem.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testThatSaveProductCallsTheRepositoryAndReturnsTheSavedProduct() {
        ProductEntity inputProductEntity = ProductEntity
                .builder()
                .name("Samsung Galaxy 10+")
                .quantity(15)
                .price(1250.75)
                .build();

        ProductEntity mockedSavedProductEntity = ProductEntity
                .builder()
                .id(1L)
                .name("Samsung Galaxy 10+")
                .quantity(15)
                .price(1250.75)
                .build();

        when(productRepository.save(inputProductEntity)).thenReturn(mockedSavedProductEntity);

        ProductEntity result = productService.saveProduct(inputProductEntity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Samsung Galaxy 10+", result.getName());
        assertEquals(15, result.getQuantity());
        assertEquals(1250.75, result.getPrice());

        verify(productRepository, times(1)).save(inputProductEntity);
    }

    @Test
    void testThatGetProductByIdReturnsTheProductWhenProductExists() throws ResourceNotFoundException {
        Long productId = 1L;

        ProductEntity existingProductEntity = ProductEntity
                .builder()
                .id(1L)
                .name("Samsung Galaxy 10+")
                .quantity(15)
                .price(1250.75)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProductEntity));

        ProductEntity result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Samsung Galaxy 10+", result.getName());
        assertEquals(15, result.getQuantity());
        assertEquals(1250.75, result.getPrice());

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testThatGetProductByIdRaisesResourceNotFoundExceptionWhenProductDoesNotExists() {
        Long productId = 100L;

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId));

        verify(productRepository, times(1)).findById(productId);
    }

}