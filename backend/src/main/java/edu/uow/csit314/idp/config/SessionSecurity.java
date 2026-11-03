package edu.uow.csit314.idp.config;
import jakarta.servlet.http.HttpSession;
import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
public final class SessionSecurity {
 public static final String USER="forma.user",ROLE="forma.role",CSRF="forma.csrf",REQUESTER="forma.requester";
 private SessionSecurity() {}
 public static String csrf(HttpSession session) { Object t=session.getAttribute(CSRF);if(t==null){t=UUID.randomUUID().toString();session.setAttribute(CSRF,t);}return t.toString(); }
 public static boolean valid(HttpSession session,String value){return session!=null&&value!=null&&session.getAttribute(CSRF)!=null&&MessageDigest.isEqual(value.getBytes(StandardCharsets.UTF_8),session.getAttribute(CSRF).toString().getBytes(StandardCharsets.UTF_8));}
 public static String requester(HttpSession session){Object t=session.getAttribute(REQUESTER);if(t==null){t=UUID.randomUUID().toString();session.setAttribute(REQUESTER,t);}return t.toString();}
 public static String hash(String value){try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));}catch(NoSuchAlgorithmException e){throw new IllegalStateException(e);}}
}
