package pl.kamilszymanski707.eshopapi.services.discount.data.dao

import org.springframework.stereotype.Repository
import pl.kamilszymanski707.eshopapi.services.discount.data.QueryableCoupon
import pl.kamilszymanski707.eshopapi.services.discount.data.domain.Coupon
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.*
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

@Repository
class CouponDao {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    private val strictDef = arrayOf("id", "productId")
    private val contextDef = arrayOf("description")
    private val excludedDef = arrayOf("amount")

    private val percentSign = "%"

    fun findByQuery(queryable: QueryableCoupon?): List<Coupon> {
        val cb = entityManager.criteriaBuilder
        val query: CriteriaQuery<Coupon> = cb.createQuery(Coupon::class.java)
        val coupon: Root<Coupon> = query.from(Coupon::class.java)

        var predicates: Array<Predicate> = arrayOf()
        if (queryable != null) {
            predicates = getConditions(queryable, cb, coupon).toTypedArray()
        }

        return entityManager.createQuery(query
            .where(*predicates)).resultList
    }

    private fun getConditions(
        queryable: QueryableCoupon,
        cb: CriteriaBuilder,
        coupon: Root<Coupon>,
    ): List<Predicate> {

        val predicates = ArrayList<Predicate>()

        queryable::class.memberProperties.forEach {
            if (
                it.visibility == KVisibility.PUBLIC
            ) {
                if (excludedDef.contains(it.name)) return@forEach

                val path: Path<Any> = coupon.get(it.name)

                val value = it.getter.call(queryable)
                if (value != null) {
                    if (strictDef.contains(it.name))
                        predicates.add(cb.equal(path, value))

                    if (contextDef.contains(it.name)) {
                        @Suppress("UNCHECKED_CAST")
                        predicates.add(cb.like(
                            cb.upper(path as Path<String>),
                            percentSign + (value as String).uppercase() + percentSign
                        ))
                    }
                }
            }
        }

        return predicates
    }
}