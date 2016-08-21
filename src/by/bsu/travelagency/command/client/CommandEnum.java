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
    CREATE_VACATION {
        {
            this.command = new CreateVacationCommand();
        }
    },
    CREATE_TRIP {
        {
            this.command = new CreateTripCommand();
        }
    },
    CREATE_SHOPPING {
        {
            this.command = new CreateShoppingCommand();
        }
    },
    VACATION_ADMIN_LIST {
        {
            this.command = new VacationAdminListCommand();
        }
    },
    TRIP_ADMIN_LIST {
        {
            this.command = new TripAdminListCommand();
        }
    },
    SHOPPING_ADMIN_LIST {
        {
            this.command = new ShoppingAdminListCommand();
        }
    },
    EDIT_VACATION_PAGE {
        {
            this.command = new EditVacationPageCommand();
        }
    },
    EDIT_VACATION{
        {
            this.command = new EditVacationCommand();
        }
    },
    EDIT_TRIP_PAGE {
        {
            this.command = new EditTripPageCommand();
        }
    },
    EDIT_TRIP{
        {
            this.command = new EditTripCommand();
        }
    },
    EDIT_SHOPPING_PAGE {
        {
            this.command = new EditShoppingPageCommand();
        }
    },
    EDIT_SHOPPING{
        {
            this.command = new EditShoppingCommand();
        }
    },
    VACATION_DELETE_ADMIN_LIST{
        {
            this.command = new VacationDeleteAdminListCommand();
        }
    },
    TRIP_DELETE_ADMIN_LIST{
        {
            this.command = new TripDeleteAdminListCommand();
        }
    },
    SHOPPING_DELETE_ADMIN_LIST{
        {
            this.command = new ShoppingDeleteAdminListCommand();
        }
    },
    DELETE_VACATION{
        {
            this.command = new DeleteVacationCommand();
        }
    },
    DELETE_TRIP{
        {
            this.command = new DeleteTripCommand();
        }
    },
    DELETE_SHOPPING{
        {
            this.command = new DeleteShoppingCommand();
        }
    },
    ORDER_VACATION{
        {
            this.command = new OrderVacationCommand();
        }
    },
    ORDER_TRIP{
        {
            this.command = new OrderTripCommand();
        }
    },
    ORDER_SHOPPING{
        {
            this.command = new OrderShoppingCommand();
        }
    },
    ORDER_LIST{
        {
            this.command = new OrderListCommand();
        }
    },
    DELETE_ORDER{
        {
            this.command = new DeleteOrderCommand();
        }
    },
    BALANCE{
        {
            this.command = new BalancePageCommand();
        }
    },
    BALANCE_ADD{
        {
            this.command = new BalanceAddCommand();
        }
    },
    CHANGE_PASSWORD{
        {
            this.command = new ChangePasswordCommand();
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
