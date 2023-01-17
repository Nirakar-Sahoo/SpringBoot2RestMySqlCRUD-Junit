package in.nareshit.raghu.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nareshit.raghu.exception.ProductNotFoundException;
import in.nareshit.raghu.model.Product;
import in.nareshit.raghu.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("rest/product")
@Api(description = "PRODUCT REST APIS")
public class ProductRestController {
	//private static Logger log=LoggerFactory.getLogger(ProductRestController.class);
	
	@Autowired
	ProductService service;
 
	//1.create Product
	@PostMapping("/save")
	public ResponseEntity<String> saveOneProduct(@RequestBody Product prod) {
		log.info("Entered into opertaion");
		ResponseEntity<String> entity=null;
		try {
			Integer id=service.createProduct(prod);
			entity=new ResponseEntity<>("Product Created with id "+id,HttpStatus.CREATED);
		    log.debug("product saved with id :{}",id);
		}
		catch (Exception e) {
			log.error("unable to perform save operation :{}",e.getMessage());
			e.printStackTrace();
		}
		log.info("about to return response");
		return entity;
	}
	
	//2.get all products
	@GetMapping("/all")
	@ApiOperation("GET ALL PRODUCTS")
	public ResponseEntity<List<Product>> fetchAllProducts(){
		ResponseEntity<List<Product>> entity=null;
		List<Product> list=service.getAllProduct();
		entity= new ResponseEntity<>(list, HttpStatus.OK);
		return entity;
	}
	
	//3.find one product by id
	@GetMapping("/find/{id}")
	@ApiOperation("FETCH ONE PRODUCT BY ID")
	public ResponseEntity<?> getOneProduct(@PathVariable Integer id){
		log.info("entered into operation");
		ResponseEntity<?> entity=null;
		try {
			Product prod=service.findOneProduct(id);
			entity= new ResponseEntity<Product>(prod, HttpStatus.OK);
			log.debug("product found: {}",prod);
		}
		catch(ProductNotFoundException pnfe) {
			log.error("unable to process request :{}",pnfe.getMessage());
			pnfe.printStackTrace();
			throw pnfe;
		}
		log.info("product about to return");
		return entity;
	}
	
	//4.delete one product
	@DeleteMapping("/remove/{id}")
	//@ApiIgnore
	public ResponseEntity<?> deleteOneProduct(@PathVariable Integer id){
		ResponseEntity<?> entity=null;
		try {
			service.removeProduct(id);
			entity=new ResponseEntity<String>(id+" product deleted",HttpStatus.OK);
		}
		catch(ProductNotFoundException pnfe) {
			pnfe.printStackTrace();
			throw pnfe;
		}
		return entity;
	}
	//5.update one product
	@PutMapping("/update")
	public ResponseEntity<String> updateFullProduct(@RequestBody Product product){
		ResponseEntity<String> entity=null;
		try {
			service.updateOneProduct(product);
			entity=new ResponseEntity<String>("Product updated",
					//HttpStatus.RESET_CONTENT
					HttpStatus.OK);
		}
		catch(ProductNotFoundException pnfe) {
			pnfe.printStackTrace();	
			throw pnfe;
		}
		return entity;
	}
	//6.partial update one product
	@PatchMapping("/modify/{id}/{name}")
	public ResponseEntity<?> partialUpdateProduct(
			@PathVariable Integer id,@PathVariable String name){
		ResponseEntity<String> entity=null;
		try {
			service.modifyProdNameById(id, name);
			entity=new ResponseEntity<String>("Product Name updated",
					//HttpStatus.PARTIAL_CONTENT 
					HttpStatus.OK);
		}
		catch(ProductNotFoundException pnfe) {
			pnfe.printStackTrace();
			throw pnfe;
		}
		return entity;
	}
	
}
