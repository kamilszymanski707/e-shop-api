package pl.kamilszymanski707.eshopapi.services.discount

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class DiscountApplication

fun main(args: Array<String>) {
	runApplication<DiscountApplication>(*args)
}
