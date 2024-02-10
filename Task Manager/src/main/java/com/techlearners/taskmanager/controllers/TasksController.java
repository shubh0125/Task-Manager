package com.techlearners.taskmanager.controllers;


import com.techlearners.taskmanager.dtos.CreatTaskDTO;
import com.techlearners.taskmanager.dtos.ErrorResponseDTO;
import com.techlearners.taskmanager.dtos.UpdateTaskDTO;
import com.techlearners.taskmanager.entities.TaskEntity;
import com.techlearners.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreatTaskDTO body) throws ParseException {
        var task =  taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadLine());

        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer id, @RequestBody UpdateTaskDTO body) throws ParseException{
        var task = taskService.updateTask(id, body.getDescription(), body.getDeadLine(), body.getCompleted());
        if(task == null){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(task);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleErrors(Exception e){
        if(e instanceof ParseException){
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid Date Format"));
        }
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal Server Error"));
    }


}
