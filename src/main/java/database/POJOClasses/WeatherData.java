package database.POJOClasses;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name ="weatherdata")
public class WeatherData {

    private String cityName;
    private String country;





    public WeatherData() {

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }




}
