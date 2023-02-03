package jp.cron.sample.bot.command;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import jp.cron.sample.profile.Profile;

import java.awt.*;

public abstract class Command extends SlashCommand {
    Profile profile;
    public Command(Profile profile, String name, String help, String... aliases) {
        this.profile = profile;
        this.name = name;
        this.help = help;
        this.aliases = aliases;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        try {
            process(event);
        } catch (Exception ex) {

        }
    }

    protected abstract void process(SlashCommandEvent event);
}
