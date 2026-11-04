package edu.uow.csit314.idp.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

/** Shared BCE Entity. Persistence is handled by its Repository. */
@Entity
@Table(name="registration_requests")
public class RegistrationRequest {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=250)
 private String name;
 @Column(nullable=false,unique=true,length=250)
 private String email;
 @Enumerated(EnumType.STRING) @Column(nullable=false)
 private Role requestedRole;
 @Column(length=100)
 private String phone;
 @Column(length=250)
 private String companyName;
 @Column(nullable=false,length=500)
 private String passwordHash;
 @Column(nullable=false)
 private String status;
 @Column(nullable=false,length=100)
 private String requesterTokenHash;
 @Column(nullable=false)
 private Instant createdAt;
 @ManyToOne @JoinColumn(name="reviewer_id")
 private UserAccount reviewer;
 @OneToOne @JoinColumn(name="created_account_id")
 private UserAccount createdAccount;
 @Column
 private Instant reviewedAt;
 public RegistrationRequest() {}
 public Long getId() { return id; }
 public String getName() { return name; }
 public void setName(String value) { this.name=value; }
 public String getEmail() { return email; }
 public void setEmail(String value) { this.email=value; }
 public Role getRequestedRole() { return requestedRole; }
 public void setRequestedRole(Role value) { this.requestedRole=value; }
 public String getPhone() { return phone; }
 public void setPhone(String value) { this.phone=value; }
 public String getCompanyName() { return companyName; }
 public void setCompanyName(String value) { this.companyName=value; }
 public String getPasswordHash() { return passwordHash; }
 public void setPasswordHash(String value) { this.passwordHash=value; }
 public String getStatus() { return status; }
 public void setStatus(String value) { this.status=value; }
 public String getRequesterTokenHash() { return requesterTokenHash; }
 public void setRequesterTokenHash(String value) { this.requesterTokenHash=value; }
 public Instant getCreatedAt() { return createdAt; }
 public void setCreatedAt(Instant value) { this.createdAt=value; }
 public UserAccount getReviewer() { return reviewer; }
 public void setReviewer(UserAccount value) { this.reviewer=value; }
 public UserAccount getCreatedAccount() { return createdAccount; }
 public void setCreatedAccount(UserAccount value) { this.createdAccount=value; }
 public Instant getReviewedAt() { return reviewedAt; }
 public void setReviewedAt(Instant value) { this.reviewedAt=value; }
 public boolean isPending() { return "PENDING".equals(status); }
 public void review(UserAccount admin, boolean approved) { if (!isPending()) throw new IllegalStateException("Already reviewed"); status=approved?"APPROVED":"REJECTED"; reviewer=admin; reviewedAt=Instant.now(); }
}
