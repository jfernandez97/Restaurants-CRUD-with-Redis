package com.restaurantes.restaurantes_redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurantes.restaurantes_redis.model.Restaurante;

import java.util.List;

public interface RestauranteService {
    List<Restaurante> getAll();
    Restaurante getById(Long id);
    Restaurante create(Restaurante restaurante);
    Restaurante update(Long id,Restaurante restaurante);
    Restaurante delete(Long id);
    Restaurante saveInCache(Restaurante restaurante) throws JsonProcessingException;
}
