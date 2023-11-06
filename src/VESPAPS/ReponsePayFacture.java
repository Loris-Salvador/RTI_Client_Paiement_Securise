package VESPAPS;


import communication.Reponse;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class ReponsePayFacture implements Reponse {
    private boolean valide;
    private String message;
    private byte[] hmac;

    public ReponsePayFacture(boolean v, String m, SecretKey cleSession) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IOException {
        valide = v;
        message = m;

        Mac hm = Mac.getInstance("HMAC-MD5","BC");
        hm.init(cleSession);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeBoolean(v);
        dos.writeUTF(m);
        hm.update(baos.toByteArray());
        hmac = hm.doFinal();
    }

    public boolean isValide() {
        return valide;
    }

    public String getMessage() {
        return message;
    }

    public boolean VerifyAuthenticity(SecretKey cleSession) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IOException {
// Construction du HMAC local
        Mac hm = Mac.getInstance("HMAC-MD5","BC");
        hm.init(cleSession);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeBoolean(valide);
        dos.writeUTF(message);
        hm.update(baos.toByteArray());
        byte[] hmacLocal = hm.doFinal();

        return MessageDigest.isEqual(hmac,hmacLocal);
    }
}
