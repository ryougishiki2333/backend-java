package com.example.demo.enums;

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

    public static ArticleState fromState(int state) {
        for (ArticleState articleState : ArticleState.values()) {
            if (articleState.getState() == state) {
                return articleState;
            }
        }
        throw new IllegalArgumentException("Invalid state: " + state);
    }
}