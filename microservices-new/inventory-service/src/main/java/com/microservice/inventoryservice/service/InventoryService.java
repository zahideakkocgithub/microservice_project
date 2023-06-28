package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.repository.InventoryRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    @SneakyThrows
    public boolean isInStock(String skuCode)
    {
        return true;
     /*  if( inventoryRepository.findBySkuCodeIn(skuCode).size()>0)
            return true;
        else
            return false;*/
    }
}
