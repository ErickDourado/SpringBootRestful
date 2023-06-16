package br.com.erickdourado.exceptions;

public class RequiredObjectIsNullException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RequiredObjectIsNullException(String msg) {
		super(msg);
	}
	
	public RequiredObjectIsNullException() {
		super("It is not allowed to persist a null object!");
	}
}