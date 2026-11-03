package edu.uow.csit314.idp.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

/** Shared BCE Entity. Persistence is handled by its Repository. */
@Entity
@Table(name="user_accounts")
public class UserAccount {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=250)
 private String email;
 @Column(nullable=false,length=500)
 private String passwordHash;
 @Enumerated(EnumType.STRING) @Column(nullable=false)
 private Role defaultRole;
 @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="account_roles",joinColumns=@JoinColumn(name="account_id")) @Enumerated(EnumType.STRING) @Column(name="role",nullable=false)
 private Set<Role> roles = new HashSet<>();
 @Column(nullable=false)
 private String status;
 @OneToOne(cascade=CascadeType.ALL) @JoinColumn(name="profile_id",unique=true)
 private UserProfile profile;
 @Column(nullable=false) private long credentialVersion;
 public long getCredentialVersion(){return credentialVersion;}
 public void revokeSessions(){credentialVersion++;}
 public UserAccount() {}
 public Long getId() { return id; }
 public String getEmail() { return email; }
 public void setEmail(String value) { this.email=value; }
 public String getPasswordHash() { return passwordHash; }
 public void setPasswordHash(String value) { this.passwordHash=value; }
 public Role getDefaultRole() { return defaultRole; }
 public void setDefaultRole(Role value) { this.defaultRole=value; }
 public Set<Role> getRoles() { return roles; }
 public void setRoles(Set<Role> value) { this.roles=value; }
 public String getStatus() { return status; }
 public void setStatus(String value) { this.status=value; }
 public UserProfile getProfile() { return profile; }
 public void setProfile(UserProfile value) { this.profile=value; }
 public boolean isActive() { return "ACTIVE".equals(status); }
 public boolean canUse(Role role) { return roles.contains(role); }
}
