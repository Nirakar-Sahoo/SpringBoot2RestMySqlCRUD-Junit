package in.nareshit.raghu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.exception.ProductNotFoundException;
import in.nareshit.raghu.model.Product;
import in.nareshit.raghu.repositary.ProductRepositary;
import in.nareshit.raghu.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepositary prepo;

	//1.save one product
	@Override
	public Integer createProduct(Product p) {
		/*Product prod=prepo.save(p);
		Integer id=prod.getProdId();
		return id;   */
		return prepo.save(p).getProdId();
	}
	
	//2.find one product
	@Override
	public Product findOneProduct(Integer id) {
		/*Optional<Product> opt=prepo.findById(id);
		if(opt.isPresent())
			return opt.get();
		else
			throw new ProductNotFoundException(id+" not exist");  */
		return prepo.findById(id).orElseThrow(
				       ()->new ProductNotFoundException(id+" id product not exist") );
	}

	//3.get all product
	@Override
	public List<Product> getAllProduct() {
		return prepo.findAll();
	}

	//4.delete one product
	@Override
	public void removeProduct(Integer id) {
		prepo.delete(findOneProduct(id));

	}

	//5.update one product
	@Override
	public void updateOneProduct(Product p) {
		if(p.getProdId()!=null && prepo.existsById(p.getProdId())) {
			prepo.save(p);
		}
		else {
			throw new ProductNotFoundException(p.getProdId()+" with Product not found");
		}
	}

	//6.update product name by id
	@Transactional
	@Override
	public void modifyProdNameById(Integer id, String name) {
		if(prepo.existsById(id)) {
			prepo.updateProdNameById(id, name);
		}
		else {
			throw new ProductNotFoundException(id+" with Product not found");
		}
		
	}

}
