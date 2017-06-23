package com.actualize.mortgage.validation.domainmodels;

import java.util.ArrayList;
import java.util.List;

public class GroupConditions {

    private List<String> identifiers = new ArrayList<>();
    private List<String> logicalOperators;
    private List<GroupConditions> groupConditions;
    private List<Boolean> results;

    public GroupConditions(String target, String unaryOperator, String value) {
        identifiers.add(target.trim());
        identifiers.add(unaryOperator.trim());
        identifiers.add(value.trim());
    }

    /**
     * @return the identifiers
     */
    public List<String> getIdentifiers() {
        return identifiers;
    }

    /**
     * @param identifiers
     *            the identifiers to set
     */
    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    /**
     * @return the logicalOperators
     */
    public List<String> getLogicalOperators() {
        return logicalOperators;
    }

    /**
     * @param logicalOperators
     *            the logicalOperators to set
     */
    public void setLogicalOperators(List<String> logicalOperators) {
        this.logicalOperators = logicalOperators;
    }

    /**
     * @return the groupConditions
     */
    public List<GroupConditions> getGroupConditions() {
        return groupConditions;
    }

    /**
     * @param groupConditions
     *            the groupConditions to set
     */
    public void setGroupConditions(List<GroupConditions> groupConditions) {
        this.groupConditions = groupConditions;
    }

    /**
     * @return the results
     */
    public List<Boolean> getResults() {
        return results;
    }

    /**
     * @param results
     *            the results to set
     */
    public void setResults(List<Boolean> results) {
        this.results = results;
    }
}
