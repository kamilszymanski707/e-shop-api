package pl.kamilszymanski707.eshopapi.lib.utilslib.exception

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(NOT_FOUND)
class ResourceNotFoundException(message: String) : RuntimeException(message)