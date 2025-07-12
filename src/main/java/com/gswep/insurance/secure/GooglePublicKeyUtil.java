package com.gswep.insurance.secure;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

public class GooglePublicKeyUtil {
    public PublicKey getGooglePublicKey(String kid) throws Exception {
        // 1. JWKS 받아오기
        URL url = new URL("https://www.googleapis.com/oauth2/v3/certs");
        InputStream is = url.openStream();
        String jwksJson = new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining("\n"));
        JSONObject jwks = new JSONObject(jwksJson);
        JSONArray keys = jwks.getJSONArray("keys");

        for (int i = 0; i < keys.length(); i++) {
            JSONObject key = keys.getJSONObject(i);
            if (key.getString("kid").equals(kid)) {
                String modulus = key.getString("n");
                String exponent = key.getString("e");
                byte[] nBytes = Base64.getUrlDecoder().decode(modulus);
                byte[] eBytes = Base64.getUrlDecoder().decode(exponent);

                BigInteger n = new BigInteger(1, nBytes);
                BigInteger e = new BigInteger(1, eBytes);

                RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return keyFactory.generatePublic(keySpec);
            }
        }

        throw new Exception("Public key not found for kid: " + kid);
    }

}
