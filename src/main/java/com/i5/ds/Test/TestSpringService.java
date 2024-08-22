package com.i5.ds.Test;

import com.i5.ds.Test.TestSpring;
import com.i5.ds.Test.TestSpringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestSpringService {

    private final TestSpringRepository testSpringRepository;

    @Autowired
    public TestSpringService(TestSpringRepository testSpringRepository) {
        this.testSpringRepository = testSpringRepository;
    }

    public TestSpring saveEntity(String name) {
        TestSpring entity = new TestSpring(name);
        return testSpringRepository.save(entity);
    }

    public List<TestSpring> findAllEntities() {
        return testSpringRepository.findAll();
    }

    // 추가적인 비즈니스 로직 메서드를 여기에 추가할 수 있습니다.
}
