package org.example.moodtracker.dto;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author duoyian
 * @date 2026/4/24
 */
@Data
public class MoodRecordDTO {
    @NotNull(message = "心情等级不能为空")
    @Min(value = 0, message = "心情等级最小为0")
    @Max(value = 5, message = "心情等级最大为5")
    private Integer moodLevel;

    private String description;
    private String tags;
    private String weather;
}
