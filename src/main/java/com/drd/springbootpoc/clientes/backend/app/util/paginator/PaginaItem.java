package com.drd.springbootpoc.clientes.backend.app.util.paginator;

public class PaginaItem {
	private int numero;
	private boolean actual;

	public PaginaItem(int numero, boolean actual) {
		this.numero = numero;
		this.actual = actual;
	}

	public int getNumero() {
		return numero;
	}

	public boolean isActual() {
		return actual;
	}
}
