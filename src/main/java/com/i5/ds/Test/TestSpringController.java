package com.i5.ds.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-spring")
public class TestSpringController {

    private final TestSpringService testSpringService;

    @Autowired
    public TestSpringController(TestSpringService testSpringService) {
        this.testSpringService = testSpringService;
    }

    // 더미 데이터를 삽입하는 엔드포인트
    @PostMapping("/add")
    public TestSpring addTestSpring(@RequestParam String name, Long id) {
        return testSpringService.saveEntity(name);
    }

    // 모든 엔티티를 조회하는 엔드포인트
    @GetMapping("/all")
    public List<TestSpring> getAllTestSprings() {
        return testSpringService.findAllEntities();
    }
}
