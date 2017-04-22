package com.hailong.common.utils.telecomUtil;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hailong.common.utils.HtttpRequest;







/**
 * 对字符串的加密、解密操作工具类
 * @author Administrator
 *
 */

public class DesUtils {
    
    public static final Logger logger = LoggerFactory.getLogger(DesUtils.class);
    public DesUtils() {
    }

    /**
     * DES加密
     * @param data
     * @param firstKey
     * @param secondKey
     * @param thirdKey
     * @return
     */
    public String strEnc(String data,String firstKey,String secondKey,String thirdKey) {
        int leng = data.length();
        String encData = "";
        List firstKeyBt = null, secondKeyBt = null, thirdKeyBt = null;
        int firstLength = 0, secondLength = 0, thirdLength = 0;
        if (firstKey != null && firstKey != "") {
            firstKeyBt = getKeyBytes(firstKey);
            firstLength = firstKeyBt.size();
        }
        if (secondKey != null && secondKey != "") {
            secondKeyBt = getKeyBytes(secondKey);
            secondLength = secondKeyBt.size();
        }
        if (thirdKey != null && thirdKey != "") {
            thirdKeyBt = getKeyBytes(thirdKey);
            thirdLength = thirdKeyBt.size();
        }

        if (leng > 0) {
            if (leng < 4) {
                int[] bt = strToBt(data);
                int[] encByte = null;
                if (firstKey != null && firstKey != "" && secondKey != null
                        && secondKey != "" && thirdKey != null
                        && thirdKey != "") {
                    int[] tempBt;
                    int x, y, z;
                    tempBt = bt;
                    for (x = 0; x < firstLength; x++) {
                        tempBt = enc(tempBt, (int[]) firstKeyBt.get(x));
                    }
                    for (y = 0; y < secondLength; y++) {
                        tempBt = enc(tempBt, (int[]) secondKeyBt.get(y));
                    }
                    for (z = 0; z < thirdLength; z++) {
                        tempBt = enc(tempBt, (int[]) thirdKeyBt.get(z));
                    }
                    encByte = tempBt;
                } else {
                    if (firstKey != null && firstKey != "" && secondKey != null
                            && secondKey != "") {
                        int[] tempBt;
                        int x, y;
                        tempBt = bt;
                        for (x = 0; x < firstLength; x++) {
                            tempBt = enc(tempBt, (int[]) firstKeyBt.get(x));
                        }
                        for (y = 0; y < secondLength; y++) {
                            tempBt = enc(tempBt, (int[]) secondKeyBt.get(y));
                        }
                        encByte = tempBt;
                    } else {
                        if (firstKey != null && firstKey != "") {
                            int[] tempBt;
                            int x = 0;
                            tempBt = bt;
                            for (x = 0; x < firstLength; x++) {
                                tempBt = enc(tempBt, (int[]) firstKeyBt.get(x));
                            }
                            encByte = tempBt;
                        }
                    }
                }
                encData = bt64ToHex(encByte);
            } else {
                int iterator = (leng / 4);
                int remainder = leng % 4;
                int i = 0;
                for (i = 0; i < iterator; i++) {
                    String tempData = data.substring(i * 4 + 0, i * 4 + 4);
                    int[] tempByte = strToBt(tempData);
                    int[] encByte = null;
                    if (firstKey != null && firstKey != "" && secondKey != null
                            && secondKey != "" && thirdKey != null
                            && thirdKey != "") {
                        int[] tempBt;
                        int x, y, z;
                        tempBt = tempByte;
                        for (x = 0; x < firstLength; x++) {
                            tempBt = enc(tempBt, (int[]) firstKeyBt.get(x));
                        }
                        for (y = 0; y < secondLength; y++) {
                            tempBt = enc(tempBt, (int[]) secondKeyBt.get(y));
                        }
                        for (z = 0; z < thirdLength; z++) {
                            tempBt = enc(tempBt, (int[]) thirdKeyBt.get(z));
                        }
                        encByte = tempBt;
                    } else {
                        if (firstKey != null && firstKey != ""
                                && secondKey != null && secondKey != "") {
                            int[] tempBt;
                            int x, y;
                            tempBt = tempByte;
                            for (x = 0; x < firstLength; x++) {
                                tempBt = enc(tempBt, (int[]) firstKeyBt.get(x));
                            }
                            for (y = 0; y < secondLength; y++) {
                                tempBt = enc(tempBt, (int[]) secondKeyBt.get(y));
                            }
                            encByte = tempBt;
                        } else {
                            if (firstKey != null && firstKey != "") {
                                int[] tempBt;
                                int x;
                                tempBt = tempByte;
                                for (x = 0; x < firstLength; x++) {
                                    tempBt = enc(tempBt, (int[]) firstKeyBt
                                            .get(x));
                                }
                                encByte = tempBt;
                            }
                        }
                    }
                    encData += bt64ToHex(encByte);
                }
                if (remainder > 0) {
                    String remainderData = data.substring(iterator * 4 + 0,
                            leng);
                    int[] tempByte = strToBt(remainderData);
                    int[] encByte = null;
                    if (firstKey != null && firstKey != "" && secondKey != null
                            && secondKey != "" && thirdKey != null
                            && thirdKey != "") {
                        int[] tempBt;
                        int x, y, z;
                        tempBt = tempByte;
                        for (x = 0; x < firstLength; x++) {
                            tempBt = enc(tempBt, (int[]) firstKeyBt.get(x));
                        }
                        for (y = 0; y < secondLength; y++) {
                            tempBt = enc(tempBt, (int[]) secondKeyBt.get(y));
                        }
                        for (z = 0; z < thirdLength; z++) {
                            tempBt = enc(tempBt, (int[]) thirdKeyBt.get(z));
                        }
                        encByte = tempBt;
                    } else {
                        if (firstKey != null && firstKey != ""
                                && secondKey != null && secondKey != "") {
                            int[] tempBt;
                            int x, y;
                            tempBt = tempByte;
                            for (x = 0; x < firstLength; x++) {
                                tempBt = enc(tempBt, (int[]) firstKeyBt.get(x));
                            }
                            for (y = 0; y < secondLength; y++) {
                                tempBt = enc(tempBt, (int[]) secondKeyBt.get(y));
                            }
                            encByte = tempBt;
                        } else {
                            if (firstKey != null && firstKey != "") {
                                int[] tempBt;
                                int x;
                                tempBt = tempByte;
                                for (x = 0; x < firstLength; x++) {
                                    tempBt = enc(tempBt, (int[]) firstKeyBt
                                            .get(x));
                                }
                                encByte = tempBt;
                            }
                        }
                    }
                    encData += bt64ToHex(encByte);
                }
            }
        }
        return encData;
    }


    /**
     * DES解密
     * @param data
     * @param firstKey
     * @param secondKey
     * @param thirdKey
     * @return
     */
    public String strDec(String data,String firstKey,String secondKey,String thirdKey) {
        int leng = data.length();
        String decStr = "";
        List firstKeyBt = null, secondKeyBt = null, thirdKeyBt = null;
        int firstLength = 0, secondLength = 0, thirdLength = 0;
        if (firstKey != null && firstKey != "") {
            firstKeyBt = getKeyBytes(firstKey);
            firstLength = firstKeyBt.size();
        }
        if (secondKey != null && secondKey != "") {
            secondKeyBt = getKeyBytes(secondKey);
            secondLength = secondKeyBt.size();
        }
        if (thirdKey != null && thirdKey != "") {
            thirdKeyBt = getKeyBytes(thirdKey);
            thirdLength = thirdKeyBt.size();
        }

        int iterator = leng / 16;
        int i = 0;
        for (i = 0; i < iterator; i++) {
            String tempData = data.substring(i * 16 + 0, i * 16 + 16);
            String strByte = hexToBt64(tempData);
            int[] intByte = new int[64];
            int j = 0;
            for (j = 0; j < 64; j++) {
                intByte[j] = Integer.parseInt(strByte.substring(j, j + 1));
            }
            int[] decByte = null;
            if (firstKey != null && firstKey != "" && secondKey != null
                    && secondKey != "" && thirdKey != null && thirdKey != "") {
                int[] tempBt;
                int x, y, z;
                tempBt = intByte;
                for (x = thirdLength - 1; x >= 0; x--) {
                    tempBt = dec(tempBt, (int[]) thirdKeyBt.get(x));
                }
                for (y = secondLength - 1; y >= 0; y--) {
                    tempBt = dec(tempBt, (int[]) secondKeyBt.get(y));
                }
                for (z = firstLength - 1; z >= 0; z--) {
                    tempBt = dec(tempBt, (int[]) firstKeyBt.get(z));
                }
                decByte = tempBt;
            } else {
                if (firstKey != null && firstKey != "" && secondKey != null
                        && secondKey != "") {
                    int[] tempBt;
                    int x, y, z;
                    tempBt = intByte;
                    for (x = secondLength - 1; x >= 0; x--) {
                        tempBt = dec(tempBt, (int[]) secondKeyBt.get(x));
                    }
                    for (y = firstLength - 1; y >= 0; y--) {
                        tempBt = dec(tempBt, (int[]) firstKeyBt.get(y));
                    }
                    decByte = tempBt;
                } else {
                    if (firstKey != null && firstKey != "") {
                        int[] tempBt;
                        int x, y, z;
                        tempBt = intByte;
                        for (x = firstLength - 1; x >= 0; x--) {
                            tempBt = dec(tempBt, (int[]) firstKeyBt.get(x));
                        }
                        decByte = tempBt;
                    }
                }
            }
            decStr += byteToString(decByte);
        }
        return decStr;
    }

    /*
     * chang the string into the bit array
     * 
     * return bit array(it's length % 64 = 0)
     */
    public List getKeyBytes(String key) {
        List keyBytes = new ArrayList();
        int leng = key.length();
        int iterator = (leng / 4);
        int remainder = leng % 4;
        int i = 0;
        for (i = 0; i < iterator; i++) {
            keyBytes.add(i, strToBt(key.substring(i * 4 + 0, i * 4 + 4)));
        }
        if (remainder > 0) {
            // keyBytes[i] = strToBt(key.substring(i*4+0,leng));
            keyBytes.add(i, strToBt(key.substring(i * 4 + 0, leng)));
        }
        return keyBytes;
    }

    /*
     * chang the string(it's length <= 4) into the bit array
     * 
     * return bit array(it's length = 64)
     */
    public int[] strToBt(String str) {
        int leng = str.length();
        int[] bt = new int[64];
        if (leng < 4) {
            int i = 0, j = 0, p = 0, q = 0;
            for (i = 0; i < leng; i++) {
                int k = str.charAt(i);
                for (j = 0; j < 16; j++) {
                    int pow = 1, m = 0;
                    for (m = 15; m > j; m--) {
                        pow *= 2;
                    }
                    // bt.set(16*i+j,""+(k/pow)%2));
                    bt[16 * i + j] = (k / pow) % 2;
                }
            }
            for (p = leng; p < 4; p++) {
                int k = 0;
                for (q = 0; q < 16; q++) {
                    int pow = 1, m = 0;
                    for (m = 15; m > q; m--) {
                        pow *= 2;
                    }
                    // bt[16*p+q]=parseInt(k/pow)%2;
                    // bt.add(16*p+q,""+((k/pow)%2));
                    bt[16 * p + q] = (k / pow) % 2;
                }
            }
        } else {
            for (int i = 0; i < 4; i++) {
                int k = str.charAt(i);
                for (int j = 0; j < 16; j++) {
                    int pow = 1;
                    for (int m = 15; m > j; m--) {
                        pow *= 2;
                    }
                    // bt[16*i+j]=parseInt(k/pow)%2;
                    // bt.add(16*i+j,""+((k/pow)%2));
                    bt[16 * i + j] = (k / pow) % 2;
                }
            }
        }
        return bt;
    }

    /*
     * chang the bit(it's length = 4) into the hex
     * 
     * return hex
     */
    public String bt4ToHex(String binary) {
        String hex = "";
        if (binary.equalsIgnoreCase("0000")) {
            hex = "0";
        } else if (binary.equalsIgnoreCase("0001")) {
            hex = "1";
        } else if (binary.equalsIgnoreCase("0010")) {
            hex = "2";
        } else if (binary.equalsIgnoreCase("0011")) {
            hex = "3";
        } else if (binary.equalsIgnoreCase("0100")) {
            hex = "4";
        } else if (binary.equalsIgnoreCase("0101")) {
            hex = "5";
        } else if (binary.equalsIgnoreCase("0110")) {
            hex = "6";
        } else if (binary.equalsIgnoreCase("0111")) {
            hex = "7";
        } else if (binary.equalsIgnoreCase("1000")) {
            hex = "8";
        } else if (binary.equalsIgnoreCase("1001")) {
            hex = "9";
        } else if (binary.equalsIgnoreCase("1010")) {
            hex = "A";
        } else if (binary.equalsIgnoreCase("1011")) {
            hex = "B";
        } else if (binary.equalsIgnoreCase("1100")) {
            hex = "C";
        } else if (binary.equalsIgnoreCase("1101")) {
            hex = "D";
        } else if (binary.equalsIgnoreCase("1110")) {
            hex = "E";
        } else if (binary.equalsIgnoreCase("1111")) {
            hex = "F";
        }

        return hex;
    }

    /*
     * chang the hex into the bit(it's length = 4)
     * 
     * return the bit(it's length = 4)
     */
    public String hexToBt4(String hex) {
        String binary = "";
        if (hex.equalsIgnoreCase("0")) {
            binary = "0000";
        } else if (hex.equalsIgnoreCase("1")) {
            binary = "0001";
        }
        if (hex.equalsIgnoreCase("2")) {
            binary = "0010";
        }
        if (hex.equalsIgnoreCase("3")) {
            binary = "0011";
        }
        if (hex.equalsIgnoreCase("4")) {
            binary = "0100";
        }
        if (hex.equalsIgnoreCase("5")) {
            binary = "0101";
        }
        if (hex.equalsIgnoreCase("6")) {
            binary = "0110";
        }
        if (hex.equalsIgnoreCase("7")) {
            binary = "0111";
        }
        if (hex.equalsIgnoreCase("8")) {
            binary = "1000";
        }
        if (hex.equalsIgnoreCase("9")) {
            binary = "1001";
        }
        if (hex.equalsIgnoreCase("A")) {
            binary = "1010";
        }
        if (hex.equalsIgnoreCase("B")) {
            binary = "1011";
        }
        if (hex.equalsIgnoreCase("C")) {
            binary = "1100";
        }
        if (hex.equalsIgnoreCase("D")) {
            binary = "1101";
        }
        if (hex.equalsIgnoreCase("E")) {
            binary = "1110";
        }
        if (hex.equalsIgnoreCase("F")) {
            binary = "1111";
        }
        return binary;
    }

    /*
     * chang the bit(it's length = 64) into the string
     * 
     * return string
     */
    public String byteToString(int[] byteData) {
        String str = "";
        for (int i = 0; i < 4; i++) {
            int count = 0;
            for (int j = 0; j < 16; j++) {
                int pow = 1;
                for (int m = 15; m > j; m--) {
                    pow *= 2;
                }
                count += byteData[16 * i + j] * pow;
            }
            if (count != 0) {
                str += "" + (char) (count);
            }
        }
        return str;
    }

    public String bt64ToHex(int[] byteData) {
        String hex = "";
        for (int i = 0; i < 16; i++) {
            String bt = "";
            for (int j = 0; j < 4; j++) {
                bt += byteData[i * 4 + j];
            }
            hex += bt4ToHex(bt);
        }
        return hex;
    }

    public String hexToBt64(String hex) {
        String binary = "";
        for (int i = 0; i < 16; i++) {
            binary += hexToBt4(hex.substring(i, i + 1));
        }
        return binary;
    }

    /*
     * the 64 bit des core arithmetic
     */

    public int[] enc(int[] dataByte, int[] keyByte) {
        int[][] keys = generateKeys(keyByte);
        int[] ipByte = initPermute(dataByte);
        int[] ipLeft = new int[32];
        int[] ipRight = new int[32];
        int[] tempLeft = new int[32];
        int i = 0, j = 0, k = 0, m = 0, n = 0;
        for (k = 0; k < 32; k++) {
            ipLeft[k] = ipByte[k];
            ipRight[k] = ipByte[32 + k];
        }
        for (i = 0; i < 16; i++) {
            for (j = 0; j < 32; j++) {
                tempLeft[j] = ipLeft[j];
                ipLeft[j] = ipRight[j];
            }
            int[] key = new int[48];
            for (m = 0; m < 48; m++) {
                key[m] = keys[i][m];
            }
            int[] tempRight = xor(pPermute(sBoxPermute(xor(
                    expandPermute(ipRight), key))), tempLeft);
            for (n = 0; n < 32; n++) {
                ipRight[n] = tempRight[n];
            }

        }

        int[] finalData = new int[64];
        for (i = 0; i < 32; i++) {
            finalData[i] = ipRight[i];
            finalData[32 + i] = ipLeft[i];
        }
        return finallyPermute(finalData);
    }

    public int[] dec(int[] dataByte, int[] keyByte) {
        int[][] keys = generateKeys(keyByte);
        int[] ipByte = initPermute(dataByte);
        int[] ipLeft = new int[32];
        int[] ipRight = new int[32];
        int[] tempLeft = new int[32];
        int i = 0, j = 0, k = 0, m = 0, n = 0;
        for (k = 0; k < 32; k++) {
            ipLeft[k] = ipByte[k];
            ipRight[k] = ipByte[32 + k];
        }
        for (i = 15; i >= 0; i--) {
            for (j = 0; j < 32; j++) {
                tempLeft[j] = ipLeft[j];
                ipLeft[j] = ipRight[j];
            }
            int[] key = new int[48];
            for (m = 0; m < 48; m++) {
                key[m] = keys[i][m];
            }

            int[] tempRight = xor(pPermute(sBoxPermute(xor(
                    expandPermute(ipRight), key))), tempLeft);
            for (n = 0; n < 32; n++) {
                ipRight[n] = tempRight[n];
            }
        }

        int[] finalData = new int[64];
        for (i = 0; i < 32; i++) {
            finalData[i] = ipRight[i];
            finalData[32 + i] = ipLeft[i];
        }
        return finallyPermute(finalData);
    }

    public int[] initPermute(int[] originalData) {
        int[] ipByte = new int[64];
        int i = 0, m = 1, n = 0, j, k;
        for (i = 0, m = 1, n = 0; i < 4; i++, m += 2, n += 2) {
            for (j = 7, k = 0; j >= 0; j--, k++) {
                ipByte[i * 8 + k] = originalData[j * 8 + m];
                ipByte[i * 8 + k + 32] = originalData[j * 8 + n];
            }
        }
        return ipByte;
    }

    public int[] expandPermute(int[] rightData) {
        int[] epByte = new int[48];
        int i, j;
        for (i = 0; i < 8; i++) {
            if (i == 0) {
                epByte[i * 6 + 0] = rightData[31];
            } else {
                epByte[i * 6 + 0] = rightData[i * 4 - 1];
            }
            epByte[i * 6 + 1] = rightData[i * 4 + 0];
            epByte[i * 6 + 2] = rightData[i * 4 + 1];
            epByte[i * 6 + 3] = rightData[i * 4 + 2];
            epByte[i * 6 + 4] = rightData[i * 4 + 3];
            if (i == 7) {
                epByte[i * 6 + 5] = rightData[0];
            } else {
                epByte[i * 6 + 5] = rightData[i * 4 + 4];
            }
        }
        return epByte;
    }

    public int[] xor(int[] byteOne, int[] byteTwo) {
        // var xorByte = new Array(byteOne.length);
        // for(int i = 0;i < byteOne.length; i ++){
        // xorByte[i] = byteOne[i] ^ byteTwo[i];
        // }
        // return xorByte;
        int[] xorByte = new int[byteOne.length];
        for (int i = 0; i < byteOne.length; i++) {
            xorByte[i] = byteOne[i] ^ byteTwo[i];
        }
        return xorByte;
    }

    public int[] sBoxPermute(int[] expandByte) {

        // var sBoxByte = new Array(32);
        int[] sBoxByte = new int[32];
        String binary = "";
        int[][] s1 = {
                { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } };

        /* Table - s2 */
        int[][] s2 = {
                { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } };

        /* Table - s3 */
        int[][] s3 = {
                { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } };
        /* Table - s4 */
        int[][] s4 = {
                { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } };

        /* Table - s5 */
        int[][] s5 = {
                { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } };

        /* Table - s6 */
        int[][] s6 = {
                { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } };

        /* Table - s7 */
        int[][] s7 = {
                { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } };

        /* Table - s8 */
        int[][] s8 = {
                { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } };

        for (int m = 0; m < 8; m++) {
            int i = 0, j = 0;
            i = expandByte[m * 6 + 0] * 2 + expandByte[m * 6 + 5];
            j = expandByte[m * 6 + 1] * 2 * 2 * 2 + expandByte[m * 6 + 2] * 2
                    * 2 + expandByte[m * 6 + 3] * 2 + expandByte[m * 6 + 4];
            switch (m) {
            case 0:
                binary = getBoxBinary(s1[i][j]);
                break;
            case 1:
                binary = getBoxBinary(s2[i][j]);
                break;
            case 2:
                binary = getBoxBinary(s3[i][j]);
                break;
            case 3:
                binary = getBoxBinary(s4[i][j]);
                break;
            case 4:
                binary = getBoxBinary(s5[i][j]);
                break;
            case 5:
                binary = getBoxBinary(s6[i][j]);
                break;
            case 6:
                binary = getBoxBinary(s7[i][j]);
                break;
            case 7:
                binary = getBoxBinary(s8[i][j]);
                break;
            }
            sBoxByte[m * 4 + 0] = Integer.parseInt(binary.substring(0, 1));
            sBoxByte[m * 4 + 1] = Integer.parseInt(binary.substring(1, 2));
            sBoxByte[m * 4 + 2] = Integer.parseInt(binary.substring(2, 3));
            sBoxByte[m * 4 + 3] = Integer.parseInt(binary.substring(3, 4));
        }
        return sBoxByte;
    }

    public int[] pPermute(int[] sBoxByte) {
        int[] pBoxPermute = new int[32];
        pBoxPermute[0] = sBoxByte[15];
        pBoxPermute[1] = sBoxByte[6];
        pBoxPermute[2] = sBoxByte[19];
        pBoxPermute[3] = sBoxByte[20];
        pBoxPermute[4] = sBoxByte[28];
        pBoxPermute[5] = sBoxByte[11];
        pBoxPermute[6] = sBoxByte[27];
        pBoxPermute[7] = sBoxByte[16];
        pBoxPermute[8] = sBoxByte[0];
        pBoxPermute[9] = sBoxByte[14];
        pBoxPermute[10] = sBoxByte[22];
        pBoxPermute[11] = sBoxByte[25];
        pBoxPermute[12] = sBoxByte[4];
        pBoxPermute[13] = sBoxByte[17];
        pBoxPermute[14] = sBoxByte[30];
        pBoxPermute[15] = sBoxByte[9];
        pBoxPermute[16] = sBoxByte[1];
        pBoxPermute[17] = sBoxByte[7];
        pBoxPermute[18] = sBoxByte[23];
        pBoxPermute[19] = sBoxByte[13];
        pBoxPermute[20] = sBoxByte[31];
        pBoxPermute[21] = sBoxByte[26];
        pBoxPermute[22] = sBoxByte[2];
        pBoxPermute[23] = sBoxByte[8];
        pBoxPermute[24] = sBoxByte[18];
        pBoxPermute[25] = sBoxByte[12];
        pBoxPermute[26] = sBoxByte[29];
        pBoxPermute[27] = sBoxByte[5];
        pBoxPermute[28] = sBoxByte[21];
        pBoxPermute[29] = sBoxByte[10];
        pBoxPermute[30] = sBoxByte[3];
        pBoxPermute[31] = sBoxByte[24];
        return pBoxPermute;
    }

    public int[] finallyPermute(int[] endByte) {
        int[] fpByte = new int[64];
        fpByte[0] = endByte[39];
        fpByte[1] = endByte[7];
        fpByte[2] = endByte[47];
        fpByte[3] = endByte[15];
        fpByte[4] = endByte[55];
        fpByte[5] = endByte[23];
        fpByte[6] = endByte[63];
        fpByte[7] = endByte[31];
        fpByte[8] = endByte[38];
        fpByte[9] = endByte[6];
        fpByte[10] = endByte[46];
        fpByte[11] = endByte[14];
        fpByte[12] = endByte[54];
        fpByte[13] = endByte[22];
        fpByte[14] = endByte[62];
        fpByte[15] = endByte[30];
        fpByte[16] = endByte[37];
        fpByte[17] = endByte[5];
        fpByte[18] = endByte[45];
        fpByte[19] = endByte[13];
        fpByte[20] = endByte[53];
        fpByte[21] = endByte[21];
        fpByte[22] = endByte[61];
        fpByte[23] = endByte[29];
        fpByte[24] = endByte[36];
        fpByte[25] = endByte[4];
        fpByte[26] = endByte[44];
        fpByte[27] = endByte[12];
        fpByte[28] = endByte[52];
        fpByte[29] = endByte[20];
        fpByte[30] = endByte[60];
        fpByte[31] = endByte[28];
        fpByte[32] = endByte[35];
        fpByte[33] = endByte[3];
        fpByte[34] = endByte[43];
        fpByte[35] = endByte[11];
        fpByte[36] = endByte[51];
        fpByte[37] = endByte[19];
        fpByte[38] = endByte[59];
        fpByte[39] = endByte[27];
        fpByte[40] = endByte[34];
        fpByte[41] = endByte[2];
        fpByte[42] = endByte[42];
        fpByte[43] = endByte[10];
        fpByte[44] = endByte[50];
        fpByte[45] = endByte[18];
        fpByte[46] = endByte[58];
        fpByte[47] = endByte[26];
        fpByte[48] = endByte[33];
        fpByte[49] = endByte[1];
        fpByte[50] = endByte[41];
        fpByte[51] = endByte[9];
        fpByte[52] = endByte[49];
        fpByte[53] = endByte[17];
        fpByte[54] = endByte[57];
        fpByte[55] = endByte[25];
        fpByte[56] = endByte[32];
        fpByte[57] = endByte[0];
        fpByte[58] = endByte[40];
        fpByte[59] = endByte[8];
        fpByte[60] = endByte[48];
        fpByte[61] = endByte[16];
        fpByte[62] = endByte[56];
        fpByte[63] = endByte[24];
        return fpByte;
    }

    public String getBoxBinary(int i) {
        String binary = "";
        switch (i) {
        case 0:
            binary = "0000";
            break;
        case 1:
            binary = "0001";
            break;
        case 2:
            binary = "0010";
            break;
        case 3:
            binary = "0011";
            break;
        case 4:
            binary = "0100";
            break;
        case 5:
            binary = "0101";
            break;
        case 6:
            binary = "0110";
            break;
        case 7:
            binary = "0111";
            break;
        case 8:
            binary = "1000";
            break;
        case 9:
            binary = "1001";
            break;
        case 10:
            binary = "1010";
            break;
        case 11:
            binary = "1011";
            break;
        case 12:
            binary = "1100";
            break;
        case 13:
            binary = "1101";
            break;
        case 14:
            binary = "1110";
            break;
        case 15:
            binary = "1111";
            break;
        }
        return binary;
    }

    /*
     * generate 16 keys for xor
     * 
     */
    public int[][] generateKeys(int[] keyByte) {
        int[] key = new int[56];
        int[][] keys = new int[16][48];

        // keys[ 0] = new Array();
        // keys[ 1] = new Array();
        // keys[ 2] = new Array();
        // keys[ 3] = new Array();
        // keys[ 4] = new Array();
        // keys[ 5] = new Array();
        // keys[ 6] = new Array();
        // keys[ 7] = new Array();
        // keys[ 8] = new Array();
        // keys[ 9] = new Array();
        // keys[10] = new Array();
        // keys[11] = new Array();
        // keys[12] = new Array();
        // keys[13] = new Array();
        // keys[14] = new Array();
        // keys[15] = new Array();
        int[] loop = new int[] { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

        for (int i = 0; i < 7; i++) {
            for (int j = 0, k = 7; j < 8; j++, k--) {
                key[i * 8 + j] = keyByte[8 * k + i];
            }
        }

        int i = 0;
        for (i = 0; i < 16; i++) {
            int tempLeft = 0;
            int tempRight = 0;
            for (int j = 0; j < loop[i]; j++) {
                tempLeft = key[0];
                tempRight = key[28];
                for (int k = 0; k < 27; k++) {
                    key[k] = key[k + 1];
                    key[28 + k] = key[29 + k];
                }
                key[27] = tempLeft;
                key[55] = tempRight;
            }
            // var tempKey = new Array(48);
            int[] tempKey = new int[48];
            tempKey[0] = key[13];
            tempKey[1] = key[16];
            tempKey[2] = key[10];
            tempKey[3] = key[23];
            tempKey[4] = key[0];
            tempKey[5] = key[4];
            tempKey[6] = key[2];
            tempKey[7] = key[27];
            tempKey[8] = key[14];
            tempKey[9] = key[5];
            tempKey[10] = key[20];
            tempKey[11] = key[9];
            tempKey[12] = key[22];
            tempKey[13] = key[18];
            tempKey[14] = key[11];
            tempKey[15] = key[3];
            tempKey[16] = key[25];
            tempKey[17] = key[7];
            tempKey[18] = key[15];
            tempKey[19] = key[6];
            tempKey[20] = key[26];
            tempKey[21] = key[19];
            tempKey[22] = key[12];
            tempKey[23] = key[1];
            tempKey[24] = key[40];
            tempKey[25] = key[51];
            tempKey[26] = key[30];
            tempKey[27] = key[36];
            tempKey[28] = key[46];
            tempKey[29] = key[54];
            tempKey[30] = key[29];
            tempKey[31] = key[39];
            tempKey[32] = key[50];
            tempKey[33] = key[44];
            tempKey[34] = key[32];
            tempKey[35] = key[47];
            tempKey[36] = key[43];
            tempKey[37] = key[48];
            tempKey[38] = key[38];
            tempKey[39] = key[55];
            tempKey[40] = key[33];
            tempKey[41] = key[52];
            tempKey[42] = key[45];
            tempKey[43] = key[41];
            tempKey[44] = key[49];
            tempKey[45] = key[35];
            tempKey[46] = key[28];
            tempKey[47] = key[31];
            int m;
            switch (i) {
            case 0:
                for (m = 0; m < 48; m++) {
                    keys[0][m] = tempKey[m];
                }
                break;
            case 1:
                for (m = 0; m < 48; m++) {
                    keys[1][m] = tempKey[m];
                }
                break;
            case 2:
                for (m = 0; m < 48; m++) {
                    keys[2][m] = tempKey[m];
                }
                break;
            case 3:
                for (m = 0; m < 48; m++) {
                    keys[3][m] = tempKey[m];
                }
                break;
            case 4:
                for (m = 0; m < 48; m++) {
                    keys[4][m] = tempKey[m];
                }
                break;
            case 5:
                for (m = 0; m < 48; m++) {
                    keys[5][m] = tempKey[m];
                }
                break;
            case 6:
                for (m = 0; m < 48; m++) {
                    keys[6][m] = tempKey[m];
                }
                break;
            case 7:
                for (m = 0; m < 48; m++) {
                    keys[7][m] = tempKey[m];
                }
                break;
            case 8:
                for (m = 0; m < 48; m++) {
                    keys[8][m] = tempKey[m];
                }
                break;
            case 9:
                for (m = 0; m < 48; m++) {
                    keys[9][m] = tempKey[m];
                }
                break;
            case 10:
                for (m = 0; m < 48; m++) {
                    keys[10][m] = tempKey[m];
                }
                break;
            case 11:
                for (m = 0; m < 48; m++) {
                    keys[11][m] = tempKey[m];
                }
                break;
            case 12:
                for (m = 0; m < 48; m++) {
                    keys[12][m] = tempKey[m];
                }
                break;
            case 13:
                for (m = 0; m < 48; m++) {
                    keys[13][m] = tempKey[m];
                }
                break;
            case 14:
                for (m = 0; m < 48; m++) {
                    keys[14][m] = tempKey[m];
                }
                break;
            case 15:
                for (m = 0; m < 48; m++) {
                    keys[15][m] = tempKey[m];
                }
                break;
            }
        }
        return keys;
    }
    
    
	/**
	 * String[]数据各个元素的自然排序后拼接成一个用逗号分隔的字符串
	 * @param strArray
	 * @return
	 */
	public static String naturalOrdering(String[] strArray){
		String resultStr = "";
		if(strArray != null && strArray.length > 0){
			Arrays.sort(strArray); 
			for(int i=0;i<strArray.length;i++){
	        	resultStr += i==0 ? strArray[i] : ","+strArray[i];
	        }
		}
		return resultStr;
	}
    /**
     * 获取加密后的 sign
     * @param number
     * @param user_id
     * @param password
     * @param interfaceName
     * @return
     */
	public static String getSign(String number,String user_id,String password,String interfaceName){
		String[] arr = {number,user_id,password,interfaceName};
		String key1 = "274";
    	String key2 = "917";
    	String key3 = "t5r";
    	DesUtils des = new DesUtils();
		String sign = des.strEnc(DesUtils.naturalOrdering(arr),key1,key2,key3); //sign加密
		return sign;
	}
	/**
	 * 获取加密后 的密码
	 * @param password
	 * @return
	 */
	public static String getpassWord(String password){
		String key1 = "274";
		String key2 = "917";
		String key3 = "t5r";
		DesUtils des = new DesUtils();
		String passwordEnc = des.strEnc(password,key1,key2,key3);  //密码加密 
		return passwordEnc;
	}
	//查询套餐已使用量   M
	public static String  queryPakage(String iccid){
		//String url=ApplicationProperties.get("dianxin_url");
//		String userId=ApplicationProperties.get("dianxin_url");
//		String pass=ApplicationProperties.get("dianxin_url");
		String[] arr = {iccid,"gzmzgg","gzmz6666","queryPakage"};
		String key1 = "274";
    	String key2 = "917";
    	String key3 = "t5r";
    	DesUtils des = new DesUtils();
		String sign = des.strEnc(DesUtils.naturalOrdering(arr),key1,key2,key3); //sign加密
		String passwordEnc = des.strEnc("gzmz6666",key1,key2,key3);  //密码加密
		StringBuilder sbUrl=new StringBuilder("http://221.228.17.175:1721/m2m_ec/query.do");
		sbUrl.append("?method=queryPakage&userId=gzmzgg&passWord="+passwordEnc+"&iccid="+iccid+"&sign="+sign);
//		String res=HtttpRequest.getHttpRequest("http://221.228.17.175:1721/m2m_ec/query.do?method=queryPakage&userId=gzmzgg&passWord=63DA4B299B2CD475A6E95632F4AF9BED&iccid=8986031540252055237&sign=8652A79045C5EBB534588B2C8F9A59C277D51D8374CAD5D1D6FC21C8848A7F178DC33F4FE249A27D63DA4B299B2CD475A6E95632F4AF9BED9AA5DE5D70F67539900BFB33466C88B67EFC80340F507D708E152276D0F1C726D76EE18246C07073");
		String res=HtttpRequest.getHttpRequest(sbUrl.toString());
		//解析xml  取出套餐已使用流量
		BigDecimal count=new BigDecimal(0);
		if(!"".equals(res)&&!res.equals("-1")&&!res.equals("-2")&&!res.equals("-3")&&!res.equals("-4")){
			try {
				Document doc = DocumentHelper.parseText(res);
				Element root = doc.getRootElement();
				List list= root.elements();
				Element secRoot =null;
				 if(list!=null&&list.size()>0){
					 secRoot=(Element)list.get(0);
				 }
			     List pacList=null;
			     if(secRoot!=null){
			    	 pacList=secRoot.elements("CumulRspList");
			     }
			     if(pacList!=null){
			     for(int i=0;i<pacList.size();i++){
			    	 Element ele=(Element) pacList.get(i);
			    	 for(Iterator iter = ele.elementIterator(); iter.hasNext(); ) {
			    		 Element element = (Element) iter.next();
			    		 if(element.getName().equals("CUMULATION_ALREADY")){
			    			 String value=element.getStringValue();
			    			 if(value!=null&&!"".equals(value)){
			    				 count=count.add(new BigDecimal(value));
			    			 }
			    		 }
			    	 }
			     }
			    }
				 
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		count=count.divide(new BigDecimal(1024),RoundingMode.HALF_UP);
		return count.toString();
	}
	//查询当月已使用量   M
	public static String  queryTraffic(String iccid){
		//String url=ApplicationProperties.get("dianxin_url");
//		String userId=ApplicationProperties.get("dianxin_url");
//		String pass=ApplicationProperties.get("dianxin_url");
		String[] arr = {iccid,"gzmzgg","gzmz6666","queryTraffic"};
		String key1 = "274";
		String key2 = "917";
		String key3 = "t5r";
		DesUtils des = new DesUtils();
		String sign = des.strEnc(DesUtils.naturalOrdering(arr),key1,key2,key3); //sign加密
		String passwordEnc = des.strEnc("gzmz6666",key1,key2,key3);  //密码加密
		StringBuilder sbUrl=new StringBuilder("http://221.228.17.175:1721/m2m_ec/query.do");
		sbUrl.append("?method=queryTraffic&userId=gzmzgg&passWord="+passwordEnc+"&iccid="+iccid+"&sign="+sign);
		String res=HtttpRequest.getHttpRequest(sbUrl.toString());
		System.out.println(res);
		//解析xml  取出套餐已使用流量
		BigDecimal count=new BigDecimal(0);
		if(!"".equals(res)&&!res.equals("-1")&&!res.equals("-2")&&!res.equals("-3")&&!res.equals("-4")){
			try {
				Document doc = DocumentHelper.parseText(res);
				Element root = doc.getRootElement();
				List list= root.elements();
				Element secRoot =null;
				if(list!=null&&list.size()>0){
					secRoot=(Element)list.get(0);
				}
				Element totalbyteCntElement=null;
				if(secRoot!=null){
					totalbyteCntElement=secRoot.element("TOTAL_BYTES_CNT");
					if(null != totalbyteCntElement){
						String flowNum=totalbyteCntElement.getStringValue();
						if(flowNum!=null&&!"".equals(flowNum)){
							flowNum=flowNum.substring(0, flowNum.indexOf("MB"));
							count=new BigDecimal(flowNum);
						}
					}
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return count.toString();
	}
	/**
	 * 查询当天流量
	 * @param iccid
	 * @return
	 * @throws Exception 
	 */
	public static String  queryTrafficOfToday(String iccid) throws Exception{
		String[] arr = {iccid,"gzmzgg","gzmz6666","queryTrafficOfToday"};
		String key1 = "274";
		String key2 = "917";
		String key3 = "t5r";
		DesUtils des = new DesUtils();
		String sign = des.strEnc(DesUtils.naturalOrdering(arr),key1,key2,key3); //sign加密
		String passwordEnc = des.strEnc("gzmz6666",key1,key2,key3);  //密码加密
		StringBuilder sbUrl=new StringBuilder("http://221.228.17.175:1721/m2m_ec/query.do");
		sbUrl.append("?method=queryTrafficOfToday&userId=gzmzgg&passWord="+passwordEnc+"&iccid="+iccid+"&sign="+sign);
//		String res=HtttpRequest.getHttpRequest("http://221.228.17.175:1721/m2m_ec/query.do?method=queryPakage&userId=gzmzgg&passWord=63DA4B299B2CD475A6E95632F4AF9BED&iccid=8986031540252055237&sign=8652A79045C5EBB534588B2C8F9A59C277D51D8374CAD5D1D6FC21C8848A7F178DC33F4FE249A27D63DA4B299B2CD475A6E95632F4AF9BED9AA5DE5D70F67539900BFB33466C88B67EFC80340F507D708E152276D0F1C726D76EE18246C07073");
		String res=HtttpRequest.getHttpRequestTelecom(sbUrl.toString());
		
		System.err.println(" 卡号："+arr[0]+"用户名--密码:"+arr[1]+"--"+arr[2]);
//		System.out.println("电信的返回：");
//		System.out.println(formatXML(res));
		//解析xml  取出套餐流量
		BigDecimal count=new BigDecimal(0);
		if(res==null||"".equals(res)||res.equals("-1")||res.equals("-2")||res.equals("-3")||res.equals("-4")||res.trim().contains("该号码和用户没有绑定")){
			System.err.println("异常code："+res);
			throw new Exception(res);
        }
		Document doc = DocumentHelper.parseText(res);
		Element root = doc.getRootElement();
		List list= root.elements();
		Element secRoot=null;
		if(list!=null&&list.size()>0){
			secRoot =(Element)list.get(0);
		}
		if(secRoot!=null){
			Element element=secRoot.element("TOTAL_BYTES_CNT");
			if(element!=null){
				String value=element.getStringValue();
				if(value!=null&&!value.equals("")){
					value=value.substring(0, value.indexOf("M"));
					count=new BigDecimal(value);
				}
			}
		}
		System.err.println("今天:"+count.toString());
		return count.toString();
	}
	/**
	 * 大巴
     * 查询当天流量
     * @param iccid
     * @return
     */
    public static String  queryTrafficOfTodayBus(String iccid){
        String[] arr = {iccid,"hangmei","Aa12345","queryTrafficOfToday"};
        String key1 = "274";
        String key2 = "917";
        String key3 = "t5r";
        DesUtils des = new DesUtils();
        String sign = des.strEnc(DesUtils.naturalOrdering(arr),key1,key2,key3); //sign加密
        String passwordEnc = des.strEnc("Aa12345",key1,key2,key3);  //密码加密
        StringBuilder sbUrl=new StringBuilder("http://221.228.17.175:1721/m2m_ec/query.do");
        sbUrl.append("?method=queryTrafficOfToday&userId=gzmzgg&passWord="+passwordEnc+"&iccid="+iccid+"&sign="+sign);
//      String res=HtttpRequest.getHttpRequest("http://221.228.17.175:1721/m2m_ec/query.do?method=queryPakage&userId=gzmzgg&passWord=63DA4B299B2CD475A6E95632F4AF9BED&iccid=8986031540252055237&sign=8652A79045C5EBB534588B2C8F9A59C277D51D8374CAD5D1D6FC21C8848A7F178DC33F4FE249A27D63DA4B299B2CD475A6E95632F4AF9BED9AA5DE5D70F67539900BFB33466C88B67EFC80340F507D708E152276D0F1C726D76EE18246C07073");
        String res=HtttpRequest.getHttpRequest(sbUrl.toString());
        
        System.err.println("电信的返回："+res+" 卡号："+iccid+"用户名密码:hangmei/Aa12345");
        //解析xml  取出套餐流量
        BigDecimal count=new BigDecimal(0);
        if(!"".equals(res)&&!res.equals("-1")&&!res.equals("-2")&&!res.equals("-3")&&!res.equals("-4")){
            try {
                Document doc = DocumentHelper.parseText(res);
                Element root = doc.getRootElement();
                List list= root.elements();
                Element secRoot=null;
                if(list!=null&&list.size()>0){
                    secRoot =(Element)list.get(0);
                }
                if(secRoot!=null){
                    Element element=secRoot.element("TOTAL_BYTES_CNT");
                    if(element!=null){
                        String value=element.getStringValue();
                        if(value!=null&&!value.equals("")){
                            value=value.substring(0, value.indexOf("M"));
                            count=new BigDecimal(value);
                        }
                    }
                }
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count.toString();
    }
    /**
     * http:///m2m_ec/query.do?method=queryTrafficByDate
             &userId=admin&passWord=13AF32801E008AEF7BAE37BE950FF542
             &iccid=8986031540025047173
             &startDate=20150201&endDate=20150210
             &sign=A6975665E6D6D976B53FF632D6500C4DEA3C0DF1B03B4FC9BDBB40A26DD74
             088ECD660D73C2BF59F13AF32801E008AEF24E8306250F585E80E6A937437699994AE
             34AC7EEF15729A72003C9337120BA3AE5BDBE768525F4E2F1
             79098698BC532
     * @param args
     */
    /**
     *查询时间段流量（可用于查询昨天流量）
     * @param iccid
     * @return
     * @throws Exception 
     */
    public static String queryTrafficByDate(String iccid,String startDate,String endDate) throws Exception{
        String[] arr = {iccid,"gzmzgg","gzmz6666","queryTrafficByDate"};
        String key1 = "274";
        String key2 = "917";
        String key3 = "t5r";
        DesUtils des = new DesUtils();
        String sign = des.strEnc(DesUtils.naturalOrdering(arr),key1,key2,key3); //sign加密
        String passwordEnc = des.strEnc("gzmz6666",key1,key2,key3);  //密码加密
        StringBuilder sbUrl=new StringBuilder("http://221.228.17.175:1721/m2m_ec/query.do");
        sbUrl.append("?method=queryTrafficByDate&userId=gzmzgg&passWord="+passwordEnc+"&iccid="+iccid
                +"&startDate="+startDate+"&endDate="+endDate+"&sign="+sign);
//      String res=HtttpRequest.getHttpRequest("http://221.228.17.175:1721/m2m_ec/query.do?method=queryPakage&userId=gzmzgg&passWord=63DA4B299B2CD475A6E95632F4AF9BED&iccid=8986031540252055237&sign=8652A79045C5EBB534588B2C8F9A59C277D51D8374CAD5D1D6FC21C8848A7F178DC33F4FE249A27D63DA4B299B2CD475A6E95632F4AF9BED9AA5DE5D70F67539900BFB33466C88B67EFC80340F507D708E152276D0F1C726D76EE18246C07073");
        String res=HtttpRequest.getHttpRequestTelecom(sbUrl.toString());
        
        System.err.println("卡号："+iccid+"用户名密码:"+arr[1]+"/"+arr[2]);//电信的返回："+res+
//        System.err.println(res);
        //解析xml  取出套餐流量
        BigDecimal count=new BigDecimal(0);
        if(res==null||"".equals(res)||res.equals("-1")||res.equals("-2")||res.equals("-3")||res.equals("-4")||res.trim().contains("该号码和用户没有绑定")){
        	System.err.println("异常code:"+res);
        	throw new Exception(res);
        }
        Document doc = DocumentHelper.parseText(res);
        Element root = doc.getRootElement();
        List list= root.elements();
        Element secRoot=null;
        if(list!=null&&list.size()>0){
            secRoot =(Element)list.get(0);
        }
        if(secRoot!=null){
            Element element=secRoot.element("TOTAL_BYTES_CNT");
            if(element!=null){
                String value=element.getStringValue();
                if(value!=null&&!value.equals("")){
                    value=value.substring(0, value.indexOf("M"));
                    count=new BigDecimal(value);
                }
            }
        }
        System.err.println(startDate+"到"+endDate+":"+count.toString());
        return count.toString();
    }
    /**
     *查询时间段流量（可用于查询昨天流量）
     * @param iccid
     * @return
     * @throws Exception 
     */
    public static Map<String,BigDecimal> queryTrafficByDateForMap(String iccid,String startDate,String endDate) throws Exception{
    	String[] arr = {iccid,"gzmzgg","gzmz6666","queryTrafficByDate"};
    	String key1 = "274";
    	String key2 = "917";
    	String key3 = "t5r";
    	DesUtils des = new DesUtils();
    	String sign = des.strEnc(DesUtils.naturalOrdering(arr),key1,key2,key3); //sign加密
    	String passwordEnc = des.strEnc("gzmz6666",key1,key2,key3);  //密码加密
    	StringBuilder sbUrl=new StringBuilder("http://221.228.17.175:1721/m2m_ec/query.do");
    	sbUrl.append("?method=queryTrafficByDate&userId=gzmzgg&passWord="+passwordEnc+"&iccid="+iccid
    			+"&startDate="+startDate+"&endDate="+endDate+"&sign="+sign);
//      String res=HtttpRequest.getHttpRequest("http://221.228.17.175:1721/m2m_ec/query.do?method=queryPakage&userId=gzmzgg&passWord=63DA4B299B2CD475A6E95632F4AF9BED&iccid=8986031540252055237&sign=8652A79045C5EBB534588B2C8F9A59C277D51D8374CAD5D1D6FC21C8848A7F178DC33F4FE249A27D63DA4B299B2CD475A6E95632F4AF9BED9AA5DE5D70F67539900BFB33466C88B67EFC80340F507D708E152276D0F1C726D76EE18246C07073");
    	String res=HtttpRequest.getHttpRequestTelecom(sbUrl.toString());
    	
    	System.err.println("卡号："+iccid+"用户名密码:"+arr[1]+"/"+arr[2]);//电信的返回："+res+
//        System.out.println(formatXML(res));
    	//解析xml  取出套餐流量
    	Map<String,BigDecimal> restMap=new HashMap<String, BigDecimal>();
    	BigDecimal count=new BigDecimal(0);
    	if(res==null||"".equals(res)||res.equals("-1")||res.equals("-2")||res.equals("-3")||res.equals("-4")||res.trim().contains("该号码和用户没有绑定")){
    		System.err.println("异常code:"+res);
    		throw new Exception(res);
    	}
    	Document doc = DocumentHelper.parseText(res);
    	Element root = doc.getRootElement();
    	List list= root.elements();
    	Element secRoot=null;
    	if(list!=null&&list.size()>0){
    		secRoot =(Element)list.get(0);
    	}
    	if(secRoot!=null){
    		List<Element> elementList=secRoot.elements("NEW_DATA_TICKET_QRlist");
    		if(elementList!=null&&elementList.size()>0){
    			for(Element element:elementList){
    				Element timeElement =element.element("START_TIME");
    				Element cntElement =element.element("BYTES_CNT");
    				if(timeElement!=null){
    					String time=timeElement.getStringValue();
        				if(time!=null){
        					time=time.substring(0, 10);//2014-10-01
        					if(cntElement!=null){
            					String value=cntElement.getStringValue();
            					if(value!=null&&!value.equals("")){
            	    				value=value.substring(0, value.indexOf("KB"));
            	    				count=new BigDecimal(value);
            	    				if(restMap.containsKey(time)){//map中存在该日期
            	    					restMap.put(time, restMap.get(time).add(count));
            	    				}else{
            	    					restMap.put(time,count);
            	    				}
            	    			}
            				}
        				}
        				
    				}
    				
    			}
    			
    		}
    	}
    	System.err.println(startDate+"到"+endDate+":"+restMap.toString());
    	return restMap;
    }
    public static String queryTrafficByDateBus(String iccid,String startDate,String endDate) throws Exception{
        String[] arr = {iccid,"hangmei","hangmeiA8","queryTrafficByDate"};
        String key1 = "274";
        String key2 = "917";
        String key3 = "t5r";
        DesUtils des = new DesUtils();
        String sign = des.strEnc(DesUtils.naturalOrdering(arr),key1,key2,key3); //sign加密
        String passwordEnc = des.strEnc("hangmeiA8",key1,key2,key3);  //密码加密
        StringBuilder sbUrl=new StringBuilder("http://221.228.17.175:1721/m2m_ec/query.do");
        sbUrl.append("?method=queryTrafficByDate&userId=hangmei&passWord="+passwordEnc+"&iccid="+iccid
                +"&startDate="+startDate+"&endDate="+endDate+"&sign="+sign);
//      String res=HtttpRequest.getHttpRequest("http://221.228.17.175:1721/m2m_ec/query.do?method=queryPakage&userId=gzmzgg&passWord=63DA4B299B2CD475A6E95632F4AF9BED&iccid=8986031540252055237&sign=8652A79045C5EBB534588B2C8F9A59C277D51D8374CAD5D1D6FC21C8848A7F178DC33F4FE249A27D63DA4B299B2CD475A6E95632F4AF9BED9AA5DE5D70F67539900BFB33466C88B67EFC80340F507D708E152276D0F1C726D76EE18246C07073");
        String res=HtttpRequest.getHttpRequestTelecom(sbUrl.toString());
        
        System.err.println("卡号："+iccid+"用户名密码:"+arr[1]+"/"+arr[2]);//电信的返回："+res+
//        System.err.println(res);
        //解析xml  取出套餐流量
        BigDecimal count=new BigDecimal(0);
        if("".equals(res)||res.equals("-1")||res.equals("-2")||res.equals("-3")||res.equals("-4")||res.trim().contains("该号码和用户没有绑定")){
            System.err.println("异常code:"+res);
            throw new Exception(res);
        }
        Document doc = DocumentHelper.parseText(res);
        Element root = doc.getRootElement();
        List list= root.elements();
        Element secRoot=null;
        if(list!=null&&list.size()>0){
            secRoot =(Element)list.get(0);
        }
        if(secRoot!=null){
            Element element=secRoot.element("TOTAL_BYTES_CNT");
            if(element!=null){
                String value=element.getStringValue();
                if(value!=null&&!value.equals("")){
                    value=value.substring(0, value.indexOf("M"));
                    count=new BigDecimal(value);
                }
            }
        }
        System.err.println(startDate+"到"+endDate+":"+count.toString());
        return count.toString();
    }
	//示例
    public static void main(String[] args) {
    	//接口参数    具体接口参数需参照接口文档
//    	String number = "1064910609242";     //查询号码
//    	String user_id = "ch2015";     //用户名
//    	String password = "ch2015";    //密码
//    	String interfaceName = "orderFlow";  //接口名称
    	//String number = "18101031873";     //查询号码
//    	String number = "8986031540252054417";     //真实可用的iccid
//    	String number = "8986031540252055237";     //查询号码
//    	String user_id = "gzmzgg";     //用户名
//    	String password = "gzmz6666";    //密码
//    	String password = "63DA4B299B2CD475A6E95632F4AF9BED";    //密码
//    	String interfaceName = "queryPakage";  //接口名称
//    	String interfaceName = "getTelephone";  //接口名称
//    	String interfaceName = "queryTraffic";  //接口名称
//    	String interfaceName = "queryTrafficOfToday";  //接口名称
    	
//    	String[] arr = {number,user_id,password,interfaceName};
    	
    	
    	//key值指密钥  由电信提供  每个user_id都有对应的key值。  key值平均分为三段如下：
//    	String key1 = "HL6";
//    	String key2 = "WPi";
//    	String key3 = "2v2";
//    	String key1 = "274";
//    	String key2 = "917";
//    	String key3 = "t5r";
    	//String key="274917t5r";
//    	DesUtils des = new DesUtils();
//		String sign = des.strEnc(DesUtils.naturalOrdering(arr),key1,key2,key3); //sign加密
		//sign加密结果：B494ECD84DA0C67DEE3D860A7FE4C04DD02E7651E3EF7A0E2C72EBC124AA11046614D0E763D987022817053C538B6DC64C1997854DEBFBF3B46E403662C8E376286040FB07B4CA4547018AB6B6193F89
		
		
//		String passwordEnc = des.strEnc(password,key1,key2,key3);  //密码加密 
		//密码加密结果：44C62BE54884C12592B5CFE57120DEFF
		
//		System.out.println(sign);
//		System.out.println(passwordEnc);
		
		//String signDec = des.strDec(sign,key1,key2,key3); //sign解密
		//解密结果：1064910609242,ch2015,ch2015,orderFlow
		//String passwordDec = des.strDec(passwordEnc,key1,key2,key3);//密码解密
        //密码解密结果 ：ch2015

		//System.out.println("====="+signDec);
		//System.out.println("====="+passwordDec);
		
		//System.out.println(DesUtils.queryPakage("89860315400258033607"));
//		System.out.println(DesUtils.queryTrafficOfTodayBus("8986031540025919907"));
		try {
//			DesUtils.queryTrafficByDateForMap("8986031646200334941", "20161001", "20161031");
//			DesUtils.queryTrafficByDate("8986031541025080010", "20161218", "20161218");
			DesUtils.queryTrafficOfToday("8986031540252054482");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		System.out.println(queryTrafficOfToday("8986031540025803443"));
	}

    public static String formatXML(String xml) {

        String ret = xml;
        StringWriter out = null;
        try {
            OutputFormat formate = OutputFormat.createPrettyPrint();
            out = new StringWriter();
            XMLWriter writer = new XMLWriter(out, formate);
            writer.write(DocumentHelper.parseText(xml));
            ret = out.toString();
        } catch (Exception e) {
//            System.err.println(e.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
//                System.err.println(e.getMessage());
            }
        }
        return ret;
    }
}
