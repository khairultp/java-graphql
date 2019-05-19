package com.khairul.graphql.configuration;

import com.khairul.graphql.service.MessageService;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Khairul.Thamrin at 19/05/19
 */
@Configuration
public class GrapQLConfig {

    @Autowired MessageService graphQLMessageService;

    @Bean
    public GraphQL graphQL() {
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        new AnnotatedResolverBuilder())
                .withOperationsFromSingleton(graphQLMessageService)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();

        return GraphQL.newGraphQL(schema).build();
    }
}
