package de.janmm14.jsonmessagemaker.api;

@SuppressWarnings("unused")
public final class JsonMessageOptions {

    private boolean hover = true;
    private boolean suggest = true;
    private boolean insertion = true;
    private boolean link = true;

    public boolean hover() {
        return hover;
    }

    public boolean suggest() {
        return suggest;
    }

    public boolean insertion() {
        return insertion;
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

    public JsonMessageOptions insertion(boolean insertion) {
        this.insertion = insertion;
        return this;
    }

    public JsonMessageOptions link(boolean link) {
        this.link = link;
        return this;
    }

    public JsonMessageConverter create() {
        return new JsonMessageConverter(hover, insertion, suggest, link);
    }
}
