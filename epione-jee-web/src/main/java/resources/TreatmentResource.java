package resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.omg.CORBA.IdentifierHelper;

import javax.ws.rs.core.SecurityContext;

import entities.Treatment;
import entities.User;
import interfaces.PathServiceLocal;
import interfaces.ReportServiceLocal;
import interfaces.TreatmentServiceLocal;
import interfaces.UserServiceLocal;

@Path("/treatments")
public class TreatmentResource {
	
	@EJB
	TreatmentServiceLocal ts ;
	@EJB
	PathServiceLocal ps;
	@EJB
	UserServiceLocal userServ;
	@EJB
	ReportServiceLocal rs ;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_DOCTOR")
	public Response addTreatment(Treatment treat) {
		ts.addTreatment(treat);
		return Response.status(Status.CREATED).entity(treat).build();
	}
	
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_DOCTOR")
	public Response deleteTreatment(Treatment treat) {
		ts.deleteTreatment(treat);
		return Response.status(Status.ACCEPTED).entity("Treatment Removed").build();
	}
	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_DOCTOR")
	public Response updateTreatment(Treatment treat) {
		ts.updateTreatment(treat);
		return Response.status(Status.ACCEPTED).entity(treat).build();
	}
	
	
	
	// GETALL | SEARCH by id | by recom doctor | by desc | by  recom doctor && desc
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response findTreatment(@QueryParam("id") int id, @QueryParam("recDoctor") String recDoctor, @QueryParam("desc") String desc) {
		if(id != 0) {
			
		Treatment t = ts.getTreatmentById(id);
		return Response.status(Status.FOUND).entity(t).build();
		
		}else if ((recDoctor != null) && (desc == null)) {
			
			return	Response.status(Status.FOUND).entity(ts.getTreatmentsByRecomDoc(recDoctor)).build();
			
		}else if ((recDoctor == null) && (desc != null)) {
			
			return	Response.status(Status.FOUND).entity(ts.getTreatmentsByDesc(desc)).build();
			
		}else if ((recDoctor != null) && (desc != null)) {
			
			return	Response.status(Status.FOUND).entity(ts.getTreatmentsByRecomDocAndDesc(recDoctor, desc)).build();
			
		}else {
			
			return	Response.status(Status.OK).entity(ts.getAllTreatments()).build();

		}
		
	}

	@GET
	@RolesAllowed("ROLE_PATIENT")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getPatientConnected")
	public Response getTreatmentsUserConnected(@Context SecurityContext securityContext) {
		User u=userServ.findUser(securityContext.getUserPrincipal().getName());
		
		return  Response.status(Status.OK).entity(ts.getPatientsTreatment(u.getId())).build();
	}
	
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getTreatmentPath")
	public Response getTreatmentPath(@QueryParam("idPath") int idPath ,@Context SecurityContext securityContext) {
		
		
		return  Response.status(Status.ACCEPTED).entity(ts.getTreatmentsPath(idPath)).build();
	}
	
	@GET
	@RolesAllowed("ROLE_DOCTOR")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getDoctorConnected")
	public Response getTreatmentsDoctorConnected(@Context SecurityContext securityContext) {
		User u=userServ.findUser(securityContext.getUserPrincipal().getName());
		
		return  Response.status(Status.ACCEPTED).entity(ts.getDoctorsTreatment(u.getId())).build();
	}
	
	@POST
	@RolesAllowed("ROLE_DOCTOR")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getDoctorConnected")
	public Response addTreatments(@Context SecurityContext securityContext) {
		User u=userServ.findUser(securityContext.getUserPrincipal().getName());
		
		return  Response.status(Status.ACCEPTED).entity(ts.getDoctorsTreatment(u.getId())).build();
	}
	@POST
	@Path("/copyListTreatment")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_DOCTOR")
	//@PermitAll
	public Response copyListTreatment(List<Treatment> treats,@QueryParam("idPath") int idPath) {
		System.out.println("************");
		treats.forEach(a ->{ 
			Treatment t = new Treatment();
			Treatment treatGerer =ts.getTreatmentById(a.getId());
			t.setDescription(treatGerer.getDescription());
			t.setRecomended_doc(treatGerer.getRecomended_doc());
			
			
			entities.Path p = ps.getPathById(idPath);
			List<Treatment> treatss = p.getList_treat();
			t.setPath(p);
			ts.addTreatment(t);
			p.setList_treat(treatss);
	
			//ts.updateTreatment(t);
		});
		return Response.status(Status.CREATED).entity("success").build();
	}
	
	
	@DELETE
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	@javax.ws.rs.Path("/id")
	public Response deleteTreatId(@QueryParam("idTreat") int idtreat) {
		Treatment t = ts.getTreatmentById(idtreat);
		ts.deleteTreatment(t);
		
		return Response.status(Status.ACCEPTED).entity("Treat Rmoved").build();
	}
	
	
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/count")
	public Response countTreatPath(@QueryParam("id") int id) {
		
		
		return  Response.status(Status.OK).entity(ts.countTreatPath(id)).build();
	}
	

	

}
