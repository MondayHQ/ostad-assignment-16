package com.example.warehousemanagementsystem.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

// Local Imports
import com.example.warehousemanagementsystem.entities.ProductEntity;
import com.example.warehousemanagementsystem.services.ProductService;
import com.example.warehousemanagementsystem.exceptions.ResourceNotFoundException;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void testThatCreateProductReturnsHttp201Created() throws Exception {

        ProductEntity savedProductEntity = ProductEntity
                .builder()
                .id(1L)
                .name("Samsung Galaxy 10+")
                .quantity(15)
                .price(1250.75)
                .build();

        when(productService.saveProduct(any(ProductEntity.class))).thenReturn(savedProductEntity);

        String productJson = "{\n    \"name\": \"Samsung Galaxy 10+\",\n    \"quantity\": 15,\n    \"price\": 1250.75\n}";

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/products")
                                .contentType("application/json")
                                .content(productJson)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Samsung Galaxy 10+")
                ).andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(15))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1250.75));

    }

    @Test
    void testThatGetProductReturnsHttp200OkWhenProductExists() throws Exception {

        ProductEntity savedProductEntity = ProductEntity
                .builder()
                .id(1L)
                .name("Samsung Galaxy 10+")
                .quantity(15)
                .price(1250.75)
                .build();

        when(productService.getProductById(any(Long.class))).thenReturn(savedProductEntity);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/products/1")
                                .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void testThatGetProductReturnsHttp404NotFoundForNonExistingProduct() throws Exception {
        Long productId = 100L;

        when(productService.getProductById(any(Long.class))).thenThrow(new ResourceNotFoundException("Product not found with id " + productId));

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/products/" + productId)
                                .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
