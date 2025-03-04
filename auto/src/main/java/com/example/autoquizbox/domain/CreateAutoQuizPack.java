package com.example.autoquizbox.domain;

import java.util.function.Consumer;

public interface CreateAutoQuizPack {
    void create(
            AutoTask autoTask,
            Consumer<AutoTask> callback
    );
}
