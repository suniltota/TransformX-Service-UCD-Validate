package com.actualize.mortgage.validation.domainmodels;

import java.util.Map;

public class GroupByContainer {

    private String parentContainer;
    private String xpath;
    private int minOccurs;
    private int maxOccurs;
    private Map<String, DataPointDetails> datapoints;
    private Map<String, DataPointDetails> containerAttributes;
    private Map<String, DataPointDetails> datapointAttributes;
    private String containerErrorMsg;

    public String getParentContainer() {
        return parentContainer;
    }

    public void setParentContainer(String parentContainer) {
        this.parentContainer = parentContainer;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public int getMinOccurs() {
        return minOccurs;
    }

    public void setMinOccurs(int minOccurs) {
        this.minOccurs = minOccurs;
    }

    public int getMaxOccurs() {
        return maxOccurs;
    }

    public void setMaxOccurs(int maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    public Map<String, DataPointDetails> getDatapoints() {
        return datapoints;
    }

    public void setDatapoints(Map<String, DataPointDetails> datapoints) {
        this.datapoints = datapoints;
    }

    public Map<String, DataPointDetails> getContainerAttributes() {
        return containerAttributes;
    }

    public void setContainerAttributes(Map<String, DataPointDetails> containerAttributes) {
        this.containerAttributes = containerAttributes;
    }

    public Map<String, DataPointDetails> getDatapointAttributes() {
        return datapointAttributes;
    }

    public void setDatapointAttributes(Map<String, DataPointDetails> datapointAttributes) {
        this.datapointAttributes = datapointAttributes;
    }

    public String getContainerErrorMsg() {
        return containerErrorMsg;
    }

    public void setContainerErrorMsg(String containerErrorMsg) {
        this.containerErrorMsg = containerErrorMsg;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((containerAttributes == null) ? 0 : containerAttributes.hashCode());
        result = prime * result + ((containerErrorMsg == null) ? 0 : containerErrorMsg.hashCode());
        result = prime * result + ((datapointAttributes == null) ? 0 : datapointAttributes.hashCode());
        result = prime * result + ((datapoints == null) ? 0 : datapoints.hashCode());
        //result = prime * result + maxOccurs;
        //result = prime * result + minOccurs;
        result = prime * result + ((parentContainer == null) ? 0 : parentContainer.hashCode());
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
        GroupByContainer other = (GroupByContainer) obj;
        if (containerAttributes == null) {
            if (other.containerAttributes != null)
                return false;
        } else if (!containerAttributes.equals(other.containerAttributes))
            return false;
        if (containerErrorMsg == null) {
            if (other.containerErrorMsg != null)
                return false;
        } else if (!containerErrorMsg.equals(other.containerErrorMsg))
            return false;
        if (datapointAttributes == null) {
            if (other.datapointAttributes != null)
                return false;
        } else if (!datapointAttributes.equals(other.datapointAttributes))
            return false;
        if (datapoints == null) {
            if (other.datapoints != null)
                return false;
        } else if (!datapoints.equals(other.datapoints))
            return false;
        /*if (maxOccurs != other.maxOccurs)
            return false;
        if (minOccurs != other.minOccurs)
            return false;*/
        if (parentContainer == null) {
            if (other.parentContainer != null)
                return false;
        } else if (!parentContainer.equals(other.parentContainer))
            return false;
        if (xpath == null) {
            if (other.xpath != null)
                return false;
        } else if (!xpath.equals(other.xpath))
            return false;
        return true;
    }

}
