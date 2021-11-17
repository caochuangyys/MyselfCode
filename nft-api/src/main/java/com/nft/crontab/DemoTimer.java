package com.nft.crontab;

import com.nft.core.component.LockComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Slf4j
@Component
@EnableScheduling
public class DemoTimer {

    @Autowired
    private LockComponent lockComponent;

    private static final String DEMO_JOB_METHOD_LOCK = "DEMO_JOB_METHOD_LOCK";


    @Scheduled(fixedRate = 60 * 1000)
    public void runJob(){
        Integer timeoutSec=15;
        if (lockComponent.tryLock(DEMO_JOB_METHOD_LOCK,timeoutSec ) == true){
            try {
                //TODO 业务逻辑

            }catch (Exception e){
                log.error("执行定时任务失败");
            }finally {
                lockComponent.release(DEMO_JOB_METHOD_LOCK);
            }
        }

    }
}
