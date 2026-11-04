package edu.uow.csit314.idp.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

/** Shared BCE Entity. Persistence is handled by its Repository. */
@Entity
@Table(name="interior_design_projects")
public class IDP {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="owner_id")
 private UserAccount owner;
 @ManyToOne(optional=false) @JoinColumn(name="company_id")
 private Company company;
 @ManyToOne(optional=false) @JoinColumn(name="category_id")
 private IDPCategory category;
 @Column(nullable=false,length=250)
 private String title;
 @Column(length=5000)
 private String description;
 @Column(nullable=false)
 private String stage;
 @Column(nullable=false,length=100)
 private String propertyType;
 @Column(length=250)
 private String location;
 @Column(nullable=false)
 private double budget;
 @Column(nullable=false)
 private double area;
 @Column(nullable=false)
 private int progress;
 @Column(length=1500)
 private String imageUrl;
 @Column(nullable=false)
 private Instant createdAt;
 public IDP() {}
 public Long getId() { return id; }
 public UserAccount getOwner() { return owner; }
 public void setOwner(UserAccount value) { this.owner=value; }
 public Company getCompany() { return company; }
 public void setCompany(Company value) { this.company=value; }
 public IDPCategory getCategory() { return category; }
 public void setCategory(IDPCategory value) { this.category=value; }
 public String getTitle() { return title; }
 public void setTitle(String value) { this.title=value; }
 public String getDescription() { return description; }
 public void setDescription(String value) { this.description=value; }
 public String getStage() { return stage; }
 public void setStage(String value) { this.stage=value; }
 public String getPropertyType() { return propertyType; }
 public void setPropertyType(String value) { this.propertyType=value; }
 public String getLocation() { return location; }
 public void setLocation(String value) { this.location=value; }
 public double getBudget() { return budget; }
 public void setBudget(double value) { this.budget=value; }
 public double getArea() { return area; }
 public void setArea(double value) { this.area=value; }
 public int getProgress() { return progress; }
 public void setProgress(int value) { this.progress=value; }
 public String getImageUrl() { return imageUrl; }
 public void setImageUrl(String value) { this.imageUrl=value; }
 public Instant getCreatedAt() { return createdAt; }
 public void setCreatedAt(Instant value) { this.createdAt=value; }
 public boolean belongsTo(UserAccount account) { return account != null && owner.getId().equals(account.getId()); }
 public void changeStage(String value) { if (!Set.of("CURRENT","PAST").contains(value)) throw new IllegalArgumentException("Invalid project stage"); stage=value; if ("PAST".equals(value)) progress=100; }
}
