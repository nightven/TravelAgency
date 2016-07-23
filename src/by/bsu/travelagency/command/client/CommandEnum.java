package by.bsu.travelagency.command.client;

import by.bsu.travelagency.command.*;

/**
 * Created by Михаил on 2/16/2016.
 */
public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    ENGLISH_LANGUAGE {
        {
            this.command = new EnglishLanguageCommand();
        }
    },
    RUSSIAN_LANGUAGE {
        {
            this.command = new RussianLanguageCommand();
        }
    },
    VACATION_LIST {
        {
            this.command = new VacationListCommand();
        }
    },
    VACATION_FULL {
        {
            this.command = new VacationFullCommand();
        }
    },
    TRIP_LIST {
        {
            this.command = new TripListCommand();
        }
    },
    TRIP_FULL {
        {
            this.command = new TripFullCommand();
        }
    },
    SHOPPING_LIST {
        {
            this.command = new ShoppingListCommand();
        }
    },
    SHOPPING_FULL {
        {
            this.command = new ShoppingFullCommand();
        }
    },
    TOUR_LIST {
        {
            this.command = new TourListCommand();
        }
    },
    UPLOAD {
        {
            this.command = new UploadCommand();
        }
    },
    FORWARD {
        {
            this.command = new ForwardCommand();
        }
    };
    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
