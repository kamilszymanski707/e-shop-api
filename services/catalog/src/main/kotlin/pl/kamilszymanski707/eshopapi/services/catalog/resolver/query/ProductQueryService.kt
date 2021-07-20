package pl.kamilszymanski707.eshopapi.services.catalog.resolver.query

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.Product
import pl.kamilszymanski707.eshopapi.services.catalog.resolver.ProductOutput
import java.util.regex.Pattern
import java.util.regex.Pattern.CASE_INSENSITIVE
import kotlin.reflect.KVisibility.PUBLIC
import kotlin.reflect.full.memberProperties

@Service
internal class ProductQueryService(
    private val mongoTemplate: MongoTemplate,
) {

    val strictDef = arrayOf("id", "category")
    val contextDef = arrayOf("name")
    val excludedDef = arrayOf("price")

    fun getProductsByQuery(input: ProductQueryInput?): List<ProductOutput> =
        mongoTemplate.find(
            Query().also { input?.let { query -> setConditions(query, it) } }, Product::class.java).stream()
            .map { ProductOutput(it.id!!, it.name, it.category, it.price) }
            .toList()

    private fun setConditions(input: ProductQueryInput, query: Query) =
        input::class.memberProperties.forEach {
            if (
                it.visibility == PUBLIC
            ) {
                if (excludedDef.contains(it.name)) return@forEach

                val value = it.getter.call(input)
                if (value != null) {
                    if (strictDef.contains(it.name))
                        query
                            .addCriteria(
                                where(it.name)
                                    .`is`(value))

                    if (contextDef.contains(it.name))
                        query
                            .addCriteria(
                                where(it.name)
                                    .regex(Pattern.compile(value as String, CASE_INSENSITIVE)))
                }
            }
        }
}