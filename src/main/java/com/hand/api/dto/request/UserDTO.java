package com.hand.api.dto.request;

import com.hand.domain.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import java.util.List;

@Getter
@Setter
@ExcelSheet(zh = "User Info", en = "User Info")
public class UserDTO extends User {
    @ExcelColumn(promptCode = "children", promptKey = "children", child = true)
    private List<TaskDTO> taskList;
}