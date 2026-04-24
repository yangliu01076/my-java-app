package org.example.moodtracker.repository;

import org.example.moodtracker.entity.MoodRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author duoyian
 * @date 2026/4/24
 */
@Repository
public interface MoodRecordRepository extends JpaRepository<MoodRecord, Long> {

    // 查找用户某天的记录
    MoodRecord findByUserIdAndRecordDate(Long userId, LocalDate recordDate);

    // 查找用户最近N天的记录
    List<MoodRecord> findByUserIdAndRecordDateBetweenOrderByRecordDateDesc(
            Long userId, LocalDate startDate, LocalDate endDate);

    // 统计本月数据
    @Query("SELECT COUNT(m) FROM MoodRecord m WHERE m.userId = :userId AND YEAR(m.recordDate) = YEAR(CURRENT_DATE) AND MONTH(m.recordDate) = MONTH(CURRENT_DATE)")
    Long countThisMonth(@Param("userId") Long userId);

    // 本月平均心情
    @Query("SELECT AVG(m.moodLevel) FROM MoodRecord m WHERE m.userId = :userId AND YEAR(m.recordDate) = YEAR(CURRENT_DATE) AND MONTH(m.recordDate) = MONTH(CURRENT_DATE)")
    Double averageMoodThisMonth(@Param("userId") Long userId);

    // 本月心情分布
    @Query("SELECT m.moodLevel, COUNT(m) FROM MoodRecord m WHERE m.userId = :userId AND YEAR(m.recordDate) = YEAR(CURRENT_DATE) AND MONTH(m.recordDate) = MONTH(CURRENT_DATE) GROUP BY m.moodLevel")
    List<Object[]> moodDistributionThisMonth(@Param("userId") Long userId);
}
