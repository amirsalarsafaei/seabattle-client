package apps.menu.controller;

import apps.game.controller.MatchingController;
import apps.game.views.Matching;
import apps.live.controllers.ListController;

public class MainMenuController {
    public void matchMakingButtonPressed() {
        Matching matching = new Matching();
        matching.main();
    }
    public void ShowGameList() {
        ListController listController = new ListController();
        listController.main();
    }
}
