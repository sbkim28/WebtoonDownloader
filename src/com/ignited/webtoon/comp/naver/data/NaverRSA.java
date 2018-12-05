package com.ignited.webtoon.comp.naver.data;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

public class NaverRSA {
    private int e;
    private BigInteger n;

    public void setPublic(String nvalue, String evalue){
        if(nvalue != null && evalue != null && !nvalue.isEmpty() && !evalue.isEmpty()){
            this.n = new BigInteger(nvalue, 16);
            this.e = Integer.parseInt(evalue, 16);
        }else {
            throw new IllegalArgumentException("Invalid RSA public Key");
        }
    }

    public String encrypt(String str) throws GeneralSecurityException {
        BigInteger b = pkcs1pad2(str, n.bitLength() + 7 >> 3);
        StringBuilder builder = new StringBuilder(doPubic(b).toString(16));
        int i = (n.bitLength() + 7 >> 3 << 1) - builder.length();
        while (i-- > 0) builder.insert(0,"0");
        return builder.toString();
    }

    private BigInteger doPubic(BigInteger b){
        return b.modPow(BigInteger.valueOf(this.e), this.n);
    }

    private BigInteger pkcs1pad2(String str, int l) throws GeneralSecurityException {
        if(l < str.length() + 11){
            throw new GeneralSecurityException("Message too long for RSA");
        }
        byte[] bytes = new byte[l];
        int rpt = str.length() - 1;
        while (rpt >= 0 && l > 0 ) bytes[--l] = (byte) str.charAt(rpt--);
        bytes[--l] = 0;
        SecureRandom sr = new SecureRandom();
        while (l > 2){
            byte[] b = new byte[]{0};
            while (b[0] == 0)sr.nextBytes(b);
            bytes[--l] = b[0];
        }
        bytes[--l] = 2;
        bytes[--l] = 0;

        return new BigInteger(bytes);

    }
}
