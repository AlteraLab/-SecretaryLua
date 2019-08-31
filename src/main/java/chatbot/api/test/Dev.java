package chatbot.api.test;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dev {

    private String address;
    private String name;
    private String devType;

    public Dev(String address, String name) {
        this.address = address;
        this.name = name;
    }
}
