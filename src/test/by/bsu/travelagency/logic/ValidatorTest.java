package test.by.bsu.travelagency.logic;

import by.bsu.travelagency.util.Validator;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorTest {

    /**
     * Validate login test.
     */
    @Test
    public void validateLoginTest(){
        final String REGEX_LOGIN = "([A-Za-zА-Яа-я0-9_-]){3,16}";
        String testLogin = "Testuser";
        Pattern pLogin = Pattern.compile(REGEX_LOGIN);
        Matcher mLogin = pLogin.matcher(testLogin);
        boolean expected = mLogin.matches();
        boolean actual = Validator.validateLogin(testLogin);
        Assert.assertEquals(expected, actual);
    }

}
