package jp.cron.sample.api.service.exception.impl;

import jp.cron.sample.api.service.exception.Handler;
import jp.cron.sample.util.EmbedUtil;
import jp.cron.sample.util.PermissionUtil;
import net.dv8tion.jda.api.exceptions.MissingAccessException;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
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
    public MessageCreateData handle(MissingAccessException ex) {
        return new MessageCreateBuilder()
                .setEmbeds(

                        embedUtil.generateErrorEmbed("BOTの権限が足りません。",
                                "チャンネル<#"+ex.getChannelId()+"> での権限 "+ PermissionUtil.localizedName(ex.getPermission()) +" がありません。")
                                .build()
                ).build();
    }
}
