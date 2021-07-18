package pl.kamilszymanski707.eshopapi.services.discount.data.domain

import javax.persistence.*
import javax.persistence.GenerationType.SEQUENCE

@Entity
@Table(name = "coupon")
data class Coupon(

    @Id
    @Column(
        name = "id",
        nullable = false,
        unique = true,
        updatable = false)
    @SequenceGenerator(name = "seq_coupon",
        sequenceName = "seq_coupon",
        allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,
        generator = "seq_coupon")
    val id: Int?,

    @Column(
        name = "description",
        nullable = false,
        length = 100)
    val description: String,

    @Column(
        name = "product_id",
        nullable = false,
        unique = true,
        updatable = false)
    val productId: String,

    @Column(
        name = "amount",
        nullable = false)
    val amount: Int,
)