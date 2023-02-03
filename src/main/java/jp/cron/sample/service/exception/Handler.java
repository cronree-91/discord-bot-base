package jp.cron.sample.service.exception;


import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public abstract class Handler<T extends Throwable> {

    Class<T> clazz;

    public Handler(Class<T> clazz) {
        this.clazz = clazz;
    }

    public abstract void handle(T ex, TextChannel ch);

    public boolean isHandleable(Throwable ex, TextChannel ch) {
        if (clazz.isInstance(ex)) {
            handle(clazz.cast(ex), ch);
            return true;
        }
        return false;
    }
}
