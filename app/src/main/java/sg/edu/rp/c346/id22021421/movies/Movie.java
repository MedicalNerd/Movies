package sg.edu.rp.c346.id22021421.movies;



import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String name;
    private String genre;
    private int date;
    private String ratings;

    public Movie(int id,String name, String genre, int date,String ratings) {
        this.id=id;
        this.name = name;
        this.genre = genre;
        this.date = date;
        this.ratings = ratings;
    }


    public int getId(){return id;}
    public String getname() { return name; }

    public String getGenre() { return genre; }

    public int getDate() { return date;}
    public String getRatings() {



        return ratings; }
    public void setGenre(String genre) {
        this.genre=genre;
    }
    public void setName(String name) {
        this.name=name;
    }

    public void setDate(int date) {
        this.date=date;
    }
    public void setRatings(String ratings) {
        this.ratings=ratings;
    }



}
