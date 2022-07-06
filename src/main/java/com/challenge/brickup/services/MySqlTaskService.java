package com.challenge.brickup.services;

import com.challenge.brickup.repository.TaskRepository;
import com.challenge.brickup.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class MySqlTaskService implements TaskService {

    @Autowired
    private TaskRepository taskRepo;

    @Override
    public List<Task> findAll() {
        try {
            return this.taskRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task find(Long id) {
        try {
            return this.taskRepo.findById(id).orElseThrow(() -> new RuntimeException("invalid id"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task create(Task task) {
        try {
            List<String> errors = new LinkedList<>();
            if (task.getTask() == null) {
                errors.add("name must be a string");
            }
            if (task.getDescription() == null) {
                errors.add("description must not be null");
            }
            if (!errors.isEmpty()) {
                throw new RuntimeException(errors.toString());
            }
            return this.taskRepo.save(task);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Task update(Task task) {
        try {
            List<String> errors = new LinkedList<>();
            if (task.getTask() == null) {
                errors.add("edited name must be a string");
            }
            if (task.getDescription() == null) {
                errors.add("edited description must not be null");
            }
            if (!errors.isEmpty()) {
                throw new RuntimeException(errors.toString());
            }
            Optional<Task> oldTask = this.taskRepo.findById(task.getId());

            if (oldTask.isPresent()) {
                return this.taskRepo.save(task);
            } else throw new RuntimeException("task not found");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Optional<Task> oldTask = this.taskRepo.findById(id);
            if (oldTask.isPresent()) {
                this.taskRepo.deleteById(id);
                return true;
            } else throw new RuntimeException("task not found");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
