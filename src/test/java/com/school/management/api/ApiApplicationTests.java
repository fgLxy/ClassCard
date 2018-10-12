package com.school.management.api;

import com.school.management.api.repository.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {

    @Autowired
    private IpJpaRepository jpa;
    @Autowired
    private ClassJpaRepository classJpa;

    @Autowired
    private StudentJpaRepository repository;

    @Autowired
    private ScheduleWeekJpaRepository weekJpa;

    @Autowired
    private DutyDayJpaRepository Jpa;

    @Autowired
    private AlbumJpaRepository albumJpa;

    @Autowired
    private ExamJpaRepository examJpa;

}
