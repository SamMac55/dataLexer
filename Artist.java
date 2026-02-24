public class Artist {
    String name;
    int id;
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String toString() {
        return "INSERT INTO artists (artist_id, name, country) VALUES (" + id + ", '" + name + "', 'COUNTRY');";
    }    
}
