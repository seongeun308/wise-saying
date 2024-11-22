package config;

import controller.WiseSayingController;
import repository.WiseSayingRepository;
import service.WiseSayingService;
import view.InputView;

public class BeanInitialization {
    private final InputView inputView = new InputView();

    private WiseSayingService wiseSayingService() {
        return new WiseSayingService(new WiseSayingRepository());
    }

    public WiseSayingController wiseSayingController() {
        return new WiseSayingController(wiseSayingService(), inputView());
    }

    public InputView inputView() {
        return inputView;
    }

}
