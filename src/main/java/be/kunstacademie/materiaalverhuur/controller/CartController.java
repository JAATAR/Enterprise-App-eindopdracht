package be.kunstacademie.materiaalverhuur.controller;

import be.kunstacademie.materiaalverhuur.model.CartItem;
import be.kunstacademie.materiaalverhuur.model.User;
import be.kunstacademie.materiaalverhuur.service.CartService;
import be.kunstacademie.materiaalverhuur.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public String viewCart(Authentication auth, Model model) {
        User user = userService.findByUsername(auth.getName());
        List<CartItem> cartItems = cartService.getCartItems(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartService.calculateCartTotal(user));

        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam int quantity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Authentication auth,
            RedirectAttributes redirectAttrs) {

        try {
            User user = userService.findByUsername(auth.getName());
            cartService.addToCart(user, productId, quantity, startDate, endDate);
            redirectAttrs.addFlashAttribute("success", "Product toegevoegd aan winkelmandje");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cart";
    }

    @PostMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            cartService.removeFromCart(id);
            redirectAttrs.addFlashAttribute("success", "Product verwijderd uit winkelmandje");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Fout bij verwijderen: " + e.getMessage());
        }

        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(Authentication auth, RedirectAttributes redirectAttrs) {
        User user = userService.findByUsername(auth.getName());
        cartService.clearCart(user);
        redirectAttrs.addFlashAttribute("success", "Winkelmandje geleegd");
        return "redirect:/cart";
    }
}
