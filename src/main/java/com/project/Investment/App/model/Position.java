package com.project.Investment.App.model;

import com.project.Investment.App.model.embeddedId.PositionId;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@javax.persistence.Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "position")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Data
public class Position {

    @EmbeddedId
    PositionId positionId;
    @Column(name = "AGGREGATE_ID")
    Integer aggregateId;
    @Column(name = "FREQUENCY")
    char frequency;
    @Column(name = "SECURITY_ID")
    Integer securityId;
    @Column(name = "WEIGHT")
    Double weight;
    @Column(name = "GROSS_RETURN")
    Double grossReturn;
    @Column(name = "BMV_GROSS")
    Double bmvGross;
    @Column(name = "EMV_GROSS")
    Double emvGross;
    @Column(name = "GAIN_LOSS_GROSS")
    Double gainLossGross;


}
