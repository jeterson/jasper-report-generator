package com.bergamota.jasperreport.services.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.bergamota.jasperreport.services.base.interfaces.TaskletService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleTaskService {

	private TaskScheduler scheduler;
	private Map<Integer, ScheduledFuture<?>> jobsMap = new HashMap<>();
	
	@Autowired
	public ScheduleTaskService(TaskScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void addTaskToScheduler(int jobId, TaskletService tasklet) {
		ScheduledFuture<?> scheduledTask = scheduler.schedule(tasklet, new CronTrigger(tasklet.getCronExpression(), java.util.TimeZone.getTimeZone(java.util.TimeZone.getDefault().getID())));
        jobsMap.put(jobId, scheduledTask);
		
	}

	public void removeTaskFromScheduler(int id) {
		ScheduledFuture<?> scheduledTask = jobsMap.get(id);
		if (scheduledTask != null) {
			scheduledTask.cancel(true);
			jobsMap.put(id, null);
		}
	}
	
	 @EventListener({ContextRefreshedEvent.class})
	    void contextRefreshedEvent() {
		 log.info("contextRefreshedEvent called");
	        // Get all tasks from DB and reschedule them in case of context restarted
	    }
}
