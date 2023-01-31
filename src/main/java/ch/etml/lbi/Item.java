package ch.etml.lbi;

public class Item {
    private int num;
    private String description;
    private float prix;
    private Client client;

    public Item(int num, String description, float prix, Client client) {
        this.num = num;
        this.description = description;
        this.prix = prix;
        this.client = client;
    }

    public int getNum() {
        return num;
    }

    public String getDescription() {
        return description;
    }

    public float getPrix() {
        return prix;
    }

    public Client getClient() {
        return client;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
