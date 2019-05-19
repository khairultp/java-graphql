package com.khairul.graphql.service.impl;

import com.khairul.graphql.domain.model.Message;
import com.khairul.graphql.domain.model.MessageDto;
import com.khairul.graphql.domain.repo.MessageRepo;
import com.khairul.graphql.service.MessageService;
import com.khairul.graphql.service.Subscription;
import io.leangen.graphql.annotations.*;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.util.ConcurrentMultiMap;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Khairul.Thamrin at 19/05/19
 */
@GraphQLApi
@Service
public class GraphQLMessageService implements MessageService, Subscription<MessageDto> {

    private final static Long TO_ALL_MESSAGES = 1L;

    private ConcurrentMultiMap<Long, FluxSink<MessageDto>> subscribers = new ConcurrentMultiMap<>();

    private final MessageRepo messageRepo;

    public GraphQLMessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GraphQLMutation(description = "Send message and push it to all subscriber")
    @Override
    public MessageDto sendMessage(@GraphQLArgument(name = "text") @GraphQLNonNull String text) {
        final Message message = Message.of(text);
        messageRepo.save(message);

        final MessageDto dto = MessageDto.of(message, null);

        subscribers.get(TO_ALL_MESSAGES).forEach(subscriber -> subscriber.next(dto));

        return dto;
    }

    @GraphQLMutation(description = "Update message's text using by id")
    @Override
    public MessageDto updateMessage(@GraphQLArgument(name = "id") @GraphQLNonNull Long id,
                                    @GraphQLArgument(name = "newText") @GraphQLNonNull  String newText) {

        final Optional<Message> optMessage = messageRepo.findById(id);

        return optMessage.map(msg -> {

            final String prevText = msg.getText();

            msg.setText(newText);
            messageRepo.save(msg);

            final MessageDto dto = MessageDto.of(msg, prevText);

            //subcriber only for specific message id
            subscribers.get(id).forEach(subscriber -> subscriber.next(dto));

            //subscriber for all messages
            subscribers.get(TO_ALL_MESSAGES).forEach(subscriber -> subscriber.next(dto));

            return dto;

        }).orElse(MessageDto.notFound());

    }

    @GraphQLQuery(description = "Collect message by id that has been sent out")
    @Override
    public MessageDto message(@GraphQLArgument(name = "id") @GraphQLNonNull Long id) {
        return messageRepo.findById(id).map(msg -> MessageDto.of(msg, null)).orElse(MessageDto.notFound());
    }

    @GraphQLQuery(description = "Collect message that has been sent out")
    @Override
    public List<MessageDto> allMessages() {
        return messageRepo.findAll().stream().map(msg -> MessageDto.of(msg, null)).collect(Collectors.toList());
    }

    @GraphQLSubscription(description = "Retrieve messages after send at realtime")
    @Override
    public Publisher<MessageDto> subscribe(@GraphQLArgument(name = "id", defaultValue = "1") Long id) {

        return Flux.create(subscriber -> subscribers.add(id, subscriber.onDispose(() ->
                subscribers.remove(id, subscriber))),
                FluxSink.OverflowStrategy.LATEST);
    }

}
