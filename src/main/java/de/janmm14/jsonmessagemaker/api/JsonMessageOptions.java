package de.janmm14.jsonmessagemaker.api;

@SuppressWarnings("unused")
public final class JsonMessageOptions {

    private boolean hover = true;
    private boolean suggest = true;
    private boolean run = true;
    private boolean link = true;

    public boolean hover() {
        return hover;
    }

    public boolean suggest() {
        return suggest;
    }

    public boolean run() {
        return run;
    }

    public boolean link() {
        return link;
    }

    public JsonMessageOptions hover(boolean hover) {
        this.hover = hover;
        return this;
    }

    public JsonMessageOptions suggest(boolean suggest) {
        this.suggest = suggest;
        return this;
    }

    public JsonMessageOptions run(boolean run) {
        this.run = run;
        return this;
    }

    public JsonMessageOptions link(boolean link) {
        this.link = link;
        return this;
    }

    public JsonMessageConverter create() {
        return new JsonMessageConverter(hover, run, suggest, link);
    }
}
