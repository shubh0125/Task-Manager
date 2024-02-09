package com.techlearners.taskmanager.controllers;


import com.techlearners.taskmanager.dtos.CreatTaskDTO;
import com.techlearners.taskmanager.entities.TaskEntity;
import com.techlearners.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private final TaskService taskService;

    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTasks(){

        var tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getTasksById(@PathVariable("id") Integer id){

        var task = taskService.getTaskById(id);
        if(task == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreatTaskDTO body){
        var task =  taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadLine());

        return ResponseEntity.ok(task);
    }

}
