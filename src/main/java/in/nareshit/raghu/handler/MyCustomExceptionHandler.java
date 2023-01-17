package in.nareshit.raghu.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import in.nareshit.raghu.exception.ProductNotFoundException;
import in.nareshit.raghu.model.MyErrorType;

@RestControllerAdvice
public class MyCustomExceptionHandler {
	
	/*@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> handlePnfException(ProductNotFoundException pnfe){
		ResponseEntity<String> enity=new ResponseEntity<String>(pnfe.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return enity;
	} */
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<MyErrorType> handlePnfExceptionA(ProductNotFoundException pnfe){
		ResponseEntity<MyErrorType> enity=new ResponseEntity<MyErrorType>(
				                                 new MyErrorType(pnfe.getMessage(),
				                                		 "PRODUCT",
				                                		 "JDH-567-GF",
				                                		 "PRODUCT NOT FOUND",
				                                		 new Date().toString()),
				                                 HttpStatus.INTERNAL_SERVER_ERROR);
		return enity;
	}
	
	

}
