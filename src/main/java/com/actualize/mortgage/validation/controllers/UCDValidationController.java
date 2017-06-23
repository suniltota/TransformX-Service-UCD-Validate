package com.actualize.mortgage.validation.controllers;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.actualize.mortgage.validation.domainmodels.UCDValidationErrors;
import com.actualize.mortgage.validation.services.impl.UCDValidator;

/**
 * This controller is used to define all the endpoints (API) for UCD Validation
 * Service
 * 
 * @author rsudula
 * @version 1.0
 */

@RestController
@RequestMapping("/actualize/transformx/services/ucd")
public class UCDValidationController {
	
	
	@RequestMapping(value = "/{version}/ping", method = { RequestMethod.GET })
    public String status(@PathVariable String version) throws Exception {
        return "The service for validating UCD XML is running and ready to accept your request";
    }
	/**
     * Validate the given XML against UCD Specification and return validation errors
     * 
     * @param xmldoc
     * @return UCDValidationErrors for closing disclosure
     * @throws Exception
     */
	@ResponseBody
	@RequestMapping(value = "/cd/validate", method = { RequestMethod.POST }, produces = "application/xml")
	public UCDValidationErrors validateUCDXML(@RequestBody String xmldoc) throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new InputSource(new ByteArrayInputStream(xmldoc.getBytes("utf-8"))));
		UCDValidator ucdValidator = new UCDValidator();
		return ucdValidator.validateUCDXML(document);
	}
}
