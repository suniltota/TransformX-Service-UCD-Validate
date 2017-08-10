package com.actualize.mortgage.validation.services.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import com.actualize.mortgage.validation.domainmodels.GroupByContainer;
import com.actualize.mortgage.validation.domainmodels.UCDDeliverySpec;
import com.actualize.mortgage.validation.domainmodels.UCDValidationError;
import com.actualize.mortgage.validation.domainmodels.UCDValidationErrors;
import com.actualize.mortgage.validation.services.UCDSpecReader;

public class UCDValidator {
	private static final Logger log = LogManager.getLogger(UCDValidator.class);
    /*public static void main(String[] args) {
        UCDSpecReader ucdSpecReader = new UCDSpecReaderImpl();
        EvaluateXmlNodes xmlNodes = new EvaluateXmlNodes();
        try {
            System.out.println("Starting of validation ..."+ LocalDateTime.now());
            Document document = getUCDDocument(args[0]);
            String loanType = xmlNodes.getValue(document, "MESSAGE/DOCUMENT_SETS/DOCUMENT_SET/DOCUMENTS/DOCUMENT/DEAL_SETS/DEAL_SET/DEALS/DEAL/LOANS/LOAN/TERMS_OF_LOAN/LoanPurposeType"); 
            List<UCDDeliverySpec> results = ucdSpecReader.readValues(loanType);
            Map<String, UCDDeliverySpec> uniqueIdBasedMap = new LinkedHashMap<>();
            Map<String, List<GroupByContainer>> elementsMap = ucdSpecReader.groupByContainers(results, uniqueIdBasedMap, loanType);
            xmlNodes.validateUCDDocument(document, elementsMap, uniqueIdBasedMap);
            System.out.println("Ending of validation ..."+ LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    
    public static Document getUCDDocument(String scenario) throws Exception {
        ClassLoader classLoader = UCDValidator.class.getClassLoader();
        File ucdXml = null;
        if("Purchase".equalsIgnoreCase(scenario)) {
             ucdXml = new File(classLoader.getResource("Purchase_Fixed.xml").getFile());
        } else {
            ucdXml = new File(classLoader.getResource("NonSeller_Fixed.xml").getFile());
        }
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ucdXml);
    }
    
    public UCDValidationErrors validateUCDXML(Document doc, boolean fromWebUI) throws Exception {
        UCDValidationErrors ucdValidationErrors = new UCDValidationErrors();
        List<UCDValidationError> validationErrors = new ArrayList<>();
        UCDSpecReader ucdSpecReader = new UCDSpecReaderImpl();
        EvaluateXmlNodes xmlNodes = new EvaluateXmlNodes();
        try {
            String loanType = xmlNodes.getValue(doc, "MESSAGE/DOCUMENT_SETS/DOCUMENT_SET/DOCUMENTS/DOCUMENT/DEAL_SETS/DEAL_SET/DEALS/DEAL/LOANS/LOAN/TERMS_OF_LOAN/LoanPurposeType");
            List<UCDDeliverySpec> results = ucdSpecReader.readValues(loanType);
            Map<String, UCDDeliverySpec> uniqueIdBasedMap = new LinkedHashMap<>();
            Map<String, List<GroupByContainer>> requiredElementsMap = ucdSpecReader.groupByContainers(results, uniqueIdBasedMap, loanType);
            validationErrors = new ArrayList<>(xmlNodes.validateUCDDocument(doc, requiredElementsMap, uniqueIdBasedMap, fromWebUI));
        } catch (Exception e) {
        	e.printStackTrace();
        	log.error("Validate UCD XML :" + e);
        }
        ucdValidationErrors.setValidationErrors(validationErrors);
        return ucdValidationErrors;
    }
}
