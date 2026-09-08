package com.nehemiah.resolveflowai.controller;

import com.nehemiah.resolveflowai.model.Issue;
import com.nehemiah.resolveflowai.service.IssueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping
    public Issue createIssue(@RequestBody Issue issue) {
        return issueService.createIssue(issue);
    }

    @GetMapping
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }

    @PutMapping("/{id}")
    public Issue updateIssue(@PathVariable String id, @RequestBody Issue updatedIssue) {
        return issueService.updateIssue(id, updatedIssue);
    }

    @DeleteMapping("/{id}")
    public String deleteIssue(@PathVariable String id) {
        issueService.deleteIssue(id);
        return "Issue deleted successfully";
    }
}