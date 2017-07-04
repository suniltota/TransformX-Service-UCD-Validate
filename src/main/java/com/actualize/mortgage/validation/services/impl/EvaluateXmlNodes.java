package com.actualize.mortgage.validation.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.actualize.mortgage.validation.domainmodels.DataPointDetails;
import com.actualize.mortgage.validation.domainmodels.GroupByContainer;
import com.actualize.mortgage.validation.domainmodels.UCDDeliverySpec;
import com.actualize.mortgage.validation.domainmodels.UCDValidationError;

public class EvaluateXmlNodes {

    private XPathFactory xpf = null;
    private XPath xpath = null;

    public EvaluateXmlNodes() {
        xpf = XPathFactory.newInstance();
        xpath = xpf.newXPath();
    }

    public NodeList getNodeList(Document document, String xmlPath) {
        XPathExpression expr;
        try {
            expr = xpath.compile(xmlPath);
            Object result = expr.evaluate(document, XPathConstants.NODESET);
            return (NodeList) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String getValue(Document doc, String xmlPath) {
        XPathExpression expr;
        try {
            expr = xpath.compile(xmlPath);
            String result = expr.evaluate(doc);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Set<UCDValidationError> validateUCDDocument(Document doc, Map<String, List<GroupByContainer>> elementsMap, Map<String, UCDDeliverySpec> uniqueIdBasedMap) {
        System.out.println(LocalDateTime.now());
        Set<UCDValidationError> validationErrors = new HashSet<>();
        for (String key : elementsMap.keySet()) {
            System.out.println("::::: map key ::::::" + key);
            List<GroupByContainer> containerDetails = elementsMap.get(key);
            NodeList nodes = getNodeList(doc, key);
            int nodesLength = nodes.getLength();
            boolean isAnyNodeMatched = false;
            boolean hasDatapoints = false;
            DataPointDetails datapointDetails = null;
            for (GroupByContainer container : containerDetails) {
                Map<String, DataPointDetails> datapoints = container.getDatapoints();
                if(null!=datapoints) {
                    hasDatapoints = true;
                    int matchingCount = 0;
                    for (int i = 0; i < nodes.getLength(); i++) {
                        Node node = nodes.item(i);
                        int matchCount = 0;
                        for(String datapoint : datapoints.keySet()) {
                            datapointDetails = datapoints.get(datapoint);
                            if(datapointDetails.isContainerAttribute()) {
                                NamedNodeMap nodeAttributes = node.getAttributes();
                                Map<String, String> attributesMap = getAttributesMap(nodeAttributes);
                                if("R".equalsIgnoreCase(datapointDetails.getConditionalityType())) {
                                    if (attributesMap.containsKey(datapoint)) {
                                        String attribteValueInXml = attributesMap.get(datapoint);
                                        if (null != datapointDetails.getEnumValues()) {
                                            if (datapointDetails.getEnumValues().contains(attribteValueInXml)) {
                                                matchCount++;
                                            }else{
                                            	setErrorMessage(validationErrors, key, datapointDetails);
                                            }
                                        } else {
                                            matchCount++;
                                        }
                                    }else{
                                    	setErrorMessage(validationErrors, key, datapointDetails);
                                    }
                                } else if("CR".equalsIgnoreCase(datapointDetails.getConditionalityType())) {
                                    if (attributesMap.containsKey(datapoint)) {
                                        String attribteValueInXml = attributesMap.get(datapoint);
                                        boolean isValid = ConditionalDatapointsEvaluation.validateConditionalRequiredContainerAttribute(attribteValueInXml, node, datapointDetails, container, doc, uniqueIdBasedMap);
                                        if(isValid)
                                            matchCount++;
                                    } else {
                                        matchCount++;
                                    }
                                }
                            } else if(datapointDetails.isDatapoint()) {
                                Element element = getChild((Element) node, datapoint);
                                if("R".equalsIgnoreCase(datapointDetails.getConditionalityType())) {
                                    if (null != element) {
                                        System.out.println("Element :: " + element.getNodeName() + " = \"" + element.getTextContent() + "\"");
                                        String elementVal = element.getTextContent();
                                        if (null != datapointDetails.getEnumValues()) {
                                            if (datapointDetails.getEnumValues().contains(elementVal)) {
                                                matchCount++;
                                            }else{
                                            	setErrorMessage(validationErrors, key, datapointDetails);
                                            }
                                        } else {
                                            matchCount++;
                                        }
                                    }else{
                                    	setErrorMessage(validationErrors, key, datapointDetails);
                                    }
                                } else if("CR".equalsIgnoreCase(datapointDetails.getConditionalityType())) {
                                    if (null != element) {
                                        System.out.println("Element :: " + element.getNodeName() + " = \"" + element.getTextContent() + "\"");
                                        String elementVal = element.getTextContent();
                                        if (null != datapointDetails.getEnumValues()) {
                                            if (datapointDetails.getEnumValues().contains(elementVal)) {
                                                boolean isValid = ConditionalDatapointsEvaluation.validateConditionalRequiredContainerAttribute(elementVal, node, datapointDetails, container, doc, uniqueIdBasedMap);
                                                if(isValid)
                                                    matchCount++;
                                            }
                                        } else {
                                            boolean isValid = ConditionalDatapointsEvaluation.validateConditionalRequiredContainerAttribute(elementVal, node, datapointDetails, container, doc, uniqueIdBasedMap);
                                            if(isValid)
                                                matchCount++;
                                        }
                                    } else {
                                        matchCount++;
                                    }
                                }
                            } else if(datapointDetails.isDatapointAttribute()) {
                                String attrVal = getChildAttributeValue((Element) node, datapoint);
                                if("R".equalsIgnoreCase(datapointDetails.getConditionalityType())) {
                                    if (null != attrVal) {
                                        if (null != datapointDetails.getEnumValues()) {
                                            if (datapointDetails.getEnumValues().contains(attrVal)) {
                                                matchCount++;
                                            }else{
                                            	setErrorMessage(validationErrors, key, datapointDetails);
                                            }
                                        } else {
                                        	//setErrorMessage(validationErrors, key, datapointDetails);
                                            matchCount++;
                                        }
                                    }else{
                                    	setErrorMessage(validationErrors, key, datapointDetails);
                                    }
                                } else if("CR".equalsIgnoreCase(datapointDetails.getConditionalityType())) {
                                    if (null != attrVal) {
                                        boolean isValid = ConditionalDatapointsEvaluation.validateConditionalRequiredContainerAttribute(attrVal, node, datapointDetails, container, doc, uniqueIdBasedMap);
                                        if(isValid)
                                            matchCount++;
                                    } else {
                                        matchCount++;
                                    }
                                }
                            }
                        }
                        if(matchCount == datapoints.size()) {
                            matchingCount++;
                            isAnyNodeMatched = true;
                        }
                    }
                    if (container.getMinOccurs() > matchingCount) {
                        UCDValidationError ucdValidationError = new UCDValidationError();
                        ucdValidationError.setParentContainer(container.getParentContainer());
                        ucdValidationError.setXpath(container.getXpath());
                        StringBuffer sb = new StringBuffer();
                        sb.append("The datapoints of " + container.getParentContainer() + " are not as per specification. ");
                        sb.append("Please check the datapoints and the values ");
                        int iteratorCount = 0;
                        for (String attr : container.getDatapoints().keySet()) {
                            iteratorCount++;
                            DataPointDetails attributeDetails = container.getDatapoints().get(attr);
                            sb.append(attr);
                            if (null != attributeDetails.getEnumValues())
                                sb.append(" - " + attributeDetails.getEnumValues().toString().substring(1, attributeDetails.getEnumValues().toString().length() - 1));
                            if (iteratorCount < container.getDatapoints().size())
                                sb.append(" and ");
                        }
                        ucdValidationError.setErrorMsg(sb.toString());
                        validationErrors.add(ucdValidationError);
                    }
                } else if (container.getMinOccurs() > nodesLength) {
                    UCDValidationError ucdValidationError = new UCDValidationError();
                    ucdValidationError.setParentContainer(container.getParentContainer());
                    ucdValidationError.setXpath(container.getXpath());
                    if(null!=container.getContainerErrorMsg() && !"".equalsIgnoreCase(container.getContainerErrorMsg()))
                        ucdValidationError.setErrorMsg(container.getContainerErrorMsg());
                    else
                        ucdValidationError.setErrorMsg("The cardinality of "+container.getParentContainer() +" is not as per the UCD specification. Please verify.");
                    validationErrors.add(ucdValidationError);
                }
            }
            if(nodesLength > 0 && !isAnyNodeMatched && hasDatapoints) {
            	setErrorMessage(validationErrors, key, datapointDetails);
                /*UCDValidationError ucdValidationError = new UCDValidationError();
                String[] xpathParts = key.split("/");
                String parentContainer = xpathParts[xpathParts.length-1];
                ucdValidationError.setParentContainer(parentContainer);
                ucdValidationError.setXpath(key);
                if(datapointDetails == null)
                	ucdValidationError.setErrorMsg("The "+ parentContainer +" container is not as per the UCD specification. Please verify all the datapoints.");
                else
                	ucdValidationError.setErrorMsg("The "+ parentContainer +": " + datapointDetails.getDatapointErrorMessage());
                validationErrors.add(ucdValidationError);*/
            }
        }
        System.out.println(LocalDateTime.now());
        return validationErrors;
    }
    
    public List<Node> list(final NodeList list) {
        List<Node> nodes = new ArrayList<>();
        for(int i=0; i<list.getLength(); i++) {
            nodes.add(list.item(i));
        }
        return nodes;
    }
    
    private void setErrorMessage(Set<UCDValidationError> validationErrors, String key, DataPointDetails datapointDetails){
    	UCDValidationError ucdValidationError = new UCDValidationError();
        String[] xpathParts = key.split("/");
        String parentContainer = xpathParts[xpathParts.length-1];
        ucdValidationError.setParentContainer(parentContainer);
        ucdValidationError.setXpath(key);
        if(datapointDetails == null)
        	ucdValidationError.setErrorMsg("The "+ xpathParts +" container is not as per the UCD specification. Please verify all the datapoints.");
        else
        	ucdValidationError.setErrorMsg(datapointDetails.getDatapointErrorMessage());
        validationErrors.add(ucdValidationError);
    }
    public static Map<String, String> getAttributesMap(NamedNodeMap nodeAttributes) {
        Map<String, String> attributesMap = new LinkedHashMap<>();
        if (null != nodeAttributes) {
            for (int j = 0; j < nodeAttributes.getLength(); j++) {
                Node attr = nodeAttributes.item(j);
                System.out.println("Attribute :: " + attr.getNodeName() + " = \"" + attr.getNodeValue() + "\"");
                attributesMap.put(attr.getNodeName(), attr.getNodeValue());
            }
        }
        return attributesMap;
    }

    public static Element getChild(Element parent, String name) {
        for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child instanceof Element) {
                if(name.contains("gse:")) {
                    if(name.equals(child.getNodeName())) return (Element) child;
                } else if(("mismo:"+name).equals(child.getNodeName()) || ("gse:"+name).equals(child.getNodeName())) {
                    return (Element) child;
                }
            }
        }
        return null;
    }
    
    public static String getChildAttributeValue(Element parent, String name) {
        for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child instanceof Element) {
                if(child.hasAttributes()) {
                    NamedNodeMap attributes = child.getAttributes();
                    Map<String, String> attributesMap = getAttributesMap(attributes);
                    if(attributesMap.containsKey(name)) {
                        return attributesMap.get(name);
                    }
                }
            }
        }
        return null;
    }
    
    public static Element getElementNode(NodeList nodeList, String container, String datapoint) {
        Element element = null;
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node currentNode = nodeList.item(j);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                if(currentNode.getNodeName().equals("mismo:"+container) || currentNode.getNodeName().equals("gse:"+container)) {
                    element = getChild((Element) currentNode, datapoint);
                    break;
                }
            } else {
                getElementNode(currentNode.getChildNodes(), container, datapoint);
            }
        }
        return element;
    }
    
    public static Node getParentNode(Node node, String parentName) {
        Node parentNode = node.getParentNode();
        if(parentNode.getNodeName().equals("mismo:"+parentName) || parentNode.getNodeName().equals("gse:"+parentName)) {
            return parentNode;
        } else {
            return getParentNode(parentNode, parentName);
        }
    }
    
    public static Map<String, String> getDatapointsWithValuesOfNode(Node node) {
        Map<String, String> nodeElements = new LinkedHashMap<>();
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child instanceof Element) {
                nodeElements.put(child.getNodeName(), child.getTextContent());
            }
        }
        return nodeElements;
    }

}
