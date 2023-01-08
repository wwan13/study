package com.wwan13.springsecurityoauth2.profiles;

import com.wwan13.springsecurityoauth2.accounts.Account;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Profile {

    @ManyToOne
    private Account manager;

    // todo

}
