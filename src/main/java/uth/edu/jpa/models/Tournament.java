package uth.edu.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate startDate;

    // ✅ Thêm trường location để khớp với form HTML
    private String location;

    // Các trường thêm nếu sau này bạn mở rộng
    private LocalDate endDate;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "tournament_courts",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "court_id")
    )
    private Set<Court> courts = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tournament_players",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> participants = new HashSet<>();

    // ===== Getters and Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Court> getCourts() {
        return courts;
    }

    public void setCourts(Set<Court> courts) {
        this.courts = courts;
    }

    public Set<Player> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Player> participants) {
        this.participants = participants;
    }

    public void addCourt(Court court) {
        this.courts.add(court);
    }

    public void addParticipant(Player player) {
        this.participants.add(player);
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", location='" + location + '\'' +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                '}';
    }

    public boolean isValidTournament() {
        return startDate != null && endDate != null && !endDate.isBefore(startDate);
    }

    public boolean hasCourt(Court court) {
        return courts.contains(court);
    }

    public boolean hasParticipant(Player player) {
        return participants.contains(player);
    }
}
