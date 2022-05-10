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

//SMALL: det virker nemt og hurtigt, MEDIUM: tror godt jeg kan finde ud af det, måske med lidt research. LARGE: Skal enten bruge meget tid, eller meget research.
//TODO: S: Slette primaryUserEmail i FamilyTabellen
//TODO: S: Logo og footer på pagetemplate
//TODO: M: sørge for at der kun bliver listet svømmedage fra i dag og frem. Ikke fra fortiden.
//TODO: M: Lave views i SQL?
//TODO: M: kryptere passwords
//TODO: M: Kan man mon ikke lave en constrain på SQL-tabellen, så svømmebilletterne ikke kan blive negative tal. INT UNSIGNED, så skal der også exception handling på
//TODO: M: Gøre primaryUser i User til binær??
//TODO: M: Sætte nogle flere auto increment og automatisk zero?
//TODO: L: Lave admin muligheder, slette brugere, oprette brugere, svømmedage.
//TODO: M: doven adminmulighed? Bare kunne skrive SQL direkte? Vigtigt kun for admin i så fald, og ikke noget man kan komme ind på via URL
//TODO: L: Lave noget smart så registreringen af det svømmehold folk har købt deler pladser ud til alle deres svømmedage.
//TODO: L: Exceptionproblem, når noget går galt i en mapper, så kører exceptionen den bare videre til en anden database, og looper. Den skal stoppe.
//TODO: XL: Fejlhåndtering
//TODO: M: Siderne skal tjekke at man er logget ind, så man ikke kan komme ind og gøre ting via URL
//TODO: XL: Det går galt hvis to køber samtidigt. Beskrevet i Buy.java
//TODO: XL: Mobilvisning er vigtigt
//TODO: L: Sætte det på en online server. Husk at der skal være sikkert kodeord til MySQL og at det ikke må stå i koden.
//TODO: L: læse op på syncronized block foran metode, så er der kun en der kan være der af gangen.
// Synchronized hashmap. Cemaphores..? Concurrent hashmap. API

