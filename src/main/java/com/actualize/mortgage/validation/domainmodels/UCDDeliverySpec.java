package com.actualize.mortgage.validation.domainmodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UCDDeliverySpec implements Serializable {

    private static final long serialVersionUID = 8471031475811437059L;

    private String uniqueId;
    private String mismoxpath;
    private String mismoparentcontainer;
    private String mismodatapointname;
    private String ucdsupportedenumerations;
    private String ucdFormat;
    private String conditionalityType;
    private String conditionalityDetails;
    private String cardinality;
    private String deliveryNotes;
    private String conditionality;
    private String errorMessage;
    private String validationRequired;
    private String uiLabel;
    private String uiHeader;
    
    private List<String> error = new ArrayList<>();

    /**
     * @return the uniqueId
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * @param uniqueId
     *            the uniqueId to set
     */
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }


    /**
     * @return the mismoxpath
     */
    public String getMismoxpath() {
        return mismoxpath;
    }

    /**
     * @param mismoxpath
     *            the mismoxpath to set
     */
    public void setMismoxpath(String mismoxpath) {
        this.mismoxpath = mismoxpath;
    }

    /**
     * @return the mismoparentcontainer
     */
    public String getMismoparentcontainer() {
        return mismoparentcontainer;
    }

    /**
     * @param mismoparentcontainer
     *            the mismoparentcontainer to set
     */
    public void setMismoparentcontainer(String mismoparentcontainer) {
        this.mismoparentcontainer = mismoparentcontainer;
    }

    /**
     * @return the mismodatapointname
     */
    public String getMismodatapointname() {
        return mismodatapointname;
    }

    /**
     * @param mismodatapointname
     *            the mismodatapointname to set
     */
    public void setMismodatapointname(String mismodatapointname) {
        this.mismodatapointname = mismodatapointname;
    }

    /**
     * @return the ucdsupportedenumerations
     */
    public String getUcdsupportedenumerations() {
        return ucdsupportedenumerations;
    }

    /**
     * @param ucdsupportedenumerations
     *            the ucdsupportedenumerations to set
     */
    public void setUcdsupportedenumerations(String ucdsupportedenumerations) {
        this.ucdsupportedenumerations = ucdsupportedenumerations;
    }
    
    public String getUcdFormat() {
        return ucdFormat;
    }

    public void setUcdFormat(String ucdFormat) {
        this.ucdFormat = ucdFormat;
    }

    /**
     * @return the conditionalityType
     */
    public String getConditionalityType() {
        return conditionalityType;
    }

    /**
     * @param conditionalityType the conditionalityType to set
     */
    public void setConditionalityType(String conditionalityType) {
        this.conditionalityType = conditionalityType;
    }

    /**
     * @return the conditionalityDetails
     */
    public String getConditionalityDetails() {
        return conditionalityDetails;
    }

    /**
     * @param conditionalityDetails the conditionalityDetails to set
     */
    public void setConditionalityDetails(String conditionalityDetails) {
        this.conditionalityDetails = conditionalityDetails;
    }

    /**
     * @return the cardinality
     */
    public String getCardinality() {
        return cardinality;
    }

    /**
     * @param cardinality the cardinality to set
     */
    public void setCardinality(String cardinality) {
        this.cardinality = cardinality;
    }

    /**
     * @return the deliveryNotes
     */
    public String getDeliveryNotes() {
        return deliveryNotes;
    }

    /**
     * @param deliveryNotes the deliveryNotes to set
     */
    public void setDeliveryNotes(String deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
    }
    
    /**
     * @return the conditionality
     */
    public String getConditionality() {
        return conditionality;
    }

    /**
     * @param conditionality
     *            the conditionality to set
     */
    public void setConditionality(String conditionality) {
        this.conditionality = conditionality;
    }
    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the validationRequired
     */
    public String getValidationRequired() {
        return validationRequired;
    }

    /**
     * @param validationRequired the validationRequired to set
     */
    public void setValidationRequired(String validationRequired) {
        this.validationRequired = validationRequired;
    }

    /**
     * @return the error
     */
    public List<String> getError() {
        return error;
    }

    /**
     * @param error
     *            the error to set
     */
    public void setError(List<String> error) {
        this.error = error;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getError().toString();
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