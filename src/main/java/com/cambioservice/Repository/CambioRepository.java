package com.cambioservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cambioservice.model.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, Long>{
    
    Cambio findByFromAndTo(String from, String to);
}
