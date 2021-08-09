package by.kostopravov.polyclinic.sirvice.covidDataService;

import by.kostopravov.polyclinic.dto.covidModel.Location;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidDataService {

    private static final String COVID_DATASOURCE_URL =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/" +
                    "csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    private List<Location> virusData = new ArrayList<>();

    public List<Location> getVirusData() {
        return virusData;
    }

    @PostConstruct
    @Scheduled(cron = "* * * 1 * *") //run first hour of every day
    public void parseVirusData() throws IOException, InterruptedException {
        List<Location> newData = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(COVID_DATASOURCE_URL)).
                build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            StringReader csvReader = new StringReader(response.body());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
            for (CSVRecord record : records) {
                Location location = new Location();
                location.setState(record.get("Province/State"));
                location.setCountry(record.get("Country/Region"));
                int lastDayCases = Integer.parseInt(record.get(record.size() - 1));
                int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
                location.setLastTotalCases(lastDayCases);
                location.setDelta(lastDayCases - prevDayCases);
                newData.add(location);
            }
            this.virusData = newData;
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }
}
