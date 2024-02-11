package com.initcloud.rocket23.common.enums;

import lombok.Getter;

public class BasePolicySet {

    @Getter
    public enum CSP {
        AWS1_Base_Policy_Set("AWS1_Base_Policy_Set"),
        AWS2_Base_Policy_Set("AWS2_Base_Policy_Set"),
        AWS3_Base_Policy_Set("AWS3_Base_Policy_Set"),
        NCP_Base_Policy_Set("NCP_Base_Policy_Set");

        private final String csp;

        CSP(String csp) {
            this.csp = csp;
        }

    }

    @Getter
    public enum IC{
        IC_AWS("IC_AWS"),
        IC2_AWS("IC2_AWS"),
        IC3_AWS("IC3_AWS"),
        NCP("NCP");

        private final String ic;

        IC(String ic) {
            this.ic = ic;
        }
    }


}
