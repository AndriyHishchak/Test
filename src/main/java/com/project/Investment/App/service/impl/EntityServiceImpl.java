package com.project.Investment.App.service.impl;

import com.project.Investment.App.dao.EntityDao;
import com.project.Investment.App.exception.ResourceNotFoundException;
import com.project.Investment.App.model.Entity;
import com.project.Investment.App.repository.EntityRepository;
import com.project.Investment.App.service.EntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EntityServiceImpl implements EntityService {

    private final EntityRepository repository;

    public EntityServiceImpl(EntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public EntityDao findById(String id) throws ResourceNotFoundException {
        Entity entity = repository.findByEntityId_EntityId(id).stream().findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found : " + id));
        log.info("In findById - entity: {} find by id: {}",entity,id);

        return EntityDao.fromEntity(entity);
    }
    @Override
    public EntityDao create (Entity entity) {

        Entity entitySave = repository.save(entity);
        EntityDao result = EntityDao.fromEntity(entitySave);
        log.info("IN created - airCompany: {} successfully created : ", result);
        return result;
    }

    @Override
    public List<EntityDao> getAll(Optional<String> name) {
        List<EntityDao> entityDaos = new ArrayList<>();

        if (name.isPresent()) {
            List<Entity> entities = repository.findByEntityName(name.get());
            entities.forEach(entity -> entityDaos.add(EntityDao.fromEntity(entity)));
           // log.info("IN findByName - airCompany: {} find by name: {}", airCompany, name);
            return new ArrayList<>(entityDaos);
        }
        List<Entity> entities = repository.findAll();
        entities.forEach(entity -> entityDaos.add(EntityDao.fromEntity(entity)));
       // log.info("IN getAll - {} company found", airCompanyDtos.size());
        return new ArrayList<>(entityDaos);
    }

    @Override
    public EntityDao update(String id, Entity entityPath) {

        Entity entityRefresh = repository.findByEntityId_EntityId(id).stream().findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found : " + id));

        entityRefresh.setEntityName(entityPath.getEntityName());
        entityRefresh.setEntityType(entityPath.getEntityType());
        entityRefresh.setDefaultBenchmarkId(entityPath.getDefaultBenchmarkId());

        repository.save(entityRefresh);
        log.info("IN update - AirCompany with id : {} ", id);
        return EntityDao.fromEntity(entityRefresh);
    }
    @Override
    public EntityDao updateParametersEntity(String id,
                                                Optional<String> entityType,
                                                Optional <String> entityName,
                                                Optional<String> defaultBenchmarkId ) {
        Entity entity = repository.findByEntityId_EntityId(id).stream().findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found : " + id));
        if (entityName.isPresent()) {
            entity.setEntityName(entityName.get());
            log.info("IN update Name Company - AirCompany with id : {} ", id); }
        if (entityType.isPresent()) {
            entity.setEntityType(entityType.get());
            log.info("IN update Type Company - AirCompany with id : {} ", id); }
        if (defaultBenchmarkId.isPresent()) {
            entity.setDefaultBenchmarkId(defaultBenchmarkId.get());
            log.info("IN update ");
        }
        log.info("IN finish update  - airCompany with id : {} ", id);

        return EntityDao.fromEntity(repository.save(entity));
    }
    @Override
    public void deleteEntity(String id) {
       Entity entity = repository.findByEntityId_EntityId(id).stream().findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found : " + id));
        repository.delete(entity);
        log.info("IN delete - AirCompany with id : {} ", id);
    }

    @Override
    public void deleteAllEntity() {
        repository.deleteAll();
        log.info("IN deleted All AirCompany");
    }
}
