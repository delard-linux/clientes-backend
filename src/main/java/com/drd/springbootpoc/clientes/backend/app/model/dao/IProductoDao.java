package com.drd.springbootpoc.clientes.backend.app.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.drd.springbootpoc.clientes.backend.app.model.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long> {
	
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String nombreProductoTerm);

	public List<Producto> findByNombreIgnoreCaseContaining(String nombreProductoTerm);

}
