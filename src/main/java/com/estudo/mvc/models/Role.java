package com.estudo.mvc.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name = "TB_ROLES")
public class Role implements GrantedAuthority {
  
  @Id
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
  @Column(length = 36, nullable = false, updatable = false, unique = true)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  private List<User> users;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private Date createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private Date updatedAt;

  public Role() {}
  
  public Role(String name) {
    this.name = name;
  }

  @Override
  public String getAuthority() {
    return this.name;
  }
}
