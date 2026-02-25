import java.util.Objects;

public class Song {
    String title;
    String album;
    String duration;
    int id;
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAlbum(String album) {
        this.album = album; 
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return "INSERT INTO songs (song_id, album, title, duration) VALUES (" + id + ", '" + album + "', '" + title + "', '" + duration + "');";
    }
    @Override
    public boolean equals(Object obj) {
        // Check for reference equality
        if (this == obj) return true;
        // Check for null or different class
        if (obj == null || getClass() != obj.getClass()) return false;
        // Cast obj to Person
        Song a = (Song) obj;
        // Compare fields for logical equality
        return Objects.equals(title, a.title) && Objects.equals(album, a.album) && Objects.equals(duration, a.duration);
    }

    @Override
    public int hashCode() {
        // Generate hash based on the same fields used in equals()
        return Objects.hash(title, album, duration);
    }
}
    