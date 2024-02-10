package com.techlearners.taskmanager.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTaskDTO {
    String description;
    String deadLine;
    Boolean completed;
}
