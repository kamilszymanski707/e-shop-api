package pl.kamilszymanski707.eshopapi.services.discount.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import pl.kamilszymanski707.eshopapi.services.discount.exception.ResourceFoundException
import pl.kamilszymanski707.eshopapi.services.discount.exception.ResourceNotFoundException

@Configuration
@RestControllerAdvice
internal class RestControllerAdviceConfig {

    @ExceptionHandler(ResourceFoundException::class, ResourceNotFoundException::class)
    fun handle(e: RuntimeException): ErrorMessage? {
        return e.message?.let {
            ErrorMessage(arrayOf(Error(it)), null)
        }
    }

    @ExceptionHandler(Exception::class)
    fun handle(e: Exception): ErrorMessage {
        return ErrorMessage(arrayOf(Error("Internal Server Error.")), null)
    }
}

data class ErrorMessage(
    val errors: Array<Error>,
    val data: Any?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ErrorMessage

        if (!errors.contentEquals(other.errors)) return false
        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        var result = errors.contentHashCode()
        result = 31 * result + data.hashCode()
        return result
    }
}

data class Error(val message: String?)
