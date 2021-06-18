package com.project.Investment.App.rest;

import com.project.Investment.App.model.Entity;
import com.project.Investment.App.service.impl.EntityServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/entity/")
public class RestControllerEntity {

    private final EntityServiceImpl entityService;

    public RestControllerEntity(EntityServiceImpl entityService) {
        this.entityService = entityService;
    }

    @GetMapping("{id}")
    public Entity getById (@PathVariable("id") String id) {
        return entityService.findById(id);
    }
}
