package com.project.Investment.App.service.impl;

import com.project.Investment.App.DTO.EntityDto;
import com.project.Investment.App.DTO.MapperJdbc.PerfAggregateMapper;
import com.project.Investment.App.DTO.PerfAggregateDto;
import com.project.Investment.App.model.PerfAggregate;
import com.project.Investment.App.service.PerfAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfAggregateServiceImpl implements PerfAggregateService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PerfAggregateServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PerfAggregateDto findById(Integer id) {
        PerfAggregate perfAggregate = jdbcTemplate.query("Select * from perf_aggregate where perf_aggregate_id=?", new BeanPropertyRowMapper<>(PerfAggregate.class), new Object[]{id})
                .stream().findAny().orElse(null);
        return PerfAggregateDto.fromPerfAggregate(perfAggregate);
    }

    @Override
    public List<PerfAggregate> findAll() {
        return jdbcTemplate.query("Select * from perf_aggregate",new PerfAggregateMapper());

    }

    @Override
    public Integer getCountPerfAggregate() {
        return jdbcTemplate.queryForObject("Select Count(*) from perf_aggregate", Integer.class);
    }
}
