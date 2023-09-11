package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.common.enums.Language;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasePolicyDetails extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BASE_POLICY_DETAILS_ID")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column
    private String description;

    @Column
    private String explanation;

    @Column
    private String possibleImpact;

    @Column
    private String solution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASE_POLICY_ID")
    private BasePolicy basePolicy;

    public BasePolicyDetails(Long id, Language language, String description, String explanation, String possibleImpact, String solution, BasePolicy basePolicy) {
        this.id = id;
        this.language = language;
        this.description = description;
        this.explanation = explanation;
        this.possibleImpact = possibleImpact;
        this.solution = solution;
        this.basePolicy = basePolicy;
    }
}
