package com.mcommande.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Commande
{

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String description;
    private int quantite;
    private LocalDate datee;
    private int montant;
    private int product_id;


    public Commande()
    {}

    public Commande(Integer id, String description, int quantite, LocalDate datee, int montant ,int product_id)
    {
        this.id = id;
        this.description = description;
        this.quantite = quantite;
        this.datee = datee;
        this.montant = montant;
        this.product_id = product_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public LocalDate getDatee() {
        return datee;
    }

    public void setDatee(LocalDate datee) {
        this.datee = datee;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantite=" + quantite +
                ", datee=" + datee +
                ", montant=" + montant +
                ", product_id=" + product_id +
                '}';
    }
}
