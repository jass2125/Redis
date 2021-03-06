/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.redis.core.controllers;

import io.github.jass2125.redis.core.services.client.ProductService;
import io.github.jass2125.redis.core.entity.Cart;
import io.github.jass2125.redis.core.entity.Item;
import io.github.jass2125.redis.core.entity.Product;
import io.github.jass2125.redis.core.entity.UserPrincipal;
import io.github.jass2125.redis.core.exceptions.CartException;
import io.github.jass2125.redis.core.annotations.Security;
import io.github.jass2125.redis.core.services.client.CartService;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import io.github.jass2125.redis.core.annotations.UserSession;

/**
 *
 * @author <a href="mailto:jair_anderson_bs@hotmail.com">Anderson Souza</a>
 * @author 27/08/2017 18:57:40
 */
@Named
@RequestScoped
@Security
public class CartBean implements Serializable {

    @Inject
    private Item item;
    @Inject
    private Product product;
    @Inject
    private ProductService productService;
    @Inject
    private FacesContext context;
    @Inject
    private CartService cartService;
    @Inject
    @UserSession
    private UserPrincipal userPrincipalOnession;
    @Inject
    private Cart cart;

    public CartBean() {
    }

    public Product getProduct() {
        return product;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getLoadProducts() {
        return productService.getProducts();
    }

    public String addProduct() {
        try {
            item.setProduct(product);
            cart.addItem(item);
            cartService.saveCart(userPrincipalOnession.getId(), cart);
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Seu item foi adicionado com sucesso", null));
        } catch (CartException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seu item foi adicionado com sucesso", null));
        }
        return "home.xhtml/faces-redirect=true";
    }

    public List<Item> getItems() {
        try {
            cart = cartService.getCart(String.valueOf(userPrincipalOnession.getId()));
            return cart.getItems();
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }
}
