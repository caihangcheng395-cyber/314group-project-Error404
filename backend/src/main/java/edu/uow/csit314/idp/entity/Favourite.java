package edu.uow.csit314.idp.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

/** Shared BCE Entity. Persistence is handled by its Repository. */
@Entity
@Table(name="favourites",uniqueConstraints=@UniqueConstraint(columnNames={"customer_id","project_id"}))
public class Favourite {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="customer_id")
 private UserAccount customer;
 @ManyToOne(optional=false) @JoinColumn(name="project_id")
 private IDP project;
 @Column(nullable=false)
 private Instant createdAt;
 public Favourite() {}
 public Long getId() { return id; }
 public UserAccount getCustomer() { return customer; }
 public void setCustomer(UserAccount value) { this.customer=value; }
 public IDP getProject() { return project; }
 public void setProject(IDP value) { this.project=value; }
 public Instant getCreatedAt() { return createdAt; }
 public void setCreatedAt(Instant value) { this.createdAt=value; }
}
