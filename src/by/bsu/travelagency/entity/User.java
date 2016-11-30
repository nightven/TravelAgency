package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 2/24/2016.
 */
public class User extends Entity {
    
    /** The id. */
    private long id;
    
    /** The login. */
    private String login;
    
    /** The password. */
    private String password;
    
    /** The role. */
    private int role;
    
    /** The email. */
    private String email;
    
    /** The name. */
    private String name;
    
    /** The surname. */
    private String surname;
    
    /** The discount. */
    private double discount;
    
    /** The money. */
    private int money;

    /**
     * Instantiates a new user.
     */
    public User() {
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login.
     *
     * @param login the new login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role.
     *
     * @return the role
     */
    public int getRole() {
        return role;
    }

    /**
     * Sets the role.
     *
     * @param role the new role
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     *
     * @param surname the new surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the discount.
     *
     * @return the discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Sets the discount.
     *
     * @param discount the new discount
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Gets the money.
     *
     * @return the money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets the money.
     *
     * @param money the new money
     */
    public void setMoney(int money) {
        this.money = money;
    }
}
