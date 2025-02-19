package kr.ac.chosun.devossian.domain.project_report.repository;

import kr.ac.chosun.devossian.domain.project_report.domain.ProjectReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectReportRepository extends JpaRepository<ProjectReport, Long> {
    List<ProjectReport> findAllByOrderByWeekAsc(); // 주차별 정렬된 보고서 목록 조회
}
