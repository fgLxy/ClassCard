package com.school.management.api;

import com.school.management.api.repository.IpJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {

    @Autowired
    private IpJpaRepository jpa;

    @Test
    public void Object() {
        System.out.println(jpa.findAll());
    }
//
}
