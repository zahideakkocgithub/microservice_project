package com.microservice.productservice.service;

import com.microservice.productservice.dto.ProductRequest;
import com.microservice.productservice.dto.ProductResponse;
import com.microservice.productservice.model.Product;
import com.microservice.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest)
    {
        Product product = Product.builder().name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice()).build();

        productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts()
    {
        List<ProductResponse> productResponseList = new ArrayList<>();
        List<Product> product=productRepository.findAll();
        for(int i=0;i<product.size();i++) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.get(i).getId());
            productResponse.setName(product.get(i).getName());
            productResponse.setDescription(product.get(i).getDescription());
            productResponse.setPrice(product.get(i).getPrice());
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }
}
