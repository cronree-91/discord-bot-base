package jp.cron.sample.service.exception.impl;

import jp.cron.sample.service.exception.Handler;
import jp.cron.sample.util.PermissionUtil;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import org.springframework.stereotype.Component;

@Component
public class InsufficientPermissionExceptionHandler extends Handler<InsufficientPermissionException> {
    public InsufficientPermissionExceptionHandler() {
        super(InsufficientPermissionException.class);
    }

    @Override
    public void handle(InsufficientPermissionException ex, TextChannel ch) {
        ch.sendMessage("BOTに権限が足りません。\n"+ PermissionUtil.localizedName(ex.getPermission())).queue();
    }
}
