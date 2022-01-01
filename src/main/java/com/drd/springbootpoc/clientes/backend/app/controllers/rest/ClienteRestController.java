package com.drd.springbootpoc.clientes.backend.app.controllers.rest;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ClienteDTO show(@PathVariable Long id) {
		return clienteService.obtenerCliente(id);
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
