package com.ritesh.task_manager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ritesh.task_manager.model.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
	Page<Task> findByAssignedTo(String userId, Pageable pageable);
}
