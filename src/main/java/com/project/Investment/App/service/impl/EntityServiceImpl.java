package com.project.Investment.App.service.impl;

import com.project.Investment.App.model.Entity;
import com.project.Investment.App.repository.EntityRepository;
import com.project.Investment.App.service.EntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EntityServiceImpl implements EntityService {

    private final EntityRepository entityRepository;

    public EntityServiceImpl(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }


    @Override
    public Entity findById(String id) {
        return entityRepository.findById(id).stream().findFirst().orElse(null);
    }
}
