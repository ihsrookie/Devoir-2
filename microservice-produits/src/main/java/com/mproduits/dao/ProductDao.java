package com.mproduits.dao;

import com.mproduits.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//JpaRepository : kathib lina crud diyal l'entite product
public interface ProductDao extends JpaRepository<Product,Integer>
{

}
