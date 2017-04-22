package com.hailong.common.utils;

public class OperatorUtil {

    /**
     * 运营商:1联通2电信3移动
     * @param simIccid
     * @return
     */
    public static int getSimType(String simIccid) {
        if (simIccid == null || "".equals(simIccid)) {
            return 0;
        }
        if (simIccid.startsWith("898600") || simIccid.startsWith("898602"))
            return 3;
        if (simIccid.startsWith("898601") || simIccid.startsWith("898609"))
            return 1;
        if (simIccid.startsWith("898603") || simIccid.startsWith("898606"))
            return 2;

        return 0;
    }

}
