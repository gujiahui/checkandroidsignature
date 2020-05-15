package com.joloplay.checkandroidsignature.timer;


import com.joloplay.checkandroidsignature.service.TimerClearApkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gjh
 */
@Slf4j
@Component
@Async
public class MonitorQuartz {

	@Value("${checkSignature_file_path}")
	private String checkSignatureFilePath ;
	
	@Autowired
	private TimerClearApkService timerClearApkService;

	@Scheduled(cron="${jolo.time.cron}")
	public void work() {
		Long startTime = System.currentTimeMillis();
		timerClearApkService.clearApkFile(checkSignatureFilePath);
		Long endTime = System.currentTimeMillis();
		log.info("执行消耗时间："+(endTime-startTime)+"ms");
	}
	

}
