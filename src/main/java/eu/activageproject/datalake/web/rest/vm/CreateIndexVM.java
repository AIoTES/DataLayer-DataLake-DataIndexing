package eu.activageproject.datalake.web.rest.vm;

public class CreateIndexVM {
    private CreateIndexOption options;

    public CreateIndexOption getOptions() {
        return options;
    }

    public void setOptions(CreateIndexOption options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "CreateIndexVM{" +
            "options=" + options +
            '}';
    }
}
