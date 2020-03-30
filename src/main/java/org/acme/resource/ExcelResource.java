package org.acme.resource;

import org.acme.service.ExcelService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;


@Path("/excel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ExcelResource {

    @Inject
    ExcelService excelService;

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getXml() {
        ByteArrayInputStream reportExcel = excelService.getReport();

        return Response
                .ok()
                .header("Content-Disposition", "attachment; filename=report.xlsx")
                .entity(reportExcel)
                .build();

    }

}
