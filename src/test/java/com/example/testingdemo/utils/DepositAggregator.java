package com.example.testingdemo.utils;

import com.example.testingdemo.common.Types;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class DepositAggregator implements ArgumentsAggregator {
    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor,
                                     ParameterContext parameterContext) throws ArgumentsAggregationException {
        return new Types.DepositRequest(accessor.getString(0),
                                        accessor.getString(1),
                                        accessor.getString(2),
                                        accessor.getString(3),
                                        accessor.getInteger(4),
                                        accessor.getInteger(5));
    }
}
