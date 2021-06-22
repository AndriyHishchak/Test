package com.project.Investment.App.service.impl;

import com.project.Investment.App.dao.mapperJdbc.PerfAggregateMapper;
import com.project.Investment.App.dao.PerfAggregateDao;
import com.project.Investment.App.exception.ResourceNotFoundException;
import com.project.Investment.App.model.PerfAggregate;
import com.project.Investment.App.service.PerfAggregateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PerfAggregateServiceImpl implements PerfAggregateService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PerfAggregateServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PerfAggregateDao findById(Integer id) {
        PerfAggregate perfAggregate = jdbcTemplate.query("Select * from perf_aggregate where perf_aggregate_id=?",
                new BeanPropertyRowMapper<>(PerfAggregate.class),
                new Object[]{id})
                .stream().findAny().orElseThrow(() -> new ResourceNotFoundException("PerfAggregate not found : " + id));
        log.info("In findById - PerfAggregate: {} find by id: {}",perfAggregate,id);
        return PerfAggregateDao.fromPerfAggregate(perfAggregate);
    }

    @Override
    public List<PerfAggregateDao> findAll() {
        List<PerfAggregateDao> perfAggregateDaos = new ArrayList<>();
        List<PerfAggregate> perfAggregates = jdbcTemplate.query("Select * from perf_aggregate",new PerfAggregateMapper());
        perfAggregates.forEach(perfAggregate -> perfAggregateDaos.add(PerfAggregateDao.fromPerfAggregate(perfAggregate)));
        log.info("In getAll - {} PerfAggregate found" , perfAggregateDaos.size());
        return new ArrayList<>(perfAggregateDaos);
    }

    @Override
    public Integer getCountPerfAggregate() {
        Integer count = jdbcTemplate.queryForObject("Select Count(*) from perf_aggregate", Integer.class);
        log.info("In getCountPerfAggregate - {} PerfAggregate found" , count);
        return count;
    }
}
