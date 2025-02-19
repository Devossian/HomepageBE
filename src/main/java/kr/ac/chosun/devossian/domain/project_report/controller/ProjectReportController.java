package kr.ac.chosun.devossian.domain.project_report.controller;

import kr.ac.chosun.devossian.domain.project_report.domain.ProjectReport;
import kr.ac.chosun.devossian.domain.project_report.service.ProjectReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-reports")
public class ProjectReportController {

    @Autowired
    private ProjectReportService reportService;

    @GetMapping
    public ResponseEntity<List<ProjectReport>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectReport> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @PostMapping
    public ResponseEntity<ProjectReport> createReport(@RequestBody ProjectReport report) {
        return ResponseEntity.ok(reportService.createReport(report));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectReport> updateReport(@PathVariable Long id, @RequestBody ProjectReport updatedReport) {
        return ResponseEntity.ok(reportService.updateReport(id, updatedReport));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}