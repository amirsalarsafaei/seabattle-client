package Models;

public class Response {
    public ResponseType responseType;
    public String data;
    public Response(ResponseType responseType, String data) {
        this.responseType = responseType;
        this.data = data;
    }
}
