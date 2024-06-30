package org.kurron.gurps.asset.weapon

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

@Table(name = "melee", schema = "asset")
internal data class MeleeWeapon(@Column("id") @Id val id: Int = 0,
                                @Column("version") @Version val version: Int = 0,
                                @Column("technology_level") val technologyLevel: Int = 0,
                                @Column("weapon") val weapon: String,
                                @Column("damage") val damage: String,
                                @Column("damage_type") val damageType: String,
                                @Column("cost") val cost: Int = 0,
                                @Column("weight") val weight: Int = 0,
                                @Column("strength") val strength: Int = 0,
                                @Column("notes") val notes: String = "None",
                                @Column("created_by") @CreatedBy val createdBy: String? = null,
                                @Column("created_on") @CreatedDate val createdOn: Instant? = null,
                                @Column("modified_by") @LastModifiedBy val modifiedBy: String? = null,
                                @Column("modified_on") @LastModifiedDate val modifiedOn: Instant? = null) {
    companion object {
        fun randomInstance() = MeleeWeapon(weapon = "unknown", damage = "unknown", damageType = "unknown", notes = "none")
        private fun randomString() = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE).toString(16)
    }
}
