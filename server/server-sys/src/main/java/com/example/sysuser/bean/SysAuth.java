package com.example.sysuser.bean;

import com.example.common.bean.Page;

import java.util.List;
import java.util.Objects;

import org.springframework.util.StringUtils;

/**
 * 后台权限
 */
public class SysAuth extends Page {
    /**
     * 后台权限
     */
    private Integer id;

    /**
     * 父id
     */
    private Integer parentAuthId;

    /**
     * 所有父id
     */
    private String parentAuthIds;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 链接
     */
    private String href;

    /**
     * 图标
     */
    private String icon;

    /**
     * {"name":"是否显示","0":"显示","1":"不显示"}
     */
    private Integer shows;

    private List<SysAuth> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentAuthId() {
        return parentAuthId;
    }

    public void setParentAuthId(Integer parentAuthId) {
        this.parentAuthId = parentAuthId;
    }

    public String getParentAuthIds() {
        return parentAuthIds;
    }

    public void setParentAuthIds(String parentAuthIds) {
        this.parentAuthIds = parentAuthIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getShows() {
        return shows;
    }

    public void setShows(Integer shows) {
        this.shows = shows;
    }

    public List<SysAuth> getChildren() {
        return children;
    }

    public void setChildren(List<SysAuth> children) {
        this.children = children;
    }

    public String getShows_() {
        if (StringUtils.isEmpty(shows)) {
            return "";
        } else if (shows.equals(0)) {
            return "显示";
        } else if (shows.equals(1)) {
            return "不显示";
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysAuth sysAuth = (SysAuth) o;
        return Objects.equals(id, sysAuth.id) &&
                Objects.equals(parentAuthId, sysAuth.parentAuthId) &&
                Objects.equals(parentAuthIds, sysAuth.parentAuthIds) &&
                Objects.equals(name, sysAuth.name) &&
                Objects.equals(sort, sysAuth.sort) &&
                Objects.equals(href, sysAuth.href) &&
                Objects.equals(icon, sysAuth.icon) &&
                Objects.equals(shows, sysAuth.shows) &&
                Objects.equals(children, sysAuth.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentAuthId, parentAuthIds, name, sort, href, icon, shows, children);
    }
}