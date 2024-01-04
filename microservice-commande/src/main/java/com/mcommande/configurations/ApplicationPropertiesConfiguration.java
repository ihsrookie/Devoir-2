package com.mcommande.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

//permet de déclarer une classe en tant que bean
@Component
@ConfigurationProperties("mes-config-ms")
@RefreshScope
public class ApplicationPropertiesConfiguration
{
    private int commandeslast;

    public int getLimitDeCommande()
    {
        return commandeslast;
    }

    public void setLimitDeCommande(int commandesLast)
    {
        this.commandeslast = commandesLast;
    }
}
