package com.school.management.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "permission")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_id_seq")
    @SequenceGenerator(name = "check_result_id_seq", sequenceName = "check_result_id_seq", allocationSize = 1)
    @Column(name = "per_id")
    private long perId;

    @Column(name = "per_name")
    private String perName;

    @Column(name = "per_url")
    private String perUrl;

    @Column(name = "per_method")
    private String perMethod;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public Permission(long perId) {
        this.perId = perId;
    }

    public Permission() {
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public long getPerId() {
        return perId;
    }

    public void setPerId(long perId) {
        this.perId = perId;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getPerUrl() {
        return perUrl;
    }

    public void setPerUrl(String perUrl) {
        this.perUrl = perUrl;
    }

    public String getPerMethod() {
        return perMethod;
    }

    public void setPerMethod(String perMethod) {
        this.perMethod = perMethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "perId=" + perId +
                ", perName='" + perName + '\'' +
                ", perUrl='" + perUrl + '\'' +
                ", perMethod='" + perMethod + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
