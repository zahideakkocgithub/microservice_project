package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.model.Inventory;
import com.microservice.inventoryservice.repository.InventoryRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    @SneakyThrows
    public boolean isInStock(List<String> skuCode)
    {

        if( inventoryRepository.findBySkuCodeIn(skuCode).size()>0)
        {
           /* List<Inventory> inventories=inventoryRepository.findBySkuCodeIn(skuCode);
            for(int i=0;i<inventories.size();i++)
            {
                if(inventories.get(i).getQuantity()>0)
                {
                    inventories.get(i).setQuantity(inventories.get(i).getQuantity()-1);
                    inventoryRepository.save(inventories.get(i));
                }else
                {
                    return false;
                }
            }*/
            return true;

        }
        else
            return false;
    }
}
