package com.actualize.mortgage.validation.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.actualize.mortgage.validation.UCDValidationSpringBootApplication;
import com.actualize.mortgage.validation.domainmodels.DataPointDetails;
import com.actualize.mortgage.validation.domainmodels.GroupByContainer;
import com.actualize.mortgage.validation.domainmodels.UCDDeliverySpec;
import com.actualize.mortgage.validation.services.UCDSpecReader;

public class UCDSpecReaderImpl implements UCDSpecReader {

	private static final Logger log = LogManager.getLogger(UCDSpecReaderImpl.class);
	
    //private String fileName = "ucd-delivery-specification-appendix-i.xlsx";
    private String fileName = "ucd-delivery-specification-appendix-i_new.xlsx";
    
    //private String purchaseSheetName = "UCD Purchase Spec";
    //private String nonSellerSheetName = "UCD NonSeller Spec";
    private boolean isPurchase = false;
    //private String splitDisclosureSheetName = "UCD Split Disclosure Spec";
    FormulaEvaluator evaluator = null;
    
    @Override
    public List<UCDDeliverySpec> readValues(String loanType) throws IOException {
    	log.debug("Start Read values ..."+ LocalDateTime.now());
        // Get file from resources folder
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        Workbook workbook = new XSSFWorkbook(inputStream);
        //String sheetname = nonSellerSheetName;
        if(loanType.equalsIgnoreCase("Purchase")) {
        	isPurchase = true;
        }
        Sheet sheet = workbook.getSheet("UCD Delivery Spec 01-31-17");
        evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        List<UCDDeliverySpec> results = new ArrayList<UCDDeliverySpec>();

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if(!getValue(row.getCell(5)).equals("")) {
                UCDDeliverySpec ucdDeliverySpec = new UCDDeliverySpec();
                ucdDeliverySpec.setUniqueId(getValue(row.getCell(0)));
                ucdDeliverySpec.setMismoxpath(getValue(row.getCell(5)));
                ucdDeliverySpec.setMismoparentcontainer(getValue(row.getCell(6)));
                ucdDeliverySpec.setMismodatapointname(getValue(row.getCell(7)));
                ucdDeliverySpec.setUcdsupportedenumerations(getValue(row.getCell(9)));
                ucdDeliverySpec.setUcdFormat(getValue(row.getCell(10)));
                
                //19 Purchase, 20 Error Message
                //21 Non Seller 22 Error Message
                if(isPurchase && !getValue(row.getCell(11)).equals("N/A")){
                	ucdDeliverySpec.setConditionalityType(getValue(row.getCell(11)));
                    ucdDeliverySpec.setConditionalityDetails(getValue(row.getCell(12)));
                    ucdDeliverySpec.setCardinality(getValue(row.getCell(13)));
                    ucdDeliverySpec.setDeliveryNotes(getValue(row.getCell(14)));
                	ucdDeliverySpec.setConditionality(getValue(row.getCell(19)));
                	ucdDeliverySpec.setErrorMessage(getValue(row.getCell(20)));
                	results.add(ucdDeliverySpec);
                }else if(!isPurchase && !getValue(row.getCell(15)).equals("N/A")){
                	ucdDeliverySpec.setConditionalityType(getValue(row.getCell(15)));
                    ucdDeliverySpec.setConditionalityDetails(getValue(row.getCell(16)));
                    ucdDeliverySpec.setCardinality(getValue(row.getCell(17)));
                    ucdDeliverySpec.setDeliveryNotes(getValue(row.getCell(18)));
                	ucdDeliverySpec.setConditionality(getValue(row.getCell(21)));
                	ucdDeliverySpec.setErrorMessage(getValue(row.getCell(22)));
                	results.add(ucdDeliverySpec);
                }
            }
        }
        inputStream.close();
        workbook.close();
        log.debug("End Read values ..."+ LocalDateTime.now());
        return results;
    }
    
    private String getValue(Cell cell) {

        if (cell == null) {
            return "";
        }
        int type = cell.getCellType();
        if (type == Cell.CELL_TYPE_NUMERIC) {
            Object value = cell.getNumericCellValue();
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                return date.getTime() + "";
            } else if (value instanceof Double) {
                value = ((Double) value).doubleValue() + "";
            } else if (value instanceof Integer) {
                value = ((Integer) value).intValue() + "";
            }
            return value.toString();
        } else if (type == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue() + "";
        } else {
            if (type == cell.CELL_TYPE_FORMULA) {
                return evaluator.evaluate(cell).getNumberValue() + "";
            } else if (cell.getStringCellValue().length() > 0) {
                return cell.getStringCellValue().trim();
            } else
                return "";
        }
    }
    
    
    private String getCellValue(Workbook workbook, Cell cell) {

        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(cell);
        
        if(null!=cellValue) {
            CellType cellType = cellValue.getCellTypeEnum();
            switch (cellType) {
                case BOOLEAN:
                    return (cell.getBooleanCellValue() + "").trim();
                case NUMERIC:
                    Object value = cell.getNumericCellValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        return (date.getTime() + "").trim();
                    } else if (value instanceof Double) {
                        value = ((Double) value).doubleValue() + "";
                    } else if (value instanceof Integer) {
                        value = ((Integer) value).intValue() + "";
                    }
                    return value.toString().trim();
                case STRING:
                    return cell.getStringCellValue().trim();
                case BLANK:
                    return "".trim();
                case ERROR:
                    return "".trim();
                case _NONE:
                    return "".trim();
                case FORMULA:
                    return "".trim();
                default:
                    return "";
            }
        } else
            return "";
    }
    
    
    public Map<String, List<GroupByContainer>> groupByContainers(List<UCDDeliverySpec> ucdDeliverySpecs, Map<String, UCDDeliverySpec> uniqueIdBasedMap, String loanType) {

    	log.debug("Start Group containers .." +LocalDateTime.now());
        Map<String, List<GroupByContainer>> groupingByContainerMap = new LinkedHashMap<>();
        for (UCDDeliverySpec ucdDeliverySpec : ucdDeliverySpecs) {
            if (!uniqueIdBasedMap.containsKey(ucdDeliverySpec.getUniqueId()))
                uniqueIdBasedMap.put(ucdDeliverySpec.getUniqueId(), ucdDeliverySpec);
            if (!"O".equalsIgnoreCase(ucdDeliverySpec.getConditionalityType()) && !"N/A".equalsIgnoreCase(ucdDeliverySpec.getConditionalityType()) && !ucdDeliverySpec.getMismoxpath().isEmpty()) {
            	
                List<GroupByContainer> groupByContainers = groupingByContainerMap.get(ucdDeliverySpec.getMismoxpath());
                if (null == groupByContainers) {
                    groupByContainers = new ArrayList<>();
                }
                GroupByContainer groupByContainer = null;
                if (null == ucdDeliverySpec.getMismodatapointname() || ucdDeliverySpec.getMismodatapointname().equalsIgnoreCase("")
                        || groupByContainers.isEmpty()) {
                    groupByContainer = new GroupByContainer();
                    groupByContainers.add(groupByContainer);
                } else {
                    groupByContainer = groupByContainers.get(groupByContainers.size() - 1);
                }
                prepareDatapointObjectBycontainer(ucdDeliverySpec, groupByContainer, loanType);
                if(!"".equals(ucdDeliverySpec.getMismoxpath()))
                    groupingByContainerMap.put(ucdDeliverySpec.getMismoxpath(), groupByContainers);
            }
        }
        for (Map.Entry<String, List<GroupByContainer>> entry : groupingByContainerMap.entrySet())
        {
            List<GroupByContainer> containersGrp = entry.getValue();
            if (containersGrp.size() > 1) {
                List<GroupByContainer> alterNateGroupByContainers = new ArrayList<>();
                for(GroupByContainer container : containersGrp) {
                    if(alterNateGroupByContainers.size()> 0) {
                        GroupByContainer alternateContainer = alterNateGroupByContainers.get(alterNateGroupByContainers.size()-1);
                        if(container.equals(alternateContainer)) {
                            alternateContainer.setMinOccurs(container.getMinOccurs() + alternateContainer.getMinOccurs());
                            alternateContainer.setMaxOccurs(container.getMaxOccurs() + alternateContainer.getMaxOccurs());
                        } else {
                            alterNateGroupByContainers.add(container);
                        }
                    } else {
                        alterNateGroupByContainers.add(container);
                    }
                }
                containersGrp = alterNateGroupByContainers;
            }
            groupingByContainerMap.put(entry.getKey(), containersGrp);
        }
        log.debug("End Group containers .. "+LocalDateTime.now());
        return groupingByContainerMap;
    }
    
    private void prepareDatapointObjectBycontainer(UCDDeliverySpec ucdDeliverySpec, GroupByContainer groupByContainer, String loanType) {

        List<String> containerLevelAttributes = Arrays.asList("@MISMOReferenceModelIdentifier", "@xmlns:gse", "@SequenceNumber", "@xlink:label",
                "@xlink:arcrole", "@xlink:from", "@xlink:to");
        List<String> datapointLevelAttributes = Arrays.asList("@IdentifierOwnerURI", "@gse:DisplayLabelText");

        groupByContainer.setParentContainer(ucdDeliverySpec.getMismoparentcontainer());
        groupByContainer.setXpath(ucdDeliverySpec.getMismoxpath());
        Map<String, DataPointDetails> datapoints = groupByContainer.getDatapoints();
        if (null == datapoints)
            datapoints = new LinkedHashMap<>();
        DataPointDetails ucdDataPointDetails = datapoints.get(ucdDeliverySpec.getMismodatapointname());
        if (null == ucdDataPointDetails)
            ucdDataPointDetails = new DataPointDetails();
        ucdDataPointDetails.setDatapointName(ucdDeliverySpec.getMismodatapointname());
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
        if (containerLevelAttributes.contains(ucdDeliverySpec.getMismodatapointname())) {
            ucdDataPointDetails.setContainerAttribute(true);
            String datapoint = ucdDeliverySpec.getMismodatapointname().substring(1, ucdDeliverySpec.getMismodatapointname().length());
            ucdDataPointDetails.setDatapointName(datapoint);
            datapoints.put(datapoint, ucdDataPointDetails);
            groupByContainer.setDatapoints(datapoints);
        } else if(datapointLevelAttributes.contains(ucdDeliverySpec.getMismodatapointname())) {
            ucdDataPointDetails.setDatapointAttribute(true);
            String datapoint = ucdDeliverySpec.getMismodatapointname().substring(1, ucdDeliverySpec.getMismodatapointname().length());
            ucdDataPointDetails.setDatapointName(datapoint);
            datapoints.put(datapoint, ucdDataPointDetails);
            groupByContainer.setDatapoints(datapoints);
        } else if(null != ucdDeliverySpec.getMismodatapointname() && !ucdDeliverySpec.getMismodatapointname().equalsIgnoreCase("")) {
            ucdDataPointDetails.setDatapoint(true);
            datapoints.put(ucdDeliverySpec.getMismodatapointname(), ucdDataPointDetails);
            groupByContainer.setDatapoints(datapoints);
        } else {
            groupByContainer.setContainerErrorMsg(ucdDeliverySpec.getErrorMessage());
            String cardinality = ucdDeliverySpec.getCardinality();
            if (null != cardinality && !"".equalsIgnoreCase(cardinality) ) {
                if(cardinality.indexOf(":") != -1) {
                    String[] cardinalityRange = cardinality.split(":");
                    int min = Integer.parseInt(cardinalityRange[0]);
                    int max = Integer.parseInt(cardinalityRange[1]);
                    groupByContainer.setMinOccurs(min);
                    groupByContainer.setMaxOccurs(max);
                } else if(cardinality.equalsIgnoreCase("Min = 1")) {
                    groupByContainer.setMinOccurs(1);
                    groupByContainer.setMaxOccurs(100);
                }
            }
        }
    }
}