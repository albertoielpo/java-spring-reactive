package net.ielpo.reactivestack.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.util.Assert;

import net.ielpo.reactivestack.dto.BaseRes;
import net.ielpo.reactivestack.utils.TestUtils;

/**
 * @author Alberto Ielpo
 */
@SpringBootTest
public class HelloControllerTest {

    @Autowired
    private HelloController helloController;

    @Test
    @WithMockUser(username = "test", roles = { "USER" })
    void testGetById() {
        Assert.notNull(helloController, "hello controller must be defined");
        Integer id = TestUtils.anyNumber(5);
        String expectedResult = String.format("The id is %s", id);
        BaseRes<String> unwrap = TestUtils.unwrap(helloController.getById(id));
        Assert.isTrue(unwrap.data.equals(expectedResult), "result must be the expected");

    }

    @Test
    @WithMockUser(username = "test_admin", roles = { "ADMIN" })
    void testGetByAdminId() {
        Assert.notNull(helloController, "hello controller must be defined");
        Integer id = TestUtils.anyNumber(5);
        String expectedResult = String.format("Admin route: The id is %s", id);
        BaseRes<String> unwrap = TestUtils.unwrap(helloController.getByAdminId(id));
        Assert.isTrue(unwrap.data.equals(expectedResult), "result must be the expected");
    }

}
