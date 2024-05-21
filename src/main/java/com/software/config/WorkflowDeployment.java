package com.software.config;

import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.InputStream;

//系统启动时部署工作流
@Configuration
public class WorkflowDeployment {

    @javax.annotation.Resource
    RepositoryService repositoryService;

    @Value("classpath:/processes/apply.bpmn20.xml")
    private Resource applyProcess;

    @Value("classpath:/processes/reimburse.bpmn20.xml")
    private Resource reimburseProcess;

    //自动部署
    @PostConstruct
    public void deployProcesses() {
        deployProcess(applyProcess);
        deployProcess(reimburseProcess);
    }

    private void deployProcess(Resource processFile) {
        try (InputStream is = processFile.getInputStream()) {
            repositoryService.createDeployment()
                    .addInputStream(processFile.getFilename(), is)
                    .deploy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
