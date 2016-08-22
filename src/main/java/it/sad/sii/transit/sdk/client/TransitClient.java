package it.sad.sii.transit.sdk.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.sad.sii.transit.sdk.model.Passage;
import it.sad.sii.transit.sdk.model.Run;
import it.sad.sii.transit.sdk.utils.DateUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Example client to access the TRANSIT SERVER API.
 */
public class TransitClient {

    /**
     * Charset used to encode query parameters and server response
     */
    private static final String CHARSET = "UTF-8";

    /**
     * The server's base URI
     */
    private String baseUri;

    /**
     * The credentials based on username and password
     */
    private UsernamePasswordCredentials credentials;

    /**
     * Http client for connections
     */
    private DefaultHttpClient httpClient;

    /**
     * JSON parser
     */
    private Gson gson;

    /**
     * Parameterless constructor
     * <p>
     * Please note that username, password and baseUri of the service
     * have to be provided within the file application.properties
     * Please refer to application.properties.example for an example file.
     */
    public TransitClient() {
        // Read username, password and baseUri from properties file
        ResourceBundle application = ResourceBundle.getBundle("application");
        String baseUri = application.getString("baseUri");
        String username = application.getString("username");
        String password = application.getString("password");
        init(baseUri, username, password);
    }

    public TransitClient(String baseUri, String username, String password) {
        init(baseUri, username, password);
    }

    private void init(String baseUri, String username, String password) {
        // Set base URI, eventually remove
        if (baseUri.endsWith("/"))
            baseUri = baseUri.substring(0, baseUri.length() - 2);
        this.baseUri = baseUri;

        // Set credentials
        credentials = new UsernamePasswordCredentials(username, password);

        // Set up http client
        httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);

        // Set up JSON decoder giving the correct adapter for DateTime format
        gson = new GsonBuilder().registerTypeAdapter(LocalTime.class, new JsonDeserializer() {
            @Override
            public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                String date = json.getAsString();
                if (date != null)
                    return DateUtils.parseTime(date);
                else
                    return null;
            }
        }).registerTypeAdapter(LocalDate.class, new JsonDeserializer() {
            @Override
            public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                String date = json.getAsString();
                if (date != null)
                    return DateUtils.parseDateNice(date);
                else
                    return null;
            }
        }).registerTypeAdapter(DateTime.class, new JsonDeserializer() {
            @Override
            public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                String date = json.getAsString();
                if (date != null)
                    return DateUtils.parseDateTime(date);
                else
                    return null;
            }
        }).create();
    }

    /**
     * Internal method to send data and retrieve response
     *
     * @param url The URI to the requested resource
     * @return The JSON String received from the server
     * @throws IOException If an error during the request occurs
     */
    private String sendRequestAndGetResponse(String url) throws IOException {
        // Create GET request
        HttpGet get = new HttpGet(baseUri + "/" + url);
        get.addHeader(BasicScheme.authenticate(credentials, CHARSET, false));
        get.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        get.addHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        // Execute request
        HttpResponse response = httpClient.execute(get);

        // Check returned status code, either throw IOException or return JSON as String
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200)
            throw new IOException("REQUEST FAILED! " + statusCode + ": " + response.getStatusLine().getReasonPhrase());
        else {
            String resp = IOUtils.toString(response.getEntity().getContent(), CHARSET);
            System.out.println(resp);
            return resp;
        }
    }

    /**
     * Gets a list of next N of buses passing by the specified waypoint.
     *
     * @param waypointId The id of the waypoint
     * @param amount     Maximum number of passages to return
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getNextPassages(String waypointId, Integer amount) throws IOException {
        // Set query parameters
        List<BasicNameValuePair> queryParams = new ArrayList<>();

        if (amount != null)
            queryParams.add(new BasicNameValuePair("amount", String.valueOf(amount)));

        // Create request URL
        String requesturl = String.format("passage/%s/next", waypointId);
        if (!queryParams.isEmpty())
            requesturl += "?" + URLEncodedUtils.format(queryParams, CHARSET);

        // Send request
        String response = sendRequestAndGetResponse(requesturl);

        // Check returned status code, either throw IOException or return JSON as String
        return gson.fromJson(response, new TypeToken<List<Passage>>() {}.getType());
    }

    /**
     * Gets the list of all next buses passing by the specified waypoint.
     *
     * @param waypointId The id of the waypoint
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getNextPassages(String waypointId) throws IOException {
        return getNextPassages(waypointId, null);
    }

    /**
     * Returns all buses passing by the specified waypoint within the given time interval on the given day.
     *
     * @param waypointId The id of the waypoint
     *                   //     * @param fromTime   The scheduled time has to be greater or equal than that time
     * @param toTime     The scheduled time has to be smaller or equal than that time
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getPassagesUntil(String waypointId, DateTime toTime) throws IOException {
        // Set query parameters
        List<BasicNameValuePair> queryParams = new ArrayList<>();

        //        if (fromTime != null)
        //            queryParams.add(new BasicNameValuePair("validFrom",
        //                                                   DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print
        // (fromTime)));

        if (toTime != null)
            queryParams.add(new BasicNameValuePair("validTo",
                                                   DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(toTime)));

        // Create request URL
        String requesturl = String.format("passage/%s/until", waypointId) + "?" +
                            URLEncodedUtils.format(queryParams, CHARSET);

        // Send request
        String response = sendRequestAndGetResponse(requesturl);

        // Check returned status code, either throw IOException or return JSON as String
        return gson.fromJson(response, new TypeToken<List<Passage>>() {}.getType());
    }

    /**
     * Returns all buses passing by the specified waypoint today.
     *
     * @param waypointId The id of the waypoint
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getPassagesUntil(String waypointId) throws IOException {
        return getPassagesUntil(waypointId, null);
    }

    /**
     * Returns a list of runs currently associated to the given line.
     *
     * @param lineId The id of the line
     * @return A List of runs matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Run> getLineTimesRuns(String lineId) throws IOException {
        // Create request URL
        String requesturl = String.format("line-times/%s/runs", lineId);

        // Send request
        String response = sendRequestAndGetResponse(requesturl);

        // Check returned status code, either throw IOException or return JSON as String
        return gson.fromJson(response, new TypeToken<List<Run>>() {}.getType());
    }
}