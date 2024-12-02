package com.hand.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel("Task Table")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "todo_task")
public class Task extends AuditDomain {
    private static final long serialVersionUID = 686251831177302306L;

    public static final String FIELD_ID = "id";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_STATE = "state";
    public static final String FIELD_TASK_DESCRIPTION = "taskDescription";
    public static final String FIELD_TASK_NUMBER = "taskNumber";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_TASK_TYPE = "taskType";

    @ApiModelProperty("Primary Key")
    @Id
    @GeneratedValue
    @ExcelColumn(zh = "Task ID", en = "Task ID", order = 1)
    private Long id;

    @ApiModelProperty(value = "Employee ID，TODO_USER.ID", required = true)
    @NotNull
    @ExcelColumn(zh = "Employee ID", en = "Employee ID", order = 2)
    private Long employeeId;

    @ApiModelProperty(value = "State, value set: TODO.STATE", required = true)
    @NotBlank
    @ExcelColumn(zh = "State", en = "State", order = 3)
    private String state;

    @ApiModelProperty(value = "Task Description")
    @ExcelColumn(zh = "Task Description", en = "Task Description", order = 4)
    private String taskDescription;

    @ApiModelProperty(value = "Task Number", required = true)
    @NotBlank
    @ExcelColumn(zh = "Task Number", en = "Task Number", order = 5)
    private String taskNumber;

    @ApiModelProperty(value = "Tenant ID", required = true)
    @NotNull
    @ExcelColumn(zh = "Tenant ID", en = "Tenant ID", order = 6)
    private Long tenantId;

    @ApiModelProperty(value = "Task Type", required = true)
    @ExcelColumn(zh = "Task Type", en = "Task Type", order = 7)
    private String taskType;
}