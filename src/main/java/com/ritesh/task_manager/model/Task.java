package com.ritesh.task_manager.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ritesh.task_manager.enums.TaskPriority;
import com.ritesh.task_manager.enums.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
	@Id
	private String id;
	@NotBlank
	private String title;
	private String description;
	private TaskPriority priority;
	private TaskStatus status;
	private LocalDate deadline;
	private String assignedTo;
	
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TaskPriority getPriority() {
		return priority;
	}
	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public LocalDate getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
}
