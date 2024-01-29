package com.cambioservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cambioservice.model.Cambio;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("cambio-service")
public class CambioController {

    @Autowired
    Environment environment;

    @GetMapping("/{amount}/{from}/{to}")
    public Cambio getCambio(@RequestParam(value = "amount", defaultValue = "1") Double amount,
            @RequestParam(value = "from", defaultValue = "USD") String from,
            @RequestParam(value = "to", defaultValue = "BRL") String to) {

        var port = environment.getProperty("local.server.port");
        var cambio = new Cambio(1L, from, to, BigDecimal.ONE, BigDecimal.ONE, "Cambio-Service PORT " + port);
        return cambio;
    }

}
