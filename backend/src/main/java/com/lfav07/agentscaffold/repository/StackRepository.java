package com.lfav07.agentscaffold.repository;

import com.lfav07.agentscaffold.model.stack.Stack;
import com.lfav07.agentscaffold.model.stack.StackCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {
    List<Stack> findByCategory(StackCategory category);
    Optional<Stack> findByKey(String key);
}
