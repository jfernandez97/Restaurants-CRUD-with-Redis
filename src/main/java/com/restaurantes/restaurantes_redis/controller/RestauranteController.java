package com.restaurantes.restaurantes_redis.controller;

import com.restaurantes.restaurantes_redis.model.Restaurante;
import com.restaurantes.restaurantes_redis.service.RestauranteService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes")
public class RestauranteController {
    private final RestauranteService restauranteService;


    @GetMapping
    public List<Restaurante> getAll(HttpSession session){
        return restauranteService.getAll();
    }
    @PostMapping
    public Restaurante create(@RequestBody Restaurante restaurante){
        return restauranteService.create(restaurante);
    }
    @GetMapping("/{id}")
    public Restaurante getById(@PathVariable Long id){
        return restauranteService.getById(id);
    }
    @PutMapping("/{id}")
    public Restaurante update (@PathVariable Long id, Restaurante restaurante){
        return restauranteService.update(id,restaurante);
    }
    @DeleteMapping("{id}")
    public Restaurante delete(@PathVariable Long id){
        return restauranteService.delete(id);
    }

}
