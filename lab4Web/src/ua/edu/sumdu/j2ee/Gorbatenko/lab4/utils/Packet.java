package ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils;

public class Packet {
    
    private String title;
    private String name;
    private String value;
    private String link;
    
    public Packet(String title, String name, String value, String link) {
        this.title = title;
        this.name = name;
        this.value = value;
        this.setLink(link);
    }
    
    public Packet(String title, String name, String value) {
        this.title = title;
        this.name = name;
        this.value = value;
    }
    
    public Packet(String title, String name) {
        this.title = title;
        this.name = name;
    }
    
    public Packet(String title) {
        this.title = title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }
}
