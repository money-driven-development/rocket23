package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.policy.entity.BasePolicy;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "TAG")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @JoinColumn(name = "policy_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BasePolicy basePolicy;

    @Column(name = "default_policy_name_ic")
    private String defaultPolicyNameIc;

    @Column(name = "tag")
    @Enumerated(EnumType.STRING)
    private com.initcloud.rocket23.common.enums.Tag tag;

}