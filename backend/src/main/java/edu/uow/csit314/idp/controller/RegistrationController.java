package edu.uow.csit314.idp.controller;
import edu.uow.csit314.idp.entity.*;
import edu.uow.csit314.idp.repository.*;
import edu.uow.csit314.idp.dto.PlatformDtos.*;
import edu.uow.csit314.idp.config.SessionSecurity;
import jakarta.servlet.http.HttpSession;
import java.time.Instant;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static edu.uow.csit314.idp.controller.AuthenticationController.error;
@Service @Transactional
public class RegistrationController {
 private final RegistrationRequestRepository requests;private final UserAccountRepository users;private final AuthenticationController auth;private final PasswordService passwords;private final ActivityController activity;
 public RegistrationController(RegistrationRequestRepository r,UserAccountRepository u,AuthenticationController a,PasswordService p,ActivityController e){requests=r;users=u;auth=a;passwords=p;activity=e;}
 public RegistrationResponse signup(SignupRequest d,HttpSession s){if(d.role()!=Role.CUSTOMER&&d.role()!=Role.DESIGNER)throw error(400,"Only Customer and Designer access can be requested. User Admin assigns management roles.");if(!d.password().equals(d.confirmPassword()))throw error(400,"Passwords do not match");String email=d.email().trim().toLowerCase();if(users.existsByEmailIgnoreCase(email)||requests.findByEmailIgnoreCase(email).isPresent())throw error(409,"This email already has an account or access request");RegistrationRequest r=new RegistrationRequest();r.setName(d.name().trim());r.setEmail(email);r.setRequestedRole(d.role());r.setPhone(d.phone());r.setCompanyName(d.role()==Role.DESIGNER?d.companyName():"");r.setPasswordHash(passwords.encode(d.password()));r.setStatus("PENDING");r.setCreatedAt(Instant.now());r.setRequesterTokenHash(SessionSecurity.hash(SessionSecurity.requester(s)));requests.save(r);return DtoMapper.registration(r);}
 public List<RegistrationResponse> mine(HttpSession s){return requests.findByRequesterTokenHashOrderByCreatedAtDesc(SessionSecurity.hash(SessionSecurity.requester(s))).stream().map(DtoMapper::registration).toList();}
 public List<RegistrationResponse> list(HttpSession s){auth.require(s,Role.USER_ADMIN);return requests.findAllByOrderByCreatedAtDesc().stream().map(DtoMapper::registration).toList();}
 public RegistrationResponse approve(Long id,Role role,HttpSession s){UserAccount admin=auth.require(s,Role.USER_ADMIN);if(role!=Role.CUSTOMER&&role!=Role.DESIGNER)throw error(400,"Create privileged accounts through User accounts");RegistrationRequest r=requests.lockById(id).orElseThrow(()->error(404,"Request not found"));if(!r.isPending())throw error(409,"Request already reviewed");if(users.existsByEmailIgnoreCase(r.getEmail()))throw error(409,"An account with this email already exists");UserProfile p=new UserProfile();p.setName(r.getName());p.setEmail(r.getEmail());p.setPhone(r.getPhone());p.setStatus("ACTIVE");UserAccount u=new UserAccount();u.setEmail(r.getEmail());u.setPasswordHash(r.getPasswordHash());u.setDefaultRole(role);u.setRoles(new HashSet<>(Set.of(role)));u.setStatus("ACTIVE");u.setProfile(p);users.saveAndFlush(u);r.setCreatedAccount(u);r.review(admin,true);activity.record("registration_approved",admin,null);return DtoMapper.registration(r);}
 public RegistrationResponse reject(Long id,HttpSession s){UserAccount admin=auth.require(s,Role.USER_ADMIN);RegistrationRequest r=requests.lockById(id).orElseThrow(()->error(404,"Request not found"));if(!r.isPending())throw error(409,"Request already reviewed");r.review(admin,false);activity.record("registration_rejected",admin,null);return DtoMapper.registration(r);}
}
