package com.drd.springbootpoc.clientes.backend.app.common.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;

import com.drd.springbootpoc.clientes.backend.app.common.util.MessageResolver;

@Controller
public class AppController {

	@Autowired
    private LocaleResolver localeResolver;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	MessageResolver msgResolver;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String MSG_RESPONSE_KEY_MENSAJE = "mensaje";
	private static final String MSG_RESPONSE_KEY_ERROR = "error";

	private static final String MSG_RESPONSE_VALUE_ERROR_SERVICIO = "Error al acceder al servicio";
	
	public Locale getApplicationLocale() {
		return localeResolver.resolveLocale(request);
	}
	
	public String getMessage(String key, String ...params) {
		return msgResolver.getMessage(key, getApplicationLocale(), params );
	}
	
	public String getMessage(String key) {
		return msgResolver.getMessage(key, getApplicationLocale());
	}
	
	protected ResponseEntity<Map<String, Object>> gestionarExceptionResponse(Exception e) {
		Map<String, Object> mapResult = new HashMap<>();
		log.error(MSG_RESPONSE_VALUE_ERROR_SERVICIO);
		log.error(e.getMessage(),e);
		mapResult.put(MSG_RESPONSE_KEY_MENSAJE, MSG_RESPONSE_VALUE_ERROR_SERVICIO);
		mapResult.put(MSG_RESPONSE_KEY_ERROR, e.toString() );
		return new ResponseEntity<>(mapResult,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}



}
