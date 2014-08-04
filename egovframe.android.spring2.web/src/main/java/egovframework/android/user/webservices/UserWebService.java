package egovframework.android.user.webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/sampleservice")
public interface UserWebService { 
	@GET
	@Path("/connect/{user}/{pass}")
	@Produces("application/json")
	public Response connect(@PathParam("user") String username,
			@PathParam("pass") String password);
	
	@GET
	@Path("/ws/getUserInfo/{user}")
	@Produces("text/xml")
	public UserInfo getUserInfo(@PathParam("user") String username);

}
