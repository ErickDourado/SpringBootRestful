package br.com.erickdourado.exceptions;

public class UnsupportedMathOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UnsupportedMathOperationException(String msg) {
		super(msg);
	}
}