package org.springside.examples.miniservice.functional.rs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springside.examples.miniservice.functional.BaseFunctionalTestCase;
import org.springside.examples.miniservice.rs.client.DepartmentResourceClient;
import org.springside.examples.miniservice.rs.dto.DepartmentDTO;

import com.sun.jersey.api.client.UniformInterfaceException;

public class DepartmentResourceServiceTest extends BaseFunctionalTestCase {

		private static DepartmentResourceClient client;
	
		@BeforeClass
		public static void setUpClient() throws Exception {
			client = new DepartmentResourceClient();
			client.setBaseUrl(BASE_URL + "/rs");
		}
	
		@Test
		public void getAllDepartment() {
			List<DepartmentDTO> departmentList = client.getAllDepartment();
			assertTrue(departmentList.size() >= 4);
			assertEquals("Development", departmentList.get(0).getName());
		}
	
		@Test
		public void getDeptartmentDetail() {
			DepartmentDTO department = client.getDepartmentDetail(1L);
			assertEquals("Development", department.getName());
			assertEquals(2, department.getUserList().size());
			assertEquals("Jack", department.getUserList().get(0).getName());
		}
	
		@Test
		public void getDeptartmentDetailWithInvalidId() {
			try {
				client.getDepartmentDetail(999L);
				fail("Should thrown exception while invalid id");
			} catch (UniformInterfaceException e) {
				assertEquals(404, e.getResponse().getStatus());
			}
		}	
}
