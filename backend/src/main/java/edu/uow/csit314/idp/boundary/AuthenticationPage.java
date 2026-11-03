package edu.uow.csit314.idp.boundary;
import edu.uow.csit314.idp.controller.AuthenticationController;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import edu.uow.csit314.idp.config.SessionSecurity;
import jakarta.servlet.http.*;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.web.bind.annotation.*;
/** BCE Boundary: HTTP and session handling only; authentication decisions are in Control. */
@RestController @RequestMapping("/api/auth")
public class AuthenticationPage {
 private final AuthenticationController control;
 public AuthenticationPage(AuthenticationController c){control=c;}
 @GetMapping("/csrf") public Map<String,String> csrf(HttpSession s){return Map.of("token",SessionSecurity.csrf(s));}
 @GetMapping("/me") public AccountResponse me(HttpServletRequest req){return control.me(req.getSession(false));}
 @GetMapping("/demo-users") public List<DemoUserResponse> demos(){return control.demos();}
 @PostMapping("/login") public AccountResponse login(@Valid @RequestBody LoginRequest d,HttpServletRequest r){return control.login(d,r);}
 @PostMapping("/demo-login") public AccountResponse demo(@Valid @RequestBody DemoLoginRequest d,HttpServletRequest r){return control.demo(d,r);}
 @PostMapping("/workspace") public AccountResponse workspace(@Valid @RequestBody WorkspaceRequest d,HttpSession s){return control.switchWorkspace(d,s);}
 @PostMapping("/logout") public Map<String,Boolean> logout(HttpServletRequest r){control.logout(r.getSession(false));return Map.of("ok",true);}
}
