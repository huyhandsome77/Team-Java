    package uth.edu.jpa.models;

    import jakarta.persistence.*;

    import java.util.*;

    @Entity
    public class Cart {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        @JoinColumn(name = "user_userid", unique = true)
        private User user;

        @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        private List<CartItemEntity> items = new ArrayList<>();

        public void addItem(CartItemEntity item) {
            items.add(item);
            item.setCart(this);
        }

        public void removeItem(CartItemEntity item) {
            items.remove(item);
            item.setCart(null);
        }

        public double getTotal() {
            return items.stream().mapToDouble(CartItemEntity::getThanhTien).sum();
        }

        // Getters & Setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public List<CartItemEntity> getItems() {
            return items;
        }

        public void setItems(List<CartItemEntity> items) {
            this.items = items;
        }
    }
