package com.initcloud.rocket23.common.enums;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Policy {

    public enum Provider {
        // CSPs
        AWS, OPENSTACK, NCP, AZURE, GCP,

        // 3rd Parties
        HASHICORP, K8S, DOCKER,

        // None
        NONE
    }

    public enum Type {
        TERRAFORM, CLOUDFORMATION,
        ANSIBLE,
        K8S, DOCKERFILE, DOCKER_COMPOSE,
        NONE
    }

    public enum Target {
        VULN, IAM,

        NONE
    }
}
