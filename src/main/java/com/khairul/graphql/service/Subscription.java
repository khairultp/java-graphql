package com.khairul.graphql.service;

import org.reactivestreams.Publisher;

/**
 * Created by Khairul.Thamrin at 19/05/19
 */
public interface Subscription<T> {
    Publisher<T> subscribe(Long id);
}
