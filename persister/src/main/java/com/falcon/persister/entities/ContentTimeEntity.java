package com.falcon.persister.entities;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_DATA")
public class ContentTimeEntity {

	@Id
	@Column(name = "MESSAGE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_DATA")
	@SequenceGenerator(name = "SEQ_TBL_DATA", allocationSize = 5)
	private int messageId;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "MESSAGE_TS")
	private ZonedDateTime messageTime;

	@Column(name = "ROW_CREATED_TS")
	@CreationTimestamp
	private ZonedDateTime rowCreatedTime;
	
	@Column(name = "LAST_UPDATED_TS")
	@UpdateTimestamp
	private ZonedDateTime lastUpdatedTime;

}
