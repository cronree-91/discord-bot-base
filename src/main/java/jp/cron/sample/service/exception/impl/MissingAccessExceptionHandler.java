package jp.cron.sample.service.exception.impl;

import jp.cron.sample.service.exception.Handler;
import jp.cron.sample.util.EmbedUtil;
import jp.cron.sample.util.PermissionUtil;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.MissingAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MissingAccessExceptionHandler extends Handler<MissingAccessException> {

    public MissingAccessExceptionHandler() {
        super(MissingAccessException.class);
    }

    @Autowired
    EmbedUtil embedUtil;

    @Override
    public void handle(MissingAccessException ex, TextChannel ch) {
        ch.sendMessageEmbeds(
                embedUtil.generateErrorEmbed("BOTの権限が足りません。", "チャンネル<#"+ex.getChannelId()+"> での権限 "+ PermissionUtil.localizedName(ex.getPermission()) +" がありません。")
                        .build()
        ).queue();
    }
}
