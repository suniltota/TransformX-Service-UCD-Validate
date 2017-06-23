package com.actualize.mortgage.validation.domainmodels;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VALIDATION_ERRORS")
public class UCDValidationErrors {

    private List<UCDValidationError> validationErrors;

    @XmlElement(name = "VALIDATION_ERROR")
    public List<UCDValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<UCDValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
