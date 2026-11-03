package edu.uow.csit314.idp.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

/** Shared BCE Entity. Persistence is handled by its Repository. */
@Entity
@Table(name="user_profiles")
public class UserProfile {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=250)
 private String name;
 @Column(nullable=false,length=250)
 private String email;
 @Column(length=100)
 private String phone;
 @Column(length=500)
 private String address;
 @Column(length=2000)
 private String bio;
 @Column(nullable=false)
 private String status;
 public UserProfile() {}
 public Long getId() { return id; }
 public String getName() { return name; }
 public void setName(String value) { this.name=value; }
 public String getEmail() { return email; }
 public void setEmail(String value) { this.email=value; }
 public String getPhone() { return phone; }
 public void setPhone(String value) { this.phone=value; }
 public String getAddress() { return address; }
 public void setAddress(String value) { this.address=value; }
 public String getBio() { return bio; }
 public void setBio(String value) { this.bio=value; }
 public String getStatus() { return status; }
 public void setStatus(String value) { this.status=value; }
}
