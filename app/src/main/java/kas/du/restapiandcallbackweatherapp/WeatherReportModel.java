package kas.du.restapiandcallbackweatherapp;

public class WeatherReportModel {
    private int weather__id;
    private String weather__main;
    private String weather__description;
    private float main__temp;
    private float main__feels_like;
    private float main__temp_min;
    private float main__temp_max;
    private int main__pressure;
    private int main__humidity;
    private float wind__speed;
    private String sys__country;
    private String name;

    public WeatherReportModel(int weather__id, String weather__main, String weather__description, float main__temp, float main__feels_like, float main__temp_min, float main__temp_max, int main__pressure, int main__humidity, float wind__speed, String sys__country, String name) {
        this.weather__id = weather__id;
        this.weather__main = weather__main;
        this.weather__description = weather__description;
        this.main__temp = main__temp;
        this.main__feels_like = main__feels_like;
        this.main__temp_min = main__temp_min;
        this.main__temp_max = main__temp_max;
        this.main__pressure = main__pressure;
        this.main__humidity = main__humidity;
        this.wind__speed = wind__speed;
        this.sys__country = sys__country;
        this.name = name;
    }

    public int getWeather__id() {
        return weather__id;
    }

    public void setWeather__id(int weather__id) {
        this.weather__id = weather__id;
    }

    public String getWeather__main() {
        return weather__main;
    }

    public void setWeather__main(String weather__main) {
        this.weather__main = weather__main;
    }

    public String getWeather__description() {
        return weather__description;
    }

    public void setWeather__description(String weather__description) {
        this.weather__description = weather__description;
    }

    public float getMain__temp() {
        return main__temp;
    }

    public void setMain__temp(float main__temp) {
        this.main__temp = main__temp;
    }

    public float getMain__feels_like() {
        return main__feels_like;
    }

    public void setMain__feels_like(float main__feels_like) {
        this.main__feels_like = main__feels_like;
    }

    public float getMain__temp_min() {
        return main__temp_min;
    }

    public void setMain__temp_min(float main__temp_min) {
        this.main__temp_min = main__temp_min;
    }

    public float getMain__temp_max() {
        return main__temp_max;
    }

    public void setMain__temp_max(float main__temp_max) {
        this.main__temp_max = main__temp_max;
    }

    public int getMain__pressure() {
        return main__pressure;
    }

    public void setMain__pressure(int main__pressure) {
        this.main__pressure = main__pressure;
    }

    public int getMain__humidity() {
        return main__humidity;
    }

    public void setMain__humidity(int main__humidity) {
        this.main__humidity = main__humidity;
    }

    public float getWind__speed() {
        return wind__speed;
    }

    public void setWind__speed(float wind__speed) {
        this.wind__speed = wind__speed;
    }

    public String getSys__country() {
        return sys__country;
    }

    public void setSys__country(String sys__country) {
        this.sys__country = sys__country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}