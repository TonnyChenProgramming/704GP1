package com.g7.ip;

import java.sql.SQLException;

/**
 * POS (POS_BatchManager_Spec.md, Section 2).
 *
 * Accepts a purchase order, validates it against what's on file in Recipes,
 * and on success persists it to Orders as PENDING via {@link Dao#insertOrder}.
 * On failure it writes nothing and returns a rejection reason.
 *
 * POS does not talk to the Coordinator directly — order content is pulled
 * later by the Batch Manager, not pushed (Section 2/3). recipe_id and
 * bottle_spec are validation-only here: the Orders table (reused as-is from
 * the IP schema) has no recipe/bottle_spec columns, because recipe
 * attribution happens once, per batch, when the Batch Manager admits a
 * batch — not per order.
 */
public class POS {

    private final Dao dao;

    public POS(Dao dao) {
        this.dao = dao;
    }

    /** Result of a SubmitOrder call: either Accepted(order_id) or Rejected(reason) (Section 3). */
    public static class SubmitResult {
        public final boolean accepted;
        public final Integer orderId; // set iff accepted
        public final String reason;   // set iff rejected

        private SubmitResult(boolean accepted, Integer orderId, String reason) {
            this.accepted = accepted;
            this.orderId = orderId;
            this.reason = reason;
        }

        static SubmitResult accepted(int orderId) {
            return new SubmitResult(true, orderId, null);
        }

        static SubmitResult rejected(String reason) {
            return new SubmitResult(false, null, reason);
        }

        @Override
        public String toString() {
            return accepted ? "Accepted(order_id=" + orderId + ")" : "Rejected(" + reason + ")";
        }
    }

    /**
     * SubmitOrder(customer_po, customer_id, product_id, quantity, bottle_spec, recipe)
     * -> Accepted(order_id) | Rejected(reason) (Section 3).
     *
     * recipeId identifies which recipe formulation the customer expects for this
     * product; it must exist, belong to product_id, and its bottle_type must match
     * bottle_spec, or the order is rejected without writing anything.
     */
    public SubmitResult submitOrder(String customerPo, String customerId, String productId,
                                     int quantity, String bottleSpec, int recipeId) throws SQLException {
        if (quantity <= 0) {
            return SubmitResult.rejected("quantity must be > 0, got " + quantity);
        }

        Dao.RecipeInfo recipe = dao.getRecipe(recipeId);
        if (recipe == null) {
            return SubmitResult.rejected("unknown recipe_id " + recipeId);
        }
        if (!recipe.productId.equals(productId)) {
            return SubmitResult.rejected("recipe " + recipeId + " is for product " + recipe.productId
                    + ", not " + productId);
        }
        if (!recipe.bottleType.equals(bottleSpec)) {
            return SubmitResult.rejected("bottle_spec '" + bottleSpec + "' does not match recipe " + recipeId
                    + "'s bottle_type '" + recipe.bottleType + "'");
        }

        int orderId = dao.insertOrder(customerPo, customerId, productId, quantity);
        return SubmitResult.accepted(orderId);
    }
}
