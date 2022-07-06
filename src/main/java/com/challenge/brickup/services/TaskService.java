package com.challenge.brickup.services;

import com.challenge.brickup.task.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    Task find(Long id);

    Task create(Task task);

    Task update(Task task);

    boolean delete(Long id);
}
