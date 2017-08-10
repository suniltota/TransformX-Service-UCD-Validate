package com.actualize.mortgage.validation.domainmodels;

import java.util.Set;

public class DataPointDetails {

    private String datapointName;
    private boolean containerAttribute;
    private boolean datapointAttribute;
    private boolean datapoint;
    private String datapointCondition;
    private String datapointXmlErrorMessage;
    private String datapointUIErrorMessage;
    private String validationRequired;
    private Set<String> enumValues;
    private String conditionalityType;
    private boolean isValid = false;
    private String lineNumber;
    private String uiLabel;
    private String uiHeader;
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
     * @return the datapointXmlErrorMessage
     */
    public String getDatapointXmlErrorMessage() {
        return datapointXmlErrorMessage;
    }

    /**
     * @param datapointXmlErrorMessage
     *            the datapointXmlErrorMessage to set
     */
    public void setDatapointXmlErrorMessage(String datapointXmlErrorMessage) {
        this.datapointXmlErrorMessage = datapointXmlErrorMessage;
    }
    
    /**
     * @return the datapointUIErrorMessage
     */
    public String getDatapointUIErrorMessage() {
        return datapointUIErrorMessage;
    }

    /**
     * @param datapointUIErrorMessage
     *            the datapointUIErrorMessage to set
     */
    public void setDatapointUIErrorMessage(String datapointUIErrorMessage) {
        this.datapointUIErrorMessage = datapointUIErrorMessage;
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
        result = prime * result + ((datapointXmlErrorMessage == null) ? 0 : datapointXmlErrorMessage.hashCode());
        result = prime * result + ((datapointUIErrorMessage == null) ? 0 : datapointUIErrorMessage.hashCode());
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
        if (datapointXmlErrorMessage == null) {
            if (other.datapointXmlErrorMessage != null)
                return false;
        } else if (!datapointXmlErrorMessage.equals(other.datapointXmlErrorMessage))
            return false;
        if (datapointUIErrorMessage == null) {
            if (other.datapointUIErrorMessage != null)
                return false;
        } else if (!datapointUIErrorMessage.equals(other.datapointUIErrorMessage))
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

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getUiLabel() {
		return uiLabel;
	}

	public void setUiLabel(String uiLabel) {
		this.uiLabel = uiLabel;
	}

	public String getUiHeader() {
		return uiHeader;
	}

	public void setUiHeader(String uiHeader) {
		this.uiHeader = uiHeader;
	}

}
