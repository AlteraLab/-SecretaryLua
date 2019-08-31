package chatbot.api.textbox.domain.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeySetListDTO {

    private ArrayList<KeySet> keySet;
}
