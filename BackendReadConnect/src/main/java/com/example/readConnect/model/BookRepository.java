package com.example.readConnect.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
//    List<Book> findByUserIdAndIsCompletedTrue(Long userId);
    List<Book> findByWriterId(Long writertId);
    
    @Query("SELECT j FROM Book j JOIN j.intrests i WHERE i.name = :intrest")
    List<Book> findBooksByIntrestName(@Param("intrest") String intrest);
}
