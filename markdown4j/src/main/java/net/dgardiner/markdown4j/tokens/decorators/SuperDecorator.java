package net.dgardiner.markdown4j.tokens.decorators;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Emitter;
import net.dgardiner.markdown4j.tokens.decorators.core.TokenDecorator;

public class SuperDecorator extends TokenDecorator {
    public static final String ID = "super";

    public SuperDecorator() {
        super(ID);
    }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        out.append("<sup>");
        return true;
    }

    @Override
    public boolean close(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        out.append("</sup>");
        return true;
    }
}
