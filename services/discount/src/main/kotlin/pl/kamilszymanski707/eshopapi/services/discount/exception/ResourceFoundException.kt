package pl.kamilszymanski707.eshopapi.services.discount.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class ResourceFoundException(message: String) : RuntimeException(message)