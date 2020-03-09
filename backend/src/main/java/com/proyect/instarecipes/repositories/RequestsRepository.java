package com.proyect.instarecipes.repositories;

import com.proyect.instarecipes.models.Request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestsRepository extends JpaRepository<Request, Long> {
    @Query("SELECT r FROM Request r WHERE r.id = :id_request")
    Request findRequestById(Long id_request);
    @Query("SELECT r FROM Request r ORDER BY r.id DESC")
    Page<Request> findAllRequests(PageRequest pageRequest); 
}