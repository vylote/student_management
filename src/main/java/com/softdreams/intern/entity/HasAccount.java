package com.softdreams.intern.entity;

public interface HasAccount {
    Long getId();
    String getCode();
    String getFullName();
    Account getAccount();
    void setAccount(Account account);
}
