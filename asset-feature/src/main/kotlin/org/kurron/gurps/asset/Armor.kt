package org.kurron.gurps.asset

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.concurrent.ThreadLocalRandom

@Table(name = "armor", schema = "asset")
internal data class Armor(@Column("id") @Id val id: Int = 0,
                          @Column("version") @Version val version: Int = 0,
                          @Column("type") val type: String,
                          @Column("damage_resistance") val damageResistance: Int = 0,
                          @Column("cost") val cost: Int = 0,
                          @Column("weight") val weight: Int = 0,
                          @Column("created_by") @CreatedBy val createdBy: String? = null,
                          @Column("created_on") @CreatedDate val createdOn: Instant? = null,
                          @Column("modified_by") @LastModifiedBy val modifiedBy: String? = null,
                          @Column("modified_on") @LastModifiedDate val modifiedOn: Instant? = null) {
    companion object {
        fun randomInstance() = Armor(type = "unknown")
        private fun randomString() = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE).toString(16)
    }
}
