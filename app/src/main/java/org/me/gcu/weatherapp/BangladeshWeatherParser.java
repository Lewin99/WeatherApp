package org.me.gcu.weatherapp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BangladeshWeatherParser {

    private static final String ns = null;

    public List<BangladeshWeatherData> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<BangladeshWeatherData> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<BangladeshWeatherData> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        parser.nextTag(); // Move to the next tag after <rss>

        parser.require(XmlPullParser.START_TAG, ns, "channel");
        String imageUrl = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("image")) {
                imageUrl = readImageUrl(parser);
            } else if (name.equals("item")) {
                entries.add(readItem(parser, imageUrl));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private BangladeshWeatherData readItem(XmlPullParser parser, String imageUrl) throws XmlPullParserException, IOException {
        String day = null;
        String weatherCondition = null;
        String minTemperature = null;
        String maxTemperature = null;
        String windDirection = null;
        String windSpeed = null;
        String visibility = null;
        String pressure = null;
        String humidity = null;
        String uvRisk = null;
        String pollution = null;
        String sunrise = null;
        String sunset = null;
        String latitude = null;
        String longitude = null;

        parser.require(XmlPullParser.START_TAG, ns, "item");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    String title = readText(parser);
                    // Correctly extract day and weather condition from title
                    String[] titleParts = title.split(":", 2); // Split by the first colon
                    if (titleParts.length > 1) {
                        day = titleParts[0].trim();
                        weatherCondition = titleParts[1].trim(); // Use the second part as the weather condition
                    }
                    break;
                case "description":
                    String description = readText(parser);
                    // Extract minTemperature, maxTemperature, windDirection, windSpeed, visibility,
                    // pressure, humidity, uvRisk, pollution, sunrise, and sunset from description
                    String[] descriptionParts = description.split(",");
                    for (String part : descriptionParts) {
                        part = part.trim();
                        if (part.startsWith("Minimum Temperature:")) {
                            minTemperature = part.split(":")[1].trim();
                        } else if (part.startsWith("Maximum Temperature:")) {
                            maxTemperature = part.split(":")[1].trim();
                        } else if (part.startsWith("Wind Direction:")) {
                            windDirection = part.split(":")[1].trim();
                        } else if (part.startsWith("Wind Speed:")) {
                            windSpeed = part.split(":")[1].trim();
                        } else if (part.startsWith("Visibility:")) {
                            visibility = part.split(":")[1].trim();
                        } else if (part.startsWith("Pressure:")) {
                            pressure = part.split(":")[1].trim();
                        } else if (part.startsWith("Humidity:")) {
                            humidity = part.split(":")[1].trim();
                        } else if (part.startsWith("UV Risk:")) {
                            uvRisk = part.split(":")[1].trim();
                        } else if (part.startsWith("Pollution:")) {
                            pollution = part.split(":")[1].trim();
                        } else if (part.startsWith("Sunrise:")) {
                            sunrise = part.split(":")[1].trim();
                        } else if (part.startsWith("Sunset:")) {
                            sunset = part.split(":")[1].trim();
                        }
                    }
                    break;
                case "georss:point":
                    String point = readText(parser);
                    // Extract latitude and longitude from point
                    String[] pointParts = point.split(" ");
                    if (pointParts.length > 1) {
                        latitude = pointParts[0].trim();
                        longitude = pointParts[1].trim();
                    }
                    break;
                case "image":
                    imageUrl = readImageUrl(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new BangladeshWeatherData(day, weatherCondition, minTemperature, maxTemperature, windDirection,
                windSpeed, visibility, pressure, humidity, uvRisk, pollution, sunrise, sunset,
                latitude, longitude, imageUrl);
    }

    private String readImageUrl(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "image");
        String imageUrl = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("url")) {
                imageUrl = readText(parser);
            } else {
                skip(parser);
            }
        }
        return imageUrl;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
