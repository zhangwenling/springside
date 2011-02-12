package org.springside.examples.miniweb.entity.account;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springside.examples.miniweb.entity.IdEntity;
import org.springside.modules.utils.mapper.ConvertUtils;

import com.google.common.collect.Lists;

/**
 * 角色.
 * 
 * 注释见{@link User}.
 * 
 * @author calvin
 */
@Entity
@Table(name = "ACCT_ROLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends IdEntity {

    private static final String SEPARATOR_CHARS = "|";

    private String              name;

    private String              authorities;

    private List<Authority>     authorityList   = Lists.newArrayList();

    public Role() {

    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    @Transient
    public List<String> getPermissions() {
        return Arrays.asList(StringUtils.split(authorities, SEPARATOR_CHARS));
    }

    @ManyToMany
    @JoinTable(name = "ACCT_ROLE_AUTHORITY", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }

    @Transient
    public String getAuthNames() {
        return ConvertUtils.extractElementPropertyToString(authorityList, "name", ", ");
    }

    @Transient
    public List<Long> getAuthIds() {
        return ConvertUtils.extractElementPropertyToList(authorityList, "id");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
