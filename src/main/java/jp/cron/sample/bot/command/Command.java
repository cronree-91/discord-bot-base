package jp.cron.sample.bot.command;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import jp.cron.sample.api.service.exception.ExceptionHandler;

public abstract class Command extends SlashCommand {
    ExceptionHandler handler;
    public Command(ExceptionHandler handler, String name, String help, String... aliases) {
        this.handler = handler;
        this.name = name;
        this.help = help;
        this.aliases = aliases;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        try {
            process(event);
        } catch (Exception ex) {
            handler.handle(ex, event.getTextChannel());
        }
    }

    protected abstract void process(SlashCommandEvent event);
}
