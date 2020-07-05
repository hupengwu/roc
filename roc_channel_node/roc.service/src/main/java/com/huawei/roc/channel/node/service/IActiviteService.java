
package com.huawei.roc.channel.node.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.huawei.roc.restlike.annotation.RequestBody;

/**
 * 心跳服务
 * 
 * @author h00442047
 * @since 2020年1月19日
 */
@Path("activate")
public interface IActiviteService {
    @GET
    @Path("/info")
    Object getActivateInfo(@RequestBody String bodyJson);
}
