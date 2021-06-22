package com.project.Investment.App.dao;

import com.project.Investment.App.model.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityDao {
    String entityId;
    String entityName;
    String entityType;
    LocalDate effectiveDate;
    String defaultBenchmarkId;


    public static EntityDao fromEntity(Entity entity) {

        return EntityDao.builder()
                .entityId(entity.getEntityId().getEntityId())
                .entityName(entity.getEntityName())
                .entityType(entity.getEntityType())
                .defaultBenchmarkId(entity.getDefaultBenchmarkId())
                .effectiveDate(entity.getEntityId().getEffectiveDate())
                .build();
    }
}
