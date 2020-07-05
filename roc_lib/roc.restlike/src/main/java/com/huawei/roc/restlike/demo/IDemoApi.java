
package com.huawei.roc.restlike.demo;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.huawei.roc.restlike.annotation.Param;
import com.huawei.roc.restlike.annotation.RequestBody;

@Path("/activity/his")
public interface IDemoApi {
    @POST
    @Path("/create")
    public void apiFunc1(Integer a, @RequestBody String b, @QueryParam("item") Integer c);

    @PUT
    @Path("/update")
    public void apiFunc2(Integer a, @RequestBody String b);

    @GET
    @Path("/update")
    public void apiFunc3(Integer a, @Param("param") Map<String, Object> param);

}
