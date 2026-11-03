package edu.uow.csit314.idp.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

/** Shared BCE Entity. Persistence is handled by its Repository. */
@Entity
@Table(name="activity_events")
public class ActivityEvent {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false)
 private String type;
 @Column
 private Long actorId;
 @Column
 private Long projectId;
 @Column(nullable=false)
 private Instant createdAt;
 public ActivityEvent() {}
 public Long getId() { return id; }
 public String getType() { return type; }
 public void setType(String value) { this.type=value; }
 public Long getActorId() { return actorId; }
 public void setActorId(Long value) { this.actorId=value; }
 public Long getProjectId() { return projectId; }
 public void setProjectId(Long value) { this.projectId=value; }
 public Instant getCreatedAt() { return createdAt; }
 public void setCreatedAt(Instant value) { this.createdAt=value; }
}
