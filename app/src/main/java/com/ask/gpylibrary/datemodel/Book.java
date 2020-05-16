package com.ask.gpylibrary.datemodel;

public class Book {

    private String id;
    private String name;
    private String author;
    private String edition;
    private String publisher;
    private String place;
    private String publication_date_year;
    private String pages;
    private String volume;
    private String sources;
    private String category;
    private String cost;
    private String class_no;
    private String bill_no;
    private String bill_no_date;
    private String remark;
    private String description;
    private String thumbnail_link;

    public Book()
    {
        //default
    }

    public Book(String id, String name, String author, String edition, String publisher, String place, String publication_date_year, String pages, String volume, String sources, String category, String cost, String class_no, String bill_no, String bill_no_date, String remark, String description,String thumbnail_link)
    {
        this.id = id;
        this.name = name;
        this.author = author;
        this.edition = edition;
        this.publisher = publisher;
        this.place = place;
        this.publication_date_year = publication_date_year;
        this.pages = pages;
        this.volume = volume;
        this.sources = sources;
        this.category = category;
        this.cost = cost;
        this.class_no = class_no;
        this.bill_no = bill_no;
        this.bill_no_date = bill_no_date;
        this.remark = remark;
        this.description = description;
        this.thumbnail_link = thumbnail_link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPublication_date_year() {
        return publication_date_year;
    }

    public void setPublication_date_year(String publication_date_year) {
        this.publication_date_year = publication_date_year;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getClass_no() {
        return class_no;
    }

    public void setClass_no(String class_no) {
        this.class_no = class_no;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getBill_no_date() {
        return bill_no_date;
    }

    public void setBill_no_date(String bill_no_date) {
        this.bill_no_date = bill_no_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail_link() {
        return thumbnail_link;
    }

    public void setThumbnail_link(String thumbnail_link) {
        this.thumbnail_link = thumbnail_link;
    }
}
