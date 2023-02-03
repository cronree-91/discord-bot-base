package jp.cron.sample.util;

import jp.cron.sample.profile.Profile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class EmbedUtil {

    @Autowired
    Profile profile;

    public EmbedBuilder addBaseFields(EmbedBuilder b) {
        return b.addField("不具合または質問等がありますか？", "サポートサーバーまでご連絡ください。\n[公式サポートサーバー]("+profile.serverInvite+")", false)
                .addField("このBOTを使用してみたいですか？", "[招待リンク]("+profile.botInvite+")\n[使い方](https://zenn.dev/kuronekoserver/articles/067d586519f43c)", false)
                .setFooter("Developed by "+profile.authorName, profile.authorIcon);
    }

    public EmbedBuilder generateErrorEmbed(String title, String body) {
        EmbedBuilder b = new EmbedBuilder()
                .setTitle(title)
                .setDescription(body)
                .setColor(Color.RED);
        return addBaseFields(b);
    }

    public EmbedBuilder generateSuccessEmbed(String title, String body) {
        EmbedBuilder b = new EmbedBuilder()
                .setTitle(title)
                .setDescription(body)
                .setColor(Color.GREEN);
        return addBaseFields(b);
    }

    public EmbedBuilder generateWarnEmbed(String title, String body) {
        EmbedBuilder b = new EmbedBuilder()
                .setTitle(title)
                .setDescription(body)
                .setColor(Color.YELLOW);
        return addBaseFields(b);
    }

    public EmbedBuilder errorInfoEmbed(Throwable ex, Guild g) {
        return new EmbedBuilder()
                .setTitle("エラーが発生しました。")
                .setColor(Color.RED)
                .addField("エラークラス", ex.getClass().getCanonicalName(), false)
                .addField("エラー内容", ex.getMessage().substring(0, Math.min(ex.getMessage().length(), 1023)), false)
                .addField("サーバー名", g.getName(), false)
                .addField("サーバーID", g.getId(), false);
    }

}
