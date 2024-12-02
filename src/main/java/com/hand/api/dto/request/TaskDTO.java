package com.hand.api.dto.request;

import com.hand.domain.entity.Task;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

import java.util.List;

@Getter
@Setter
@ExcelSheet(zh = "Task Info", en = "Task Info")
@Accessors(chain = true)
public class TaskDTO extends Task {
    private List<Long> empIdList;
}