package com.drd.springbootpoc.clientes.backend.app.controllers.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.drd.springbootpoc.clientes.backend.app.common.controller.AppController;
import com.drd.springbootpoc.clientes.backend.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.clientes.backend.app.model.service.IClienteService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController extends AppController{
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IClienteService clienteService;
	

	@GetMapping(value={"/clientes"})
	public List<ClienteDTO> list() {
		
		return clienteService.obtenerTodosClientes();
	}	

	@GetMapping(value={"/clientes/{id}"})
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		ClienteDTO cliente = null; 
		Map<String, Object> mapResult = new HashMap<>();
		
		try {
			cliente = clienteService.obtenerCliente(id);
		} catch (Exception e) {
			log.error("Error al acceder a la BD");
			log.error(e.getMessage(),e);
			mapResult.put("mensaje", "Error al acceder a la BD");
			mapResult.put("error", e.getMessage() );
			return new ResponseEntity<>(mapResult,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (cliente==null) {
			mapResult.put("mensaje", "El cliente ID: "
					.concat(id.toString())
					.concat(" no existe en la BD"));
			return new ResponseEntity<>(mapResult,
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(cliente,
				HttpStatus.OK);
		
	}	
	
	@PostMapping(value={"/clientes"})
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteDTO create(@RequestBody ClienteDTO cliente) {
		Long id = clienteService.crearCliente(cliente);
		return clienteService.obtenerCliente(id);
	}	

	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteDTO update(@PathVariable Long id, @RequestBody ClienteDTO cliente) {
		clienteService.actualizarClienteMerge(id, cliente);
		return clienteService.obtenerCliente(id);
	}	
	
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void delete(@PathVariable Long id) {
		ClienteDTO cliente = clienteService.obtenerCliente(id);
		
		if (cliente!= null) {
			try {
				clienteService.borrarCliente(id);
			} catch (IOException e) {
				log.error("No se puede eliminar el cliente: {}", id);
				log.error(e.getMessage(),e);
			}
		} else {
			log.error("Cliente inexistente: {}", id);
		}
		
	}	

	
}
