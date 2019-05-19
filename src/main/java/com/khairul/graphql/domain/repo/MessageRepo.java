package com.khairul.graphql.domain.repo;

import com.khairul.graphql.domain.model.Message;

import java.util.List;
import java.util.Optional;

/**
 * Created by Khairul.Thamrin at 19/05/19
 */
public interface MessageRepo {
    Message save(Message message);
    Optional<Message> findById(long id);
    List<Message> findAll();
}
