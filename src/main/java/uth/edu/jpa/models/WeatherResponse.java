package uth.edu.jpa.models;

import java.util.List;

public class WeatherResponse {
    private Location location;
    private Forecast forecast;

    // Getters and Setters
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public static class Location {
        private String name;
        private String region;
        private String country;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public static class Forecast {
        private List<Forecastday> forecastday;

        // Getters and Setters
        public List<Forecastday> getForecastday() {
            return forecastday;
        }

        public void setForecastday(List<Forecastday> forecastday) {
            this.forecastday = forecastday;
        }

        public static class Forecastday {
            private Day day;

            // Getters and Setters
            public Day getDay() {
                return day;
            }

            public void setDay(Day day) {
                this.day = day;
            }

            public static class Day {
                private double avgtempC;
                private Condition condition;

                // Getters and Setters
                public double getAvgtempC() {
                    return avgtempC;
                }

                public void setAvgtempC(double avgtempC) {
                    this.avgtempC = avgtempC;
                }

                public Condition getCondition() {
                    return condition;
                }

                public void setCondition(Condition condition) {
                    this.condition = condition;
                }

                public static class Condition {
                    private String text;

                    // Getters and Setters
                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }
                }
            }
        }
    }
}
