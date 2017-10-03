package com.sofinco.solo.model.persistent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends GenericEntity {



    @Column(name = "role_name", length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


}