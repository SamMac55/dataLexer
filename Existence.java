import java.util.Objects;
public class Existence {
    String pid;
    int songId;
    int id;
    public Existence(int id, String pid, int songId) {
        this.id = id;
        this.pid = pid;
        this.songId = songId;
    }
    public void setSongId(int songId) {
        this.songId = songId;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "INSERT INTO existence (existence_id, song_id, playlist_id) VALUES (" + id + ", " + songId + ", " + pid + ");";
    }
    @Override
    public boolean equals(Object obj) {
        // Check for reference equality
        if (this == obj) return true;
        // Check for null or different class
        if (obj == null || getClass() != obj.getClass()) return false;
        // Cast obj to Person
        Existence e = (Existence) obj;
        // Compare fields for logical equality
        return songId == e.songId && Objects.equals(pid, e.pid);
    }

    @Override
    public int hashCode() {
        // Generate hash based on the same fields used in equals()
        return Objects.hash(songId, pid);
    }
}
