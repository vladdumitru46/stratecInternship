package org.example.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ShopRepository {

    private String fileName;

    public void readFromFile(){
        
    }

}
