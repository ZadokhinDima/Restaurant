import controller.Config;
import model.dao.FactoryDAO;
import model.impl.mysql.MySQLCategoryDAO;
import org.junit.Assert;
import org.junit.Test;

public class TestConfigAndFactory {

    @Test
    public void TestPropertiesLoad(){
        Assert.assertTrue(Config.getInstance() != null);
    }

    @Test
    public void TestFactoryLoad() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        FactoryDAO factory = FactoryDAO.getInstance();
        Assert.assertTrue(factory.getCategoryDAO() instanceof MySQLCategoryDAO);
    }


}
