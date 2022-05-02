package dat.startcode.model.config;

import dat.startcode.model.persistence.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class ApplicationStart implements ServletContextListener
{
    private static ConnectionPool connectionPool;

    public ApplicationStart()
    {

    }

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        Logger.getLogger("web").log(Level.INFO, "Starting up application and connection pool");
        try
        {
            Class.forName("org.slf4j.impl.StaticLoggerBinder");
            connectionPool = new ConnectionPool();
        }
        catch (ClassNotFoundException e)
        {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static ConnectionPool getConnectionPool()
    {
            return connectionPool;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        Logger.getLogger("web").log(Level.INFO, "Shutting down application and connection pool");
        unregisterJDBCdrivers();
        connectionPool.close();
    }

    private void unregisterJDBCdrivers()
    {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        // Loop through all drivers
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements())
        {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == cl)
            {
                // This driver was registered by the webapp's ClassLoader, so deregister it:
                try
                {
                    Logger.getLogger("web").log(Level.INFO, "Deregistering JDBC driver");
                    DriverManager.deregisterDriver(driver);
                }
                catch (SQLException ex)
                {
                    Logger.getLogger("web").log(Level.SEVERE, "Error deregistering JDBC driver");
                }
            } else
            {
                // driver was not registered by the webapp's ClassLoader and may be in use elsewhere
                Logger.getLogger("web").log(Level.WARNING, "Error deregistering JDBC driver");
            }
        }
    }
}


//TODO: Kan man mon ikke lave en constrain på SQL-tabellen, så svømmebilletterne ikke kan blive negative tal
//TODO: Lave views i SQL?
//TODO: Slette primaryUserEmail i FamilyTabellen
//TODO: Gøre primaryUser i User til binær??
//TODO: Sætte nogle flere auto increment og automatisk zero?
//TODO: Lave admin muligheder, slette brugere, oprette brugere, svømmedage.
//TODO: Lave noget smart så registreringen af det svømmehold folk har købt deler pladser ud til alle deres svømmedage.
//TODO: doven adminmulighed? Bare kunne skrive SQL direkte? Vigtigt kun for admin i så fald, og ikke noget man kan komme ind på via URL
//TODO: Fejlhåndtering
//TODO: Siderne skal tjekke at man er logget ind, så man ikke kan komme ind og gøre ting via URL
//TODO: Mobilvisning er vigtigt
//TODO: Sætte det på en online server. Husk at der skal være sikkert kodeord til MySQL og at det ikke må stå i koden.
//TODO: Logo og footer på pagetemplate
