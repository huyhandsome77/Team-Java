package uth.edu.jpa.models;

import jakarta.persistence.*;

@Entity
@Table(name = "highlighted_students")
public class HighlightedStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int progress;

    private String title; // Tiêu đề đánh giá

    @Column(length = 1000)
    private String content; // Nội dung đánh giá

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player; // Liên kết đến người chơi

    // Getter và Setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

}