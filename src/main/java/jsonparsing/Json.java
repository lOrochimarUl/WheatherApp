package jsonparsing;

import five_day_forecast.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
public class Json {

    private static final String TAG_COD = "cod";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_COUNT = "cnt";
    private static final String TAG_LIST = "list";
    private static final String TAG_MAIN = "main";
    private static final String TAG_TEMPERATURE = "temp";
    private static final String TAG_FEELS_LIKE = "feels_like";
    private static final String TAG_MIN_TEMPERATURE = "temp_min";
    private static final String TAG_MAX_TEMPERATURE = "temp_max";
    private static final String TAG_PRESSURE = "pressure";
    private static final String TAG_SEA_LEVEL = "sea_level";
    private static final String TAG_GROUND_LEVEL = "grnd_level";
    private static final String TAG_HUMIDITY = "humidity";
    private static final String TAG_TEMP_KF = "temp_kf";
    private static final String TAG_WEATHER = "weather";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_ICON = "icon";
    private static final String TAG_CLOUDS = "clouds";
    private static final String TAG_ALL = "all";
    private static final String TAG_WIND = "wind";
    private static final String TAG_SPEED = "speed";
    private static final String TAG_DEGREE = "deg";
    private static final String TAG_GUST = "gust";
    private static final String TAG_VISIBILITY = "visibility";
    private static final String TAG_POP = "pop";
    private static final String TAG_SYS = "sys";
    private static final String TAG_POD = "pod";
    private static final String TAG_CITY = "city";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_COORD = "coord";
    private static final String TAG_LATITUDE = "lat";
    private static final String TAG_LONGITUDE = "lon";
    private static final String TAG_COUNTRY = "country";
    private static final String TAG_POPULATION = "population";
    private static final String TAG_TIMEZONE = "timezone";
    private static final String TAG_SUNRISE = "sunrise";
    private static final String TAG_SUNSET = "sunset";

    public Root parse() { // парсит прогноз погоды (пятидневный прогноз с шагом 3 часа)

        int count_1 = 0; // счётчик для расчёта шде ошибка

        Root root = new Root(); // создаём
        JSONParser parser = new JSONParser(); // создаём сам парсер

        try(FileReader reader = new FileReader( "test_five_days_forecast.json")){ // проверяем читается ли

            JSONObject rootJson = (JSONObject)parser.parse(reader);

            String cod = (String) rootJson.get(TAG_COD);
            long message = (long) rootJson.get(TAG_MESSAGE);
            long count = (long) rootJson.get(TAG_COUNT);

            JSONArray weatherListJsonArray = (JSONArray) rootJson.get(TAG_LIST);
            List<Weather_List> list = new ArrayList<>();

            for (Object item : weatherListJsonArray){

                count_1++;

                JSONObject everyThirdHour = (JSONObject) item;
                long dt = (long) everyThirdHour.get("dt");

                JSONObject mainInfoJSON = (JSONObject) everyThirdHour.get(TAG_MAIN);
                Double temperature = (Double) mainInfoJSON.get(TAG_TEMPERATURE);
                Double feels_like = (Double) mainInfoJSON.get(TAG_FEELS_LIKE);
                Double temp_min = (Double) mainInfoJSON.get(TAG_MIN_TEMPERATURE);
                Double temp_max = (Double) mainInfoJSON.get(TAG_MAX_TEMPERATURE);
                long pressure = (long) mainInfoJSON.get(TAG_PRESSURE);
                long sea_level = (long) mainInfoJSON.get(TAG_SEA_LEVEL);
                long ground_level = (long) mainInfoJSON.get(TAG_GROUND_LEVEL);
                long humidity = (long) mainInfoJSON.get(TAG_HUMIDITY);
                double temp_kf = (double) mainInfoJSON.get(TAG_TEMP_KF);// в json иногда имеет тип doble иногда long
                Main_Info main_info = new Main_Info(temperature, feels_like, temp_min, temp_max, (int) pressure, (int) sea_level, (int) ground_level,
                        (int) humidity);

                List<Weather_Info> weatherInfo = new ArrayList<>();
                JSONArray weatherInfoJSON = (JSONArray) everyThirdHour.get(TAG_WEATHER);

                for (Object item_2 : weatherInfoJSON){
                    JSONObject weatherJSON = (JSONObject) item_2;
                    long id = (long) weatherJSON.get(TAG_ID);
                    String main = (String) weatherJSON.get(TAG_MAIN);
                    String description = (String) weatherJSON.get(TAG_DESCRIPTION);
                    String icon = (String) weatherJSON.get(TAG_ICON);
                    Weather_Info weather = new Weather_Info((int) id, main, description, icon);
                    weatherInfo.add(weather);
                }

                JSONObject cloudsJSON = (JSONObject) everyThirdHour.get(TAG_CLOUDS);
                long all = (long) cloudsJSON.get(TAG_ALL);
                Clouds clouds = new Clouds((int) all);


                JSONObject windJSON = (JSONObject) everyThirdHour.get(TAG_WIND);
                Double speed = (Double) windJSON.get(TAG_SPEED);
                long degree = (long) windJSON.get(TAG_DEGREE);
                Double gust = (Double) windJSON.get(TAG_GUST);
                Wind_Info wind_info = new Wind_Info();
                wind_info.set(speed, (int) degree, gust);

                long visibility = (long) everyThirdHour.get(TAG_VISIBILITY);
                //long pop = (long) everyThirdHour.get(TAG_POP);

                JSONObject sysJSON = (JSONObject) everyThirdHour.get(TAG_SYS);
                String pod = (String) sysJSON.get(TAG_POD);
                Sys sys = new Sys();
                sys.set(pod);

                String dt_txt = (String) everyThirdHour.get("dt_txt");
                //Weather_List weather_list = new Weather_List((int)dt, mainInfo, weatherInfo, clouds, wind_info, (int) visibility, (int) pop,
                //        sys, dt_txt);
                //Weather_List weather_list = new Weather_List((int)dt, mainInfo, weatherInfo, clouds, wind_info, (int) visibility, 0,
                //        sys, dt_txt);
               // list.add(weather_list);

            }

            JSONObject cityJson = (JSONObject) rootJson.get(TAG_CITY);
            long id = (long) cityJson.get(TAG_ID);
            String name = (String) cityJson.get(TAG_NAME);


            JSONObject coordJSON = (JSONObject) cityJson.get(TAG_COORD);
            Double lat = (Double) coordJSON.get(TAG_LATITUDE);
            Double lon = (Double) coordJSON.get(TAG_LONGITUDE);
            Coord coord  = new Coord(lat, lon);

            String country = (String) cityJson.get(TAG_COUNTRY);
            long population = (long) cityJson.get(TAG_POPULATION);
            long timezone = (long) cityJson.get(TAG_TIMEZONE);
            long sunrise = (long) cityJson.get(TAG_SUNRISE);
            long sunset = (long) cityJson.get(TAG_SUNSET);
            City city = new City(id, name, coord, country, (int) population, (int) timezone, sunrise, sunset);

            root.Set(cod,(int) message,(int) count,list, city);


            return  root;
        } catch (Exception e) {
            System.out.println("Parsing error: " + e);
            System.out.println("line: " + count_1);

        }
        return null;
    }
}
