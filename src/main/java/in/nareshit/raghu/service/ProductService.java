package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.model.Product;

public interface ProductService {
	
	//save one product
	public Integer createProduct(Product p);
	//find one product
	public Product findOneProduct(Integer id);
	//get all product
	public List<Product> getAllProduct();
	//delete one product
	public void removeProduct(Integer id);
	//update one product
	public void updateOneProduct(Product p);
	//partial update one product
	public void modifyProdNameById(Integer id,String name);
}
