package jp.cron.sample.bot.command.impl;

import com.jagrosh.jdautilities.command.SlashCommandEvent;
import jp.cron.sample.bot.command.Command;
import jp.cron.sample.profile.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends Command {
    @Autowired
    public HelpCommand(Profile profile) {
        super(profile, "help", "ヘルプを表示します。");
    }

    @Autowired
    Profile profile;

    @Override
    protected void process(SlashCommandEvent event) {
    }
}
