package com.challenge.brickup.controller;

import com.challenge.brickup.task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

@SpringBootTest
class TaskControllerTest {

    @Autowired
    TaskController taskController;
    Task task = new Task("tarefa", "descrição", "feito");
    MockMultipartFile file = new MockMultipartFile("data", "filename.png", "text/plain", "some xml".getBytes());


    @Test
    void givenAValidTaskIsInformed_ShouldCreateANewTask() {
        Task createdTask = taskController.create(task).getBody();
        Assertions.assertNotNull(createdTask);
        Assertions.assertEquals(createdTask.getTask(), "tarefa");
        Assertions.assertNotNull(createdTask.getId());
    }


    @Test
    void findAll() {
        Task createdTask = taskController.create(task).getBody();
        Assertions.assertNotNull(createdTask);
        Assertions.assertEquals(createdTask.getTask(), "tarefa");

        List<Task> taskList = taskController.findAll();
        Assertions.assertNotNull(taskList);
        Assertions.assertNotEquals(taskList.size(), 0);
    }

    @Test
    void ShouldBeAbleToCreateATaskWithImg() {
    }
}