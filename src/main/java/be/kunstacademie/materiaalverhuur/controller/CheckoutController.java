package be.kunstacademie.materiaalverhuur.controller;

import be.kunstacademie.materiaalverhuur.model.Order;
import be.kunstacademie.materiaalverhuur.model.User;
import be.kunstacademie.materiaalverhuur.service.OrderService;
import be.kunstacademie.materiaalverhuur.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public String showCheckout(Authentication auth, Model model) {
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "checkout";
    }

    @PostMapping("/confirm")
    public String confirmOrder(Authentication auth, RedirectAttributes redirectAttrs) {
        try {
            User user = userService.findByUsername(auth.getName());
            Order order = orderService.checkout(user);
            redirectAttrs.addFlashAttribute("orderId", order.getId());
            redirectAttrs.addFlashAttribute("confirmationNumber", order.getConfirmationNumber());
            return "redirect:/checkout/confirmation";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
    }

    @GetMapping("/confirmation")
    public String showConfirmation(Model model) {
        return "confirmation";
    }

    @GetMapping("/orders")
    public String showOrders(Authentication auth, Model model) {
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("orders", orderService.getUserOrders(user));
        return "orders";
    }
}
