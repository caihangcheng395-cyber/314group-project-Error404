package edu.uow.csit314.idp.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

/** Shared BCE Entity. Persistence is handled by its Repository. */
@Entity
@Table(name="project_view_events")
public class ProjectViewEvent {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="project_id")
 private IDP project;
 @ManyToOne(optional=false) @JoinColumn(name="viewer_id")
 private UserAccount viewer;
 @Column(nullable=false)
 private Instant createdAt;
 public ProjectViewEvent() {}
 public Long getId() { return id; }
 public IDP getProject() { return project; }
 public void setProject(IDP value) { this.project=value; }
 public UserAccount getViewer() { return viewer; }
 public void setViewer(UserAccount value) { this.viewer=value; }
 public Instant getCreatedAt() { return createdAt; }
 public void setCreatedAt(Instant value) { this.createdAt=value; }
}
