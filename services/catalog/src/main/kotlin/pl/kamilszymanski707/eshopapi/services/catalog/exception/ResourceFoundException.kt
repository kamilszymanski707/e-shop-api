package pl.kamilszymanski707.eshopapi.services.catalog.exception

import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(CONFLICT)
class ResourceFoundException(message: String) : RuntimeException(message)