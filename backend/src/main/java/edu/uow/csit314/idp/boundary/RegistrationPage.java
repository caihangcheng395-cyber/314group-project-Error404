package edu.uow.csit314.idp.boundary;
import edu.uow.csit314.idp.controller.RegistrationController;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api")
public class RegistrationPage {
 private final RegistrationController c;public RegistrationPage(RegistrationController c){this.c=c;}
 @PostMapping("/auth/signup") @ResponseStatus(org.springframework.http.HttpStatus.CREATED) public RegistrationResponse signup(@Valid @RequestBody SignupRequest d,HttpSession s){return c.signup(d,s);}
 @GetMapping("/auth/signup-status") public List<RegistrationResponse> mine(HttpSession s){return c.mine(s);}
 @GetMapping("/registrations") public List<RegistrationResponse> list(HttpSession s){return c.list(s);}
 @PostMapping("/registrations/{id}/approve") public RegistrationResponse approve(@PathVariable Long id,@Valid @RequestBody ReviewRequest d,HttpSession s){return c.approve(id,d.role(),s);}
 @PostMapping("/registrations/{id}/reject") public RegistrationResponse reject(@PathVariable Long id,HttpSession s){return c.reject(id,s);}
}
