// 代码生成时间: 2025-10-14 02:55:37
package com.example.supplychain;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.data.validation.Constraints;
import static play.mvc.Results.ok;
import static play.mvc.Results.badRequest;
import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;
import play.Logger;

// Define the Product entity
@Entity
public class Product extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Constraints.Required
    public String name;
    @Constraints.Required
    public String description;

    public static Finder<Long, Product> find = new Finder<>(Long.class, Product.class);

    public static Product create(Product product) {
        product.save();
        return product;
    }

    public static Product update(Long id, Product product) {
        Product existingProduct = find.byId(id);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found.");
        }
        existingProduct.name = product.name;
        existingProduct.description = product.description;
        existingProduct.update();
        return existingProduct;
    }

    public static Product delete(Long id) {
        Product existingProduct = find.byId(id);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found.");
        }
        existingProduct.delete();
        return existingProduct;
    }
}

// Define the Supplier entity
@Entity
public class Supplier extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Constraints.Required
    public String companyName;
    @Constraints.Required
    public String contactInfo;

    public static Finder<Long, Supplier> find = new Finder<>(Long.class, Supplier.class);

    public static Supplier create(Supplier supplier) {
        supplier.save();
        return supplier;
    }

    public static Supplier update(Long id, Supplier supplier) {
        Supplier existingSupplier = find.byId(id);
        if (existingSupplier == null) {
            throw new RuntimeException("Supplier not found.");
        }
        existingSupplier.companyName = supplier.companyName;
        existingSupplier.contactInfo = supplier.contactInfo;
        existingSupplier.update();
        return existingSupplier;
    }

    public static Supplier delete(Long id) {
        Supplier existingSupplier = find.byId(id);
        if (existingSupplier == null) {
            throw new RuntimeException("Supplier not found.");
        }
        existingSupplier.delete();
        return existingSupplier;
    }
}

// Define the Order entity
@Entity
public class Order extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long productId;
    public Long supplierId;
    public Integer quantity;
    public String status;

    public static Finder<Long, Order> find = new Finder<>(Long.class, Order.class);

    public static Order create(Order order) {
        order.save();
        return order;
    }

    public static Order update(Long id, Order order) {
        Order existingOrder = find.byId(id);
        if (existingOrder == null) {
            throw new RuntimeException("Order not found.");
        }
        existingOrder.productId = order.productId;
        existingOrder.supplierId = order.supplierId;
        existingOrder.quantity = order.quantity;
        existingOrder.status = order.status;
        existingOrder.update();
        return existingOrder;
    }

    public static Order delete(Long id) {
        Order existingOrder = find.byId(id);
        if (existingOrder == null) {
            throw new RuntimeException("Order not found.");
        }
        existingOrder.delete();
        return existingOrder;
    }
}

// Define the SupplyChainManagement controller
public class SupplyChainManagement extends Controller {
    // Endpoint to add a new product
    public Result addProduct() {
        Form<Product> productForm = Form.form(Product.class).bindFromRequest();
        if (productForm.hasErrors()) {
            return badRequest(productForm.errorsAsJson());
        }
        Product newProduct = Product.create(productForm.get());
        return ok(Json.toJson(newProduct));
    }

    // Endpoint to update an existing product
    public Result updateProduct(Long id) {
        Form<Product> productForm = Form.form(Product.class).bindFromRequest();
        if (productForm.hasErrors()) {
            return badRequest(productForm.errorsAsJson());
        }
        Product updatedProduct = Product.update(id, productForm.get());
        return ok(Json.toJson(updatedProduct));
    }

    // Endpoint to delete a product
    public Result deleteProduct(Long id) {
        Product deletedProduct = Product.delete(id);
        return ok(Json.toJson(deletedProduct));
    }

    // Endpoint to add a new supplier
    public Result addSupplier() {
        Form<Supplier> supplierForm = Form.form(Supplier.class).bindFromRequest();
        if (supplierForm.hasErrors()) {
            return badRequest(supplierForm.errorsAsJson());
        }
        Supplier newSupplier = Supplier.create(supplierForm.get());
        return ok(Json.toJson(newSupplier));
    }

    // Endpoint to update an existing supplier
    public Result updateSupplier(Long id) {
        Form<Supplier> supplierForm = Form.form(Supplier.class).bindFromRequest();
        if (supplierForm.hasErrors()) {
            return badRequest(supplierForm.errorsAsJson());
        }
        Supplier updatedSupplier = Supplier.update(id, supplierForm.get());
        return ok(Json.toJson(updatedSupplier));
    }

    // Endpoint to delete a supplier
    public Result deleteSupplier(Long id) {
        Supplier deletedSupplier = Supplier.delete(id);
        return ok(Json.toJson(deletedSupplier));
    }

    // Endpoint to add a new order
    public Result addOrder() {
        Form<Order> orderForm = Form.form(Order.class).bindFromRequest();
        if (orderForm.hasErrors()) {
            return badRequest(orderForm.errorsAsJson());
        }
        Order newOrder = Order.create(orderForm.get());
        return ok(Json.toJson(newOrder));
    }

    // Endpoint to update an existing order
    public Result updateOrder(Long id) {
        Form<Order> orderForm = Form.form(Order.class).bindFromRequest();
        if (orderForm.hasErrors()) {
            return badRequest(orderForm.errorsAsJson());
        }
        Order updatedOrder = Order.update(id, orderForm.get());
        return ok(Json.toJson(updatedOrder));
    }

    // Endpoint to delete an order
    public Result deleteOrder(Long id) {
        Order deletedOrder = Order.delete(id);
        return ok(Json.toJson(deletedOrder));
    }
}