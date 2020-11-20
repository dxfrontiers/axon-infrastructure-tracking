package de.digitalfrontiers.axon.infrastructure.persistence

import org.springframework.data.domain.Persistable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "singleton_aggregate_id")
data class SingletonAggregateId(
    @Id
    @Column(name = "aggregate_type")
    val type: String,
    @Column(name = "aggregate_id")
    val aggregateIdentifier: String
): Persistable<String> {
    override fun getId(): String? = type
    override fun isNew(): Boolean = true
}
