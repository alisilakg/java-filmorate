package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Event {

    private int id;
    private long timestamp;
    private int userId;
    private String eventType;
    private String operation;
    private int entityId;
}
