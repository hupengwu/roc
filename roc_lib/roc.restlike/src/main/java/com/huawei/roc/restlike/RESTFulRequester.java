
package com.huawei.roc.restlike;

/**
 * 请求者：REST API请求
 * 
 * @author h00442047
 * @since 2020年1月19日
 */
public class RESTFulRequester {
    /**
     * 资源标识
     */
    private String resource;

    /**
     * 动作：POST/PUT/GET/DELETE
     */
    private String method;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((resource == null) ? 0 : resource.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RESTFulRequester other = (RESTFulRequester) obj;
        if (method == null) {
            if (other.method != null)
                return false;
        } else if (!method.equals(other.method))
            return false;
        if (resource == null) {
            if (other.resource != null)
                return false;
        } else if (!resource.equals(other.resource))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RESTFulRequester [httpResource=" + resource + ", httpMethod=" + method + "]";
    }

}
