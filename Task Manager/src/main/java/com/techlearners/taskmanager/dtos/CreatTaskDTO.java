package com.techlearners.taskmanager.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
public class CreatTaskDTO {
    String title;
    String description;
    String deadLine;
}
