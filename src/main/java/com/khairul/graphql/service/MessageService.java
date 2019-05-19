package com.khairul.graphql.service;

import com.khairul.graphql.domain.model.MessageDto;

import java.util.List;

/**
 * Created by Khairul.Thamrin at 19/05/19
 */
public interface MessageService {
    MessageDto sendMessage(String text);
    MessageDto updateMessage(Long id, String newText);
    MessageDto message(Long id);
    List<MessageDto> allMessages();
}
