package com.portal.domain.orgs;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Organizations {
	private String uuid;
	  private String name;
	  private String description;
	  private String status;
	  private String accountPlanName;
	  private String accountPlanUuid;
	
	  public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccountPlanName() {
		return accountPlanName;
	}
	public void setAccountPlanName(String accountPlanName) {
		this.accountPlanName = accountPlanName;
	}
	public String getAccountPlanUuid() {
		return accountPlanUuid;
	}
	public void setAccountPlanUuid(String accountPlanUuid) {
		this.accountPlanUuid = accountPlanUuid;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
