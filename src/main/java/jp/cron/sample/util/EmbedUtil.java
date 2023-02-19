package jp.cron.sample.util;

import jp.cron.sample.AuthorInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class EmbedUtil {

    @Value("${bot.info.botInvite}")
    String botInvite;

    public EmbedBuilder addBaseFields(EmbedBuilder b) {
        return b.addField("不具合または質問等がありますか？", "サポートサーバーまでご連絡ください。\n[公式サポートサーバー]("+ AuthorInfo.SERVER_INVITE +")", false)
                .addField("このBOTを使用してみたいですか？", "[招待リンク]("+botInvite+")", false)
                .setFooter("Developed by "+AuthorInfo.NAME, AuthorInfo.AVATAR);
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
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("エラーが発生しました。")
                .setColor(Color.RED)
                .addField("エラークラス", ex.getClass().getCanonicalName(), false)
                .addField("エラー内容", ex.getMessage().substring(0, Math.min(ex.getMessage().length(), 1023)), false);
        if (g!=null)
            eb.addField("サーバー名", g.getName(), false)
                    .addField("サーバーID", g.getId(), false);
        return eb;
    }

}
