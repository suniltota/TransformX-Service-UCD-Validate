package com.actualize.mortgage.validation.domainmodels;

import javax.xml.bind.annotation.XmlElement;

public class UCDValidationError {

    private String dataPointName;
    private String parentContainer;
    private String xpath;
    private String errorMsg;

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

}
