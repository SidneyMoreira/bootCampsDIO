package digitalinnovation.one.experts.productcatalog.repository;

import digitalinnovation.one.experts.productcatalog.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findByName(String name);

}
