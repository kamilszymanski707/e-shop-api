package pl.kamilszymanski707.eshopapi.services.catalog.data.dao

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pl.kamilszymanski707.eshopapi.services.catalog.data.QueryableProduct
import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.Product
import java.util.regex.Pattern
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

@Repository
class ProductDao(
    private val mongoTemplate: MongoTemplate,
) {

    private val strictDef = arrayOf("id", "category")
    private val contextDef = arrayOf("name")
    private val excludedDef = arrayOf("price")

    fun findByQuery(input: QueryableProduct?): List<Product> {
        return mongoTemplate.find(
            Query().also { input?.let { query -> setConditions(query, it) } }, Product::class.java)
            .toList()
    }

    private fun setConditions(
        input: QueryableProduct,
        query: Query,
    ) =
        input::class.memberProperties.forEach {
            if (
                it.visibility == KVisibility.PUBLIC
            ) {
                if (excludedDef.contains(it.name)) return@forEach

                val value = it.getter.call(input)
                if (value != null) {
                    if (strictDef.contains(it.name))
                        query
                            .addCriteria(
                                Criteria.where(it.name)
                                    .`is`(value))

                    if (contextDef.contains(it.name))
                        query
                            .addCriteria(
                                Criteria.where(it.name)
                                    .regex(Pattern.compile(value as String, Pattern.CASE_INSENSITIVE)))
                }
            }
        }
}