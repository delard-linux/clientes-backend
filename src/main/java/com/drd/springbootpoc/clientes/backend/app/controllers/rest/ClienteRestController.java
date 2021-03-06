package com.drd.springbootpoc.clientes.backend.app.controllers.rest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drd.springbootpoc.clientes.backend.app.common.controller.AppController;
import com.drd.springbootpoc.clientes.backend.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.clientes.backend.app.model.service.IClienteService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController extends AppController{
	
	private static final String MSG_RESPONSE_KEY_MENSAJE = "mensaje";
	private static final String MSG_RESPONSE_KEY_CLIENTE = "cliente";
	private static final String MSG_RESPONSE_KEY_ERRORES = "errors";
	
	@Autowired
	private IClienteService clienteService;

	@GetMapping(value={"/clientes"})
	public List<ClienteDTO> list() {
		
		return clienteService.obtenerTodosClientes();
	}	

	@GetMapping(value={"/clientes/{id}"})
	public ResponseEntity<Map<String, Object>> show(@PathVariable Long id) {
		
		ClienteDTO cliente = null; 
		
		try {
			cliente = clienteService.obtenerCliente(id);
		} catch (Exception e) {
			return gestionarExceptionResponse(e);
		}
		
		if (cliente==null) {
			return gestionarResponse(
					"El cliente ID: ".concat(id.toString()).concat(" no existe en la BD"),
					cliente,
					HttpStatus.NOT_FOUND);
		}
		
		return gestionarResponse(
				"Cliente obtenido con ??xito",
				cliente,
				HttpStatus.OK);
		
	}	
	
	@PostMapping(value={"/clientes"})
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ClienteDTO cliente,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return gestionarResponseNoValida("Cliente no v??lido", result);
		}
		
		Long id = null;
		ClienteDTO clienteGuardado = null;
		
		try {
			id = clienteService.crearCliente(cliente);
			clienteGuardado = clienteService.obtenerCliente(id);
		} catch (Exception e) {
			return gestionarExceptionResponse(e);
		}

		return gestionarResponse(
				"Cliente creado con ??xito",
				clienteGuardado,
				HttpStatus.CREATED);

	}	

	@PutMapping("/clientes/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, 
			@Valid @RequestBody ClienteDTO cliente,
			BindingResult result) {
			
		if(result.hasErrors()) {
			return gestionarResponseNoValida("Cliente no v??lido", result);
		}
		
		ClienteDTO clienteGuardado = null;
		
		try {
			clienteService.actualizarClienteMerge(id, cliente);
			clienteGuardado = clienteService.obtenerCliente(id);
		} catch (Exception e) {
			return gestionarExceptionResponse(e);
		}

		return gestionarResponse(
				"Cliente actualizado con ??xito",
				clienteGuardado,
				HttpStatus.CREATED);
		
	}	
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

		ClienteDTO cliente = null;

		try {
			cliente = clienteService.obtenerCliente(id);
			if (cliente != null)
				clienteService.borrarCliente(id);
		} catch (Exception e) {
			return gestionarExceptionResponse(e);
		}

		if (cliente != null)
			return gestionarResponse(
					"Cliente eliminado con ??xito",
					cliente,
					HttpStatus.OK);
		else
			return gestionarResponse(
					"Cliente inexistente: ".concat(id.toString()),
					cliente,
					HttpStatus.NOT_FOUND);
	}


	private ResponseEntity<Map<String, Object>> gestionarResponse(String msg, ClienteDTO cliente, HttpStatus status) {
		
		Map<String, Object> mapResult = new HashMap<>();
		
		mapResult.put(MSG_RESPONSE_KEY_MENSAJE, msg);
		if(cliente != null)
			mapResult.put(MSG_RESPONSE_KEY_CLIENTE, cliente);
		
		return new ResponseEntity<>(mapResult, status);

	}
	
	private ResponseEntity<Map<String, Object>> gestionarResponseNoValida(String msg, BindingResult result) {
		
		Map<String, Object> mapResult = new HashMap<>();
		
		mapResult.put(MSG_RESPONSE_KEY_MENSAJE, msg);
		
		List<String> errors = result.getFieldErrors()
								.stream()
								.map(err -> err.getField() + " - " + err.getDefaultMessage())
								.collect(Collectors.toList());
		
		mapResult.put(MSG_RESPONSE_KEY_ERRORES, errors);
		
		return new ResponseEntity<>(mapResult, HttpStatus.BAD_REQUEST);

	}
	
	
}
