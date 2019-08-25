package com.fachrinfl.movie.features.tv.model;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tv implements Parcelable
{

    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = new ArrayList<>();
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = new ArrayList<>();
    public final static Parcelable.Creator<Tv> CREATOR = new Creator<Tv>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Tv createFromParcel(Parcel in) {
            return new Tv(in);
        }

        public Tv[] newArray(int size) {
            return (new Tv[size]);
        }

    }
            ;

    protected Tv(Parcel in) {
        this.originalName = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
        this.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.genreIds, (java.lang.Integer.class.getClassLoader()));
        this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.originCountry, (java.lang.String.class.getClassLoader()));
    }

    public Tv() {
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(originalName);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(popularity);
        dest.writeValue(voteCount);
        dest.writeValue(voteAverage);
        dest.writeValue(firstAirDate);
        dest.writeValue(posterPath);
        dest.writeList(genreIds);
        dest.writeValue(originalLanguage);
        dest.writeValue(backdropPath);
        dest.writeValue(overview);
        dest.writeList(originCountry);
    }

    public int describeContents() {
        return 0;
    }

}
