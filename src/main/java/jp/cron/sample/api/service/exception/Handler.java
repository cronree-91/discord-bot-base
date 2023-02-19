package jp.cron.sample.api.service.exception;


import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public abstract class Handler<T extends Throwable> {

    Class<T> clazz;

    public Handler(Class<T> clazz) {
        this.clazz = clazz;
    }

    public abstract MessageCreateData handle(T ex);

    public boolean isHandleable(Throwable ex) {
        if (clazz.isInstance(ex)) {
            handle(clazz.cast(ex));
            return true;
        }
        return false;
    }
}
