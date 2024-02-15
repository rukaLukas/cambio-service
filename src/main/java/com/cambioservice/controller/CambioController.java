package com.cambioservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cambioservice.Repository.CambioRepository;
import com.cambioservice.model.Cambio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Cambio Endpoint")
@RestController
@RequestMapping("cambio-service")
public class CambioController {

    @Autowired
    Environment environment;

    @Autowired
    private CambioRepository repository;

    private Logger logger = LoggerFactory.getLogger(CambioController.class);

    @Operation(summary = "Get a specific cambio by your amount, from and to")
    @GetMapping("/{amount}/{from}/{to}")
    public Cambio getCambio(@RequestParam(value = "amount", defaultValue = "1") Double amount,
            @RequestParam(value = "from", defaultValue = "USD") String from,
            @RequestParam(value = "to", defaultValue = "BRL") String to) {

        logger.info("method=getCambio, amount={}, from={}, to={}", amount, from, to);
        var port = environment.getProperty("local.server.port");
        var cambio = repository.findByFromAndTo(from, to);
        if (cambio == null)
            throw new RuntimeException("Currency Unsupported");
        
        var conversionFactor = cambio.getConversionFactor();
        var convertedValue = conversionFactor.multiply(BigDecimal.valueOf(amount));
        cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        cambio.setEnvironment(port);

        return cambio;
    }

}
