package com.example.demo.repository;
import java.util.List;
import com.example.demo.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    // Có thể thêm hàm tùy chỉnh như:
    List<Test> findByMessage(String message);
}
