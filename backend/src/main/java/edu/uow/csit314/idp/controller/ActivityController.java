package edu.uow.csit314.idp.controller;
import edu.uow.csit314.idp.entity.*;
import edu.uow.csit314.idp.repository.ActivityEventRepository;
import java.time.Instant;
import org.springframework.stereotype.Service;
@Service
public class ActivityController {
 private final ActivityEventRepository events;
 public ActivityController(ActivityEventRepository events){this.events=events;}
 public void record(String type,UserAccount actor,Long projectId){ActivityEvent e=new ActivityEvent();e.setType(type);e.setActorId(actor==null?null:actor.getId());e.setProjectId(projectId);e.setCreatedAt(Instant.now());events.save(e);}
}
