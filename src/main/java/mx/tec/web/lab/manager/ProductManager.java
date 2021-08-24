/**
 * 
 */
package mx.tec.web.lab.manager;



import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import mx.tec.web.lab.entity.Product;
import mx.tec.web.lab.entity.Sku;
import mx.tec.web.lab.repository.ProductRepository;

/**
 * @author koopa
 *
 */
@Service
public class ProductManager {

	@Resource
	ProductRepository productRepository;
	
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> getProduct(final long id) {
		Optional<Product> foundProduct = productRepository.findById(id);
		return foundProduct;
	}
	
	/**
	 * Retrieve an specific product based on a given product id
	 * @param pattern Pattern to search
	 * @return Optional containing a product if the product was found or empty otherwise
	 */
	public List<mx.tec.web.lab.entity.Product> getProducts(final String pattern) {
		return productRepository.findByNameLike(pattern);
	}


	public Product addProduct(final Product newProduct) {
		for (final Sku newSku : newProduct.getChildSkus()) {
			newSku.setParentProduct(newProduct);
		}
		
		Product savedProduct = productRepository.save(newProduct);
		return savedProduct;
	}
	
	public void deleteProduct(final Product existingProduct) {
		productRepository.delete(existingProduct);
	}
	
	public void updateProduct(final long id, final Product modifiedProduct) {
		if (modifiedProduct.getId() == id) {
			for (final Sku modifiedSku : modifiedProduct.getChildSkus()) {
				modifiedSku.setParentProduct(modifiedProduct);
			}			
			
			productRepository.save(modifiedProduct);
		}
	}



}
