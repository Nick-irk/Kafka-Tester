package ru.amin.kafkatester.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageDto extends AbstractDto{

    private String name;
    private String model;

    public MessageDto(String name) {
        this.name = name;
    }

}
