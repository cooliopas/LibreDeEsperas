package com.uxor.turnos.domain.exception;


public class UxorException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor con mensaje de error.
	 * @param msg El mensaje de error asociado con la excepci�n
	 */

	public UxorException(String msg) {
		super(msg);
	}
	
	/**
	 * Constructor con mensjae de error y causa ra�z.
	 * 
	 * @param msg el mensaje de error asociado con la excepci�n.
	 * @param cause la causa ra�z asociado con la excepci�n.
	 */
	public UxorException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
