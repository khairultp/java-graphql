package com.khairul.graphql.interfaces;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Khairul.Thamrin at 19/05/19
 */
@RestController
public class MessageController {

    private GraphQL graphQL;

    public MessageController(GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @PostMapping(value = "/graphql")
    @ResponseBody
    public Map<String, Object> messageGraphQl(@RequestBody Map<String, String> request, HttpServletRequest raw) {
        final ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput()
                .query(request.get("query"))
                .operationName(request.get("operationName"))
                .context(raw)
                .build());

        return executionResult.toSpecification();
    }
}
