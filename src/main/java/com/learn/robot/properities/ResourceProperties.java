package com.learn.robot.properities;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(
        prefix = "resource-server"
)
public class ResourceProperties {
    private Boolean enable;
    private String exceptionCode;
    private String whiteList;
    private Boolean loadResourceAuthority;

    public ResourceProperties() {
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList;
    }

    public String[] getExceptionCode() {
        return StringUtils.isEmpty(this.exceptionCode) ? null : this.exceptionCode.split(",");
    }

    public String[] getWhiteList() {
        return StringUtils.isEmpty(this.whiteList) ? null : this.whiteList.split(",");
    }

    public Boolean getEnabled() {
        return this.enable;
    }

    public void setEnabled(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getLoadResourceAuthority() {
        return this.loadResourceAuthority == null ? false : this.loadResourceAuthority;
    }

    public void setLoadResourceAuthority(Boolean loadResourceAuthority) {
        this.loadResourceAuthority = loadResourceAuthority;
    }
}
