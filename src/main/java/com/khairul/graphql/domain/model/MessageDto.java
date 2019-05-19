package com.khairul.graphql.domain.model;

import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * Created by Khairul.Thamrin at 19/05/19
 */
public class MessageDto {

    private long id;
    private String text;
    private String prevText;

    public MessageDto(long id, String text, String prevText) {
        this.id = id;
        this.text = text;
        this.prevText = prevText;
    }

    public static MessageDto of(Message message, String prevText) {
        return new MessageDto(message.getId(), message.getText(), prevText);
    }

    public static MessageDto notFound() {
        return new MessageDto(0,null, null);
    }

    @GraphQLQuery(name = "id", description = "A message's id")
    public long getId() {
        return id;
    }

    @GraphQLQuery(name = "text", description = "A current message's text")
    public String getText() {
        return text;
    }

    @GraphQLQuery(name = "prevText", description = "A previous message's text")
    public String getPrevText() {
        return prevText;
    }
}
