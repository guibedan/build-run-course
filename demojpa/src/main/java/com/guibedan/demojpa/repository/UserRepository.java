package com.guibedan.demojpa.repository;

import com.guibedan.demojpa.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM tb_users WHERE name = ?1",
            countQuery = "SELECT COUNT(*) FROM tb_users WHERE name = ?1",
            nativeQuery = true)
    Page<UserEntity> findByUsername(String userName, PageRequest pageRequest);

    Page<UserEntity> findByAgeGreaterThanEqual(int age, PageRequest pageRequest);

    Page<UserEntity> findByUsernameAndAgeGreaterThanEqual(String userName, int age, PageRequest pageRequest);

}
