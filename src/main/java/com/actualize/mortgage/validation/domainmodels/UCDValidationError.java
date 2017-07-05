package com.actualize.mortgage.validation.domainmodels;

import javax.xml.bind.annotation.XmlElement;

public class UCDValidationError {

    private String dataPointName;
    private String parentContainer;
    private String xpath;
    private String errorMsg;
    private String lineNumber;

    @XmlElement(name = "DATAPOINT_NAME")
    public String getDataPointName() {
        return dataPointName;
    }

    public void setDataPointName(String dataPointName) {
        this.dataPointName = dataPointName;
    }

    @XmlElement(name = "PARENT_CONTAINER")
    public String getParentContainer() {
        return parentContainer;
    }

    public void setParentContainer(String parentContainer) {
        this.parentContainer = parentContainer;
    }

    @XmlElement(name = "XPATH")
    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }
    
    @XmlElement(name = "ERROR_MESSAGE")
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    @XmlElement(name = "LINE_NUMBER")
	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((xpath == null) ? 0 : xpath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UCDValidationError other = (UCDValidationError) obj;
		if (xpath == null) {
			if (other.xpath != null)
				return false;
		} else if (!xpath.equals(other.xpath))
			return false;
		return true;
	}

}
