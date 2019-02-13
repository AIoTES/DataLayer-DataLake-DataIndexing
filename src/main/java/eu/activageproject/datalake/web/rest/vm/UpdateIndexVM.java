package eu.activageproject.datalake.web.rest.vm;

public class UpdateIndexVM {

    private UpdateIndexOption options;

    public UpdateIndexOption getOptions() {
        return options;
    }

    public void setOptions(UpdateIndexOption options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "UpdateIndexVM{" +
            "options=" + options +
            '}';
    }
}
