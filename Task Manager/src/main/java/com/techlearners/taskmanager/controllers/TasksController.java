package com.techlearners.taskmanager.controllers;


import com.techlearners.taskmanager.dtos.CreatTaskDTO;
import com.techlearners.taskmanager.dtos.ErrorResponseDTO;
import com.techlearners.taskmanager.dtos.TaskResponseDTO;
import com.techlearners.taskmanager.dtos.UpdateTaskDTO;
import com.techlearners.taskmanager.entities.TaskEntity;
import com.techlearners.taskmanager.service.NotesService;
import com.techlearners.taskmanager.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private final TaskService taskService;
    private final NotesService notesService;
    private ModelMapper modelMapper = new ModelMapper();

    public TasksController(TaskService taskService, NotesService notesService) {
        this.taskService = taskService;
        this.notesService = notesService;
    }

    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTasks(){

        var tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTasksById(@PathVariable("id") Integer id){

        var task = taskService.getTaskById(id);
        var notes = notesService.getNotesForTask(id);
        if(task == null){
            return ResponseEntity.notFound().build();
        }
        var taskResponse = modelMapper.map(task, TaskResponseDTO.class);
        taskResponse.setNotes(notes);
        return ResponseEntity.ok(taskResponse);
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
