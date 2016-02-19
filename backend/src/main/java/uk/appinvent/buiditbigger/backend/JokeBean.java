package uk.appinvent.buiditbigger.backend;

/** The object model for the data we are sending through endpoints */
public class JokeBean {

    private String joke;

    public String getData() {
        return joke;
    }

    public void setData(String joke) {
       this.joke = joke;
    }
}