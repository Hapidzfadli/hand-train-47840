package com.hand.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hand.infra.constant.UserConstant;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.hzero.core.cache.CacheValue;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

/**
 * User Table(User)实体类
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-12-02 15:17:34
 */

@Getter
@Setter
@ApiModel("User Table")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "todo_user")
public class User extends AuditDomain {
    private static final long serialVersionUID = 704320488455674611L;

    public static final String FIELD_ID = "id";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_EMPLOYEE_NAME = "employeeName";
    public static final String FIELD_EMPLOYEE_NUMBER = "employeeNumber";
    public static final String FIELD_USER_ACCOUNT = "userAccount";
    public static final String FIELD_USER_PASSWORD = "userPassword";

    @ApiModelProperty("Table Id")
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty(value = "Email")
    @ExcelColumn(en = "Email")
    private String email;

    @ApiModelProperty(value = "Employee Name", required = true)
    @NotBlank
    @ExcelColumn(en = "Name")
    private String employeeName;

    @ApiModelProperty(value = "Employee Number", required = true)
    @NotBlank
    @ExcelColumn(en = "Employee ID")
    private String employeeNumber;

    @ApiModelProperty(value = "User Account", required = true)
    @NotBlank
    @ExcelColumn(en = "User Account")
    private String userAccount;

    @ApiModelProperty(value = "User Password", required = true)
    @NotBlank
    @ExcelColumn(en = "User Password")
    private String userPassword;

    @CacheValue(key= UserConstant.Configuration.CACHE_USER,
            primaryKey = "createdBy", searchKey = "employeeName",
            structure = CacheValue.DataStructure.MAP_OBJECT
    )
    private Long createdBy;

    @CacheValue(key=UserConstant.Configuration.CACHE_USER,
            primaryKey = "lastUpdatedBy", searchKey = "employeeName",
            structure = CacheValue.DataStructure.MAP_OBJECT
    )
    private Long lastUpdatedBy;

}

