package com.khairul.graphql.domain.repo;

import com.khairul.graphql.domain.model.Message;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Khairul.Thamrin at 19/05/19
 */
@Repository
public class DefaultMessageRepo implements MessageRepo {

    private Map<Long, Message> repo = new HashMap<>();

    @Override
    public Message save(Message message) {
        repo.put(message.getId(), message);
        return message;
    }

    @Override
    public Optional<Message> findById(long id) {
        return Optional.ofNullable(repo.get(id));
    }

    @Override
    public List<Message> findAll() {
        return repo.values().stream().collect(Collectors.toList());
    }
}
