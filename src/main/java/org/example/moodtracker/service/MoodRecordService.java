package org.example.moodtracker.service;

import org.example.moodtracker.dto.MoodRecordDTO;
import org.example.moodtracker.entity.MoodRecord;
import org.example.moodtracker.repository.MoodRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author duoyian
 * @date 2026/4/24
 */
@Service
public class MoodRecordService {

    @Resource
    private MoodRecordRepository moodRecordRepository;

    private final Long DEFAULT_USER_ID = 1L; // 简化版本

    @Transactional
    public MoodRecord saveMoodRecord(MoodRecordDTO dto) {
        LocalDate today = LocalDate.now();

        // 检查今天是否已有记录
        MoodRecord existing = moodRecordRepository.findByUserIdAndRecordDate(DEFAULT_USER_ID, today);

        MoodRecord record;
        if (existing != null) {
            // 更新现有记录
            record = existing;
        } else {
            // 创建新记录
            record = new MoodRecord();
            record.setUserId(DEFAULT_USER_ID);
            record.setRecordDate(today);
        }

        // 设置数据
        record.setMoodLevel(dto.getMoodLevel());
        record.setDescription(dto.getDescription());
        record.setTags(dto.getTags());
        record.setWeather(dto.getWeather());

        return moodRecordRepository.save(record);
    }

    public List<MoodRecord> getRecentRecords(int days) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days - 1);

        return moodRecordRepository.findByUserIdAndRecordDateBetweenOrderByRecordDateDesc(
                DEFAULT_USER_ID, startDate, endDate);
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // 本月记录天数
        Long totalDays = moodRecordRepository.countThisMonth(DEFAULT_USER_ID);
        stats.put("totalDays", totalDays);

        // 本月平均心情
        Double averageMood = moodRecordRepository.averageMoodThisMonth(DEFAULT_USER_ID);
        stats.put("averageMood", averageMood != null ? averageMood : 0.0);

        // 本月心情分布
        List<Object[]> distribution = moodRecordRepository.moodDistributionThisMonth(DEFAULT_USER_ID);
        Map<Integer, Long> moodDistMap = new HashMap<>();
        for (Object[] obj : distribution) {
            moodDistMap.put((Integer) obj[0], (Long) obj[1]);
        }
        stats.put("moodDistribution", moodDistMap);

        return stats;
    }

    public MoodRecord getTodayRecord() {
        return moodRecordRepository.findByUserIdAndRecordDate(DEFAULT_USER_ID, LocalDate.now());
    }
}