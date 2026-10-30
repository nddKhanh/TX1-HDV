package com.example.TX1.repository;
import com.example.TX1.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface ProductRepository extends JpaRepository<Product,Long> {
 @Query("select p from Product p join fetch p.category c where " +
        "(:keyword is null or lower(p.name) like lower(concat('%',:keyword,'%'))) " +
        "and (:categoryId is null or c.id = :categoryId)")
 Page<Product> search(@Param("keyword") String keyword,@Param("categoryId") Long categoryId,Pageable pageable);
 boolean existsByCategoryId(Long categoryId);
}