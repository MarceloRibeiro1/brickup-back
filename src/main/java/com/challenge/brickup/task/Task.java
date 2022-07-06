package com.challenge.brickup.task;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "task", nullable = false)
    private String task;

    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "picture")
    private String picture;

    public Task(String task, String description, String status) {
        this.task = task;
        this.description = description;
        this.status = status;
        this.picture = null;
    }

    public Task(String task, String description, String status, String picture) {
        this.task = task;
        this.description = description;
        this.status = status;
        this.picture = picture;
    }

    public Task() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
