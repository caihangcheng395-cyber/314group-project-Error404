package edu.uow.csit314.idp.controller;
import edu.uow.csit314.idp.entity.*;
import edu.uow.csit314.idp.repository.*;
import edu.uow.csit314.idp.dto.PlatformDtos.ReportResponse;
import jakarta.servlet.http.HttpSession;
import java.time.*;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static edu.uow.csit314.idp.controller.AuthenticationController.error;
@Service @Transactional(readOnly=true)
public class PlatformReportsController {
 private final ActivityEventRepository events;private final UserAccountRepository users;private final IDPRepository projects;private final AuthenticationController auth;
 public PlatformReportsController(ActivityEventRepository e,UserAccountRepository u,IDPRepository p,AuthenticationController a){events=e;users=u;projects=p;auth=a;}
 public ReportResponse generate(String period,HttpSession s){auth.require(s,Role.PLATFORM_MANAGER);int days=switch(period){case "daily"->1;case "weekly"->7;case "monthly"->30;default->throw error(400,"Choose daily, weekly or monthly");};Instant to=Instant.now(),from=to.minus(Duration.ofDays(days));List<ActivityEvent> rows=events.findByCreatedAtBetweenOrderByCreatedAtAsc(from,to);return new ReportResponse(period,from,to,rows.size(),count(rows,"project_viewed"),count(rows,"shortlist_added"),count(rows,"shortlist_removed"),count(rows,"project_created"),count(rows,"login"),count(rows,"registration_approved"),users.count(),projects.count());}
 private long count(List<ActivityEvent> rows,String type){return rows.stream().filter(e->type.equals(e.getType())).count();}
 public String csv(String period,HttpSession s){ReportResponse r=generate(period,s);return "Metric,Value\r\nPeriod,"+r.period()+"\r\nFrom,"+r.from()+"\r\nTo,"+r.to()+"\r\nActivities,"+r.activities()+"\r\nProject views,"+r.projectViews()+"\r\nShortlist additions,"+r.shortlistAdds()+"\r\nShortlist removals,"+r.shortlistRemovals()+"\r\nNew projects,"+r.newProjects()+"\r\nLogins,"+r.logins()+"\r\nApproved registrations,"+r.approvals()+"\r\nCurrent accounts,"+r.accounts()+"\r\nCurrent projects,"+r.projects()+"\r\n";}
}
