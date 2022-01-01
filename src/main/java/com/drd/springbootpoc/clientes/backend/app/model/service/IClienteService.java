package com.drd.springbootpoc.clientes.backend.app.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.drd.springbootpoc.clientes.backend.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.clientes.backend.app.model.domain.ClienteSearchCriteria;
import com.drd.springbootpoc.clientes.backend.app.model.domain.FacturaDTO;
import com.drd.springbootpoc.clientes.backend.app.model.domain.ProductoDTO;
import com.drd.springbootpoc.clientes.backend.app.util.paginator.Pagina;

public interface IClienteService {
	
	public ClienteDTO obtenerCliente(Long id);

	public ClienteDTO obtenerClienteConFacturas(Long id);

	public List<ClienteDTO> obtenerTodosClientes();

	public Pagina<ClienteDTO> obtenerTodosClientes(Pageable pageable);

	public Long crearCliente(ClienteDTO cliente);
	
	public Long crearClienteConFoto(ClienteDTO cliente, MultipartFile file) throws IOException;

	public void actualizarClienteMerge(Long id, ClienteDTO cliente);
	
	public void actualizarCliente(ClienteDTO cliente);
	
	public void actualizarClienteConFoto(ClienteDTO cliente, MultipartFile file) throws IOException;
	
	public Long guardarCliente(ClienteDTO cliente, MultipartFile file) throws IOException;	

	public void borrarCliente(Long id) throws IOException;
	
	public boolean borrarFotoCliente(Long id) throws IOException;

	public Pagina<ClienteDTO> obtenerTodosClientesCriteria(Pageable pageable, ClienteSearchCriteria criteria);

	public List<ProductoDTO> obtenerProductosPorNombre(String nombreProductoTerm);
	
	public void guardarFactura(FacturaDTO factura);

	public FacturaDTO obtenerFacturaYCliente(Long idFactura);
	
	public ProductoDTO obtenerProducto(Long id);
	
	public void borrarFactura(Long id);
	
}
