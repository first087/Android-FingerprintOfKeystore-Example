package com.example.fingerprintofkeystore;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringBuilder stringBuilder = new StringBuilder("Fingerprint\n");

        try {
            stringBuilder.append("\nMD5 : ");
            stringBuilder.append(getFingerprint(MessageDigest.getInstance("MD5")));

            stringBuilder.append("\nSHA-1 : ");
            stringBuilder.append(getFingerprint(MessageDigest.getInstance("SHA-1")));

            stringBuilder.append("\nSHA-256 : ");
            stringBuilder.append(getFingerprint(MessageDigest.getInstance("SHA-256")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.tvFingerprint)).setText(stringBuilder.toString());
    }

    private String getFingerprint(MessageDigest messageDigest)
            throws PackageManager.NameNotFoundException {

        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),
                PackageManager.GET_SIGNATURES);

        messageDigest.update(packageInfo.signatures[0].toByteArray());

        byte[] digest = messageDigest.digest();

        StringBuilder stringBuilder = new StringBuilder();

        for (byte byteDigest : digest) {
            stringBuilder.append(String.format("%02X:", byteDigest));
        }

        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }
}
