package com.mproduits.controller;

import com.mproduits.configurations.ApplicationPropertiesConfiguration;
import com.mproduits.dao.ProductDao;
import com.mproduits.model.Product;
import com.mproduits.exceptions.ProductNotFoundException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.context.config.annotation.RefreshScope;


import java.util.List;
import java.util.Optional;

@EnableCircuitBreaker
@Configuration
@EnableHystrixDashboard
@RestController
public class ProductController implements HealthIndicator
{
    @Autowired
    ProductDao productDao;

    @Autowired
    ApplicationPropertiesConfiguration appProperties;


    @GetMapping("/myMessage")

    @HystrixCommand(fallbackMethod = "myHistrixbuildFallbackMessage", commandProperties ={@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")}, threadPoolKey = "messageThreadPool")

    public String getMessage() throws InterruptedException {
        System.out.println("Message from EmployeeController.getMessage(): Begin To sleep for 3 scondes ");
        Thread.sleep(3000);
        return "Message from EmployeeController.getMessage(): End from sleep for 3 scondes ";
    }
    private String myHistrixbuildFallbackMessage()
    {
        return "Message from myHistrixbuildFallbackMessage() : Hystrix Fallback message ( after timeout : 1 second )";
    }


    // Affiche la liste de tous les produits disponibles
    @GetMapping(value = "/Produits")
    public List<Product> listeDesProduits() throws ProductNotFoundException
    {
        System.out.println("******************ProductController listeDesProduits()******************");
        List<Product> products = productDao.findAll();

        if (products.isEmpty())
        {
            throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");
        }
        //kaijib lina liste de produit diyal product lifiha 10 ou insplitiha mn 0 tal appProperties.getLimitDeProduits() = 4
        List<Product> listeLimitee = products.subList(0,appProperties.getLimitDeProduits());
        return listeLimitee;
    }

    // Récuperer un produit par son id
    @GetMapping(value = "/Produits/{id}")
    public Optional<Product> recupererUnProduit(@PathVariable int id) throws ProductNotFoundException {
        System.out.println("******************ProductController recupererUnProduit(@PathVariable int id)******************");
        Optional<Product> product = productDao.findById(id);
        if (!product.isPresent())
        {
            throw new ProductNotFoundException("Le produit correspondant à l'id " + id + " n'existe pas");
        }
        return product;
    }


    @Override
    public Health health()
    {
        System.out.println("******************Actuator : ProductController health()******************");
        List<Product> products = productDao.findAll();
        if (products.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }


}
