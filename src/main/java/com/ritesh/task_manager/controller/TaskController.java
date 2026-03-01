package com.ritesh.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ritesh.task_manager.model.Task;
import com.ritesh.task_manager.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @GetMapping
    public ResponseEntity<Page<Task>> getAll(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(taskService.getTasks(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(
            @PathVariable String id,
            @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}