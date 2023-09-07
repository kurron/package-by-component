package org.kurron.gurps.campaign

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.concurrent.ThreadLocalRandom

@Table(name = "campaign", schema = "campaign")
internal data class Campaign(@Column("id") @Id val id: Int = 0,
                             @Column("version") @Version val version: Int = 0,
                             @Column("created_by") @CreatedBy val createdBy: String = "unknown",
                             @Column("created_on") @CreatedDate val createdOn: LocalDateTime = LocalDateTime.now(),
                             @Column("modified_by") @LastModifiedBy val modifiedBy: String = "unknown",
                             @Column("modified_on") @LastModifiedDate val modifiedOn: LocalDateTime = LocalDateTime.now()) {
    companion object {
        fun randomInstance() = Campaign()
        private fun randomString() = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE).toString(16)
    }
}
