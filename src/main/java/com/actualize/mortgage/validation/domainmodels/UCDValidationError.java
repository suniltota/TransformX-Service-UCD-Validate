package com.actualize.mortgage.validation.domainmodels;

import javax.xml.bind.annotation.XmlElement;

public class UCDValidationError {

    private String dataPointName;
    private String parentContainer;
    private String xpath;
    private String errorMsg;
    private String uiErrorMsg;
    private String lineNumber;
    private String uiLabel;
    private String uiHeader;
    
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
    
    @XmlElement(name = "UI_ERROR_MESSAGE")
    public String getUIErrorMsg() {
        return uiErrorMsg;
    }

    public void setUIErrorMsg(String uiErrorMsg) {
        this.uiErrorMsg = uiErrorMsg;
    }
    
    @XmlElement(name = "LINE_NUMBER")
	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	@XmlElement(name = "UI_LABEL")
	public String getUiLabel() {
		return uiLabel;
	}

	public void setUiLabel(String uiLabel) {
		this.uiLabel = uiLabel;
	}
	@XmlElement(name = "UI_HEADER")
	public String getUiHeader() {
		return uiHeader;
	}

	public void setUiHeader(String uiHeader) {
		this.uiHeader = uiHeader;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataPointName == null) ? 0 : dataPointName.hashCode());
		result = prime * result + ((lineNumber == null) ? 0 : lineNumber.hashCode());
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
		if (dataPointName == null) {
			if (other.dataPointName != null)
				return false;
		} else if (!dataPointName.equals(other.dataPointName))
			return false;
		if (lineNumber == null) {
			if (other.lineNumber != null)
				return false;
		} else if (!lineNumber.equals(other.lineNumber))
			return false;
		if (xpath == null) {
			if (other.xpath != null)
				return false;
		} else if (!xpath.equals(other.xpath))
			return false;
		return true;
	}

}
