package com.example.code.repositories;

import com.example.code.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "graph.User.roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findByUsername(String username);

    User findUserByEmail(String email);
}
