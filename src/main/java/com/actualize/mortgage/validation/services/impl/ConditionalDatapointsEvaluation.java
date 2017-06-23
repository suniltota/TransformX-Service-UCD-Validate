package com.actualize.mortgage.validation.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.actualize.mortgage.validation.domainmodels.DataPointDetails;
import com.actualize.mortgage.validation.domainmodels.GroupByContainer;
import com.actualize.mortgage.validation.domainmodels.UCDDeliverySpec;

public class ConditionalDatapointsEvaluation {

    public static boolean validateConditionalRequiredContainerAttribute(String currentDatapointValue, Node node, DataPointDetails dataPointDetails, GroupByContainer container, Document doc, Map<String, UCDDeliverySpec> uniqueIdMap) {
        boolean isValid = false;
        String datapointCondition = dataPointDetails.getDatapointCondition();
        if (null != datapointCondition && !"".equals(datapointCondition)) {
            String[] conditionLines = datapointCondition.split("\n");
            for(int e=0; e<conditionLines.length; e++)
                conditionLines[e] = conditionLines[e].trim();
            if(conditionLines.length==1) {
                return validateConditionalLine(conditionLines[0], currentDatapointValue, node, dataPointDetails, container, doc, uniqueIdMap);
            } else if(conditionLines.length==3) {
                boolean firstLineConditionVal = validateConditionalLine(conditionLines[0], currentDatapointValue, node, dataPointDetails, container, doc, uniqueIdMap);
                if("OR".equals(conditionLines[1]) && firstLineConditionVal)
                    return firstLineConditionVal;
                else if("AND".equals(conditionLines[1]) && !firstLineConditionVal)
                    return firstLineConditionVal;
                boolean secondLineConditionVal = validateConditionalLine(conditionLines[2], currentDatapointValue, node, dataPointDetails, container, doc, uniqueIdMap);
                if("AND".equals(conditionLines[1])) {
                    return (firstLineConditionVal && secondLineConditionVal);
                } else {
                    return (firstLineConditionVal || secondLineConditionVal);
                }
            } else {
                List<String> resultsOflines = new ArrayList<>();
                boolean isGroupCondition = false;
                List<String> groupConditionsResults = new ArrayList<>();
                for(int i=0; i<conditionLines.length; i++) {
                    if(conditionLines[i].equals("(")) {
                        isGroupCondition = true;
                    } else if(conditionLines[i].equals(")")) {
                        isGroupCondition = false;
                        boolean groupConditionFinalResult = Boolean.valueOf(groupConditionsResults.get(0));
                        if(!resultsOflines.isEmpty()) {
                            boolean result = false;
                            if("AND".equals(resultsOflines.get(1))) {
                                result = groupConditionFinalResult && Boolean.valueOf(resultsOflines.get(0));
                            } else {
                                result = groupConditionFinalResult || Boolean.valueOf(resultsOflines.get(0));
                            }
                            resultsOflines.clear();
                            resultsOflines.add(Boolean.toString(result));
                        } else {
                            resultsOflines.add(Boolean.toString(groupConditionFinalResult));
                        }
                    } else {
                        if("AND".equals(conditionLines[i])) {
                            if(isGroupCondition)
                                groupConditionsResults.add("AND");
                            else
                                resultsOflines.add("AND");
                        } else if("OR".equals(conditionLines[i])){
                            if(isGroupCondition)
                                groupConditionsResults.add("OR");
                            else
                                resultsOflines.add("OR");
                        } else {
                            boolean isSatisfied = validateConditionalLine(conditionLines[i], currentDatapointValue, node, dataPointDetails, container, doc, uniqueIdMap);
                            if(isGroupCondition) {
                                if(!groupConditionsResults.isEmpty()) {
                                    boolean groupConditionsResult = false;
                                    if("AND".equals(groupConditionsResults.get(1))) {
                                        groupConditionsResult = isSatisfied && Boolean.valueOf(groupConditionsResults.get(0));
                                    } else {
                                        groupConditionsResult = isSatisfied || Boolean.valueOf(groupConditionsResults.get(0));
                                    }
                                    groupConditionsResults.clear();
                                    groupConditionsResults.add(Boolean.toString(groupConditionsResult));
                                } else {
                                    groupConditionsResults.add(Boolean.toString(isSatisfied));
                                }
                            } else {
                                if(!resultsOflines.isEmpty()) {
                                    boolean result = false;
                                    if("AND".equals(resultsOflines.get(1))) {
                                        result = isSatisfied && Boolean.valueOf(resultsOflines.get(0));
                                    } else {
                                        result = isSatisfied || Boolean.valueOf(resultsOflines.get(0));
                                    }
                                    resultsOflines.clear();
                                    resultsOflines.add(Boolean.toString(result));
                                } else {
                                    resultsOflines.add(Boolean.toString(isSatisfied));
                                }
                            }
                        }
                    }
                }
                isValid = Boolean.valueOf(resultsOflines.get(0));
            }
        } else 
            return true;
        return isValid;
    }
    
    public static boolean validateConditionalLine(String conditionLine, String currentDatapointValue, Node node, DataPointDetails dataPointDetails, GroupByContainer container, Document doc, Map<String, UCDDeliverySpec> uniqueIdMap) {
        String[] conditionSplitForContainer = conditionLine.split(":");
        String travelContainer = conditionSplitForContainer[0];
        String targetCondition = conditionSplitForContainer[1];
        String[] conditionsTokens = targetCondition.split("\\$\\$");
        for(int e=0; e<conditionsTokens.length; e++)
            conditionsTokens[e] = conditionsTokens[e].trim();
        UCDDeliverySpec targetDatapointSpec = uniqueIdMap.get(conditionsTokens[0]);
        DataPointDetails targetDatapointDetails =  prepareDatapointBySpec(targetDatapointSpec);
        if(travelContainer.equals("THIS")) {
            if(container.getDatapoints().containsKey(targetDatapointSpec.getMismodatapointname())) {
                targetDatapointDetails = container.getDatapoints().get(targetDatapointSpec.getMismodatapointname());
                Element element = getElementFromNode((Element) node, targetDatapointDetails.getDatapointName());
                return validateValueOfCondition(conditionsTokens, element, targetDatapointDetails, currentDatapointValue);
            } else {
                return false;
            }
        } else if(travelContainer.equals("NONE")) {
            NodeList targetNodes = getNodeList(doc, targetDatapointSpec.getMismoxpath());
            for (int j = 0; j < targetNodes.getLength(); j++) {
                Node targetNode = targetNodes.item(j);
                Element element = getElementFromNode((Element) targetNode, targetDatapointDetails.getDatapointName());
                boolean conditionSatisfied =  validateValueOfCondition(conditionsTokens, element, targetDatapointDetails, currentDatapointValue);
                if(conditionSatisfied)
                    return true;
            }
            return false;
        } else {
            Element element = null;
            if(container.getXpath().contains(travelContainer)){
                Node parentNode = getParentNode(node, travelContainer);
                String[] targetXpathSplit = targetDatapointSpec.getMismoxpath().split("/");
                String xpathAfterTravelContainer = "";
                for(int counter=targetXpathSplit.length - 1; counter >= 0;counter--){
                    if( targetXpathSplit[counter].equals(travelContainer))
                        break;
                    xpathAfterTravelContainer = targetXpathSplit[counter] +"/" + xpathAfterTravelContainer;
                }
                if(xpathAfterTravelContainer.endsWith("OTHER/")) {
                    xpathAfterTravelContainer = xpathAfterTravelContainer.substring(0, xpathAfterTravelContainer.length()-1);
                    NodeList nodeList = getNodeList(parentNode, xpathAfterTravelContainer);
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node otherNode = nodeList.item(j);
                        element = getElementFromNode((Element) otherNode, targetDatapointSpec.getMismodatapointname());
                        boolean conditionSatisfied =  validateValueOfCondition(conditionsTokens, element, targetDatapointDetails, currentDatapointValue);
                        if(conditionSatisfied)
                            return true;
                    }
                } else {
                    xpathAfterTravelContainer = xpathAfterTravelContainer + targetDatapointSpec.getMismodatapointname();
                    NodeList nodeList = getNodeList(parentNode, xpathAfterTravelContainer);
                    if(nodeList.getLength()==0) {
                        element = null;
                        boolean conditionSatisfied =  validateValueOfCondition(conditionsTokens, element, targetDatapointDetails, currentDatapointValue);
                        if(conditionSatisfied)
                            return true;
                    } else {
                        for (int j = 0; j < nodeList.getLength(); j++) {
                            element = (Element) nodeList.item(j);
                            boolean conditionSatisfied =  validateValueOfCondition(conditionsTokens, element, targetDatapointDetails, currentDatapointValue);
                            if(conditionSatisfied)
                                return true;
                        } 
                    }
                }
                return false;
            } else {
                String[] targetXpathSplit = targetDatapointSpec.getMismoxpath().split("/");
                String xpathAfterTravelContainer = "";
                for(int counter=targetXpathSplit.length - 1; counter >= 0;counter--){
                    if( targetXpathSplit[counter].equals(travelContainer))
                        break;
                    xpathAfterTravelContainer = targetXpathSplit[counter] +"/" + xpathAfterTravelContainer;
                }
                xpathAfterTravelContainer = xpathAfterTravelContainer + targetDatapointSpec.getMismodatapointname();
                NodeList nodeList = getNodeList(node, xpathAfterTravelContainer);
                if(nodeList.getLength()==0) {
                    element = null;
                    boolean conditionSatisfied =  validateValueOfCondition(conditionsTokens, element, targetDatapointDetails, currentDatapointValue);
                    if(conditionSatisfied)
                        return true;
                } else {
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        element = (Element) nodeList.item(j);
                        boolean conditionSatisfied =  validateValueOfCondition(conditionsTokens, element, targetDatapointDetails, currentDatapointValue);
                        if(conditionSatisfied)
                            return true;
                    }
                }
                return false;
            }
        }
    }

    public static Element getElementFromNode(Element parent, String name) {
        for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child instanceof Element) {
                if(name.contains("gse:")) {
                    if (name.equals(child.getNodeName())) return (Element) child;
                } else {
                    if (("mismo:" + name).equals(child.getNodeName()) || ("gse:" + name).equals(child.getNodeName())) {
                        return (Element) child;
                    }
                }
            }
        }
        return null;
    }
    
    public static Node getParentNode(Node node, String parentName) {
        if(node.getNodeName().equals("mismo:"+parentName) || node.getNodeName().equals("gse:"+parentName))
            return node;
        Node parentNode = node.getParentNode();
        if(parentNode.getNodeName().equals("mismo:"+parentName) || parentNode.getNodeName().equals("gse:"+parentName)) {
            return parentNode;
        } else {
            return getParentNode(parentNode, parentName);
        }
    }
    
    public static void  getElementNode(Node node, String container, String datapoint, Element element) {
        NodeList nodeList = getNodeList(node, container+"/"+datapoint);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                if (currentNode.getNodeName().equals("mismo:"+container) || currentNode.getNodeName().equals("gse:"+container)) {
                    element = getElementFromNode((Element) currentNode, datapoint);
                }
            }
        }
    }
    
    public static NodeList getNodeList(Document document, String xmlPath) {
        try {
            XPathExpression expr = XPathFactory.newInstance().newXPath().compile(xmlPath);
            Object result = expr.evaluate(document, XPathConstants.NODESET);
            return (NodeList) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static NodeList getNodeList(Node node, String xmlPath) {
        try {
            XPathExpression expr = XPathFactory.newInstance().newXPath().compile(xmlPath);
            Object result = expr.evaluate(node, XPathConstants.NODESET);
            return (NodeList) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean validateValueOfCondition(String[] conditionsTokens, Element xmlTargetElement, DataPointDetails targetDatapointDetails, String valueOfcurrentNodeinXml) {
        boolean conditionSatisfied = false;
        switch(conditionsTokens[1]) {
            case "==" :
                if(conditionsTokens[2].equals("NULL")) {
                    return conditionSatisfied = null == xmlTargetElement ? true : false;
                } else {
                    String targetElementVal = null != xmlTargetElement ? xmlTargetElement.getTextContent() : null;
                    if(conditionsTokens[2].equals(targetElementVal)) {
                        return conditionSatisfied = true;
                    }
                }
                break;
            case "!=":
                if(conditionsTokens[2].equals("NULL")) {
                    return conditionSatisfied = null != xmlTargetElement ? true : false;
                } else if(conditionsTokens[2].equals("ENUM")) {
                    if(!targetDatapointDetails.getEnumValues().contains(valueOfcurrentNodeinXml)) {
                        return conditionSatisfied = true;
                    }
                } else {
                    String targetElementVal = null != xmlTargetElement ? xmlTargetElement.getTextContent() : null;
                    if(!conditionsTokens[2].equals(targetElementVal)) {
                        return conditionSatisfied = true;
                    }
                }
                break;
            case "<": {
                        if(null==xmlTargetElement) return false;
                        Double targetElementVal = Double.parseDouble(xmlTargetElement.getTextContent());
                        Double conditionVal = Double.parseDouble(conditionsTokens[2]);
                        if(targetElementVal < conditionVal) {
                            return conditionSatisfied = true;
                        }
                      }
                    break;
            case ">": {
                        if(null==xmlTargetElement) return false;
                        Double targetElementVal = Double.parseDouble(xmlTargetElement.getTextContent());
                        Double conditionVal = Double.parseDouble(conditionsTokens[2]);
                        if(targetElementVal > conditionVal) {
                            return conditionSatisfied = true;
                        }
                      }
                      break;
            case "<=": {
                        if(null==xmlTargetElement) return false;
                        Double targetElementVal = Double.parseDouble(xmlTargetElement.getTextContent());
                        Double conditionVal = Double.parseDouble(conditionsTokens[2]);
                        if(targetElementVal <= conditionVal) {
                            return conditionSatisfied = true;
                        }
                       }
                       break;
            case ">=": {
                        if(null==xmlTargetElement) return false;
                        Double targetElementVal = Double.parseDouble(xmlTargetElement.getTextContent());
                        Double conditionVal = Double.parseDouble(conditionsTokens[2]);
                        if(targetElementVal >= conditionVal) {
                            return conditionSatisfied = true;
                        }
                       }
                       break;
            default:
                return conditionSatisfied;
        }
        return conditionSatisfied;
    }
    
    
    public static DataPointDetails prepareDatapointBySpec(UCDDeliverySpec ucdDeliverySpec) {

        List<String> containerLevelAttributes = Arrays.asList("@MISMOReferenceModelIdentifier", "@xmlns:gse", "@SequenceNumber", "@xlink:label",
                "@xlink:arcrole", "@xlink:from", "@xlink:to");
        List<String> datapointLevelAttributes = Arrays.asList("@IdentifierOwnerURI", "@gse:DisplayLabelText");

        DataPointDetails ucdDataPointDetails = new DataPointDetails();
        
        if (containerLevelAttributes.contains(ucdDeliverySpec.getMismodatapointname())) {
            ucdDataPointDetails.setContainerAttribute(true);
            String datapoint = ucdDeliverySpec.getMismodatapointname().substring(1, ucdDeliverySpec.getMismodatapointname().length());
            ucdDataPointDetails.setDatapointName(datapoint);
        } else if(datapointLevelAttributes.contains(ucdDeliverySpec.getMismodatapointname())) {
            ucdDataPointDetails.setDatapointAttribute(true);
            String datapoint = ucdDeliverySpec.getMismodatapointname().substring(1, ucdDeliverySpec.getMismodatapointname().length());
            ucdDataPointDetails.setDatapointName(datapoint);
        } else if(null != ucdDeliverySpec.getMismodatapointname() && !ucdDeliverySpec.getMismodatapointname().equalsIgnoreCase("")) {
            ucdDataPointDetails.setDatapoint(true);
            ucdDataPointDetails.setDatapointName(ucdDeliverySpec.getMismodatapointname());
        }
        ucdDataPointDetails.setDatapointCondition(ucdDeliverySpec.getConditionality());
        ucdDataPointDetails.setDatapointErrorMessage(ucdDeliverySpec.getErrorMessage());
        ucdDataPointDetails.setValidationRequired(ucdDeliverySpec.getValidationRequired());
        ucdDataPointDetails.setConditionalityType(ucdDeliverySpec.getConditionalityType());
        if (ucdDeliverySpec.getUcdFormat().equalsIgnoreCase("Enumerated") || !ucdDeliverySpec.getUcdsupportedenumerations().isEmpty()) {
            Set<String> enumValues = ucdDataPointDetails.getEnumValues();
            if (null == enumValues)
                enumValues = new HashSet<>();
            String[] enumArray = ucdDeliverySpec.getUcdsupportedenumerations().split("\\|");
            for(int e=0; e<enumArray.length; e++)
                enumArray[e] = enumArray[e].trim();
            enumValues.addAll(Arrays.asList(enumArray));
            ucdDataPointDetails.setEnumValues(enumValues);
        }
        
        return ucdDataPointDetails;
    }
    
}
