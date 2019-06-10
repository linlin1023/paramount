package com.paramount.admin.controller;

import com.paramount.admin.common.utils.LogAnnotation;
import com.paramount.admin.service.GenerateService;
import com.paramount.admin.vo.BeanField;
import com.paramount.admin.vo.GenerateDetail;
import com.paramount.admin.vo.GenerateInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 */
@Api("代码生成")
@RestController
@RequestMapping("/generate")
public class GenetateController {

    @Autowired
    private GenerateService generateService;

    @ApiOperation("根据表名显示表信息")
    @GetMapping(params = {"tableName"})
    @RequiresPermissions("generate:edit")
    public GenerateDetail generateByTableName(String tableName) {
        GenerateDetail detail = new GenerateDetail();
        detail.setBeanName(generateService.upperFirstChar(tableName));
        List<BeanField> fields = generateService.listBeanField(tableName);
        detail.setFields(fields);
        return detail;
    }

    @LogAnnotation
    @ApiOperation("生成代码")
    @PostMapping
    @RequiresPermissions("generate:edit")
    public void saveCode(@RequestBody GenerateInput input) {
        generateService.saveCode(input);
    }
}
