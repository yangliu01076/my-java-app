package org.example.moodtracker.controller;

import org.example.moodtracker.dto.MoodRecordDTO;
import org.example.moodtracker.entity.MoodRecord;
import org.example.moodtracker.service.MoodRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * @author duoyian
 * @date 2026/4/24
 */
@RestController
@RequestMapping("/api/mood")
@Validated
public class MoodRecordController {

    @Resource
    private MoodRecordService moodRecordService;

    @PostMapping("/save")
    public ResponseEntity<?> saveMoodRecord(@Valid @RequestBody MoodRecordDTO dto) {
        try {
            MoodRecord record = moodRecordService.saveMoodRecord(dto);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "心情记录保存成功");
            response.put("data", record);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse("保存失败: " + e.getMessage()));
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(@RequestParam(defaultValue = "30") int days) {
        try {
            List<MoodRecord> records = moodRecordService.getRecentRecords(days);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", records);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse("获取历史记录失败"));
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStatistics() {
        try {
            Map<String, Object> stats = moodRecordService.getStatistics();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", stats);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse("获取统计数据失败"));
        }
    }

    @GetMapping("/today")
    public ResponseEntity<?> getTodayRecord() {
        try {
            MoodRecord todayRecord = moodRecordService.getTodayRecord();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", todayRecord);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse("获取今日记录失败"));
        }
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        return errorResponse;
    }
}
