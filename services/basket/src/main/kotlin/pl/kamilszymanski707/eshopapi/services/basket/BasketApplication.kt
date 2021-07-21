package pl.kamilszymanski707.eshopapi.services.basket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class BasketApplication

fun main(args: Array<String>) {
	runApplication<BasketApplication>(*args)
}
