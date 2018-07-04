package hibernate.config;

import database.POJOClasses.WeatherData;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class HibernateConfig {

    private static SessionFactory sessionFactory = null;

    static {
        Locale.setDefault(Locale.ENGLISH);
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public static SessionFactory getSessionFactory() {

        return sessionFactory;
    }

    public static void main(String[] args) {

        List<Object> data = null;
      /*  Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory();
       // SessionFactory sessionFactory =  HibernateConfig.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();*/
        SessionFactory factory = getSessionFactory();
        // factory.close();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        SQLQuery query = session.createSQLQuery("Select * from weatherdata;");
        query.addEntity(WeatherData.class);
        data = query.list();


        for (Iterator iterator = data.iterator(); iterator.hasNext(); ) {
            WeatherData weatherData = (WeatherData) iterator.next();
            System.out.println(weatherData.getCityName());

        }

        session.close();
        transaction.commit();
        factory.close();

    }
}
