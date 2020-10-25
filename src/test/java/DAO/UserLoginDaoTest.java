package DAO;

import first.servlet.DAO.UserLoginDao;
import first.servlet.services.LoginValidationResult;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserLoginDaoTest
{
    private UserLoginDao underTest;

    @BeforeEach
    public void setup() {
        underTest = UserLoginDao.Empty();
    }

    @Test()
    public void shouldValidateNameAndPasswordDoesntExists() {
        // given / when
        LoginValidationResult isValid = underTest.login("", "");

        // then
        Assertions.assertEquals(false, isValid.getStatus());
    }

    @Test()
    public void shouldValidateWhetherEmailIsEmpty() {
        // given / when
        LoginValidationResult isValid = underTest.login("", "asds");

        // then
        Assertions.assertEquals(false, isValid.getStatus());
    }

    @Test()
    public void shouldValidateWhetherPasswordIsEmpty() {
        // given / when
        LoginValidationResult isValid = underTest.login("s@s.pl", "");

        // then
        Assertions.assertEquals(false, isValid.getStatus());
    }
}
