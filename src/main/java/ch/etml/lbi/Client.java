package ch.etml.lbi;

public class Client {
    private int num;
    private String prenom;
    private float solde;

    public Client(int num, String prenom, float solde) {
        this.num = num;
        this.prenom = prenom;
        this.solde = solde;
    }

    public int getNum() {
        return num;
    }

    public String getPrenom() {
        return prenom;
    }

    public float getSolde() {
        return solde;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }
}
