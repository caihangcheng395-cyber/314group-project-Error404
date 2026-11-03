package edu.uow.csit314.idp.controller;
import edu.uow.csit314.idp.entity.*;
import edu.uow.csit314.idp.repository.*;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import edu.uow.csit314.idp.config.SessionSecurity;
import jakarta.servlet.http.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@Service @Transactional
public class AuthenticationController {
 private final UserAccountRepository users;private final PasswordService passwords;private final ActivityController activity;
 @Value("${app.demo-login-enabled:true}") private boolean demoEnabled;
 public AuthenticationController(UserAccountRepository u,PasswordService p,ActivityController a){users=u;passwords=p;activity=a;}
 public static ResponseStatusException error(int code,String message){return new ResponseStatusException(HttpStatus.valueOf(code),message);}
 public UserAccount optional(HttpSession session){if(session==null||session.getAttribute(SessionSecurity.USER)==null)return null;Long id=(Long)session.getAttribute(SessionSecurity.USER);UserAccount u=users.findById(id).orElse(null);if(u==null||!u.isActive()||!Objects.equals(session.getAttribute("forma.version"),u.getCredentialVersion()))return null;Role role=activeRole(session,u);return u.canUse(role)?u:null;}
 public Role activeRole(HttpSession session,UserAccount u){Object role=session.getAttribute(SessionSecurity.ROLE);return role==null?u.getDefaultRole():Role.valueOf(role.toString());}
 public UserAccount require(HttpSession session,Role... roles){UserAccount u=optional(session);if(u==null)throw error(401,"Please log in with an active account");if(roles.length>0&&!Arrays.asList(roles).contains(activeRole(session,u)))throw error(403,"This workspace cannot perform that action");return u;}
 public AccountResponse me(HttpSession s){UserAccount u=optional(s);return u==null?null:DtoMapper.account(u,activeRole(s,u));}
 public AccountResponse login(LoginRequest d,HttpServletRequest req){UserAccount u=users.findByEmailIgnoreCase(d.email().trim()).orElse(null);if(u==null||!passwords.matches(d.password(),u.getPasswordHash())||!u.isActive())throw error(401,"Email or password is incorrect, or the account is suspended");return establish(u,d.workspace()==null?u.getDefaultRole():d.workspace(),req);}
 public AccountResponse demo(DemoLoginRequest d,HttpServletRequest req){if(!demoEnabled)throw error(404,"Demo login is disabled");UserAccount u=users.findById(d.userId()).orElseThrow(()->error(404,"Demo account not found"));if(!teamEmails().contains(u.getEmail())||!u.isActive())throw error(403,"This demo account is unavailable");return establish(u,d.workspace(),req);}
 private AccountResponse establish(UserAccount u,Role role,HttpServletRequest req){if(!u.canUse(role))throw error(403,"This account is not assigned to that workspace");HttpSession s=req.getSession(true);req.changeSessionId();s.setAttribute(SessionSecurity.USER,u.getId());s.setAttribute("forma.version",u.getCredentialVersion());s.setAttribute(SessionSecurity.ROLE,role.name());activity.record("login",u,null);return DtoMapper.account(u,role);}
 public AccountResponse switchWorkspace(WorkspaceRequest d,HttpSession s){UserAccount u=require(s);if(!u.canUse(d.workspace()))throw error(403,"Workspace is not assigned to this account");s.setAttribute(SessionSecurity.ROLE,d.workspace().name());return DtoMapper.account(u,d.workspace());}
 public void logout(HttpSession s){UserAccount u=optional(s);if(u!=null)activity.record("logout",u,null);if(s!=null)s.invalidate();}
 public List<DemoUserResponse> demos(){if(!demoEnabled)return List.of();String[] names={"liao.yijou@forma.demo","designer@forma.demo","cai.hangcheng@forma.demo","li.shan@forma.demo","admin@forma.demo","keith.low@forma.demo"};List<List<Integer>> sprints=List.of(List.of(2,3),List.of(1,2),List.of(4),List.of(2),List.of(1),List.of(3,4));String[] stories={"13–38","1–29","39–48","13–29","1–12","30–48"};List<DemoUserResponse> result=new ArrayList<>();for(int i=0;i<names.length;i++){UserAccount u=users.findByEmailIgnoreCase(names[i]).orElse(null);if(u!=null&&u.isActive())result.add(new DemoUserResponse(u.getId(),u.getProfile()==null?u.getEmail():u.getProfile().getName(),u.getEmail(),Set.copyOf(u.getRoles()),sprints.get(i),stories[i]));}return result;}
 private Set<String> teamEmails(){return Set.of("liao.yijou@forma.demo","designer@forma.demo","cai.hangcheng@forma.demo","li.shan@forma.demo","admin@forma.demo","keith.low@forma.demo");}
}
