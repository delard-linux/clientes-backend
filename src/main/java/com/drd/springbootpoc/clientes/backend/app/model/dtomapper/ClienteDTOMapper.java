package com.drd.springbootpoc.clientes.backend.app.model.dtomapper;

import java.util.ArrayList;
import java.util.List;

import com.drd.springbootpoc.clientes.backend.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.clientes.backend.app.model.entity.Cliente;

public class ClienteDTOMapper {

	private ClienteDTOMapper() {}	
	
	public static List<ClienteDTO> transformEntityListToDTOList(Iterable<Cliente> clientesEntityIterable) {

		List<ClienteDTO> clientes =  new ArrayList<>();
		
		clientesEntityIterable.forEach(cl -> 
						clientes.add(transformEntityToDTO(cl)));
		
		return clientes;
	}
	
	public static ClienteDTO transformEntityToDTO(Cliente clienteEntity) {

		return new ClienteDTO(clienteEntity.getId(),
				clienteEntity.getNombre(), 
				clienteEntity.getApellido(), 
				clienteEntity.getEmail(), 
				clienteEntity.getBornAt(), 
				clienteEntity.getCreateAt(),
				clienteEntity.getFoto());

	}

	public static Cliente transformDTOToEntity(ClienteDTO cliente) {

		var clienteEntity = new Cliente();
		
		clienteEntity.setId(cliente.getId());
		clienteEntity.setNombre(cliente.getNombre());
		clienteEntity.setApellido(cliente.getApellido());
		clienteEntity.setEmail(cliente.getEmail());
		clienteEntity.setBornAt(cliente.getBornAt());
		clienteEntity.setCreateAt(cliente.getCreateAt());
		clienteEntity.setFoto(cliente.getFoto());
		
		return clienteEntity;

	}	
	
	public static Cliente mergeDTOToEntity(ClienteDTO currentCliente, ClienteDTO cliente) {

		var clienteEntity = new Cliente();
		
		clienteEntity.setId(currentCliente.getId());
		clienteEntity.setNombre(cliente.getNombre()!=null ? cliente.getNombre() : currentCliente.getNombre());
		clienteEntity.setApellido(cliente.getApellido()!=null ? cliente.getApellido() : currentCliente.getApellido());
		clienteEntity.setEmail(cliente.getEmail()!=null ? cliente.getEmail() : currentCliente.getEmail());
		clienteEntity.setBornAt(cliente.getBornAt()!=null ? cliente.getBornAt() : currentCliente.getBornAt());
		clienteEntity.setCreateAt(cliente.getCreateAt()!=null ? cliente.getCreateAt() : currentCliente.getCreateAt());
		clienteEntity.setFoto(cliente.getFoto()!=null ? cliente.getFoto() : currentCliente.getFoto());
		
		return clienteEntity;

	}
	
	
}
