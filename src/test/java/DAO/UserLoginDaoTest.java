package DAO;

import first.servlet.DAO.UserLoginDao;
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

    }

    @Test()
    public void shouldValidateWhetherEmailIsEmpty() {

    }

    @Test()
    public void shouldValidateWhetherPasswordIsEmpty() {

    }
}
