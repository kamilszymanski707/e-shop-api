package pl.kamilszymanski707.eshopapi.services.catalog.config

import graphql.GraphQLException
import graphql.kickstart.spring.error.ThrowableGraphQLError
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException

@Configuration
class ExceptionHandlerConfig {

    @ExceptionHandler(GraphQLException::class, ConstraintViolationException::class)
    fun handle(e: Exception): ThrowableGraphQLError = ThrowableGraphQLError(e)

    @ExceptionHandler(RuntimeException::class)
    fun handle(e: RuntimeException): ThrowableGraphQLError =
        ThrowableGraphQLError(e, "Internal Server Error.")
}