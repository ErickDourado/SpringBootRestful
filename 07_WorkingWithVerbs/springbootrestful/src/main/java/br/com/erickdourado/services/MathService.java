package br.com.erickdourado.services;

import org.springframework.stereotype.Service;

import br.com.erickdourado.exceptions.UnsupportedMathOperationException;

@Service
public class MathService {
	
	public Double operationWithTwoNumbers(String operator, String strNumberOne, String strNumberTwo) {
		
		if (!isNumeric(strNumberOne) || !isNumeric(strNumberTwo)) {
			throw new UnsupportedMathOperationException("Please, set a numeric value!");
		}
		
		Double numberOne = convertToDouble(strNumberOne);
		Double numberTwo = convertToDouble(strNumberTwo);
		
		if (operator.equals("sum")) {
			return numberOne + numberTwo;						
		} else if (operator.equals("subtraction")) {
			return numberOne - numberTwo;	
		} else if (operator.equals("multiplication")) {
			return numberOne * numberTwo;				
		} else if (operator.equals("division")) {
			return numberOne / numberTwo;				
		} else if (operator.equals("average")) {
			return (numberOne + numberTwo) / 2;				
		}
				
		throw new UnsupportedMathOperationException("Please, set a valid operator!");
	}
	
	public Double sqrt(String strNumber) {
		
		if (!isNumeric(strNumber) || !isNumeric(strNumber)) {
			throw new UnsupportedMathOperationException("Please, set a numeric value!");
		}
		
		Double number = convertToDouble(strNumber);
		
		return Math.sqrt(number);
	}
	
	private Double convertToDouble(String strNumber) {
		if (strNumber == null) return 0D;
		
		// BR 10,25 US 10.25
		String number = strNumber.replaceAll(",", ".");
		
		if (isNumeric(number)) return Double.parseDouble(number);
		
		return 0D;
	}

	private boolean isNumeric(String strNumber) {
		if (strNumber == null) return false;
		
		// BR 10,25 US 10.25
		String number = strNumber.replaceAll(",", ".");
		
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
