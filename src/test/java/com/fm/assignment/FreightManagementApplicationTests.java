package com.fm.assignment;

import com.fm.assignment.api.controller.PathController;
import com.fm.assignment.core.dao.PathRepository;
import com.fm.assignment.core.entity.PathEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FreightManagementApplicationTests {
	@Autowired
	PathController pathController;
	@Test
	public void contextLoads(){

	}

}
