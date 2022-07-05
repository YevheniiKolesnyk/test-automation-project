package api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Data
@Builder
@AllArgsConstructor
@JsonSerialize
public class IndexDto {

    private String controller;
    private Integer SubmitCreate;
    private Boolean ajax;
    private String email_create;
    private String back;
    private String token;

    public static IndexDto buildCustomer(String email, String token) {
        return builder()
                .controller("authentication")
                .SubmitCreate(1)
                .ajax(true)
                .email_create(email)
                .token(token)
                .build();
    }

}