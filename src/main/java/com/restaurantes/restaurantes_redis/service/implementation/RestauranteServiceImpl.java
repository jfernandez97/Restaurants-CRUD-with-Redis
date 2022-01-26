package com.restaurantes.restaurantes_redis.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurantes.restaurantes_redis.cache.CacheClient;
import com.restaurantes.restaurantes_redis.model.Restaurante;
import com.restaurantes.restaurantes_redis.repository.RestauranteRepository;
import com.restaurantes.restaurantes_redis.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RestauranteServiceImpl  implements RestauranteService{

    private final RestauranteRepository repository;
    private final CacheClient<Restaurante> cache;

    @Override
    public List<Restaurante>getAll(){
        return (List<Restaurante>) repository.findAll();
    }
    @Override
    public Restaurante create(Restaurante restaurante) {
        Restaurante data=null;
        try {
            data = repository.save(restaurante);
            saveInCache(data);
        }catch (JsonProcessingException e){
            log.error("Error",e);
        }
        return data;
    }
    @Override
    public Restaurante getById(Long id){
        Restaurante found = null;
        try {
            var dataFromCache=cache.recover(id.toString(), Restaurante.class);
            if(!Objects.isNull(dataFromCache)){
                found=dataFromCache;
            }
            found = repository.findById(id).orElseThrow(Exception::new);
            if(!Objects.isNull(found)){
                return saveInCache(found);
            }
        }catch (JsonProcessingException e){
            log.error("Error converting message to string",e);
        }catch (Exception e){
            e.printStackTrace();
        }
        return found;
    }
    @Override
    public Restaurante update(Long id, Restaurante restaurante) {
        Restaurante toUpdate =null;
        try {
            var dataFromCache=cache.recover(id.toString(),Restaurante.class);
            if (!Objects.isNull(dataFromCache)){
                toUpdate=dataFromCache;
                toUpdate.setMenu(restaurante.getMenu());
                repository.save(toUpdate);
            }
            var dataFromDatabase =repository.findById(id).orElseThrow(Exception::new);
            dataFromDatabase.setMenu(restaurante.getMenu());
            repository.save(dataFromDatabase);
            return saveInCache(dataFromDatabase);
        }catch (JsonProcessingException e){
            log.error("Error converting message to string",e);
        }catch (Exception e){
            e.printStackTrace();
        }
        return toUpdate;
    }

    @Override
    public Restaurante delete(Long id) {
        Optional<Restaurante> dataFromDataBase = repository.findById(id);
        Restaurante toDelete =null;
        try {
            var dataFromCache = cache.recover(id.toString(),Restaurante.class);
            if(!Objects.isNull(dataFromCache)){
                toDelete=dataFromCache;
                repository.delete(toDelete);
            }
            if(dataFromDataBase.isPresent()){
                toDelete=dataFromDataBase.get();
                repository.delete(toDelete);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return toDelete;
    }

    @Override
    public Restaurante saveInCache(Restaurante restaurante) throws JsonProcessingException {
        return cache.save(restaurante.getId().toString(),restaurante);
    }
}
