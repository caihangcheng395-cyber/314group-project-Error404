package edu.uow.csit314.idp.boundary;
import edu.uow.csit314.idp.controller.PlatformReportsController;
import edu.uow.csit314.idp.dto.PlatformDtos.ReportResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
@RestController @RequestMapping("/api/reports")
public class PlatformReportsPage {
 private final PlatformReportsController c;public PlatformReportsPage(PlatformReportsController c){this.c=c;}
 @GetMapping("/{period}") public ReportResponse generate(@PathVariable String period,HttpSession s){return c.generate(period,s);}
 @GetMapping(value="/{period}/export",produces="text/csv") public ResponseEntity<String> csv(@PathVariable String period,HttpSession s){return ResponseEntity.ok().header("Content-Disposition","attachment; filename=forma-"+period+"-report.csv").body(c.csv(period,s));}
}
