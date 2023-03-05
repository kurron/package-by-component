package org.kurron.gurps.user

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "account", schema = "account")
data class Account(@Column("id") @Id val id: Int = 0,
                   @Column("version") @Version val version: Int = 0,
                   @Column("username") val username: String,
                   @Column("password") val password: String,
                   @Column("created_by") @CreatedBy val createdBy: String = "unknown",
                   @Column("created_on") @CreatedDate val createdOn: LocalDateTime = LocalDateTime.MIN,
                   @Column("modified_by") @LastModifiedBy val modifiedBy: String = "unknown",
                   @Column("modified_on") @LastModifiedDate val modifiedOn: LocalDateTime = LocalDateTime.MIN)
