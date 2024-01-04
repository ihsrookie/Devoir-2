package com.mcommande.controller;

import com.mcommande.model.Commande;
import com.mcommande.configurations.ApplicationPropertiesConfiguration;
import com.mcommande.dao.CommandeDao;
import com.mcommande.exceptions.CommandeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class CommandeController implements HealthIndicator //tester l’état de santé du MS
{
    @Autowired
    CommandeDao commandeDao;

    @Autowired
    ApplicationPropertiesConfiguration appProperties;

    // Afficher la liste de tous les commandes des 10 dernier jour
    @GetMapping(value = "/CommandesLast10Days")
    public List<Commande> listeDesCommande() throws CommandeNotFoundException
    {
        System.out.println("******************CommandeController listeDesCommandes last 10 days()******************");
        List<Commande> commandes = commandeDao.findAll();
        System.out.println("Number of commandes retrieved: " + commandes.size());

        if (commandes.isEmpty())
        {
            throw new CommandeNotFoundException("Aucun commande n'est disponible");
        }
        LocalDate tenDaysAgo = LocalDate.now().minusDays(appProperties.getLimitDeCommande());

        // Retrieve commands from the last 10 days
        List<Commande> commandesLast10Days = commandeDao.findByDateAfter(tenDaysAgo);

        return commandesLast10Days;
    }

    // Afficher la liste de tous les commandes disponibles
    @GetMapping(value = "/Commandes")
    public List<Commande> AllCommande() throws CommandeNotFoundException {
        System.out.println("******************CommandeController listeDesCommandes()******************");
        List<Commande> commandes = commandeDao.findAll();
        return commandes;
    }

    // chercher une commande par son id
    @GetMapping(value = "/Commandes/{id}")
    public Optional<Commande> recupererUneCommande(@PathVariable int id) throws CommandeNotFoundException
    {
        System.out.println("******************CommandeController recupererUneCommande(@PathVariable int id)******************");
        Optional<Commande> commande = commandeDao.findById(id);
        if (!commande.isPresent())
        {
            throw new CommandeNotFoundException("La commande correspondant à l'id " + id + " n'existe pas");
        }
        return commande;
    }


    // inserer une nouvelle commande
    @PostMapping("/Commandes")
    public ResponseEntity<Commande> createTutorial(@RequestBody Commande newCommand) {
        try
        {
            Commande command = commandeDao.save(new Commande(newCommand.getId(), newCommand.getDescription(),newCommand.getQuantite(),newCommand.getDatee(),newCommand.getMontant(), newCommand.getProduct_id()));
            return new ResponseEntity<>(command, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Modifier une commande par son id
    @PutMapping("/Commandes/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable("id") int id, @RequestBody Commande command) {
        Optional<Commande> commandedata = commandeDao.findById(id);

        if (commandedata.isPresent())
        {
            Commande _comman = commandedata.get();
            _comman.setId(command.getId());
            _comman.setDescription(command.getDescription());
            _comman.setQuantite(command.getQuantite());
            _comman.setDatee(command.getDatee());
            _comman.setMontant(command.getMontant());
            _comman.setProduct_id(command.getProduct_id());
            return new ResponseEntity<>(commandeDao.save(_comman), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Supprimer une Commande par son id
    @DeleteMapping(value = "/Commandes/{id}")
    public void supprimerCommandes(@PathVariable int id) throws CommandeNotFoundException
    {
        System.out.println(" ********* CommandeController supprimerCommande(@PathVariable int id) ");
        Optional<Commande> commande = commandeDao.findById(id);
        if (!commande.isPresent())
        {
            throw new CommandeNotFoundException("Le Commande correspondant à l'id " + id + " n'existe pas");
        }
        commandeDao.deleteById(id);
    }

    //supprimer tout les commandes
    @DeleteMapping("/Commandes")
    public ResponseEntity<HttpStatus> deleteAllCommand()
    {
        try {
            commandeDao.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Recharger la configuration sans redémarrer le miscroservice
    @Override
    public Health health()
    {
        System.out.println("******************Actuator : CommandeController health()******************");
        List<Commande> commandes = commandeDao.findAll();
        if (commandes.isEmpty())
        {
            return Health.down().build();

        }
        return Health.up().build();
    }

}

