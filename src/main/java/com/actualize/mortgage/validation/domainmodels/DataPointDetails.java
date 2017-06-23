package com.actualize.mortgage.validation.domainmodels;

import java.util.Set;

public class DataPointDetails {

    private String datapointName;
    private boolean containerAttribute;
    private boolean datapointAttribute;
    private boolean datapoint;
    private String datapointCondition;
    private String datapointErrorMessage;
    private String validationRequired;
    private Set<String> enumValues;
    private String conditionalityType;

    /**
     * @return the datapointName
     */
    public String getDatapointName() {
        return datapointName;
    }

    /**
     * @param datapointName
     *            the datapointName to set
     */
    public void setDatapointName(String datapointName) {
        this.datapointName = datapointName;
    }

    /**
     * @return the containerAttribute
     */
    public boolean isContainerAttribute() {
        return containerAttribute;
    }

    /**
     * @param containerAttribute
     *            the containerAttribute to set
     */
    public void setContainerAttribute(boolean containerAttribute) {
        this.containerAttribute = containerAttribute;
    }

    /**
     * @return the datapointAttribute
     */
    public boolean isDatapointAttribute() {
        return datapointAttribute;
    }

    /**
     * @param datapointAttribute
     *            the datapointAttribute to set
     */
    public void setDatapointAttribute(boolean datapointAttribute) {
        this.datapointAttribute = datapointAttribute;
    }

    /**
     * @return the datapoint
     */
    public boolean isDatapoint() {
        return datapoint;
    }

    /**
     * @param datapoint
     *            the datapoint to set
     */
    public void setDatapoint(boolean datapoint) {
        this.datapoint = datapoint;
    }

    /**
     * @return the datapointCondition
     */
    public String getDatapointCondition() {
        return datapointCondition;
    }

    /**
     * @param datapointCondition
     *            the datapointCondition to set
     */
    public void setDatapointCondition(String datapointCondition) {
        this.datapointCondition = datapointCondition;
    }

    /**
     * @return the datapointErrorMessage
     */
    public String getDatapointErrorMessage() {
        return datapointErrorMessage;
    }

    /**
     * @param datapointErrorMessage
     *            the datapointErrorMessage to set
     */
    public void setDatapointErrorMessage(String datapointErrorMessage) {
        this.datapointErrorMessage = datapointErrorMessage;
    }

    /**
     * @return the validationRequired
     */
    public String getValidationRequired() {
        return validationRequired;
    }

    /**
     * @param validationRequired
     *            the validationRequired to set
     */
    public void setValidationRequired(String validationRequired) {
        this.validationRequired = validationRequired;
    }

    /**
     * @return the enumValues
     */
    public Set<String> getEnumValues() {
        return enumValues;
    }

    /**
     * @param enumValues
     *            the enumValues to set
     */
    public void setEnumValues(Set<String> enumValues) {
        this.enumValues = enumValues;
    }

    /**
     * @return the conditionalityType
     */
    public String getConditionalityType() {
        return conditionalityType;
    }

    /**
     * @param conditionalityType
     *            the conditionalityType to set
     */
    public void setConditionalityType(String conditionalityType) {
        this.conditionalityType = conditionalityType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((conditionalityType == null) ? 0 : conditionalityType.hashCode());
        result = prime * result + (containerAttribute ? 1231 : 1237);
        result = prime * result + (datapoint ? 1231 : 1237);
        result = prime * result + (datapointAttribute ? 1231 : 1237);
        result = prime * result + ((datapointCondition == null) ? 0 : datapointCondition.hashCode());
        result = prime * result + ((datapointErrorMessage == null) ? 0 : datapointErrorMessage.hashCode());
        result = prime * result + ((datapointName == null) ? 0 : datapointName.hashCode());
        result = prime * result + ((enumValues == null) ? 0 : enumValues.hashCode());
        result = prime * result + ((validationRequired == null) ? 0 : validationRequired.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DataPointDetails other = (DataPointDetails) obj;
        if (conditionalityType == null) {
            if (other.conditionalityType != null)
                return false;
        } else if (!conditionalityType.equals(other.conditionalityType))
            return false;
        if (containerAttribute != other.containerAttribute)
            return false;
        if (datapoint != other.datapoint)
            return false;
        if (datapointAttribute != other.datapointAttribute)
            return false;
        if (datapointCondition == null) {
            if (other.datapointCondition != null)
                return false;
        } else if (!datapointCondition.equals(other.datapointCondition))
            return false;
        if (datapointErrorMessage == null) {
            if (other.datapointErrorMessage != null)
                return false;
        } else if (!datapointErrorMessage.equals(other.datapointErrorMessage))
            return false;
        if (datapointName == null) {
            if (other.datapointName != null)
                return false;
        } else if (!datapointName.equals(other.datapointName))
            return false;
        if (enumValues == null) {
            if (other.enumValues != null)
                return false;
        } else if (!enumValues.equals(other.enumValues))
            return false;
        if (validationRequired == null) {
            if (other.validationRequired != null)
                return false;
        } else if (!validationRequired.equals(other.validationRequired))
            return false;
        return true;
    }

}
