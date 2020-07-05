
package com.huawei.roc.channel.master.service.south.service.noderegiste;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.huawei.roc.restlike.RESTfulContextVO;
import com.huawei.roc.restlike.annotation.LinkerParam;

/**
 * 
 * @author h00442047
 * @since 2020年1月19日
 */
@Path("register")
public interface INodeRegisteRecver {
    @GET
    @Path("/info")
    public Object getRegisterInfo(@LinkerParam Object channel, @Context RESTfulContextVO contextVO);
}