package config;

import controller.WiseSayingController;
import repository.WiseSayingRepository;
import service.WiseSayingService;

public class BeanInitialization {
    private WiseSayingService wiseSayingService() {
        return new WiseSayingService(new WiseSayingRepository());
    }

    public WiseSayingController wiseSayingController() {
        return new WiseSayingController(wiseSayingService());
    }
}
