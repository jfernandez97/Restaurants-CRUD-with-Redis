package com.restaurantes.restaurantes_redis.repository;

import com.restaurantes.restaurantes_redis.model.Restaurante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends CrudRepository<Restaurante,Long> {

}
