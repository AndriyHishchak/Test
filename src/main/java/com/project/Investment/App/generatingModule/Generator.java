package com.project.Investment.App.generatingModule;

import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.project.Investment.App.model.Position;
import com.project.Investment.App.model.embeddedId.PositionId;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.awt.geom.Dimension2D;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@Component
public class Generator {


    public List<Position> generatorPosition (LocalDate startDate,String entityId) {
        List<Position> positions = new ArrayList<>();

        Map<Integer,Integer> securityIdAndAggregateID = new HashMap<>();
        securityIdAndAggregateID.put(1,3);
        securityIdAndAggregateID.put(2,3);
        securityIdAndAggregateID.put(3,3);
        securityIdAndAggregateID.put(4,3);
        securityIdAndAggregateID.put(5,3);
        securityIdAndAggregateID.put(6,4);
        securityIdAndAggregateID.put(7,4);
        securityIdAndAggregateID.put(8,4);
        securityIdAndAggregateID.put(9,4);

        Double[] StartBmv = distributionBmvTest(5,4);


        int total = 1000000;
        for (int i = 0,j = 1; i < securityIdAndAggregateID.size(); i++,j++) {

            Double bmvGross = StartBmv[i];
            Double emvGross = returnEmvGross(bmvGross);


            Position position = Position.builder()
                    .positionId(new PositionId(entityId,startDate))
                    .aggregateId(securityIdAndAggregateID.get(j))
                    .securityId(j)
                    .frequency('D')
                    .weight(returnWeight(bmvGross,total))
                    .bmvGross(bmvGross)
                    .emvGross(emvGross)
                    .grossReturn((emvGross - bmvGross)/bmvGross * 100)
                    .gainLossGross(emvGross - bmvGross)
                    .build();
            positions.add(position);
        }



        LocalDate finishDate = startDate.plusMonths(3);
        for (LocalDate date = startDate.plusDays(1); date.isBefore(finishDate); date = date.plusDays(1)){

            if (date.getDayOfWeek().getValue() < 6) {

                for (int i = 0, j = 1, k = positions.size()-securityIdAndAggregateID.size(); i < securityIdAndAggregateID.size(); i++,j++,k++) {

                    Double bmvGross = positions.get(k).getEmvGross();
                    Double emvGross = returnEmvGross(bmvGross);

                    Position position = Position.builder()
                            .positionId(new PositionId(entityId,date))
                            .aggregateId(securityIdAndAggregateID.get(j))
                            .securityId(j)
                            .frequency('D')
                            .weight(returnWeight(bmvGross,total))
                            .bmvGross(bmvGross)
                            .emvGross(emvGross)
                            .grossReturn(grossReturn(bmvGross,emvGross))
                            .gainLossGross(emvGross - bmvGross)
                            .build();
                    positions.add(position);
                }
            }
        }
        System.out.println("Positions size - " + positions.size());
        for (Position position : positions){
            System.out.println(position);
        }
        return positions;
    }

    private Double returnWeight (Double bmvGross,int total) {
        return bmvGross / total * 100;
    }

    private Double grossReturn (Double bmvGross, Double emvGross) {
        return (emvGross - bmvGross)/bmvGross * 100;
    }

    private Double returnEmvGross (Double bmvGross) {
        Random random = new Random();
       boolean boolRandom = random.nextBoolean();

       if (boolRandom){
          return bmvGross + (bmvGross * random.nextInt(10)/100);
       }else {
           return bmvGross - (bmvGross * random.nextInt(10)/100);
       }
    }

    private Double[] distributionBmv(int valueSecurity) {

        Double[] distributionBmv = new Double[valueSecurity];
        int total = 1000000;
        Random rand = new Random();
        for (int i = 0; i<distributionBmv.length-1;i++) {
            distributionBmv[i] = (double) rand.nextInt(total);
            total -= distributionBmv[i];
        }
        distributionBmv[distributionBmv.length-1] = (double) total;

        return distributionBmv;
    }



    private Double[] distributionBmvTest(int valueSecurity1,int valueSecurity2) {

        Double[] distributionBmv1 = new Double[valueSecurity1];
        Double[] distributionBmv2 = new Double[valueSecurity2];
        int total1 = 500000;
        int total2 = 500000;
        Random rand = new Random();
        for (int i = 0; i<distributionBmv1.length-1;i++) {
            distributionBmv1[i] = (double) rand.nextInt(total1);
            total1 -= distributionBmv1[i];
        }
        distributionBmv1[distributionBmv1.length-1] = (double) total1;
        for (int i = 0; i<distributionBmv2.length-1;i++) {
            distributionBmv2[i] = (double) rand.nextInt(total2);
            total2 -= distributionBmv2[i];
        }
        distributionBmv2[distributionBmv2.length-1] = (double) total2;
        return Stream.concat(Arrays.stream(distributionBmv1),Arrays.stream(distributionBmv2)).toArray(Double[]::new);
    }

}
