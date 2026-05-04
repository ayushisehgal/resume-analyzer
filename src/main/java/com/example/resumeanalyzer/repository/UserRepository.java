package com.example.resumeanalyzer.repository;





import org.springframework.data.jpa.repository.JpaRepository;
import com.example.resumeanalyzer.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}