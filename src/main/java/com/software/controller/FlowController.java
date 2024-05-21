package com.software.controller;

import com.software.util.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/flow")
public class FlowController {
    @Resource
    RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 提交报销申请并启动报销流程
     * @param businessKey 业务键，唯一标识业务数据
     * @param formData 表单数据，报销信息
     */
    @PostMapping("/submitReimburse")
    public AjaxResult submitReimburse(@RequestBody Map<String, String> formData) {
        try {
            String processDefinitionKey = "reimburse"; // 固定为报销流程的定义键

            String businessKey = (String) formData.get("businessKey");
            if (businessKey == null || businessKey.isEmpty()) {
                return AjaxResult.error("业务键不能为空");
            }

            Map<String, Object> variables = new HashMap<>(formData);
            variables.remove("businessKey"); // 移除 businessKey，因为它不是流程变量

            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
            return AjaxResult.success("报销流程启动成功", processInstance.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("报销流程启动失败");
        }
    }
    /**
     * 提交出差申请并启动出差流程
     * @param businessKey 业务键
     * @param formData 表单数据
     */
    @PostMapping("/submitTravel")
    public AjaxResult submitTravel(@RequestBody Map<String, String> formData) {
        try {
            String processDefinitionKey = "apply"; // 固定为出差流程的定义键


            String businessKey = (String) formData.get("businessKey");
            if (businessKey == null || businessKey.isEmpty()) {
                return AjaxResult.error("业务键不能为空");
            }

            Map<String, Object> variables = new HashMap<>(formData);
            variables.remove("businessKey"); // 移除 businessKey，因为它不是流程变量
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
            return AjaxResult.success("出差流程启动成功", processInstance.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("出差流程启动失败");
        }
    }

//    @ApiOperation("上传一个工作流文件")
//    @PostMapping("/uploadworkflow")
//    @ResponseBody
//    public AjaxResult fileupload(@RequestParam MultipartFile uploadfile) {
//        try {
//            String filename = uploadfile.getOriginalFilename();
//            InputStream is = uploadfile.getInputStream();
//            if (filename.endsWith("zip")) {
//                repositoryService.createDeployment().name(filename).addZipInputStream(new ZipInputStream(is)).deploy();
//            } else if (filename.endsWith("bpmn") || filename.endsWith("xml")) {
//                repositoryService.createDeployment().name(filename).addInputStream(filename, is).deploy();
//            } else {
//                return AjaxResult.error("文件格式错误");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return AjaxResult.error("部署失败");
//        }
//        return AjaxResult.success("部署成功");
//    }


}
