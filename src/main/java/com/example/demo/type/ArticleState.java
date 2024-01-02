package com.example.demo.type;

public enum ArticleState {
    DRAFT(0),
    PUBLISHED(1),
    DELETED(2);

    private final int state;

    ArticleState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }
}