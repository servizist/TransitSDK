package it.sad.sii.transit.client;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.sad.sii.transit.model.Passage;
import it.sad.sii.transit.model.Run;
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
 * <p/>
 * Please note that username, password and baseUri of the service
 * have to be provided within the file application.properties
 * Please refer to application.properties.example for an example file.
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
     * Constructor
     */
    public TransitClient() {
        // Read username, password and baseUri from properties file
        ResourceBundle application = ResourceBundle.getBundle("application");
        String baseUri = application.getString("baseUri");
        String username = application.getString("username");
        String password = application.getString("password");

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
        gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new JsonDeserializer<DateTime>() {
            public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(json.getAsString());
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
        else
            return IOUtils.toString(response.getEntity().getContent(), CHARSET);
    }

    /**
     * Given a specific waypoint, line, run, scheduled time and validity date this method returns the list of passages,
     * i.e., buses of this line and run passing by the waypoint at the given time and date.
     *
     * @param waypointId    The id of the waypoint
     * @param lineId        The id of the line
     * @param runId         The id of the run
     * @param scheduledTime The time scheduled when the bus is scheduled to arrive at the waypoint
     * @param validAt       The date of the passage
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getPassages(Integer waypointId, Integer lineId, Integer runId, LocalTime scheduledTime,
                                     LocalDate validAt) throws IOException {
        // Set query parameters
        List<BasicNameValuePair> queryParams = new ArrayList<>();

        queryParams.add(new BasicNameValuePair("lineId", String.valueOf(lineId)));

        if (runId != null)
            queryParams.add(new BasicNameValuePair("runId", String.valueOf(runId)));

        if (scheduledTime != null)
            queryParams.add(new BasicNameValuePair("scheduledTime",
                                                   DateTimeFormat.forPattern("HH:mm:ss").print(scheduledTime)));

        if (validAt != null)
            queryParams.add(new BasicNameValuePair("validAt", DateTimeFormat.forPattern("yyyyMMdd").print(validAt)));

        // Create request URL
        String requesturl =
                String.format("passage/%s/get", waypointId) + "?" + URLEncodedUtils.format(queryParams, CHARSET);

        // Send request
        String response = sendRequestAndGetResponse(requesturl);

        // Parse response from JSON String to an object of Passage and return it
        return gson.fromJson(response, new TypeToken<List<Passage>>() {}.getType());
    }

    /**
     * Given a specific waypoint and line this method returns the list of today's passages,
     * i.e., buses of this line passing by the waypoint today.
     *
     * @param waypointId The id of the waypoint
     * @param lineId     The id of the line
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getPassages(Integer waypointId, Integer lineId) throws IOException {
        return getPassages(waypointId, lineId, null, null, null);
    }

    /**
     * Given a specific waypoint, line, run and scheduled time this method returns the list of today's passages,
     * i.e., buses of this line and run passing by at the given time today.
     *
     * @param waypointId    The id of the waypoint
     * @param lineId        The id of the line
     * @param runId         The id of the run
     * @param scheduledTime The time scheduled when the bus is scheduled to arrive at the waypoint
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getPassages(Integer waypointId, Integer lineId, Integer runId, LocalTime scheduledTime)
            throws IOException {
        return getPassages(waypointId, lineId, runId, scheduledTime, null);
    }

    /**
     * Given a specific waypoint, line, run and validity date this method returns the list of passages,
     * i.e., buses of this line and run passing by at the given date.
     *
     * @param waypointId The id of the waypoint
     * @param lineId     The id of the line
     * @param runId      The id of the run
     * @param validAt    The date of the passage
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getPassages(Integer waypointId, Integer lineId, Integer runId, LocalDate validAt)
            throws IOException {
        return getPassages(waypointId, lineId, runId, null, validAt);
    }

    /**
     * Given a specific waypoint, line, scheduled time and validity date this method returns the list of passages,
     * i.e., buses of this line passing by at the given time and date.
     *
     * @param waypointId    The id of the waypoint
     * @param lineId        The id of the line
     * @param scheduledTime The time scheduled when the bus is scheduled to arrive at the waypoint
     * @param validAt       The date of the passage
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getPassages(Integer waypointId, Integer lineId, LocalTime scheduledTime, LocalDate validAt)
            throws IOException {
        return getPassages(waypointId, lineId, null, scheduledTime, validAt);
    }

    /**
     * Given a specific waypoint, line and run this method returns the list of today's passages,
     * i.e., buses of this line and run passing by the waypoint today.
     *
     * @param waypointId The id of the waypoint
     * @param lineId     The id of the line
     * @param runId      The id of the run
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getPassages(Integer waypointId, Integer lineId, Integer runId) throws IOException {
        return getPassages(waypointId, lineId, runId, null, null);
    }

    /**
     * Given a specific waypoint, line and scheduled time this method returns the list of today's passages,
     * i.e., buses of this line passing by the waypoint at the given time today.
     *
     * @param waypointId    The id of the waypoint
     * @param lineId        The id of the line
     * @param scheduledTime The time scheduled when the bus is scheduled to arrive at the waypoint
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getPassages(Integer waypointId, Integer lineId, LocalTime scheduledTime) throws IOException {
        return getPassages(waypointId, lineId, null, scheduledTime, null);
    }

    /**
     * Given a specific waypoint, line and validity date this method returns the list of passages,
     * i.e., buses of this line passing by the waypoint at the given date.
     *
     * @param waypointId The id of the waypoint
     * @param lineId     The id of the line
     * @param validAt    The date of the passage
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getPassages(Integer waypointId, Integer lineId, LocalDate validAt) throws IOException {
        return getPassages(waypointId, lineId, null, null, validAt);
    }


    /**
     * Gets a list of next N of buses passing by the specified waypoint.
     *
     * @param waypointId The id of the waypoint
     * @param amount     Maximum number of passages to return
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getNextPassages(Integer waypointId, Integer amount) throws IOException {
        // Set query parameters
        List<BasicNameValuePair> queryParams = new ArrayList<>();

        if (amount != null)
            queryParams.add(new BasicNameValuePair("amount", String.valueOf(amount)));

        // Create request URL
        String requesturl =
                String.format("passage/%s/next", waypointId) + "?" + URLEncodedUtils.format(queryParams, CHARSET);

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
    public List<Passage> getNextPassages(int waypointId) throws IOException {
        return getNextPassages(waypointId, null);
    }

    /**
     * Returns all buses passing by the specified waypoint within the given time interval on the given day.
     *
     * @param waypointId The id of the waypoint
     * @param validAt    The date of the passage
     * @param fromTime   The scheduled time has to be greater or equal than that time
     * @param toTime     The scheduled time has to be smaller or equal than that time
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getAllPassagesForDay(Integer waypointId, LocalDate validAt, LocalTime fromTime,
                                              LocalTime toTime) throws IOException {
        // Set query parameters
        List<BasicNameValuePair> queryParams = new ArrayList<>();

        if (validAt != null)
            queryParams.add(new BasicNameValuePair("validAt", DateTimeFormat.forPattern("yyyyMMdd").print(validAt)));

        if (fromTime != null)
            queryParams.add(new BasicNameValuePair("fromTime",
                                                   DateTimeFormat.forPattern("HH:mm:ss").print(fromTime)));

        if (toTime != null)
            queryParams.add(new BasicNameValuePair("toTime",
                                                   DateTimeFormat.forPattern("HH:mm:ss").print(toTime)));

        // Create request URL
        String requesturl = String.format("passage/%s/all-for-day", waypointId) + "?" +
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
    public List<Passage> getAllPassagesForDay(Integer waypointId) throws IOException {
        return getAllPassagesForDay(waypointId, null, null, null);
    }

    /**
     * Returns all buses passing by the specified waypoint within on the given day.
     *
     * @param waypointId The id of the waypoint
     * @param validAt    The date of the passage
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getAllPassagesForDay(Integer waypointId, LocalDate validAt) throws IOException {
        return getAllPassagesForDay(waypointId, validAt, null, null);
    }

    /**
     * Returns all buses passing by the specified waypoint within the given time interval today.
     *
     * @param waypointId The id of the waypoint
     * @param fromTime   The scheduled time has to be greater or equal than that time
     * @param toTime     The scheduled time has to be smaller or equal than that time
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getAllPassagesForDay(Integer waypointId, LocalTime fromTime, LocalTime toTime)
            throws IOException {
        return getAllPassagesForDay(waypointId, null, fromTime, toTime);
    }

    /**
     * Returns all buses passing by the specified waypoint within the given interval of days.
     *
     * @param waypointId The id of the waypoint
     * @param validFrom  The passage has to be scheduled at or after this day
     * @param validTo    The passage has to be scheduled at or before this day
     * @return A List of passages matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Passage> getAllPassagesForInterval(Integer waypointId, LocalDate validFrom, LocalDate validTo)
            throws IOException {
        // Set query parameters
        List<BasicNameValuePair> queryParams = new ArrayList<>();

        if (validFrom != null)
            queryParams
                    .add(new BasicNameValuePair("validFrom", DateTimeFormat.forPattern("yyyyMMdd").print(validFrom)));

        if (validTo != null)
            queryParams.add(new BasicNameValuePair("validTo", DateTimeFormat.forPattern("yyyyMMdd").print(validTo)));

        // Create request URL
        String requesturl = String.format("passage/%s/all-for-interval", waypointId) + "?" +
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
    public List<Passage> getAllPassagesForInterval(Integer waypointId) throws IOException {
        return getAllPassagesForDay(waypointId, null, null);
    }

    /**
     * Returns a list of runs currently associated to the given line.
     *
     * @param lineId The id of the line
     * @return A List of runs matching the given parameters
     * @throws IOException If an exception during the request occurred
     */
    public List<Run> getLineTimesRuns(Integer lineId) throws IOException {
        // Create request URL
        String requesturl = String.format("line-times/%s/runs", lineId);

        // Send request
        String response = sendRequestAndGetResponse(requesturl);

        // Check returned status code, either throw IOException or return JSON as String
        return gson.fromJson(response, new TypeToken<List<Run>>() {}.getType());
    }
}