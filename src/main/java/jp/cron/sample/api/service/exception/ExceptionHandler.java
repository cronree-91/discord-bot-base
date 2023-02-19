package jp.cron.sample.api.service.exception;

import jp.cron.sample.util.EmbedUtil;
import jp.cron.sample.util.ILogger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ExceptionHandler implements ILogger {

//    @Value("${bot.report.error}")
//    String errorReportChannelId;

    @Autowired
    List<Handler> handlers;
    @Autowired
    EmbedUtil embedUtil;

    Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public void handle(Throwable ex, Guild g) {
        handle(
                ex,
                g.getJDA(),
                null,
                null,
                null
        );
    }

    public void handle(Throwable ex, TextChannel ch) {
        handle(ex, ch.getJDA(), ch, null, null);
    }

    public void handle(Throwable ex, Message msg) {
        handle(ex, msg.getJDA(), null, msg, null);
    }

    public void handle(Throwable ex, JDA jda) {
        handle(ex, jda, null, null, null);
    }

    public void handle(Throwable ex, IReplyCallback replyCallback) {
        handle(ex, replyCallback.getJDA(), null, null, replyCallback);
    }


    private void handle(Throwable ex, JDA jda, TextChannel ch, Message msg, IReplyCallback replyCallback) {
        TextChannel errorChannel = null;
//        TextChannel errorChannel = jda.getTextChannelById(errorReportChannelId);
        if (ch != null || msg != null || replyCallback != null) {
            for (Handler handler : handlers) {
                if (handler.isHandleable(ex)) {
                    MessageCreateData msgData = handler.handle(ex);
                    if (msgData == null)
                        continue;
                    if (msg != null)
                        msg.reply(msgData).queue();
                    else if (ch != null)
                        ch.sendMessage(msgData).queue();
                    else if (replyCallback != null)
                        replyCallback.reply(msgData).queue();
                    return;
                }
            }
        }
        Guild guild;
        if (ch!=null)
            guild = ch.getGuild();
        else if (msg!=null)
            guild = msg.getGuild();
        else
            guild = null;

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        pw.flush();
        String stacktrace = sw.toString();

        if (errorChannel!=null)
            errorChannel.sendMessage(
                    new MessageCreateBuilder()
                            .addEmbeds(
                                    embedUtil.errorInfoEmbed(ex, guild)
                                            .setTitle("処理できない例外が発生しました。")
                                            .build()
                            )
                            .addFiles(FileUpload.fromData(
                                    stacktrace.getBytes(StandardCharsets.UTF_8), "stacktrace.txt"
                            ))
                            .build()
            ).queue();

        getLogger().error("Unhandled exception");
        ex.printStackTrace();

    }
}
