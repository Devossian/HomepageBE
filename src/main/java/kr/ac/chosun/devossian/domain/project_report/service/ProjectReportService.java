package kr.ac.chosun.devossian.domain.project_report.service;

import kr.ac.chosun.devossian.domain.project_report.domain.ProjectReport;
import kr.ac.chosun.devossian.domain.project_report.repository.ProjectReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectReportService {

    @Autowired
    private ProjectReportRepository reportRepository;

    public List<ProjectReport> getAllReports() {
        return reportRepository.findAllByOrderByWeekAsc();
    }

    public ProjectReport getReportById(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public ProjectReport createReport(ProjectReport report) {
        return reportRepository.save(report);
    }

    public ProjectReport updateReport(Long id, ProjectReport updatedReport) {
        ProjectReport existingReport = getReportById(id);
        existingReport.setWeek(updatedReport.getWeek());
        existingReport.setTitle(updatedReport.getTitle());
        existingReport.setContent(updatedReport.getContent());
        return reportRepository.save(existingReport);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }
}
