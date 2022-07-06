package com.challenge.brickup.controller;

import com.challenge.brickup.services.InMemoryPictureService;
import com.challenge.brickup.services.MySqlTaskService;
import com.challenge.brickup.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/task")
@CrossOrigin(value = "http://localhost:3000")
public class TaskController {

    @Autowired
    private MySqlTaskService mySqlTaskService;

    @Autowired
    private InMemoryPictureService inMemoryPictureService;

    @PostMapping(value = "/createImg")
    @ResponseBody
    public ResponseEntity<Task> createImg(@RequestParam("file") MultipartFile file, @RequestParam("task") String taskName, @RequestParam("description") String description, @RequestParam("status") String status) {
        try {
            Task task = new Task(taskName, description, status);
            Task createdTask = mySqlTaskService.create(task);
            if (file.isEmpty()) throw new RuntimeException("file must not be empty");
            Boolean isImgSaved = inMemoryPictureService.saveImg(file, createdTask.getId().toString());
            if (isImgSaved) {
                task.setPicture(createdTask.getId().toString());
                mySqlTaskService.update(task);
            } else {
                mySqlTaskService.delete(createdTask.getId());
                throw new RuntimeException("image not saved");
            }
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return null;
        }
    }


    @PostMapping(value = "/create")
    @ResponseBody
    public ResponseEntity<Task> create(@RequestBody Task task) {
        try {
            Task createdTask = mySqlTaskService.create(task);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return null;
        }
    }


    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<Task> edit(@RequestBody Task task) {
        try {
            Task createdTask = mySqlTaskService.update(task);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/editImg")
    @ResponseBody
    public ResponseEntity<Task> editImg(@RequestParam("file") MultipartFile file, @RequestParam("task") String taskName, @RequestParam("description") String description, @RequestParam("status") String status, @RequestParam("id") Long id, @RequestParam("picture") String picture) {
        try {
            Task task = new Task(taskName, description, status, picture);
            task.setId(id);
            if (file.isEmpty()) throw new RuntimeException("file must not be empty");
            if (!task.getPicture().isEmpty()) {
                inMemoryPictureService.removeImg(task.getPicture());
            }
            Boolean isImgSaved = inMemoryPictureService.saveImg(file, id.toString());
            if (isImgSaved) {
                task.setPicture(id.toString());
                mySqlTaskService.update(task);
            } else {
                throw new RuntimeException("image not saved");
            }
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/findAll")
    @ResponseBody
    public List<Task> findAll() {
        try {
            return mySqlTaskService.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getImg")
    @ResponseBody
    public byte[] getImg(@RequestParam("picture") String picture) {
        return inMemoryPictureService.getImg(picture);
    }
}

