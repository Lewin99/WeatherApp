package org.me.gcu.weatherapp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser {

    private static final String ns = "http://www.w3.org/1999/xhtml"; // Namespace URI for RSS 2.0
    private String locationName; // Field to store the location name

    // Constructor that accepts a locationName
    public XmlParser(String locationName) {
        this.locationName = locationName;
    }

    public List<Item> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            List<Item> items = new ArrayList<>();

            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, null, "rss");

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if ("channel".equals(name)) {
                    while (parser.next() != XmlPullParser.END_TAG) {
                        if (parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }
                        name = parser.getName();
                        if ("item".equals(name)) {
                            Item item = readItem(parser);
                            items.add(item);
                        } else {
                            skip(parser);
                        }
                    }
                } else {
                    skip(parser);
                }
            }
            return items;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Item readItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        String weatherCondition = null;
        String temperature = null;
        String windSpeed = null;
        String humidity = null;

        parser.require(XmlPullParser.START_TAG, null, "item");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            switch (name) {
                case "title":
                    String title = readText(parser);
                    weatherCondition = extractWeatherCondition(title);
                    break;
                case "description":
                    String description = readText(parser);
                    temperature = extractTemperature(description);
                    windSpeed = extractWindSpeed(description);
                    humidity = extractHumidity(description);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        // Use the locationName field when creating a new Item
        return new Item(locationName,weatherCondition, temperature, windSpeed, humidity );
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

    private String extractWeatherCondition(String title) {
        Pattern pattern = Pattern.compile(": (.*?),");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String extractTemperature(String description) {
        Pattern pattern = Pattern.compile("Temperature: (.*?)°C");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String extractWindSpeed(String description) {
        Pattern pattern = Pattern.compile("Wind Speed: (.*?)mph");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String extractHumidity(String description) {
        Pattern pattern = Pattern.compile("Humidity: (.*?)%");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
