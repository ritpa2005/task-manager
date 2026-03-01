package com.ritesh.task_manager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ritesh.task_manager.enums.TaskStatus;
import com.ritesh.task_manager.enums.UserRole;
import com.ritesh.task_manager.model.Task;
import com.ritesh.task_manager.model.User;
import com.ritesh.task_manager.repository.TaskRepository;
import com.ritesh.task_manager.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
	private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task createTask(Task task) {

        if (task.getAssignedTo() != null) {
            userRepository.findById(task.getAssignedTo())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
        }

        task.setStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }

    public Page<Task> getTasks(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("deadline").ascending());
        return taskRepository.findAll(pageable);
    }
    
    public Page<Task> getTasksForUser(String userEmail, int page, int size) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(page, size);

        if (user.getRole() == UserRole.ADMIN) {
            return taskRepository.findAll(pageable);
        } else {
            return taskRepository.findByAssignedTo(user.getId(), pageable);
        }
    }

    public Task updateTask(String id, Task updatedTask) {

        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setPriority(updatedTask.getPriority());
        existing.setStatus(updatedTask.getStatus());
        existing.setDeadline(updatedTask.getDeadline());

        return taskRepository.save(existing);
    }

    public void deleteTask(String id) {

        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }

        taskRepository.deleteById(id);
    }
}
