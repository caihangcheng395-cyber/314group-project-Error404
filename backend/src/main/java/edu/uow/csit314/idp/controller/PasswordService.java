package edu.uow.csit314.idp.controller;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Service;
@Service
public class PasswordService {
 public String encode(String password){if(password==null||password.length()<8||password.length()>128)throw new IllegalArgumentException("Use a password with 8 to 128 characters");byte[] salt=new byte[16];new SecureRandom().nextBytes(salt);return "pbkdf2$210000$"+Base64.getEncoder().encodeToString(salt)+"$"+Base64.getEncoder().encodeToString(derive(password,salt,210000));}
 public boolean matches(String password,String encoded){try{String[] p=encoded.split("\\$");return p.length==4&&p[0].equals("pbkdf2")&&MessageDigest.isEqual(derive(password,Base64.getDecoder().decode(p[2]),Integer.parseInt(p[1])),Base64.getDecoder().decode(p[3]));}catch(Exception e){return false;}}
 private byte[] derive(String password,byte[] salt,int count){try{KeySpec spec=new PBEKeySpec(password.toCharArray(),salt,count,256);return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();}catch(Exception e){throw new IllegalStateException("Password hashing failed",e);}}
}
