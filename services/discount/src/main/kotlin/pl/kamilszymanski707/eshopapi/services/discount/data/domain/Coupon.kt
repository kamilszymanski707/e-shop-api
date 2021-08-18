package pl.kamilszymanski707.eshopapi.services.discount.data.domain

import pl.kamilszymanski707.eshopapi.services.discount.data.QueryableCoupon
import javax.persistence.*
import javax.persistence.GenerationType.SEQUENCE

@Entity
@Table(name = "coupon")
class Coupon : QueryableCoupon {

    @field:Id
    @field:Column(
        name = "id",
        nullable = false,
        unique = true,
        updatable = false)
    @field:SequenceGenerator(name = "seq_coupon",
        sequenceName = "seq_coupon",
        allocationSize = 1)
    @field:GeneratedValue(strategy = SEQUENCE,
        generator = "seq_coupon")
    override var id: Int? = null

    @field:Column(
        name = "description",
        nullable = false,
        length = 100)
    override var description: String? = null

    @field:Column(
        name = "product_id",
        nullable = false,
        unique = true,
        updatable = false)
    override var productId: String? = null

    @field:Column(
        name = "amount",
        nullable = false)
    var amount: Int? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coupon

        if (id != other.id) return false
        if (description != other.description) return false
        if (productId != other.productId) return false
        if (amount != other.amount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (productId?.hashCode() ?: 0)
        result = 31 * result + (amount ?: 0)
        return result
    }

    override fun toString(): String {
        return "Coupon(id=$id, description=$description, productId=$productId, amount=$amount)"
    }

    companion object {

        fun createInstance(
            id: Int?, description: String?,
            productId: String?, amount: Int?,
        ): Coupon {

            val result = Coupon()
            result.id = id
            result.description = description
            result.productId = productId
            result.amount = amount

            return result
        }
    }
}
