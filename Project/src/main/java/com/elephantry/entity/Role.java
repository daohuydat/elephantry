package com.elephantry.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "role")
@Table(name = "role")
public class Role {
	@Id
	@Column(name = "RoleId")
	private String roleId;

	@Column(name = "RoleName")
	private String roleName;

	public Role() {
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
