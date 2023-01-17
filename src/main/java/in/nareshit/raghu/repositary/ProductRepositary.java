package in.nareshit.raghu.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.Product;

public interface ProductRepositary extends JpaRepository<Product, Integer> {

	@Modifying
	@Query("UPDATE Product SET prodName=:pname WHERE prodId=:pid")
	public void updateProdNameById(Integer pid,String pname);
}
