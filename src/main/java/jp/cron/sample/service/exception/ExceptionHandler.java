package jp.cron.sample.service.exception;

import jp.cron.sample.profile.Profile;
import jp.cron.sample.util.EmbedUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ExceptionHandler {

    @Autowired
    List<Handler> handlers;
    @Autowired
    Profile profile;
    @Autowired
    EmbedUtil embedUtil;

    Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public void handle(Throwable ex, Guild g) {
        handle(ex,
                g.getTextChannels().stream().filter(TextChannel::canTalk).findFirst().orElseThrow(()->new RuntimeException("Not found loggable channels", ex))
        );
    }

    public void handle(Throwable ex, TextChannel ch) {
        System.out.print("Exception: "+ex.getClass()+" >"+ex.getMessage());
        ex.printStackTrace();

        if (profile.errorChannel==null)
            return;
        TextChannel errorChannel = ch.getJDA().getTextChannelById(profile.errorChannel);
        if (errorChannel==null)
            return;

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        pw.flush();
        String stacktrace = sw.toString();

        errorChannel.sendMessage(
                new MessageCreateBuilder()
                        .addEmbeds(
                                embedUtil.errorInfoEmbed(ex, ch.getGuild())
                                        .setTitle("処理できない例外が発生しました。")
                                        .build()
                        )
                        .addFiles(FileUpload.fromData(
                                stacktrace.getBytes(StandardCharsets.UTF_8), "stacktrace.txt"
                        ))
                        .build()
        ).queue();

        logger.error("Unhandled exception", ex);
    }
}
