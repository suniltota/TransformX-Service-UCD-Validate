package com.actualize.mortgage.validation.controllers;

import java.io.ByteArrayInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import com.actualize.mortgage.validation.domainmodels.UCDValidationErrors;
import com.actualize.mortgage.validation.services.impl.PositionalXMLReader;
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
	
	private static final Logger LOG = LogManager.getLogger(UCDValidationController.class);
	
	@RequestMapping(value = "/validate/ping", method = { RequestMethod.GET })
    public String status() throws Exception {
		LOG.info("user "+SecurityContextHolder.getContext().getAuthentication().getName()+" used Service: ping Validation Service");
        return "The service for validating UCD XML is running and ready to accept your request";
    }
	/**
     * Validate the given XML against UCD Specification and return validation errors
     * 
     * @param xmldoc
     * @return UCDValidationErrors for closing disclosure
     * @throws Exception
     */
	@RequestMapping(value = "/cd/{version}/validate", method = { RequestMethod.POST }, produces = "application/xml")
	public UCDValidationErrors validateUCDXML(@PathVariable String version, @RequestBody String xmldoc) throws Exception {
		LOG.info("user "+SecurityContextHolder.getContext().getAuthentication().getName()+" used Service: Validate UCD XML");
		Document document = PositionalXMLReader.readXML(new ByteArrayInputStream(xmldoc.getBytes("utf-8")));
				//.parse(new InputSource(new ByteArrayInputStream(xmldoc.getBytes("utf-8"))));
		UCDValidator ucdValidator = new UCDValidator();
		boolean fromWebUI = "WebUI".equalsIgnoreCase(version) ? true : false;
		return ucdValidator.validateUCDXML(document, fromWebUI);
	}
}
