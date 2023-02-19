package jp.cron.sample.api.service.exception.impl;

import jp.cron.sample.api.service.exception.Handler;
import jp.cron.sample.util.PermissionUtil;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Component;

@Component
public class InsufficientPermissionExceptionHandler extends Handler<InsufficientPermissionException> {
    public InsufficientPermissionExceptionHandler() {
        super(InsufficientPermissionException.class);
    }

    @Override
    public MessageCreateData handle(InsufficientPermissionException ex) {
        return new MessageCreateBuilder()
                .setContent("BOTに権限が足りません。\n"+ PermissionUtil.localizedName(ex.getPermission())).build();
    }
}
