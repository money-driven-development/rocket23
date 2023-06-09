// package com.initcloud.rocket23.common.entity;
//
// import java.time.LocalDateTime;
//
// import javax.persistence.Column;
// import javax.persistence.EntityListeners;
// import javax.persistence.MappedSuperclass;
//
// import org.springframework.data.annotation.CreatedDate;
// import org.springframework.data.annotation.LastModifiedDate;
// import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
// import lombok.AccessLevel;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
//
// @Getter
// @EntityListeners(AuditingEntityListener.class)
// @MappedSuperclass
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// public abstract class BaseEntity {
//
// 	@CreatedDate
// 	@Column(name = "created_at")
// 	private LocalDateTime createdAt;
//
// 	@LastModifiedDate
// 	@Column(name = "modified_at")
// 	private LocalDateTime modifiedAt;
//
// 	protected BaseEntity(LocalDateTime createdAt, LocalDateTime modifiedAt) {
// 		this.createdAt = createdAt;
// 		this.modifiedAt = modifiedAt;
// 	}
// }
