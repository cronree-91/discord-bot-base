package jp.cron.sample;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import jp.cron.sample.util.ILogger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner, ILogger {

    @Autowired
    CommandClientBuilder commandClientBuilder;
    @Autowired
    JDABuilder jdaBuilder;
    @Value("${bot.info.clientid}")
    String clientId;

    @Override
    public void run(String... args) throws Exception {
        CommandClient client = commandClientBuilder.build();
        JDA jda = jdaBuilder
                .addEventListeners(client)
                .build();

        while (jda.getStatus() != JDA.Status.SHUTDOWN) {
        }

        getLogger().warn("SHUTTING DOWN...");

        // SHUTDOWN

        getLogger().warn("SHUTDOWN COMPLETE");
        System.exit(0);
    }
}