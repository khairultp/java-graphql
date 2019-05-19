package com.khairul.graphql.domain.model;

/**
 * Created by Khairul.Thamrin at 19/05/19
 */

public class Message {

    private long id;
    private String text;

    public Message(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public static Message of(String text) {
        long randomId = System.currentTimeMillis();
        return new Message(randomId, text);
    }

    public long getId() {
        return id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
