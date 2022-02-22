package com.terabyte.library;

import java.util.Locale;

public class Book implements Comparable<Book>{
    private int id;
    private byte[] image;
    private String title, author, description;

    public Book(int id, String title, String author, String description, byte[] image) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public int compareTo(Book book) {
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','m','n','l','o','p','q','r','s','t','u','v','w','x','y','z'};
        int result =0;
        for(int i =0; i<Math.min(author.length(), book.getAuthor().length());i++) {
            int indexInAlphabet1 = getIndexOfCharInCharArray(alphabet, author.toLowerCase(Locale.ROOT).charAt(i));
            int indexInAlphabet2 = getIndexOfCharInCharArray(alphabet, book.getAuthor().toLowerCase(Locale.ROOT).charAt(i));
            if(indexInAlphabet1>indexInAlphabet2) {
                result = 1;
                break;
            }
            if(indexInAlphabet1<indexInAlphabet2) {
                result = -1;
                break;
            }
        }
        return result;
    }

    private int getIndexOfCharInCharArray(char[] array,char ch) {
        int result = 0;
        for (int i = 0; i<array.length;i++) {
            if(ch==array[i]) {
                result = i;
            }
        }
        return result;
    }
}
