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

//SMALL: det virker nemt og hurtigt, MEDIUM: tror godt jeg kan finde ud af det, m??ske med lidt research. LARGE: Skal enten bruge meget tid, eller meget research.
//TODO: S: Slette primaryUserEmail i FamilyTabellen
//TODO: S: Logo og footer p?? pagetemplate
//DONE: M: s??rge for at der kun bliver listet sv??mmedage fra i dag og frem. Ikke fra fortiden.
//TODO: M: Lave views i SQL?
//TODO: M: kryptere passwords
//TODO: M: Frontcontrolleren laver /fc/fc/fc/fc/fc i url'en efter nogle clicks.
//DONE: M: Kan man mon ikke lave en constrain p?? SQL-tabellen, s?? sv??mmebilletterne ikke kan blive negative tal. INT UNSIGNED, //TODO: s?? skal der ogs?? exception handling p??
//TODO: M: G??re primaryUser i User til bin??r??
//TODO: M: S??tte nogle flere auto increment og automatisk zero?
//DONE: L: Lave admin muligheder, slette brugere, oprette brugere, sv??mmedage.
//DONE: L: Lave noget smart s?? registreringen af det sv??mmehold folk har k??bt deler pladser ud til alle deres sv??mmedage.
//TODO: L: Exceptionproblem, n??r noget g??r galt i en mapper, s?? k??rer exceptionen den bare videre til en anden database, og looper. Den skal stoppe.
//TODO: XL: Fejlh??ndtering
//TODO: M: Siderne skal tjekke at man er logget ind, s?? man ikke kan komme ind og g??re ting via URL
//DONE: XL: Det g??r galt hvis to k??ber samtidigt. Beskrevet i Buy.java
//TODO: XL: Mobilvisning er vigtigt
//DONE: L: S??tte det p?? en online server. Husk at der skal v??re sikkert kodeord til MySQL og at det ikke m?? st?? i koden.
//TODO: L: l??se op p?? syncronized block foran metode, s?? er der kun en der kan v??re der af gangen.
//TODO: L: Lave en ??nske-funktion, hvor man kan ??nske billetter og folk der vil s??lge kan kontakte en direkte p?? tlf-nummer
// Synchronized hashmap. Cemaphores..? Concurrent hashmap. API
//TODO: L: S??tte login log op,,, i -tomcat?
//TODO: L: Generelle problemer hvis folk reloader deres sider
//DONE: L: Hvordan flytter jeg billetterne tilbage til til-salg fra reserveret, hvis brugeren ikke har trykket fortryd?
//DONE: M: Virker k??b og salg egentlig, hvis ikke brugeren i forvejen har sv??mmedagen oprettet? Og skal listen s??tttes op, s?? de dage med 0 billetter ikke vises?

//TODO: 1: S??tte ??nskebillet-funktionen op. Hvis nogen s??tter noget til salg en dag, hvor nogen har ??nsket, f??r de besked p?? at sende en sms.
//TODO: 2: Lave admin-side s?? dele af databasen atomatisk bliver udfyldt.
//TODO: 3: Fejlh??ndtering
//TODO: 4: Smukkesere siden med CSS + mobilvenlig
//TODO: 5: Finde ud af hvad dropletten pr??cist koster og om den er stabil
//TODO: 6: f?? bedre URL?
//TODO: 7: L??gge koden op i Tomcat igen

