package edu.uow.csit314.idp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** BCE Entity: a persisted interior-design-project category. */
@Entity
@Table(name = "idp_categories")
public class IDPCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    protected IDPCategory() { } // JPA

    public IDPCategory(String name, String description, boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isActive() { return active; }

    public void rename(String newName) { this.name = newName; }
    public void changeDescription(String newDescription) { this.description = newDescription; }
    public void setActive(boolean active) { this.active = active; }
}
