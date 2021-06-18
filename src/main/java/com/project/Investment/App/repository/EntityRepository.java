package com.project.Investment.App.repository;

import com.project.Investment.App.model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityRepository extends JpaRepository<Entity,String> {
    List<Entity> findByEntityType(String type);
}
