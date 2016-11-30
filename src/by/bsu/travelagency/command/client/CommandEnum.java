package by.bsu.travelagency.command.client;

import by.bsu.travelagency.command.*;

/**
 * Created by Михаил on 2/16/2016.
 */
public enum CommandEnum {
    
    /** The login. */
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    
    /** The logout. */
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    
    /** The register. */
    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    
    /** The english language. */
    ENGLISH_LANGUAGE {
        {
            this.command = new EnglishLanguageCommand();
        }
    },
    
    /** The russian language. */
    RUSSIAN_LANGUAGE {
        {
            this.command = new RussianLanguageCommand();
        }
    },
    
    /** The vacation list. */
    VACATION_LIST {
        {
            this.command = new VacationListCommand();
        }
    },
    
    /** The vacation full. */
    VACATION_FULL {
        {
            this.command = new VacationFullCommand();
        }
    },
    
    /** The trip list. */
    TRIP_LIST {
        {
            this.command = new TripListCommand();
        }
    },
    
    /** The trip full. */
    TRIP_FULL {
        {
            this.command = new TripFullCommand();
        }
    },
    
    /** The shopping list. */
    SHOPPING_LIST {
        {
            this.command = new ShoppingListCommand();
        }
    },
    
    /** The shopping full. */
    SHOPPING_FULL {
        {
            this.command = new ShoppingFullCommand();
        }
    },
    
    /** The tour list. */
    TOUR_LIST {
        {
            this.command = new TourListCommand();
        }
    },
    
    /** The create vacation. */
    CREATE_VACATION {
        {
            this.command = new CreateVacationCommand();
        }
    },
    
    /** The create trip. */
    CREATE_TRIP {
        {
            this.command = new CreateTripCommand();
        }
    },
    
    /** The create shopping. */
    CREATE_SHOPPING {
        {
            this.command = new CreateShoppingCommand();
        }
    },
    
    /** The vacation admin list. */
    VACATION_ADMIN_LIST {
        {
            this.command = new VacationAdminListCommand();
        }
    },
    
    /** The trip admin list. */
    TRIP_ADMIN_LIST {
        {
            this.command = new TripAdminListCommand();
        }
    },
    
    /** The shopping admin list. */
    SHOPPING_ADMIN_LIST {
        {
            this.command = new ShoppingAdminListCommand();
        }
    },
    
    /** The edit vacation page. */
    EDIT_VACATION_PAGE {
        {
            this.command = new EditVacationPageCommand();
        }
    },
    
    /** The edit vacation. */
    EDIT_VACATION{
        {
            this.command = new EditVacationCommand();
        }
    },
    
    /** The edit trip page. */
    EDIT_TRIP_PAGE {
        {
            this.command = new EditTripPageCommand();
        }
    },
    
    /** The edit trip. */
    EDIT_TRIP{
        {
            this.command = new EditTripCommand();
        }
    },
    
    /** The edit shopping page. */
    EDIT_SHOPPING_PAGE {
        {
            this.command = new EditShoppingPageCommand();
        }
    },
    
    /** The edit shopping. */
    EDIT_SHOPPING{
        {
            this.command = new EditShoppingCommand();
        }
    },
    
    /** The vacation delete admin list. */
    VACATION_DELETE_ADMIN_LIST{
        {
            this.command = new VacationDeleteAdminListCommand();
        }
    },
    
    /** The trip delete admin list. */
    TRIP_DELETE_ADMIN_LIST{
        {
            this.command = new TripDeleteAdminListCommand();
        }
    },
    
    /** The shopping delete admin list. */
    SHOPPING_DELETE_ADMIN_LIST{
        {
            this.command = new ShoppingDeleteAdminListCommand();
        }
    },
    
    /** The delete vacation. */
    DELETE_VACATION{
        {
            this.command = new DeleteVacationCommand();
        }
    },
    
    /** The delete trip. */
    DELETE_TRIP{
        {
            this.command = new DeleteTripCommand();
        }
    },
    
    /** The delete shopping. */
    DELETE_SHOPPING{
        {
            this.command = new DeleteShoppingCommand();
        }
    },
    
    /** The order vacation. */
    ORDER_VACATION{
        {
            this.command = new OrderVacationCommand();
        }
    },
    
    /** The order trip. */
    ORDER_TRIP{
        {
            this.command = new OrderTripCommand();
        }
    },
    
    /** The order shopping. */
    ORDER_SHOPPING{
        {
            this.command = new OrderShoppingCommand();
        }
    },
    
    /** The order list. */
    ORDER_LIST{
        {
            this.command = new OrderListCommand();
        }
    },
    
    /** The delete order. */
    DELETE_ORDER{
        {
            this.command = new DeleteOrderCommand();
        }
    },
    
    /** The balance. */
    BALANCE{
        {
            this.command = new BalancePageCommand();
        }
    },
    
    /** The balance add. */
    BALANCE_ADD{
        {
            this.command = new BalanceAddCommand();
        }
    },
    
    /** The change password. */
    CHANGE_PASSWORD{
        {
            this.command = new ChangePasswordCommand();
        }
    },
    
    /** The create user. */
    CREATE_USER{
        {
            this.command = new CreateUserCommand();
        }
    },
    
    /** The user admin list. */
    USER_ADMIN_LIST{
        {
            this.command = new UserAdminListCommand();
        }
    },
    
    /** The edit user page. */
    EDIT_USER_PAGE{
        {
            this.command = new EditUserPageCommand();
        }
    },
    
    /** The edit user. */
    EDIT_USER{
        {
            this.command = new EditUserCommand();
        }
    },
    
    /** The user delete admin list. */
    USER_DELETE_ADMIN_LIST{
        {
            this.command = new UserDeleteAdminListCommand();
        }
    },
    
    /** The delete user. */
    DELETE_USER{
        {
            this.command = new DeleteUserCommand();
        }
    },
    
    /** The search. */
    SEARCH{
        {
            this.command = new SearchListCommand();
        }
    },
    
    /** The vacation sort. */
    VACATION_SORT{
        {
            this.command = new VacationSortCommand();
        }
    },
    
    /** The trip sort. */
    TRIP_SORT{
        {
            this.command = new TripSortCommand();
        }
    },
    
    /** The shopping sort. */
    SHOPPING_SORT{
        {
            this.command = new ShoppingSortCommand();
        }
    },
    
    /** The forward. */
    FORWARD {
        {
            this.command = new ForwardCommand();
        }
    };
    
    /** The command. */
    ActionCommand command;
    
    /**
     * Gets the current command.
     *
     * @return the current command
     */
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
