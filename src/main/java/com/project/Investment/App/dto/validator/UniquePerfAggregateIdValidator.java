package com.project.Investment.App.dto.validator;

import com.project.Investment.App.service.PerfAggregateService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePerfAggregateIdValidator implements ConstraintValidator<UniquePerfAggregateId,Integer> {

    private final PerfAggregateService service;

    public UniquePerfAggregateIdValidator(PerfAggregateService service) {
        this.service = service;
    }


    @Override
    public boolean isValid(Integer perfAggregateId, ConstraintValidatorContext context) {
        return service.isPerfAggregateIdExists(perfAggregateId);
    }
}
