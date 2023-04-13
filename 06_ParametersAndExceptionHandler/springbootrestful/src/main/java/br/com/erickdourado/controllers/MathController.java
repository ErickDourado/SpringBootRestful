package br.com.erickdourado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.erickdourado.services.MathService;

@RestController
public class MathController {
	
	@Autowired
	MathService mathService;
	
	@GetMapping("/{operator}/{numberOne}/{numberTwo}")
	public ResponseEntity<Double> operationWithTwoNumbers(@PathVariable(value = "operator") String operator,
														  @PathVariable(value = "numberOne") String numberOne,
														  @PathVariable(value = "numberTwo") String numberTwo) {
		
		Double result = mathService.operationWithTwoNumbers(operator, numberOne, numberTwo);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/sqrt/{strNumber}")
	public ResponseEntity<Double> sqrt(@PathVariable(value = "strNumber") String strNumber) {
		
		Double result = mathService.sqrt(strNumber);
		
		return ResponseEntity.ok(result);
	}
}
