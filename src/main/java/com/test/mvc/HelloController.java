package com.test.mvc;

import com.test.mvc.exceptions.NoSuchBusException;
import com.test.mvc.exceptions.NotEnoughMoneyException;
import com.test.mvc.security.SuperSecurityService;
import com.test.mvc.service.BusService;
import com.test.mvc.service.UserService;
import com.test.mvc.valueObjects.Bus;
import com.test.mvc.valueObjects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
public class HelloController {

    public static final String OPERATION_SUCCESS = "Operation success";
    public static final String OPERATION_FAILED = "Operation failed";
    public static final String USER_IS_NOT_EXIST = "User is not exist";
    public static final String PLEASE_LOG_IN = "Please log in";
    public static final String NO_MONEY = "NO MONEY";
    public static final String BUS_BOUGHT = "bus bought :)";
    public static final String USER_HASN_T_SUCH_BUS = "User hasn't such bus";
    public static final String BUS_SOLD = "Bus Sold";

    private BusService busService;
    private UserService userService;
    private SuperSecurityService securityService;

    @Autowired(required = true)
    @Qualifier(value = "securityService")
    public void setSecurityService(SuperSecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired(required = true)
    @Qualifier(value = "busService")
    public void setBusService(BusService bs) {
        this.busService = bs;
    }

    @RequestMapping(value = "/view-bus", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Bus> listBuss() {
        return busService.listBuss();
    }

    @RequestMapping(value = "/login/{login}", method = RequestMethod.GET)
    public
    @ResponseBody
    String login(@PathVariable String login) {
        User user = userService.getUserByLogin(login);
        if (user != null) {
            securityService.login(user);
            return OPERATION_SUCCESS;
        }
        return USER_IS_NOT_EXIST;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public
    @ResponseBody
    String logout() {
        securityService.logout();
        return OPERATION_SUCCESS;
    }


    @RequestMapping(value = "/my-info", method = RequestMethod.GET)
    public
    @ResponseBody
    Object userInfo() {
        User logedInUser = securityService.getLogedInUser();
        if (!securityService.isLogedIn(logedInUser)) {
            return PLEASE_LOG_IN;
        }
        User userById = userService.getUserById(logedInUser.getId());
        return userById.getBuss();
    }

    @RequestMapping(value = "/buy/{tc}", method = RequestMethod.GET)
    public
    @ResponseBody
    String buy(@PathVariable String tc) {
        User logedInUser = securityService.getLogedInUser();
        if (!securityService.isLogedIn(logedInUser)) {
            return PLEASE_LOG_IN;
        }
        try {
            busService.buyBus(logedInUser, tc);
            return BUS_BOUGHT;
        } catch (NotEnoughMoneyException notEnoughMoneyException) {
            return NO_MONEY;
        }
    }


    @RequestMapping(value = "/sell/{tc}", method = RequestMethod.GET)
    public
    @ResponseBody
    String sell(@PathVariable String tc) {
        User logedInUser = securityService.getLogedInUser();
        if (!securityService.isLogedIn(logedInUser)) {
            return PLEASE_LOG_IN;
        }
        try {
            busService.sellBus(logedInUser, tc);
            return BUS_SOLD;
        } catch (NoSuchBusException e) {
            return USER_HASN_T_SUCH_BUS;
        }

    }

}