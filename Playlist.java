public class Playlist {
    String name;
    String pid;

    public void setName(String name) {
        this.name = name;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
    public String toString() {
        return "INSERT INTO playlists (playlist_id,username, name,duration) VALUES ('" + pid + "', 'USERNAME', '" + name + "', 'DURATION');";
    }
}
