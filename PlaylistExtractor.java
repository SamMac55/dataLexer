import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;

public class PlaylistExtractor extends JSONBaseVisitor<Void> {
    //keep track of the current playlist, song, artist, and album we are on as we traverse the tree
    Song currentTrack;
    Playlist currentPlaylist;
    Artist currentArtist;
    Album currentAlbum;
    
    //counters for the ids of the entities
    int artistIdCounter;
    int albumIdCounter;
    int songIdCounter;
    int existenceIdCounter;
    int accredationIdCounter;

    //limits for how many playlists and songs to parse, -1 means no limit
    int numPlaylistsToParse;
    int playlistsParsed=0;
    int playListSongLimit;
    int numSongsInCurrentPlaylist=0;

    //structures to ensure uniquness and hold extracted values
    Set<Playlist> playlists = new HashSet<>();
    Set<Song> songs = new HashSet<>();
    HashMap<String, Album> albums = new HashMap<>();
    HashMap<String, Artist> artists = new HashMap<>();
    Set<Existence> existences = new HashSet<>();
    Set<Accredation> accreditations = new HashSet<>();
    HashMap <String, Song> songsMap = new HashMap<>();

    public static void main(String[] args) throws Exception {

        //antlr4 setup to read from standard input and parse the JSON
        CharStream input = CharStreams.fromStream(System.in);
        JSONLexer lexer = new JSONLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);

        ParseTree tree = parser.json();
        
        //args should look like: 
        // java PlaylistExtractor <startingArtistId> <startingAlbumId> <startingSongId> <startingExistenceId> <startingAccredationId> <numPlaylistsToParse> <playListSongLimit>
        if(args.length != 7){
            System.out.println("Usage: java PlaylistExtractor <startingArtistId> <startingAlbumId> <startingSongId> <startingExistenceId> <startingAccredationId> <numPlaylistsToParse> <playListSongLimit>");
            return;
        }

        //creating the playlist extractor with the provided starts/limits
        PlaylistExtractor extractor = new PlaylistExtractor(
        Integer.parseInt(args[0]), // artistIdStart
        Integer.parseInt(args[1]), // albumIdStart
        Integer.parseInt(args[2]), // songIdStart
        Integer.parseInt(args[3]), // existenceIdStart
        Integer.parseInt(args[4]), // accredationIdStart
        Integer.parseInt(args[5]), // numPlaylistsToParse
        Integer.parseInt(args[6])  // playListSongLimit
        );

        //call visit to start extraction
        extractor.visit(tree);

        extractor.printResults();
    }

    @Override
    public Void visitPair(JSONParser.PairContext ctx) {

    if (numPlaylistsToParse != -1 && playlistsParsed >= numPlaylistsToParse) {
        return visitChildren(ctx);
    }

    String key = stripQuotes(ctx.STRING().getText());

    if (playListSongLimit != -1 &&
        numSongsInCurrentPlaylist >= playListSongLimit &&
        (key.equals("artist_name") ||
         key.equals("album_name") ||
         key.equals("track_name") ||
         key.equals("duration_ms"))) {
        return visitChildren(ctx);
    }

    if (key.equals("name")) {
        currentPlaylist = new Playlist();
        currentPlaylist.setName(getValue(ctx));
        numSongsInCurrentPlaylist = 0;
    }

    else if (key.equals("pid")) {
        currentPlaylist.setPid(getValue(ctx));
        playlists.add(currentPlaylist);
        playlistsParsed++;
    }

    else if (key.equals("artist_name")) {

        if (!artists.containsKey(getValue(ctx))) {
            currentArtist = new Artist();
            currentArtist.setName(getValue(ctx));
            currentArtist.setId(artistIdCounter++);
            artists.put(currentArtist.name, currentArtist);
        } else {
            currentArtist = artists.get(getValue(ctx));
        }
    }

    else if (key.equals("track_name")) {

        currentTrack = new Song();
        currentTrack.setTitle(getValue(ctx));
        currentTrack.setId(songIdCounter++);
    }

    else if (key.equals("duration_ms")) {

        if (playListSongLimit != -1 &&
            numSongsInCurrentPlaylist >= playListSongLimit) {
            return visitChildren(ctx);
        }

        currentTrack.setDuration(Double.parseDouble(getValue(ctx)) / 60000);
    }

    else if (key.equals("album_name")) {

        String albumKey = currentArtist.name + "||" + getValue(ctx);

        if (!albums.containsKey(albumKey)) {
            currentAlbum = new Album();
            currentAlbum.setName(getValue(ctx));
            currentAlbum.setArtistName(currentArtist.name);
            currentAlbum.setId(albumIdCounter++);
            currentAlbum.findArtistId(artists);
            albums.put(albumKey, currentAlbum);
        } else {
            currentAlbum = albums.get(albumKey);
        }

        currentTrack.setAlbum(currentAlbum.id);

        String songKey = currentArtist.name + "||"
                       + currentTrack.title + "||"
                       + currentAlbum.name;

        if (songsMap.containsKey(songKey)) {
            currentTrack = songsMap.get(songKey);
        } else {
            songsMap.put(songKey, currentTrack);
            songs.add(currentTrack);
        }

        numSongsInCurrentPlaylist++;

        existences.add(
            new Existence(existenceIdCounter++, currentPlaylist.pid, currentTrack.id)
        );

        accreditations.add(
            new Accredation(accredationIdCounter++, currentTrack.id, currentArtist.id)
        );
    }

    return visitChildren(ctx);
}

    //written out default constructor if you want counts to start at 0
    public PlaylistExtractor(){
        artistIdCounter = 0;
        albumIdCounter = 0;
        songIdCounter = 0;
        existenceIdCounter = 0;
        accredationIdCounter = 0;
        numPlaylistsToParse = -1;
        playListSongLimit = -1;
    }

    //constructor that allows you to set starting counts for the ids.
    public PlaylistExtractor(int artistIdCounter, int albumIdCounter, int songIdCounter, int existenceIdCounter, int accredationIdCounter, int numPlaylistsToParse, int playListSongLimit){ 
        this.artistIdCounter = artistIdCounter;
        this.albumIdCounter = albumIdCounter;
        this.songIdCounter = songIdCounter;
        this.existenceIdCounter = existenceIdCounter;
        this.accredationIdCounter = accredationIdCounter;
        this.numPlaylistsToParse = numPlaylistsToParse;
        this.playListSongLimit = playListSongLimit;
    }

    //helper method
    private String stripQuotes(String s) {
        return s.substring(1, s.length() - 1);
    }

    //helper method to get the value of a pair context without quotes
    private String getValue(JSONParser.PairContext ctx) {
        return ctx.value().getText().replace("\"", "");
    }

    private void printResults(){
        System.out.println("-- ARTISTS --");
        for ( Artist artist : artists.values()) {
            System.out.println(artist);
        }
        System.out.println("-- ALBUMS --");
        for (Album album : albums.values()) {
            System.out.println(album);
        }
        System.out.println("-- SONGS --");
        for (Song song : songs) {
            System.out.println(song);
        }
        System.out.println("-- PLAYLISTS --");
        for (Playlist playlist : playlists) {
            System.out.println(playlist);
        }
        System.out.println("-- EXISTENCES --");
        for (Existence existence : existences) {
            System.out.println(existence);
        }
        System.out.println("-- ACCREDITATIONS --");
        for (Accredation accredation : accreditations) {
            System.out.println(accredation);
        }
        System.out.println("-- SUMMARY --");
        System.out.println("Extracted a total of " + artists.size() + " artists, " 
        + albums.size() + " albums, " + songs.size() + " songs, " + playlists.size() 
        + " playlists, " + existences.size() + " existences, and " + accreditations.size() + " accreditations.");
    }
}


