package com.example.autoquizbox.domain;

public interface AutoTaskManager {

    void addTask(AutoTask autoTask);

    void pause();

    void resume();
}
