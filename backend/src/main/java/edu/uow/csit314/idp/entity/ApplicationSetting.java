package edu.uow.csit314.idp.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

/** Shared BCE Entity. Persistence is handled by its Repository. */
@Entity
@Table(name="application_settings")
public class ApplicationSetting {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true)
 private String keyName;
 @Column(nullable=false)
 private String settingValue;
 public ApplicationSetting() {}
 public Long getId() { return id; }
 public String getKeyName() { return keyName; }
 public void setKeyName(String value) { this.keyName=value; }
 public String getSettingValue() { return settingValue; }
 public void setSettingValue(String value) { this.settingValue=value; }
}
