package net.dgardiner.markdown.flavours.github.decorators.tokens;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.decorators.tokens.base.TokenDecorator;

public class GithubEmojiDecorator extends TokenDecorator {
    public static final String ID = "github:emoji";

    public GithubEmojiDecorator() { super(ID); }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        if(args.length != 1) {
            return false;
        }

        String key = args[0];

        out.append("<img class=\"emoji\" title=\":")
           .append(key)
           .append(":\" alt=\":")
           .append(key)
           .append(":\" src=\"https://assets.github.com/images/icons/emoji/")
           .append(key)
           .append(".png\" height=\"20\" width=\"20\" align=\"absmiddle\">");

        return true;
    }
}
