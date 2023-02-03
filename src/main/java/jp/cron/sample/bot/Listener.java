package jp.cron.sample.bot;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Listener extends ListenerAdapter {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        logger.info("Bot is ready");
        logger.info("BOT NAME: " + event.getJDA().getSelfUser().getName());
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;
    }
}


