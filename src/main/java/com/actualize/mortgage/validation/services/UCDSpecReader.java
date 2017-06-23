package com.actualize.mortgage.validation.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.actualize.mortgage.validation.domainmodels.GroupByContainer;
import com.actualize.mortgage.validation.domainmodels.UCDDeliverySpec;

public interface UCDSpecReader {

    List<UCDDeliverySpec> readValues(String loanPurposeType) throws IOException;

    Map<String, List<GroupByContainer>> groupByContainers(List<UCDDeliverySpec> ucdDeliverySpecs, Map<String, UCDDeliverySpec> uniqueIdBasedMap, String loanPurposeType);
}