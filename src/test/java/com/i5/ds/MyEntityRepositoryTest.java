package com.i5.ds;

import com.i5.ds.Test.TestSpring;
import com.i5.ds.Test.TestSpringRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MyEntityRepositoryTest {

    @Autowired
    private TestSpringRepository testSpringRepository;

    @Test
    public void testSaveEntity() {
        TestSpring entity = new TestSpring("Test Name");
        TestSpring savedEntity = testSpringRepository.save(entity);

        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getId()).isNotNull();
        assertThat(savedEntity.getName()).isEqualTo("Test Name");
    }

    @Test
    public void testFindAllEntities() {
        TestSpring entity1 = new TestSpring("Test Name 1");
        TestSpring entity2 = new TestSpring("Test Name 2");

        testSpringRepository.save(entity1);
        testSpringRepository.save(entity2);

        List<TestSpring> entities = testSpringRepository.findAll();

        assertThat(entities).hasSize(2);
        assertThat(entities).extracting(TestSpring::getName).contains("Test Name 1", "Test Name 2");
    }
}
