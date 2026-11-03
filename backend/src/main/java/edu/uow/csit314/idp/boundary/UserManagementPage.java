package edu.uow.csit314.idp.boundary;
import edu.uow.csit314.idp.controller.UserManagementController;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api")
public class UserManagementPage {
 private final UserManagementController c;public UserManagementPage(UserManagementController c){this.c=c;}
 @GetMapping("/profiles") public List<ProfileResponse> profiles(@RequestParam(required=false) String query,HttpSession s){return c.profiles(query,s);}
 @GetMapping("/profiles/{id}") public ProfileResponse profile(@PathVariable Long id,HttpSession s){return c.profile(id,s);}
 @PostMapping("/profiles") public ProfileResponse createProfile(@Valid @RequestBody ProfileRequest d,HttpSession s){return c.saveProfile(null,d,s);}
 @PutMapping("/profiles/{id}") public ProfileResponse updateProfile(@PathVariable Long id,@Valid @RequestBody ProfileRequest d,HttpSession s){return c.saveProfile(id,d,s);}
 @DeleteMapping("/profiles/{id}") public Map<String,Boolean> deleteProfile(@PathVariable Long id,HttpSession s){c.deleteProfile(id,s);return Map.of("ok",true);}
 @PatchMapping("/profiles/{id}/suspension") public ProfileResponse suspendProfile(@PathVariable Long id,HttpSession s){return c.suspendProfile(id,s);}
 @GetMapping("/accounts") public List<AccountResponse> accounts(@RequestParam(required=false) String query,HttpSession s){return c.accounts(query,s);}
 @GetMapping("/accounts/{id}") public AccountResponse account(@PathVariable Long id,HttpSession s){return c.account(id,s);}
 @PostMapping("/accounts") public AccountResponse createAccount(@Valid @RequestBody AccountRequest d,HttpSession s){return c.saveAccount(null,d,s);}
 @PutMapping("/accounts/{id}") public AccountResponse updateAccount(@PathVariable Long id,@Valid @RequestBody AccountRequest d,HttpSession s){return c.saveAccount(id,d,s);}
 @PatchMapping("/accounts/{id}/suspension") public AccountResponse suspendAccount(@PathVariable Long id,HttpSession s){return c.suspendAccount(id,s);}
}
