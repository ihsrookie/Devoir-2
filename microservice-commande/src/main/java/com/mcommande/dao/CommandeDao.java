package com.mcommande.dao;

import com.mcommande.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
//JpaRepository : katjib lina crud diyal l'entite product
public interface CommandeDao extends JpaRepository<Commande,Integer>
{
    @Query("SELECT c FROM Commande c WHERE c.datee >= CURRENT_DATE - 10")
    List<Commande> findByDateAfter(LocalDate datee);

}
