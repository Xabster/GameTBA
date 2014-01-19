package platsnip.view;

import platsnip.model.State;

public interface View {
    public void render(State state);
    public void initialize();
}
