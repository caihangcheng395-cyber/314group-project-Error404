package edu.uow.csit314.idp.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

/** Shared BCE Entity. Persistence is handled by its Repository. */
@Entity
@Table(name="companies")
public class Company {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="owner_id")
 private UserAccount owner;
 @Column(nullable=false,unique=true,length=250)
 private String name;
 @Column(nullable=false,length=250)
 private String email;
 @Column(length=100)
 private String phone;
 @Column(length=500)
 private String address;
 @Column(length=5000)
 private String description;
 public Company() {}
 public Long getId() { return id; }
 public UserAccount getOwner() { return owner; }
 public void setOwner(UserAccount value) { this.owner=value; }
 public String getName() { return name; }
 public void setName(String value) { this.name=value; }
 public String getEmail() { return email; }
 public void setEmail(String value) { this.email=value; }
 public String getPhone() { return phone; }
 public void setPhone(String value) { this.phone=value; }
 public String getAddress() { return address; }
 public void setAddress(String value) { this.address=value; }
 public String getDescription() { return description; }
 public void setDescription(String value) { this.description=value; }
}
