package com.adqt.springservice.service;

import com.adqt.springservice.entity.ColumnInformation;
import com.adqt.springservice.entity.RuleValue;
import com.adqt.springservice.repo.ColumnRepository;
import com.adqt.springservice.repo.RuleValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class ProfilingExecutor {

    @Autowired
    RuleValueRepository ruleValueRepository;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    PipeLineCreator pipeLineCreator;
    
    @Autowired
    DataQualityStatService dataQualityStatService;

    public void executeProfiling(String tableName) throws FileNotFoundException {
        List<RuleValue> rules = ruleValueRepository.findByTableName(tableName);
        List<ColumnInformation> columns = columnRepository.findByTableName(tableName);
        Schema schema = new Schema(columns);
        dataQualityStatService.lauchDataQuality(tableName);
        pipeLineCreator.preProcess(tableName,schema,rules);
    }
}
