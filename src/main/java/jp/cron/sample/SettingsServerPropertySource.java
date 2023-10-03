package jp.cron.sample;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class SettingsServerPropertySource extends PropertySource<String> {
    public SettingsServerPropertySource() {
        super("settings-server");
    }

    HttpClient client = HttpClient.newHttpClient();

    @Override
    public Object getProperty(@NotNull String name) {
        String uri = System.getenv("C_SETTINGS_SERVER_URI");
        String secret = System.getenv("C_SETTINGS_SERVER_SECRET");

        if (uri==null || secret==null)
            return null;

        if (!uri.endsWith("/"))
            uri = uri + "/";

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(uri + name))
                .header("Authorization", secret)
                .build();

        try {
            HttpResponse<String> res = client.send(req, responseInfo ->
                            HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8));

            if (res.statusCode()==200) {
                return res.body();
            } else if (res.statusCode()==404) {
                return null;
            } else {
                logger.error("SettingsServer returned an unexpected status code: "+res.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
