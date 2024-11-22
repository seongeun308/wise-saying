import config.BeanInitialization;
import controller.WiseSayingController;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        BeanInitialization beanInitialization = new BeanInitialization();
        WiseSayingController controller = beanInitialization.wiseSayingController();
        controller.run();
    }
}
