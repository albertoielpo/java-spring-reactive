package net.ielpo.reactivestack.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import net.ielpo.reactivestack.dto.BaseRes;
import net.ielpo.reactivestack.util.TestUtils;

/**
 * @author Alberto Ielpo
 */
@SpringBootTest
public class PublicControllerTest {

    @Autowired
    private PublicController publicController;

    @Test
    void testGetById() {
        Assert.notNull(publicController, "public controller must be defined");
        Integer id = TestUtils.anyNumber(5);
        String expectedResult = String.format("This is a public controller - id param: %s", id);
        BaseRes<String> unwrap = TestUtils.unwrap(publicController.getById(id));
        Assert.isTrue(unwrap.data.equals(expectedResult), "result must be the expected");

    }

}
